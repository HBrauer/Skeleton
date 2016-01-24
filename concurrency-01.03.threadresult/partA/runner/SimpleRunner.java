package runner;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class SimpleRunner {

    public SimpleRunner(final int cnt, final char c) {
        Runnable task = new Runnable() {
            public void run() {
            	System.out.print(Thread.currentThread().getName()+": ");
                for (int i = 0; i < cnt; i++)
                    System.out.print(c);
                System.out.println();
            }
        };

//        ... execute the task ...
        
        // using a plain thread
        
        // using a thread pool
        
        // using a fork-join-pool
        
        // using the common pool
        
        // using CompletableFuture
        
    }

    public static void main(String[] args) {
        new SimpleRunner(500,'*');
    }
}
