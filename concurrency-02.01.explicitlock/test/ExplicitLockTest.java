/*
  Based on course material for "Concurrent Programming Java", a seminar 
  prepared and owned by Angelika Langer & Klaus Kreft, November 2003
  contact: http://www.langer.camelot.de/ or mailto: langer@camelot.de

  © Copyright by Angelika Langer & Klaus Kreft.
  Permission to use, copy, and modify this software for any non-profit
  purpose is hereby granted to attendants of the above mentioned seminar 
  without fee, provided that the above copyright notice appears in all 
  copies.  Angelika Langer and Klaus Kreft make no representations about 
  the suitability of this software for any purpose.  It is provided 
  "as is" without express or implied warranty.
*/
package test;

import search.MyCollection;

public class ExplicitLockTest {
  
  public static void main(String[] args) {
  	MyCollection col = new MyCollection();
  	System.out.println(Thread.currentThread().getName()+": collection contains: "+col);
  	
  	TaskProvider helper = new TaskProvider(col);
	Runnable remover = helper.getRemover();	
	Runnable modifier = helper.getModifier();
	
	Thread modifierThread1 = new Thread(modifier);
	modifierThread1.setDaemon(false);
	modifierThread1.start();
	
	Thread removerThread = new Thread(remover);
	removerThread.setDaemon(false);
	removerThread.start();

	Thread modifierThread2 = new Thread(modifier);
	modifierThread2.setDaemon(false);
	modifierThread2.start();
	
	try { 
		modifierThread1.join();
		modifierThread2.join();
		removerThread.join();
		Thread.sleep(50); 
	} catch (InterruptedException e) {}
	System.out.println(Thread.currentThread().getName()+": collection contains: "+col);
  }
}
