package processing;

import java.util.concurrent.*;

public final class Consumer implements Runnable {
    private BlockingQueue<Integer> queue;
    private final Thread thread;

    public Consumer(BlockingQueue<Integer> l, int poison, int howMuchPoison, int id) {
        queue = l;
        thread = new Thread(this, "Consumer-" + id);
        thread.start();
    }

    public void run() {
        for (;;) {
            Integer result;
            try {
                result = queue.take();
            } catch (InterruptedException e) {
                return;
            }
            System.out.println(Thread.currentThread().getName() + ": consumed "
                    + result);
        }
    }
}
