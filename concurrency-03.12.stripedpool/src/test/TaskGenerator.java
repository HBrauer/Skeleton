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


import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import pool.util.ClientCallable;
import pool.util.ClientRunnable;
import pool.util.HasTaskId;
import pool.util.HasTerminationTimeStamp;

/*
 * The TaskGenerator creates task that carry a client id and a task id.
 * For testing purposes each client task enqueues itself into a queue
 * after it has been executed.  The tasks will appear in this queue 
 * in the order in which they terminated.
 * 
 * This queue will later be used by the OrderChecker to check whether
 * all tasks terminated in the intended order.
 */
public final class TaskGenerator {
    private static final long SLEEP_TIME = 1;
    
    private static final ThreadLocalRandom clientIdGenerator = ThreadLocalRandom.current();
    private static final AtomicInteger taskIdGenerator = new AtomicInteger();
    

    public static Runnable makeRunnableClientTask(final boolean verbose) {
    	class StampedClientRunnable extends HasTerminationTimeStamp implements ClientRunnable {
            private final int clientId = clientIdGenerator.nextInt(1,5);
            private final int taskId = taskIdGenerator.getAndIncrement();
            public void run() {
                if (verbose) { 
                    for (int i=0;i<clientId;i++) 
                        System.out.print("   .");
                    System.out.println(taskId);
                }
                
                try {
                    Thread.sleep(SLEEP_TIME*(50-clientId));
                } catch (InterruptedException e) { }
                takeTerminationTimeStamp();
            }
            public int getClientId() {
               return clientId;
            }
            public int getTaskId() {
            	return taskId;
            }
            public String toString() {
                return "("+clientId+"/"+taskId+"/"+getTerminationTimeStamp()+")";
            }
    	}
        return new StampedClientRunnable();
    }
    public static Callable<Integer> makeCallableClientTask(final boolean verbose) {  
    	class StampedClientCallable extends HasTerminationTimeStamp implements ClientCallable<Integer> {
            private final int clientId = clientIdGenerator.nextInt(1,5);
            private final int taskId = taskIdGenerator.getAndIncrement();
            public Integer call() throws Exception {
                if (verbose) { 
                    for (int i=0;i<clientId;i++)
                        System.out.print("   .");
                    System.out.println(taskId);
                }
                
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) { }
                takeTerminationTimeStamp();
                return taskId;
            }
            public int getClientId() {
               return clientId;
            }
            public int getTaskId() {
            	return taskId;
            }
            public String toString() {
            	return "("+clientId+"/"+taskId+"/"+getTerminationTimeStamp()+")";
            }
    	}
        return new StampedClientCallable();
    }
    
    public static Runnable makeRunnableTask(final boolean verbose) {
    	class StampedRunnableWithTaskId extends HasTerminationTimeStamp implements Runnable, HasTaskId {
            private final int taskId = taskIdGenerator.getAndIncrement();
            public void run() {
                if (verbose) { 
                    System.out.println(taskId);
                }
                
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) { }
                takeTerminationTimeStamp();
            }
            public int getTaskId() {
            	return taskId;
            }
            public String toString() {
                return "("+"XXX"+"/"+taskId+"/"+getTerminationTimeStamp()+")";
            }
    	}
        return new StampedRunnableWithTaskId();
    }
    
    public static  Callable<Integer> makeCallableTask(final boolean verbose) {
    	class StampedCallableWithTaskId extends HasTerminationTimeStamp implements Callable<Integer>, HasTaskId {
            private final int taskId = taskIdGenerator.getAndIncrement();
            public Integer call() throws Exception {
                if (verbose) { 
                    System.out.println(taskId);
                }
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) { }
                takeTerminationTimeStamp();
                return taskId;
            }
            public int getTaskId() {
            	return taskId;
            }
            public String toString() {
                return "("+"XXX"+"/"+taskId+"/"+getTerminationTimeStamp()+")";
            }
    	}
        return new StampedCallableWithTaskId();
    }

}
