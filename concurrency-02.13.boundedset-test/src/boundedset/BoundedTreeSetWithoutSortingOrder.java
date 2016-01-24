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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class BoundedTreeSetWithoutSortingOrder<T> implements BoundedSet<T> {
    protected final Set<T> set;
    protected final Semaphore sem;

    
    public BoundedTreeSetWithoutSortingOrder(int bound) {
        set = Collections.synchronizedSet(new HashSet<T>());
        sem = new Semaphore(bound);
    }
    public boolean add(T o) throws InterruptedException {
        sem.acquire();

        boolean wasAdded = false;
        try {
            wasAdded = doAdd(o);
            return wasAdded;
        }
        finally {
            if (!wasAdded)
                sem.release();
        }
    }
    public boolean remove(T o) {
        boolean wasRemoved = doRemove(o);
        if (wasRemoved) 
            sem.release();
        return wasRemoved;
    }
    protected boolean doAdd(T o) throws InterruptedException {
    		return set.add(o);
    }
    protected boolean doRemove(T o) {
    		return set.remove(o);
    }
}
