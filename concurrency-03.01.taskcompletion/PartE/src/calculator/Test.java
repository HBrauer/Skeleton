/*
  Based on course material for "Concurrent Java", a seminar 
  prepared and owned by Angelika Langer & Klaus Kreft, February 2007
  contact: http://www.AngelikaLanger.com 

  © Copyright 1995 - 2010 by Angelika Langer & Klaus Kreft.
  Permission to use, copy, and modify this software for any non-profit
  purpose is hereby granted to attendants of the above mentioned seminar 
  without fee, provided that the above copyright notice appears in all 
  copies.  Angelika Langer and Klaus Kreft make no representations about 
  the suitability of this software for any purpose.  It is provided 
  "as is" without express or implied warranty.
*/
package calculator;

public class Test {

	public static void main(String[] args) {
		final EventProducer eventProducer = new EventProducer();
		final EventConsumer eventConsumer = new EventConsumer();
		Thread producerThread = new Thread(eventProducer, "event producer");
		producerThread.start();
	}
}
