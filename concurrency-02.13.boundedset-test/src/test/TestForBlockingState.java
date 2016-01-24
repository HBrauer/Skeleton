package test;

import java.lang.reflect.InvocationTargetException;

import boundedset.BoundedSet;

public final class TestForBlockingState {
	
    /*************************************************************************
     * Starts an Adder thread, add SEQUENCE_SIZE+1 many distinct items (plus some duplicates)
     * and check whether it blocks when then bounded set is full.     
     *************************************************************************/
	
	private static final int SEQUENCE_SIZE = 10;

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
            	System.out.println("** TESTING FOR WAIT STATE ON FULL SET");
            	System.out.println("** MAX SIZE: "+SEQUENCE_SIZE);
            	System.out.println("** CLASS   : "+bsetType.getName());
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
