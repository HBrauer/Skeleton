package test;

import java.util.concurrent.*;
import executor.BoundedExecutor;

public final class Test {

	public static void main(String[] args) throws InterruptedException {
		
		BoundedExecutor boundedExecutor = new BoundedExecutor(5,5);
		try {
			for (int i=0;i<100;i++) {

				final int taskId = i;
				boundedExecutor.execute(new Runnable() {
					private final int id = taskId;
					public String toString() {
						return "task-"+id;
					}
					public void run() {
						System.out.println(Thread.currentThread().getName()+": executing "+this);
					}
				});

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			boundedExecutor.shutdown();
			boundedExecutor.awaitTermination(60, TimeUnit.SECONDS);
		}
	}

}
