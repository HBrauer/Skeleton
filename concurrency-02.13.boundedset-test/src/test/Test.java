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
package test;

import boundedset.BoundedTreeSet;
import boundedset.BoundedTreeSetWithoutExceptionHandling;
import boundedset.BoundedTreeSetWithoutSizeLimit;
import boundedset.BoundedTreeSetWithoutSortingOrder;
import boundedset.BoundedTreeSetWithoutSynchronization;

public final class Test {

    public static void main(String[] args) {
    	/*
    	 * Write a test that starts a number of adder and remover threads and 
    	 * verifies that the set's size is always within its limits, 
    	 * i.e. the set's size must be not be less than 0 and not greater than its size limit.
    	 */
//        TestForTreeSizeLimit.runTest(BoundedTreeSetWithoutSizeLimit.class);
//        TestForTreeSizeLimit.runTest(BoundedTreeSetWithoutExceptionHandling.class);
//        TestForTreeSizeLimit.runTest(BoundedTreeSetWithoutSynchronization.class);
//        TestForTreeSizeLimit.runTest(BoundedTreeSetWithoutSortingOrder.class);
//        TestForTreeSizeLimit.runTest(BoundedTreeSet.class);
        
        TestForBlockingState.runTest(BoundedTreeSetWithoutSizeLimit.class);
//        TestForBlockingState.runTest(BoundedTreeSetWithoutExceptionHandling.class);
//        TestForBlockingState.runTest(BoundedTreeSetWithoutSynchronization.class);
//        TestForBlockingState.runTest(BoundedTreeSetWithoutSortingOrder.class);
//        TestForBlockingState.runTest(BoundedTreeSet.class);
    }
}
