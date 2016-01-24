/*
  Based on course material for "Concurrent Programming Java", a seminar 
  prepared and owned by Angelika Langer & Klaus Kreft, November 2012
  contact: http://www.AngelikaLanger.com

  © Copyright 1995-2013 by Angelika Langer & Klaus Kreft.
  Permission to use, copy, and modify this software for any non-profit
  purpose is hereby granted to attendants of the above mentioned seminar 
  without fee, provided that the above copyright notice appears in all 
  copies.  Angelika Langer and Klaus Kreft make no representations about 
  the suitability of this software for any purpose.  It is provided 
  "as is" without express or implied warranty.
*/
package test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public final class ShutdownTestHelper {
    private static boolean verbose = false;
    private static final long SLEEP_TIME = 1000;

    public static void testShutdown(ExecutorService pool, boolean verb) throws InterruptedException {
        boolean tmp = verbose;
        verbose = verb;
        if (verbose) {
            System.out.println(pool);
            System.out.println("starting friendly shutdown ...");
        }
        pool.shutdown();
        
        if (verbose) {
            System.out.println(pool);
            if (pool.awaitTermination(5, TimeUnit.MINUTES))
                System.out.println("... friendly shutdown completed");
            if (! pool.isTerminated())
                System.out.println(">>>  This is bad !!!");
            System.out.println(pool);
        }
        verbose = tmp;
    }
    public static List<Runnable> testShutdownNow(ExecutorService pool, boolean verb) throws InterruptedException {
        boolean tmp = verbose;
        verbose = verb;
        if (verbose) {
            System.out.println(pool);
            System.out.print("starting abrupt shutdown ...   ");
        }
        Thread.sleep(SLEEP_TIME);
        List<Runnable> pendingTasks = pool.shutdownNow();
        
        if (verbose) {
            System.out.println(pool);
            System.out.println("pending tasks: "+pendingTasks);
            if (pool.awaitTermination(5, TimeUnit.MINUTES))
                System.out.println("... abrupt shutdown completed");
            if (! pool.isTerminated())
                System.out.println(">>>  This is bad !!!");
            System.out.println(pool);
        }
        verbose = tmp;
        return pendingTasks;
    }

}
