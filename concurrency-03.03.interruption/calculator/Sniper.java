/*
  Based on course material for "Concurrent Programming Java", a seminar 
  prepared and owned by Angelika Langer & Klaus Kreft, August 2003
  contact: http://www.langer.camelot.de/ or mailto: langer@camelot.de

  © Copyright by Angelika Langer & Klaus Kreft.
  Permission to use, copy, and modify this software for any non-profit
  purpose is hereby granted to attendants of the above mentioned seminar 
  without fee, provided that the above copyright notice appears in all 
  copies.  Angelika Langer and Klaus Kreft make no representations about 
  the suitability of this software for any purpose.  It is provided 
  "as is" without express or implied warranty.
*/
package calculator;
import java.util.*;
import java.util.concurrent.*;

public class Sniper  implements Runnable {

		private Thread thread = null;
		private Future task = null;
		private int frequency = 1;
		private boolean useStop = false;
		
        public Sniper(int freq, Thread th, boolean stop) { frequency = freq; thread = th; useStop = stop; }
        public Sniper(int freq, Future c) { frequency = freq; task = c; }
        public Sniper(int freq, Thread th, Future c) { frequency = freq; thread = th; task = c; }
		
		public void run() {
			Random generator = new Random();
			int trigger = Math.abs(generator.nextInt()%frequency);

			try { Thread.sleep (trigger); }
			catch (InterruptedException e) {  }
			if (thread!=null && task!=null) {
				switch (trigger%3) 
				{
				case 0: {
					System.out.println(Thread.currentThread().getName()+": interrupt sent");
					thread.interrupt();
					break;
			 	  }
			 	case 1: {
					System.out.println(Thread.currentThread().getName()+": stop sent");
					thread.stop();
					break;
			 	  }
			 	case 2: {
					System.out.println(Thread.currentThread().getName()+": cancellation sent");
					task.cancel(true);
					break;
			 	  }
				}
			}
			else {
				if (thread!=null) {
					if (useStop) {
						switch (trigger%2) {
							case 0: {
								System.out.println(Thread.currentThread().getName()+": interrupt sent");
								thread.interrupt();
								break;
							}
							case 1: {
								System.out.println(Thread.currentThread().getName()+": stop sent");
								thread.stop();
								break;
							}
						}
					}
					else {
						System.out.println(Thread.currentThread().getName()+": interrupt sent");
						thread.interrupt();
					}
				}
				if (task!=null) {
					System.out.println(Thread.currentThread().getName()+": cancellation sent");
					task.cancel(true);
				}
			}
		}
}
