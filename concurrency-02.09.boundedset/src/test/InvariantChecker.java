package test;

import java.util.Set;
import java.util.function.Function;

public interface InvariantChecker<T> {
	public boolean isValid();
	
    public void registerAddPostCondition(TriFunction<Set<T>,T,Boolean,Boolean> checker);
    public void registerRemovePostCondition(TriFunction<Set<T>,T,Boolean,Boolean> checker);
    
    /*
     * Check for valid tree size: 0 <= size <= SEQUENCE_SIZE
     */
    public static <E> Function<Set<E>,Boolean> sizeLimitChecker(int SEQUENCE_SIZE) {
    	return set -> {
    	  boolean success = true;
	      if(set.size()<0 || set.size()>SEQUENCE_SIZE) {
	    	success = false;
			System.err.print(Thread.currentThread().getName()+": ");
			System.err.println("ERROR: illegal tree size = "+set.size());
          }
	      return success;
	   };
    }
}
