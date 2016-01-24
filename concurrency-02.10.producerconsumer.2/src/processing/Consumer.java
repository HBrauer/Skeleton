package processing;

import java.util.List;


public final class Consumer implements Runnable {
    private List<Integer> queue; 
    private Thread myThread;

    public Consumer(List<Integer> l,int id) {
        queue = l;
        myThread = new Thread(this,"Consumer-"+id);
        myThread.start();
    }
    public void run() {
        for(;;) synchronized(queue) {
        	while(queue.size() == 0 && !shuttingDown())
                try { queue.wait(); }
                catch(InterruptedException e) {
                    System.err.println(Thread.currentThread().getName()+": was interrupted - bye bye ...");
                    return; 
            }
            if (queue.size() == 0 && shuttingDown())  {
                System.err.println(Thread.currentThread().getName()+": job is done - bye bye ...");
                return;
            } 
            Integer result = (Integer)queue.remove(0);
            System.out.println(Thread.currentThread().getName()+": consumed "+result);
        }
    }
    private boolean shuttingDown() {
    	return myThread.isInterrupted();
    }
    public void shutDown()  {
    	System.out.println(Thread.currentThread().getName()+": shutdown request delivered");
    }
}
