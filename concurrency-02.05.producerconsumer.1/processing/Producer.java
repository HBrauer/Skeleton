package processing;

import java.util.List;

public final class Producer implements Runnable {
	private List<Integer> queue;
	private int howMany;
	private Thread myThread;

	public Producer(List<Integer> queue, int howMany, int threadId) {
		this.queue = queue;
		this.howMany = howMany;
		myThread = new Thread(this, "Producer-" + threadId);
		myThread.start();
	}

	public void run() {
		for(int i = 0; i < howMany; ++i){
			synchronized (queue) {
				queue.add(i);
				queue.notifyAll();
			}
		}
	}
}
