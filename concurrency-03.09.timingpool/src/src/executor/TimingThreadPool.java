package executor;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.logging.*;

public class TimingThreadPool extends ThreadPoolExecutor {
   
    public TimingThreadPool(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

    protected void beforeExecute(Thread t, Runnable r) {
        // TODO: record start time
    }

    protected void afterExecute(Runnable r, Throwable t) {
    	// TODO: record end time and print total execution time
    }

    protected void terminated() {
    	// TODO: print average execution time
    }
}