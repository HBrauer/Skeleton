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
import java.util.concurrent.locks.StampedLock;

/*
 * implementation with read-write-lock
 */
public class SLIntStackImpl implements IntStack {

	private final int[] array;
	private volatile int cnt = -1;

	private StampedLock lock = new StampedLock();

	public SLIntStackImpl(int sz) {
		array = new int[sz];
	}

	@Override
	public void push(int elm) {
		long stamp = lock.writeLock();
		try {
			array[++cnt] = elm;
		} finally {
			lock.unlock(stamp);
		}

	}

	@Override
	public int pop() {
		long stamp = lock.writeLock();
		try {
			if (cnt >= 0) {
				return array[cnt--];
			}
		} finally {
			lock.unlock(stamp);
		}

		throw new IndexOutOfBoundsException();
	}

	@Override
	public int peek() {

		long stamp;
		do {
			stamp = lock.tryOptimisticRead();
			if (cnt >= 0) {
				return array[cnt];
			} else {
				throw new IndexOutOfBoundsException();
			}
		} while (lock.validate(stamp));

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
