package solver;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.naming.OperationNotSupportedException;

public class CustomThreadPoolExecutor extends ThreadPoolExecutor {

	public CustomThreadPoolExecutor(int arg0, int arg1, long arg2,
			TimeUnit arg3, BlockingQueue<Runnable> arg4) {
		super(arg0, arg1, arg2, arg3, arg4);
		// TODO Auto-generated constructor stub
	}

	public CustomThreadPoolExecutor(int arg0, int arg1, long arg2,
			TimeUnit arg3, BlockingQueue<Runnable> arg4, ThreadFactory arg5) {
		super(arg0, arg1, arg2, arg3, arg4, arg5);
		// TODO Auto-generated constructor stub
	}

	public CustomThreadPoolExecutor(int arg0, int arg1, long arg2,
			TimeUnit arg3, BlockingQueue<Runnable> arg4,
			RejectedExecutionHandler arg5) {
		super(arg0, arg1, arg2, arg3, arg4, arg5);
		// TODO Auto-generated constructor stub
	}

	public CustomThreadPoolExecutor(int arg0, int arg1, long arg2,
			TimeUnit arg3, BlockingQueue<Runnable> arg4, ThreadFactory arg5,
			RejectedExecutionHandler arg6) {
		super(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
		// TODO Auto-generated constructor stub
	}
	public void shutdown() {
		throw new UnsupportedOperationException("shame on you !!!  you are not supposed to shut down the pool !!!");
	}
	public void myShutdown() {
		super.shutdown();
	}
	public List<Runnable> shutdownNow() {
		throw new UnsupportedOperationException("shame on you !!!  you are not supposed to shut down the pool !!!");
	}
	public List<Runnable> myShutdownNow() {
		return super.shutdownNow();
	}

}
