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
//         ... to be done ...
    }
}
