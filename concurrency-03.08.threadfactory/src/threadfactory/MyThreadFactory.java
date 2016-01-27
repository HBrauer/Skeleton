package threadfactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThreadFactory implements ThreadFactory {
	private static AtomicInteger createdThread = new AtomicInteger();
	private static AtomicInteger activeThreads = new AtomicInteger();
	private String name;

	public MyThreadFactory(String name) {
		this.name = name;
	}

	public Thread newThread(Runnable r) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				activeThreads.incrementAndGet();
				r.run();
				activeThreads.decrementAndGet();
			}
		}, name + " " +  createdThread.get());
		thread.setUncaughtExceptionHandler((t, e) -> System.err.println(t.getName() + " " + e));
		createdThread.incrementAndGet();
		return thread;
	}

	public static int getThreadsCreated() {
		return createdThread.get();
	}

	public static int getThreadsActive() {
		return activeThreads.get();
	}
}
