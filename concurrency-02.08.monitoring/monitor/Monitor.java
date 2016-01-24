package monitor;

import java.io.PrintStream;
import java.lang.management.*;
import java.util.*;

public final class Monitor {

    public static void printThreadInfos(PrintStream stream, 
                                          Collection<ThreadInfo> threadInfo) {
        if (stream==null || threadInfo==null) return;
        for (ThreadInfo ti : threadInfo) 
            printThreadInfo(stream, ti);
    }
    private static void printThreadInfo(PrintStream s, ThreadInfo ti) {
         .. to be done ... 
    }
    ////////////////////////////////////////////////////////////////////////////

    public static Collection<ThreadInfo> getLiveThreads() {
         .. to be done ...  
        return null;
    }
    ////////////////////////////////////////////////////////////////////////////
    
    public static Collection<ThreadInfo> getDeadLockedThreads() {
         .. to be done ...  
        return null;
    }
    ////////////////////////////////////////////////////////////////////////////

    public static Collection<ThreadInfo> getBlockedOrWaitingThreads() {
         .. to be done ...  
        return null;
    }
}
