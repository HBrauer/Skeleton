package test;

import java.lang.reflect.InvocationTargetException;

import boundedset.BoundedSet;

public class TestForTreeSizeLimit {
   
    /*************************************************************************
     * Starts several Adder and Remover threads in order to test the 
     * BoundedTreeSet.
     * 
     * Check that the set is bounded:
     * ------------------------------
     * Check that the current sequence size never exceeds the set's capacity, 
     * i.e. SEQUENCE_SIZE. 
     * 
     *************************************************************************/
    
    private static final int SEQUENCE_SIZE = 2;
    private static final int NUMBER_OF_ADDERS = 2;
    private static final int HOWMANY_ADDITIONS = 3;
    private static final int NUMBER_OF_REMOVERS = 1;
    private static final int HOWMANY_REMOVALS = 6;

    public static <T extends BoundedSet<Integer>>
    boolean runTest(Class<T> bsetType) {
    	boolean success = true;
    	T bset = null;
		try {
			bset = bsetType.getConstructor(int.class).newInstance(SEQUENCE_SIZE);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
			success = false;
			return success;
		}
    	
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

                ... to be done ...
            	
                System.out.println("\n** CYCLE "+(success?"SUCCESSFUL":"FAILED")+" ********************\n");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        return success;
    }
}
