package processing;

import java.util.List;

public final class Consumer implements Runnable {
	private List<Integer> queue;
	private Thread myThread;

	public Consumer(List<Integer> queue, int id) {
		this.queue = queue;
		myThread = new Thread(this, "Consumer-" + id);
		myThread.start();
	}

	public void run() {

		while (true) {
			Integer value = null;
			synchronized (queue) {
				if (queue.size() == 0) {
					try {
						queue.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					value = queue.remove(queue.size() - 1);
				}
			}
			if (value != null) {
				System.out.println(value);
			}
		}
	}
}
