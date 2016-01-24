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
package solver;

import java.util.*;
import java.util.concurrent.*;
import task.*;

public final class Test {

	public static void main(String[] args) {
		CustomThreadPoolExecutor threadPool //= Executors.newFixedThreadPool(5);		
        = new CustomThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		List<Task> tasks = new ArrayList<Task>();
		for (int i = 0; i < 10; i++)
			tasks.add(new Task());

		try {
			
			for (int i=0;i<2;i++) {
				System.out.println("°°°°°°°°°°°°°°°°°°°°° ROUND #"+(i+1)+" °°°°°°°°°°°°°°°°°°°°°");
				Solver.<List<Integer>> solve(threadPool, tasks,
							new ResultPrinter());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			threadPool.myShutdown();
		}
	}
}
