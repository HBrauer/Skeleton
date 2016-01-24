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

public class IntStackUser {
	private IntStack stack;

	public IntStackUser(IntStack stack) {
		this.stack = stack;
	}

	public Runnable getConsumer() {
		return new Runnable() {
			public void run() {
				for (int i = 0; i < 10; i++) {
					try {Thread.sleep(10);} catch (InterruptedException e1) {}
					int res;
					try {
						res = stack.pop();
						System.out.println(Thread.currentThread().getName()
								+ " popped: " + res);
					} catch (IndexOutOfBoundsException e) {
						System.out.println(Thread.currentThread().getName()
								+ " popped: " + ">>> nothing");
					}
					try {
						res = stack.peek();
						System.out.println(Thread.currentThread().getName()
								+ " peek  : " + res + " size: " + stack.size());
					} catch (IndexOutOfBoundsException e) {
						System.out.println(Thread.currentThread().getName()
								+ " peek  : " + "-" + " size: " + stack.size());
					}
				}
			}
		};
	}

	public Runnable getProducer() {
		return new Runnable() {
			public void run() {
				for (int i = 0; i < 10; i++) {
					try {Thread.sleep(10);} catch (InterruptedException e1) {}
					try {
						stack.push(i);
						System.out.println(Thread.currentThread().getName()
								+ " pushed: " + i);
					} catch (IndexOutOfBoundsException e) {
						System.out.println(Thread.currentThread().getName()
								+ " pushed: " + ">>> nothing");
					}
					try {
						int res = stack.peek();
						System.out.println(Thread.currentThread().getName()
								+ " peek  : " + res + " size: " + stack.size());
					} catch (IndexOutOfBoundsException e) {
						System.out.println(Thread.currentThread().getName()
								+ " peek  : " + "-" + " size: " + stack.size());
					}
				}
			}
		};
	}

}
