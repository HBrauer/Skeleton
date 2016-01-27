package test;

import java.util.concurrent.*;
import threadfactory.MyThreadFactory;

public class Test {
	private static final MyThreadFactory myThreadFactory = new MyThreadFactory("TestThread");
	private static final ExecutorService executor = Executors.newFixedThreadPool(10, myThreadFactory);

	public static void main(String... args) {
		for (int i = 0; i < 5; i++) {
			executor.execute(new Runnable() {
				public void run() {
					String threadName = Thread.currentThread().getName();
					System.out.println("Here I am ... " + threadName);
					System.out.println(threadName + ": created threads: " + myThreadFactory.getThreadsCreated());
					System.out.println(threadName + ": live threads: " + myThreadFactory.getThreadsActive());
					throw new IllegalStateException();
				}
			});
		}
		executor.shutdown();
	}

}
