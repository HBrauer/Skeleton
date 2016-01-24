package test;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

import boundedset.BoundedSet;

public class Test {
    private static final int MODULO = 5; 

    private static final class Adder  {   
    	
	    public Adder(
	    		final BoundedSet<Integer> set,
	   		    final int id,
	   		    final int howMany, 
	   		    final CountDownLatch startSignal, 
	   		    final CountDownLatch endSignal
	   		    ) {
	        String threadName = set.getClass().getSimpleName()+"-Adder-"+id;
	    	Supplier<Boolean> task 	= () -> {
		    		Thread.currentThread().setName(threadName);
		    		boolean success = false;
		        	try {
						startSignal.await();
			            System.out.print(Thread.currentThread().getName()+": ");
			            System.out.println("adding "+howMany+" items ...");
			            Random generator = ThreadLocalRandom.current();
			            int added = 0;
			            while (added<howMany) {
			                   int element = Math.abs(generator.nextInt()%MODULO);
		                       boolean result = set.add(element);
			                   if (result) {
			                      added++;                              
			                   }
			            }
			            success = ((InvariantChecker<Integer>)set).isValid();
		        	} catch (InterruptedException ie) {
						ie.printStackTrace();
						success = false;					
					} finally {
						System.out.print(Thread.currentThread().getName()+": ");
			            System.out.println("DONE adding "+howMany+" items ...");
			            endSignal.countDown();
					}
					return success;
	    	};
	        CompletableFuture.supplyAsync(task);
		   }
    }
	

    private static class Remover  {

        public Remover(
        	   final BoundedSet<Integer> set,
 		       final int id,
 		       final int howMany, 
 		       final CountDownLatch startSignal, 
 		       final CountDownLatch endSignal
 		       ) {
        	String threadName = set.getClass().getSimpleName()+"-Remover-"+id;
        	
	    	Supplier<Boolean> task = () ->  {
		    		Thread.currentThread().setName(threadName);
		    		boolean success = false;
		        	try {
						startSignal.await();
			            System.out.print(Thread.currentThread().getName()+": ");
			            System.out.println("removing "+howMany+" items ...");
			            Random generator = ThreadLocalRandom.current();
			            int removed = 0;
			            while (removed<howMany) {
			                 int element = Math.abs(generator.nextInt()%MODULO);
			                 boolean result = set.remove(element);
			                 if (result) {
			                     removed++;
			                 }
			            }
			            success = ((InvariantChecker<Integer>)set).isValid();
		        	} catch (InterruptedException ie) {
						ie.printStackTrace();
						success = false;					
					} finally {
						System.out.print(Thread.currentThread().getName()+": ");
						System.out.println("DONE removing "+howMany+" items ...");
			            endSignal.countDown();
					}
					return success;
	    	};
        	 
 	        CompletableFuture.supplyAsync(task);
        }
     
    }
    
    private static final int SEQUENCE_SIZE = 2;
    private static final int NUMBER_OF_ADDERS = 2;
    private static final int HOWMANY_ADDITIONS = 3;
    private static final int NUMBER_OF_REMOVERS = 1;
    private static final int HOWMANY_REMOVALS = 6;
    
    private static final long TEST_FAILURE_DETECT_TIMEOUT = 3000; // TimeUnit.MILLISECONDS
   
    /*************************************************************************
     * Starts several Adder and Remover threads in order to test the 
     * BoundedTreeSet.
     * 
     * Check that the set is bounded:
     * ------------------------------
     * Make sure that the set's capacity, i.e. SEQUENCE_SIZE, is less than 
     * the number of additions and check that the current sequence size never 
     * exceeds the capacity. 
     * 
     * Check that add() and remove() work:
     * -----------------------------------
     * 
     * If NUMBER_OF_ADDERS * HOWMANY_ADDITIONS equals 
     * NUMBER_OF_REMOVERS * HOWMANY_REMOVALS then the collection should be 
     * empty after each cycle.
     * 
     *************************************************************************/

	private static boolean runCycle(int cycleId, 
			                              BoundedSet<Integer> set,
			                              CountDownLatch endOfCycleSignal) throws InterruptedException {
		CountDownLatch endofAdditionSignal = new CountDownLatch(NUMBER_OF_ADDERS);
		CountDownLatch endOfRemovalSignal = new CountDownLatch(NUMBER_OF_REMOVERS);
		CountDownLatch startSignal = new CountDownLatch(1);
		boolean success = true;

		for (int i = 0; i < NUMBER_OF_ADDERS; i++) {
			new Adder(set, cycleId * 10 + i, HOWMANY_ADDITIONS, startSignal, endofAdditionSignal);
		}
		for (int i = 0; i < NUMBER_OF_REMOVERS; i++) {
			new Remover(set, cycleId * 10 + i, HOWMANY_REMOVALS, startSignal, endOfRemovalSignal);
		}
		startSignal.countDown();
		try {
			endofAdditionSignal.await(TEST_FAILURE_DETECT_TIMEOUT,TimeUnit.MILLISECONDS);
			endOfRemovalSignal.await(TEST_FAILURE_DETECT_TIMEOUT,TimeUnit.MILLISECONDS);
			success = ((InvariantChecker<Integer>)set).isValid();
		} catch (InterruptedException e) {
			e.printStackTrace();
			success = false;
		}
		endOfCycleSignal.countDown();
		return success;
	}

    public static <T extends BoundedSet<Integer> & InvariantChecker<Integer>>
    boolean runTest(T bset) {
    	boolean success = true;

    	final Function<Set<Integer>,Boolean> sizeChecker = InvariantChecker.sizeLimitChecker(SEQUENCE_SIZE);
    	
    	bset.registerAddPostCondition(
   			 (set,o,wasAdded) -> { 
   			                  boolean testSuccess = true;
   			                  if (wasAdded) {
   			                	  System.out.print(Thread.currentThread().getName()+": ");
		   			              System.out.print("ADDED "+o);
		                          System.out.println(" / set: "+set);
		                          testSuccess = sizeChecker.apply(set); 
   			                  } 
   			                  return testSuccess;
   			      });
    	bset.registerRemovePostCondition(
      			 (set,o,wasRemoved) -> {
      			                  boolean testSuccess = true;
      			                  if (wasRemoved) {
      			                	  System.out.print(Thread.currentThread().getName()+": ");
		      			              System.out.print("REMOVED "+o);
		                              System.out.println(" / set: "+set);   
		                              testSuccess = sizeChecker.apply(set); 
      			                  }    
      			                  return testSuccess;
      			      });
    	
    	Thread.currentThread().setName("Controller");
        final int NUMBER_OF_CYCLES = 2;
      
        for (int i=0;i<NUMBER_OF_CYCLES;i++)
            try {
            	System.out.println("\n************************************************");
            	System.out.println("** TESTING FOR SIZE LIMIT");
            	System.out.println("** MAX SIZE: "+SEQUENCE_SIZE);
            	System.out.println("** CLASS   : "+bset.getClass().getName());
            	System.out.println("** CYCLE   : "+i);
            	System.out.println("************************************************\n");
                CountDownLatch endCycle = new CountDownLatch(1);
                success = runCycle(i+1,bset,endCycle);
                endCycle.await(TEST_FAILURE_DETECT_TIMEOUT,TimeUnit.MILLISECONDS);  // not strictly necessary, but helps avoiding garbles printout
                System.out.println("\n** CYCLE "+(success?"SUCCESSFUL":"FAILED")+" ********************\n");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        return success;
    }
    public static void main(String[] args) {
        Test.runTest(new CheckedBoundedTreeSet<Integer>(SEQUENCE_SIZE));
    }
}
