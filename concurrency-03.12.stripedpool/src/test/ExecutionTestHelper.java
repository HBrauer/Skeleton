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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


public class ExecutionTestHelper {
    private static boolean verbose = false;
    private static final long SLEEP_TIME = 10;
    private static final int HOW_MANY_TASKS = 32;
    
    
    private interface Submission {
        Future<Integer> submit(ExecutorService pool, List<Object> submittedTasks);
    }
    
    public static  List<Future<Integer>> testFrame(
            final ExecutorService pool, 
            final List<Object> submittedTasks, 
            Submission sub, 
            String desc, 
            boolean verb) throws InterruptedException {
        boolean tmp = verbose;
        verbose = verb;
        if (verbose) {
            System.out.println();
            System.out.println("============================================================================");
            System.out.println("==== TEST "+desc+" ===================================");
            System.out.println("============================================================================");
        }
        List<Future<Integer>> futuresForSubmittedTasks = new ArrayList<>();
        for (int i=0; i<HOW_MANY_TASKS; i++) {
            futuresForSubmittedTasks.add(sub.submit(pool,submittedTasks));
        }
        if (verbose) {
            System.out.println(pool);
        }
        Thread.sleep(SLEEP_TIME);
        verbose = tmp;
        return futuresForSubmittedTasks;
    }

    public static List<Future<Integer>> testExecuteClientRunnable(final List<Object> submittedTasks, final ExecutorService pool, final boolean verbose) throws InterruptedException {
      return testFrame(pool, submittedTasks,
        new Submission() {
            public Future<Integer> submit(ExecutorService pool, List<Object> submittedTasks) {
            	Runnable task = TaskGenerator.makeRunnableClientTask(verbose);            	
                pool.execute(task); 
                submittedTasks.add(task);
                return null;
            }
        }, "EXECUTION OF CLIENT RUNNABLES", verbose
      );
    }
    public static List<Future<Integer>> testSubmitClientRunnable(final List<Object> submittedTasks, final ExecutorService pool, final boolean verbose) throws InterruptedException {
        return testFrame(pool, submittedTasks, 
          new Submission() {
              public Future<Integer> submit(ExecutorService pool, List<Object> submittedTasks) {
            	  Runnable task = TaskGenerator.makeRunnableClientTask(verbose);
            	  Future<Integer> result = (Future<Integer>) pool.submit(task);
            	  submittedTasks.add(task);
                  return result;
              }
          },  "SUBMISSION OF CLIENT RUNNABLES", verbose
        );
    }
    public static List<Future<Integer>> testSubmitClientCallable(final List<Object> submittedTasks, ExecutorService pool, final boolean verbose) throws InterruptedException {
        return testFrame(pool, submittedTasks,
          new Submission() {
              public Future<Integer> submit(ExecutorService pool, List<Object> submittedTasks) {
            	  Callable<Integer> task = TaskGenerator.makeCallableClientTask(verbose);            	  
                  Future<Integer> result = pool.submit(task);
                  submittedTasks.add(task);
                  return result;
              }
          }, "SUBMISSION OF CLIENT CALLABLES", verbose
        );
    }
    public static List<Future<Integer>> testExecuteRegularRunnable(final List<Object> submittedTasks, final ExecutorService pool, final boolean verbose) throws InterruptedException {
        return testFrame(pool, submittedTasks,
          new Submission() {
              public Future<Integer> submit(ExecutorService pool, List<Object> submittedTasks) {
            	  Runnable task = TaskGenerator.makeRunnableTask(verbose);            	  
                  pool.execute(task); 
                  submittedTasks.add(task);
                  return null;
              }
          }, "EXECUTION OF REGULAR RUNNABLES", verbose
        );
    }
    public static List<Future<Integer>> testSubmitRegularCallable(final List<Object> submittedTasks, ExecutorService pool, final boolean verbose) throws InterruptedException {
        return testFrame(pool, submittedTasks,
          new Submission() {
              public Future<Integer> submit(ExecutorService pool, List<Object> submittedTasks) {
            	  Callable<Integer> task = TaskGenerator.makeCallableTask(verbose);            	  
                  Future<Integer> result = pool.submit(task); 
                  submittedTasks.add(task);
                  return result;
              }
          }, "EXECUTION OF REGULAR CALLABLES", verbose
        );
      }

}
