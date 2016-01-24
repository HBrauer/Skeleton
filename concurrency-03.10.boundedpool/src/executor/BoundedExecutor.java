package executor;

import java.util.concurrent.*;

public class BoundedExecutor {
	private final ExecutorService exec;

	public BoundedExecutor(int numberOfThreads, int sizeOfQueue) {
		this.exec = new ThreadPoolExecutor(numberOfThreads,numberOfThreads, 
				0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(sizeOfQueue) );
	}

	public void execute(final Runnable command) throws InterruptedException {
		exec.execute(command);
	}

	public void shutdown() {
		exec.shutdown();
	}

	public void awaitTermination(int i, TimeUnit seconds)
			throws InterruptedException {
		exec.awaitTermination(i, seconds);
	}
}
