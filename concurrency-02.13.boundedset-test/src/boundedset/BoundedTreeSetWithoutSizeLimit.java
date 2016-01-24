package boundedset;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class BoundedTreeSetWithoutSizeLimit<T>  implements BoundedSet<T> {
	protected Set<T> set;

    public BoundedTreeSetWithoutSizeLimit(int bound) {
        set = Collections.synchronizedSet(new TreeSet<T>());
    }
    public boolean add(T o) throws InterruptedException {
        return doAdd(o);
    }
    public boolean remove(T o) {
        return doRemove(o);
    }
    protected boolean doAdd(T o) throws InterruptedException {
		return set.add(o);
	}
	protected boolean doRemove(T o) {
			return set.remove(o);
	}
}
