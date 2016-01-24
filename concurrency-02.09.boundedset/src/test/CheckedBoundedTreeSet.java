package test;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Semaphore;

import boundedset.BoundedTreeSet;

public class CheckedBoundedTreeSet<T> extends BoundedTreeSet<T> implements InvariantChecker<T> {

	private volatile boolean valid = true;
	public boolean isValid() { return valid; }
	
	private TriFunction<Set<T>,T,Boolean,Boolean> checkerAddPostCondition;
	private TriFunction<Set<T>,T,Boolean,Boolean> checkerRemovePostCondition;

    public void registerAddPostCondition(TriFunction<Set<T>,T,Boolean,Boolean> checker) {
    	Objects.requireNonNull(checker);
    	checkerAddPostCondition = checker;
    }
    public void registerRemovePostCondition(TriFunction<Set<T>,T,Boolean,Boolean> checker) {
    	Objects.requireNonNull(checker);
    	checkerRemovePostCondition = checker;
    }
    
	public CheckedBoundedTreeSet(int bound) {
		super(bound);
	}
    public boolean doAdd(T o) throws InterruptedException {
    	synchronized(set) {
    		boolean result;
    		result = super.doAdd(o);
    		if (checkerAddPostCondition!=null) valid = checkerAddPostCondition.apply(set,o,result) && valid;
    		return result;
    	}
    }
    public boolean doRemove(T o) {
    	synchronized(set) {
    		boolean result;
    		result = super.doRemove(o);
    		if (checkerRemovePostCondition!=null) valid = checkerRemovePostCondition.apply(set,o,result) && valid;
    		return result;
    	}
    }
}
