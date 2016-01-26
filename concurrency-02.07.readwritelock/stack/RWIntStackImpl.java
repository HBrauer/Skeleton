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

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * implementation with read-write-lock
 */
public class RWIntStackImpl implements IntStack {

	private final int[] array;
	private volatile int cnt = -1;

	private final Lock readLock;

	private final Lock writeLock;

	public RWIntStackImpl(int sz) {
		array = new int[sz];
		ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
		readLock = reentrantReadWriteLock.readLock();
		writeLock = reentrantReadWriteLock.writeLock();
	}

	@Override
	public void push(int elm) {
		writeLock.lock();

		try {
			array[++cnt] = elm;
		} finally {
			writeLock.unlock();
		}

	}

	@Override
	public int pop() {
		writeLock.lock();
		try {
			if (cnt >= 0) {
				return array[cnt--];
			} 
		} finally {
			writeLock.unlock();
		}
		
		throw new IndexOutOfBoundsException();
	}

	@Override
	public int peek() {
		readLock.lock();
		try {
			if (cnt >= 0) {
				return array[cnt];
			} 
		} finally {
			readLock.unlock();
		}
		
		throw new IndexOutOfBoundsException();
	}

	@Override
	public int size() {
		return cnt;
	}

	@Override
	public int capacity() {
		return array.length;
	}

}
