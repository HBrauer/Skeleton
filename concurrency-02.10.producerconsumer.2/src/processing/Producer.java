package processing;

import java.util.List;


public final class Producer implements Runnable {
    private List<Integer> queue;
    private int howMany;
    private int threadId;
    private Thread myThread;
    
    public Producer(List<Integer> l, int howMany, int threadId) {
        queue = l;
        this.howMany = howMany;
        this.threadId = threadId;
        myThread = new Thread(this,"Producer-"+threadId);
        myThread.start();
    }
    public void run() {
        for (int i=0;i<howMany;i++) synchronized(queue) {
            queue.add(new Integer(i*threadId));
            System.out.println(Thread.currentThread().getName()+": produced "+i*threadId);
            queue.notifyAll();
        }
        System.out.println(Thread.currentThread().getName()+": production finished");
    }
}
