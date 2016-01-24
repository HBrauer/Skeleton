package executor;

import java.util.*;
import java.util.concurrent.*;

public class TrackingExecutor  {
    private final ExecutorService exec;
   
    public TrackingExecutor(ExecutorService exec) {
    	this.exec =  exec;
    }
    
    public List<Runnable> getCancelledTasks() {
        // TODO
    }

    public void execute(final Runnable runnable) {
        // TODO
    }
	public boolean awaitTermination(long arg0, TimeUnit arg1)
		// TODO
	}
	public List<Runnable> shutdownNow() {
		// TODO
	}
}


