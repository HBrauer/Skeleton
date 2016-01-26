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
package stack;

public class IntStackImpl implements IntStack {

	private final int[] array;
	private volatile int cnt = 0;

	public IntStackImpl(int sz) {
		array = new int[sz];
	}

	synchronized public void push(int elm) {
		if (cnt < array.length)
			array[cnt++] = elm;
		else
			throw new IndexOutOfBoundsException();
	}

	synchronized public int pop() {
		if (cnt > 0)
			return (array[--cnt]);
		else
			throw new IndexOutOfBoundsException();
	}

	synchronized public int peek() {
		if (cnt > 0)
			return (array[cnt - 1]);
		else
			throw new IndexOutOfBoundsException();
	}

	public int size() {
		return cnt;
	}

	public int capacity() {
		return (array.length);
	}
}
