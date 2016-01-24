package test;

import java.lang.management.ThreadInfo;
import java.util.Collection;

import monitor.*;
import application.*;

public final class Test { 
    public static void main(String[] args) {
        // start producer and consumer threads
        Controller controller = new Controller(3);
        controller.launch();
        
        
        Collection<ThreadInfo> threadInfos;
        
        // take a look at all live threads
        threadInfos = Monitor.getLiveThreads();
        Monitor.printThreadInfos(System.err, threadInfos);
        
        // look for deadlocks
        threadInfos = Monitor.getDeadLockedThreads();
        Monitor.printThreadInfos(System.err, threadInfos);
         
        // kill deadlocked threads
        controller.kill();
        
        // look for starved threads or new deadlocks
        threadInfos = Monitor.getBlockedOrWaitingThreads();
        Monitor.printThreadInfos(System.err, threadInfos);
        
        // kill starved or deadlocked threads
        controller.kill();
        
        // take a look at all surviving live threads
        threadInfos = Monitor.getLiveThreads();
        Monitor.printThreadInfos(System.err, threadInfos);
    }
}
