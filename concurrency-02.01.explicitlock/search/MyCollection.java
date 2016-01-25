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
package search;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import test.FakeModifier;

public class MyCollection {
	private Collection<ElementType> data = CollectionProvider.makeCollection();

	private Lock lock = new ReentrantLock();

	public int size() {
		lock.lock();
		try {
			return data.size();
		} finally {
			lock.unlock();
		}
	}

	public String toString() {
		lock.lock();
		try {
			return data.toString();
		} finally {
			lock.unlock();
		}
	}

	public boolean remove(Predicate<ElementType> isInteresting) {
		boolean found = false;
		lock.lock();
		try {
			Iterator<ElementType> iter = data.iterator();
			while (iter.hasNext()) {
				ElementType element = iter.next();
				if (isInteresting.evaluate(element)) {
					found = true;
					synchronized (element) {
						iter.remove();
					}
				}
			}
		} finally {
			lock.unlock();
		}
		return found;
	}

	public boolean modify(Predicate<ElementType> isInteresting) {
		boolean found = false;
		ElementType element = null;
		lock.lock();
		try {
			Iterator<ElementType> iter = data.iterator();
			while (iter.hasNext()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				} // for test purposes only
				element = iter.next();
				if (isInteresting.evaluate(element)) {
					found = true;
					break;
				}
			}
		} finally {
			synchronized (element) {
				lock.unlock();
				if (found) {
					// perform a time-consuming modification
					FakeModifier.modify(element);
				}
			}
		}

		return found;
	}
}
