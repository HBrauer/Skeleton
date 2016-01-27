package processing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public final class Consumer implements Runnable {
	private BlockingQueue<Integer> queue;
	private final Thread thread;

	public Consumer(BlockingQueue<Integer> l, int id) {
		queue = l;
		thread = new Thread(this, "Consumer-" + id);
		thread.start();
	}

	public void run() {

		while (true) {
			Integer result = null;
			try {
				result = queue.take();
			} catch (InterruptedException e) {
				List<Integer> values = new ArrayList<>();
				queue.drainTo(values);
				values.forEach(v -> System.err.println(Thread.currentThread().getName() + ": consumed " + v));
			}
			System.err.println(Thread.currentThread().getName() + ": consumed " + result);
		}
	}

	public void shutDown() {
		System.out.println(Thread.currentThread().getName() + ": shutdown request delivered");
		thread.interrupt();
	}

}
