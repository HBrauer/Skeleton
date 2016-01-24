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


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import pool.StripedThreadPoolExecutor;



public final class Test {
    private static final boolean verbose = false;
    
    private static void test(ExecutorService pool) throws InterruptedException {
        System.out.println();
        System.out.println("****************************************************************************");
        System.out.println("**                                                                        **");
        System.out.println("**      TESTING "+pool.getClass().getName());
        System.out.println("**                                                                        **");
        System.out.println("****************************************************************************");
        System.out.println();
        
        List<Object> submittedTasks = new ArrayList<>();
        
        List<Future<Integer>> futuresForSubmittedTasks = null;        
        futuresForSubmittedTasks = ExecutionTestHelper.testExecuteClientRunnable(submittedTasks, pool, verbose);      
        futuresForSubmittedTasks = ExecutionTestHelper.testSubmitClientRunnable(submittedTasks,pool, verbose);
        futuresForSubmittedTasks = ExecutionTestHelper.testSubmitClientCallable(submittedTasks,pool, verbose);
        
        /* Note that enabling the purge test will cancel a couple of tasks for testing purposes, 
         * which affects the subsequent order checking because some tasks will not have been executed.
         */
//        List<Future<Integer>> cancelledTasks = RemoveTestHelper.testPurge(pool, futuresForSubmittedTasks, verbose);
        
        futuresForSubmittedTasks = ExecutionTestHelper.testExecuteRegularRunnable(submittedTasks, pool, verbose);
        futuresForSubmittedTasks = ExecutionTestHelper.testSubmitRegularCallable(submittedTasks,pool, verbose);
        
        /* Note that enabling the shutDownNow test will shut down the pool immediately, 
         * which affects the subsequent order checking because many tasks will have been cancelled.
         */
//        List<Runnable> discardedPendingTasks = ShutdownTestHelper.testShutdownNow(pool, verbose);
        ShutdownTestHelper.testShutdown(pool, verbose);  
        
        boolean orderingOkay = new OrderChecker().check(submittedTasks, pool, verbose);
        if (!orderingOkay) {
            System.out.println();
            System.out.println("............................................................................");
            System.out.println("..      TESTING "+pool.getClass().getName()+" failed");
            System.out.println("............................................................................");
            System.out.println();
        }
    }


    public static void main(String[] args) throws InterruptedException {
    	
     // using a regular thread pool (in order to demonstrate that task can get ahead of each other)
    	test(new ThreadPoolExecutor(2, 2, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>()));
        

     // using the striped thread pool
    	test(new StripedThreadPoolExecutor(2, 2, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>()));
    }
}
