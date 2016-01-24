package logger;

import java.io.*;
import java.util.concurrent.*;

public class LogServiceImplementation implements LogService {
    private final BlockingQueue<String> queue;
    private final LoggerThread logger;
    private static final int CAPACITY =3;

    public LogServiceImplementation(Writer writer) {
        this.queue = new LinkedBlockingQueue<String>(CAPACITY);
        this.logger = new LoggerThread(writer);
    }

    public void start() { logger.start(); }
    public void stop()  { 
    	System.out.println("stop request delivered");
    	logger.interrupt(); 
    }

    public void log(String msg) throws InterruptedException {
        queue.put(msg);
    }

    private class LoggerThread extends Thread {
        private final PrintWriter writer;
        public LoggerThread (Writer w) {
        	writer = new PrintWriter(w,true);
        }
        public void run() {
            try {
                while (true) {
                   writer.println(queue.take());
                }
            } catch(InterruptedException ignored) {
            } finally {
                writer.close();
            }
        }
    }
}
