package processing;

import java.util.concurrent.*;

public final class Producer implements Runnable {
    private BlockingQueue<Integer> queue;
    private CountDownLatch done;
    private int howMany;
    private int threadId;
    private Thread myThread;
    
    public Producer(BlockingQueue<Integer> l, int howMany, CountDownLatch d, int threadId) {
        queue = l;
        done  = d;
        this.howMany = howMany;
        this.threadId = threadId;
        myThread = new Thread(this,"Producer-"+threadId);
        myThread.start();
    }
    public void run() {
        for (int i=0;i<howMany;i++) {
            try {
                queue.put(new Integer(i*threadId));
            } catch (InterruptedException e) {
                return;
            }
            System.out.println(Thread.currentThread().getName()+": produced "+i*threadId);
        }
        done.countDown();
    }
}
