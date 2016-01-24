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
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public final class RemoveTestHelper {
    private static boolean verbose = false;

    public static List<Future<Integer>> testPurge(ExecutorService pool, List<Future<Integer>> futures, boolean verb) {
        boolean tmp = verbose;
        verbose = verb;
        if (pool instanceof ThreadPoolExecutor) {
            if (verbose) {
                System.out.println();
                System.out.println("============================================================================");
                System.out.println("==== TEST PURGE ============================================================");
                System.out.println("============================================================================");
            }
            List<Future<Integer>> cancelledTasks = new ArrayList<>();
            ThreadPoolExecutor p = (ThreadPoolExecutor)pool;
            Queue<Runnable> q = p.getQueue();
            for (int i=0; i<futures.size(); i++) {
                if (i%2 == 0) {
                    futures.get(i).cancel(true);
                    cancelledTasks.add(futures.get(i));
                    if (verbose)
                        System.out.println("cancelled "+  futures.get(i));
                }
            }
            p.purge();
            q = p.getQueue();
            return cancelledTasks;
        }
        verbose = tmp;
        return null;
    }

}
