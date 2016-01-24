package stack;
import java.util.concurrent.locks.*;

public class BlockingIntStack {

  private Object fullCon = new int[1];
  private Object emptyCon = new int[1];
 
  private final int[] array;
  private int cnt = 0;
  
  public BlockingIntStack(int sz) { array = new int[sz]; }

  public void push(int element) {
	  synchronized(fullCon) {
	      while (cnt == array.length) { 
	        try { fullCon.wait(); }
	        catch (InterruptedException e) { }
	      }
	      synchronized(emptyCon) {    // <<< nested synchronized block
	        array[cnt++] = element;
	        emptyCon.notify();
	      }
	  }
  }

  public int pop() { 
	synchronized(emptyCon) { 
	      while (cnt == 0) {
	        try { emptyCon.wait(); }
	        catch (InterruptedException e) { }
	      }
	      synchronized(fullCon) {    // <<< nested synchronized block
	        int tmp = array[--cnt];
	        fullCon.notify();
	        return (tmp);
	      }
	}
  }

	
	public static void main(String[] args) {
		final BlockingIntStack stack = new BlockingIntStack(5);
		Runnable consumer = new Runnable() {
			public void run() {
				for (int i=0; i<10; i++) {
					int res = stack.pop();
					System.out.println("read: "+res);
				}
			}
		};
		
		Runnable producer = new Runnable() {
			public void run() {
				for (int i=0; i<10; i++) {
					System.out.println("writing: "+i);
					stack.push(i);
				}
			}
		};
		
		Thread th1 = new Thread(consumer);
		th1.setDaemon(true);
		th1.start();
		
		try { Thread.sleep(5000); } catch (Exception e) {}
		
		Thread th2 = new Thread(producer);
		th2.setDaemon(true);
		th2.start();
		
		try { Thread.sleep(5000); } catch (Exception e) {}
	}
}
