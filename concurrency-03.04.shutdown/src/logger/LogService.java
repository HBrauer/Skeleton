package logger;

public interface LogService {
	public void start();
	public void stop() throws InterruptedException ;
	public void log(String msg) throws InterruptedException;
}
