package test;

import java.io.PrintWriter;
import logger.*;

public class Test {
	private static LogService logger = new LogServiceImplementation(new PrintWriter(System.err,true));

	private static void test() {
    	logger.start();
    	try {
    		for (int i=0;i<10;i++) {
    			logger.log("fatal error "+i+": please contact your system administator");
    		}
    		logger.stop();
    		for (int i=0;i<10;i++) {
    			logger.log("warning "+i+": system resource approaches exhaustion");
    		}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

    public static void main(String... arg) {
    	test();
    }
}
