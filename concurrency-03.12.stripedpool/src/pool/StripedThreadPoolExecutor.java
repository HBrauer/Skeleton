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
package pool;

import static pool.util.Utilities.isClientCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import pool.util.ClientFutureTask;

public class StripedThreadPoolExecutor extends ThreadPoolExecutor {
    private Map<Integer,Queue<Runnable>> clientMap = new HashMap<>();

    public StripedThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
            long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }
    
    
    
    
    /*
     * These methods are overridden for test purposes.  They make sure that tasks that are
     * submitted to the pool via submit() (rather than execute()) still contain 
     * client id / task id / time stamp.  Remember, submit() wraps tasks into FutureTasks,
     * which do not have the content we need for testing and checking.  Via newTaskFor() 
     * we replace the default FutureTask wrapper by a ClientFutureTask instead.
     */
    
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        if (isClientCommand(runnable)) 
            return new ClientFutureTask<T>(runnable, value);
        else
            return new FutureTask<T>(runnable, value);
    }
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        if (isClientCommand(callable)) 
            return new ClientFutureTask<T>(callable);
        else
            return new FutureTask<T>(callable);
    }
   

}
