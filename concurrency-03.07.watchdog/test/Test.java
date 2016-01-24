/*
  Based on course material for "Concurrent Java", a seminar 
  prepared and owned by Angelika Langer & Klaus Kreft 
  contact: http://www.AngelikaLanger.com or mailto: contact@AngelikaLanger.com

  © Copyright June 2009 by Angelika Langer & Klaus Kreft.
  Permission to use, copy, and modify this software for any non-profit
  purpose is hereby granted to attendants of the above mentioned seminar 
  without fee, provided that the above copyright notice appears in all 
  copies.  Angelika Langer and Klaus Kreft make no representations about 
  the suitability of this software for any purpose.  It is provided 
  "as is" without express or implied warranty.
*/
package test;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import scheduling.ScheduledWatchDog;
import scheduling.ServerThread;


public final class Test {
	public static void main(String[] args) {
        final int SIZE = 10;
        ScheduledThreadPoolExecutor timer = new ScheduledThreadPoolExecutor(SIZE/2);
        
        ServerThread[] servers = new ServerThread[SIZE];
        for (int i=0;i<SIZE;i++) {
            servers[i] = new ServerThread(i);
            
            // start watchdog per server thread
            ... to be done ...
        }
	}
}
