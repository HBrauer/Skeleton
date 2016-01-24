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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import pool.util.HasClientId;
import pool.util.HasTaskId;
import pool.util.HasTerminationTimeStamp;

public final class OrderChecker {
    private static boolean verbose = true;
    private final Map<Integer,List<TaskContent>> clientTasks = new HashMap<>(); 
    private final List<TaskContent> regularTasks = new ArrayList<>();
    
    private void splitResults(List<Object> submittedTasks) {          
        for (Object submittedTask : submittedTasks) {
            if (submittedTask instanceof HasClientId) {
            	HasClientId task = (HasClientId)submittedTask;
                Integer clientId = task.getClientId();
                if (! clientTasks.containsKey(clientId)) {
                    clientTasks.put(clientId, new ArrayList<TaskContent>());
                }    
                if (task instanceof HasTaskId && task instanceof HasTerminationTimeStamp) {
                	TaskContent content = new TaskContent(((HasTaskId)task).getTaskId(),((HasTerminationTimeStamp)task).getTerminationTimeStamp());
            		clientTasks.get(clientId).add(content);
                }
            } else {
                if (submittedTask instanceof HasTaskId && submittedTask instanceof HasTerminationTimeStamp) {
                	TaskContent content = new TaskContent(((HasTaskId)submittedTask).getTaskId(),((HasTerminationTimeStamp)submittedTask).getTerminationTimeStamp());
                	regularTasks.add(content);
                }
            }
        }
    }
    private boolean checkListForSortingOrder (Integer clientId, List<TaskContent> list) {
        List<TaskContent> copy = new ArrayList<>(list);
        Collections.sort(copy);
        boolean okay = copy.equals(list);
        if (verbose) {
            if (clientId==null) {
                System.out.println("regular tasks are "+(okay?"in order":"not in order (which is okay for regular tasks)"));
                System.out.println("regular tasks  : "+list);
            } else {                
                System.out.println("client #"+clientId+" tasks are "+(okay?"in order":"NOT in order"));
                System.out.println("client #"+clientId+" tasks: "+list);
            }
            System.out.println("sorted copy    : "+copy);
        }
        return okay;
    }
    private boolean checkResults() {
        boolean okay = true;
        for (Integer clientId : clientTasks.keySet()) {
            List<TaskContent> list = clientTasks.get(clientId);
            boolean tmp = checkListForSortingOrder(clientId,list); 
            okay = okay && tmp;
        }
        boolean tmp = checkListForSortingOrder(null,regularTasks); 
        okay = okay && tmp;
        return okay;
    }
    public boolean check(List<Object> submittedTasks, ExecutorService pool, boolean verb) throws InterruptedException {
        boolean tmp = verbose;
        verbose = verb;
        pool.awaitTermination(1, TimeUnit.HOURS);
        
        if (verbose) {
            System.out.println();
            System.out.println("============================================================================");
            System.out.println("==== CHECK RESULTS FOR CORRECT ORDER =======================================");
            System.out.println("============================================================================");
        }
        splitResults(submittedTasks);
        verbose = tmp;
        return checkResults();
    }

}
