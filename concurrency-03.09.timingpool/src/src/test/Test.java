package test;

import java.util.concurrent.*;
import executor.TimingThreadPool;

public final class Test {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService regularExecutor 
	    = new TimingThreadPool(5,5,60,TimeUnit.SECONDS,
	    		                 new LinkedBlockingQueue<Runnable>());
	try {
		for (int i=0;i<10;i++) {

			final int taskId = i;
			regularExecutor.submit(new Runnable() {
				private final int id = taskId;
				public String toString() {
					return "task-"+id;
				}
				public void run() {
					System.err.println(Thread.currentThread().getName()+": executing "+this);
				}
			});

		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		regularExecutor.shutdown();
		regularExecutor.awaitTermination(60, TimeUnit.SECONDS);
	}

	}
	

}
