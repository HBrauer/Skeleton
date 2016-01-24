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

import java.util.Set;
import java.util.concurrent.Semaphore;

public class BoundedTreeSet<T> implements BoundedSet<T> {
    protected final Set<T> set;
    protected final Semaphore sem;
    
    public BoundedTreeSet(int bound) {
    	// ... to be done ...
    }

    // ... to be done ...

    /*
     * These two methods are for test purposes. 
     * Use them where you would otherwise use the set's add() and remove() methods directly. 
     * They are overridden in the derived class CheckedBoundedTreeSet to enable a rudimentary test.
     */
    protected boolean doAdd(T o) throws InterruptedException {
    		return set.add(o);
    }
    protected boolean doRemove(T o) {
    		return set.remove(o);
    }
}
