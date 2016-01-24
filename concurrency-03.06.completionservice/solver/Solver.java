package solver;

import java.util.*;
import java.util.concurrent.*;
import task.*;

public final class Solver {
	/*
	 * the intended solution:  
	 * - use a completion service
     * - retrieve results
     * - if a task fails to produce a result 
     *      then cancel all remaining tasks
	 */
    public static <R> void solve(
            Executor e,
            Collection< ? extends Callable<R>> tasks, 
            User<R> usr)
            throws InterruptedException {
        
        ... to be done ...
    }
}
