package runner;

import java.util.concurrent.*;

public final class SimpleRunner {
	private static long doTask(final int cnt, final char c) {
    	long start = System.currentTimeMillis();
    	System.out.print(Thread.currentThread().getName()+": ");
        for (int i = 0; i < cnt; i++)
            System.out.print(c);
        System.out.println();
        long duration = System.currentTimeMillis() - start;
        return duration;
	}
	public SimpleRunner(final int cnt, final char c) { 
    		
    	}
    	// TODO
        ... prepare the method above for asynchronous execution ...
        ... e.g. above wrap it into a Callable ...

        // TODO
        ... execute the task and retrieve the result ...
        
        long result = 0;
        
        // using a plain thread
        
        result = ...;
        System.out.println("It took "+result+" msec.");
        
        // using a thread pool
        
        result = ...;
        System.out.println("It took "+result+" msec.");
        
        // using a fork-join-pool
        
        result = ...;
        System.out.println("It took "+result+" msec.");
        
        // using CompletableFuture
        
        result = ...;
        System.out.println("It took "+result+" msec.");
    }

    public static void main(String[] args) {
        new SimpleRunner(5,'~');
    }
}
