package pool.util;

import java.util.concurrent.TimeUnit;

public abstract class HasTerminationTimeStamp {
	private static long baseLine = System.currentTimeMillis();
	static {
		try {TimeUnit.MILLISECONDS.sleep(1);} catch (InterruptedException e) {}
	}
	private volatile long terminationTimeStamp = 0L;
	
	public void takeTerminationTimeStamp() {
		terminationTimeStamp = System.currentTimeMillis()-baseLine;
	}
	public long getTerminationTimeStamp() {
		return terminationTimeStamp;
	}
}
