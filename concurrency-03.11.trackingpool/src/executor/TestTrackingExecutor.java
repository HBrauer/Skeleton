package executor;


import java.util.List;
import java.util.concurrent.*;


public final class TestTrackingExecutor {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService regularExecutor 
		= new ThreadPoolExecutor(5,5,60,TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
		TrackingExecutor trackingExecutor 
			= new TrackingExecutor(regularExecutor);
		try {
			for (int i=0;i<100;i++) {

				final int taskId = i;
				trackingExecutor.execute(new Runnable() {
					private final int id = taskId;
					public String toString() {
						return "task-"+id;
					}
					public void run() {
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName()+": executing "+this);
					}
				});

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			List<Runnable> discardedTasks = trackingExecutor.shutdownNow();
			if (trackingExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
				List<Runnable> cancelledTasks = trackingExecutor.getCancelledTasks();
				System.out.println("discarded tasks: "+discardedTasks);
				System.out.println("cancelled tasks: "+cancelledTasks);
			}
		}
	}

}
