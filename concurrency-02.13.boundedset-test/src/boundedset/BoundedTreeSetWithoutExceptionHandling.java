package boundedset;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Semaphore;

public class BoundedTreeSetWithoutExceptionHandling<T> implements BoundedSet<T> {
    protected final Set<T> set;
    protected final Semaphore sem;

    
    public BoundedTreeSetWithoutExceptionHandling(int bound) {
        set = new TreeSet<T>();
        sem = new Semaphore(bound);
    }
    public boolean add(T o) throws InterruptedException {
        sem.acquire();
        return doAdd(o);
    }
    public boolean remove(T o) {
        boolean wasRemoved = doRemove(o);
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
