/*
  Based on course material for "Concurrent Java", a seminar prepared
  and owned by Angelika Langer & Klaus Kreft.
  contact: http://www.AngelikaLanger.com/ or mailto: info@AngelikaLanger.com

  © Copyright 1995-2014 by Angelika Langer & Klaus Kreft. All rights reserved.

  Permission to use, copy, and modify this software for any non-profit
  purpose is hereby granted to attendants of the above mentioned seminar
  without fee, provided that the above copyright notice appears in all
  copies.  Angelika Langer and Klaus Kreft make no representations about
  the suitability of this software for any purpose.  It is provided
  "as is" without express or implied warranty.
*/
package test;

import stack.IntStack;
import stack.IntStackImpl;
import stack.RWIntStackImpl;
import stack.SLIntStackImpl;

public class Test {
	private static void test(IntStack stack) {
		IntStackUser user = new IntStackUser(stack);
		Runnable consumer = user.getConsumer();	
		Runnable producer = user.getProducer();
		
		Thread producerThread = new Thread(producer);
		producerThread.setDaemon(true);
		producerThread.start();
		
		Thread consumerThread = new Thread(consumer);
		consumerThread.setDaemon(true);
		consumerThread.start();
		
		try { Thread.sleep(500); } catch (InterruptedException e) {}
	}

  public static void main(String[] args) {
	test(new IntStackImpl(5));
	test(new RWIntStackImpl(5));
	test(new SLIntStackImpl(5));
  }
}
