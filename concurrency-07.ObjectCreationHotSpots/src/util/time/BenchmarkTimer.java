/*
 * Created on 25.10.2006
 *
 */
package util.time;

import java.lang.management.*;

public final class BenchmarkTimer {
    private static long elapsedTimeBegin;
    private static long cpuTimeBegin;
    private static long elapsedTimeEnd;
    private static long cpuTimeEnd;
    
    private static ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();

    public  static void takeStartTime() {
//        System.out.println("CPU time supported: "+threadBean.isCurrentThreadCpuTimeSupported());
//        System.out.println("CPU time enabled  : "+threadBean.isThreadCpuTimeEnabled());

        if (  !threadBean.isCurrentThreadCpuTimeSupported()
           || !threadBean.isThreadCpuTimeEnabled()
            )
            System.err.println("cannot take CPU time");
        
        elapsedTimeBegin = System.currentTimeMillis();
        cpuTimeBegin = threadBean.getCurrentThreadCpuTime() ;         
    }
    public static void takeEndTime() {
        elapsedTimeEnd = System.currentTimeMillis();
        cpuTimeEnd = threadBean.getCurrentThreadCpuTime() ;         
    }
    public static void printDiffTime() {
        System.out.println("elapsed time: " + (elapsedTimeEnd-elapsedTimeBegin) + " msec");        
        System.out.println("CPU     time: " + ((cpuTimeEnd-cpuTimeBegin)/1000000L) + " msec"); 
    }
}
