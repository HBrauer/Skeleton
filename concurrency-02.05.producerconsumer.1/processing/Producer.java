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
//         ... to be done ...
    }
}
