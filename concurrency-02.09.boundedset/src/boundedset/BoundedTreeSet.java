/*
  Based on course material for "Concurrent Java", a seminar 
  prepared and owned by Angelika Langer & Klaus Kreft, February 2007
  contact: http://www.AngelikaLanger.com 

  © Copyright by Angelika Langer & Klaus Kreft.
  Permission to use, copy, and modify this software for any non-profit
  purpose is hereby granted to attendants of the above mentioned seminar 
  without fee, provided that the above copyright notice appears in all 
  copies.  Angelika Langer and Klaus Kreft make no representations about 
  the suitability of this software for any purpose.  It is provided 
  "as is" without express or implied warranty.
*/
package boundedset;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Semaphore;

public class BoundedTreeSet<T> implements BoundedSet<T> {
	protected final ConcurrentSkipListSet<T> set;
	protected final Semaphore semaphore;

	public BoundedTreeSet(int bound) {
		this.semaphore = new Semaphore(bound);
		this.set = new ConcurrentSkipListSet<>();
	}

	// ... to be done ...

	/*
	 * These two methods are for test purposes. Use them where you would
	 * otherwise use the set's add() and remove() methods directly. They are
	 * overridden in the derived class CheckedBoundedTreeSet to enable a
	 * rudimentary test.
	 */
	protected boolean doAdd(T o) throws InterruptedException {
		return set.add(o);
	}

	protected boolean doRemove(T o) {
		return set.remove(o);
	}

	@Override
	public boolean add(T o) throws InterruptedException {
		semaphore.acquire();
		boolean result = doAdd(o);
		if (!result) {
			semaphore.release();
		}

		return result;

	}

	@Override
	public boolean remove(T o) {
		boolean result = doRemove(o);
		if (result) {
			semaphore.release();
		}
		return result;
	}
}
