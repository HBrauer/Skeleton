package test;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import boundedset.BoundedSet;

public final class TestForBlockingState {

	private static final class BlockTest<T extends BoundedSet<Integer>> implements Callable<Void> {
		T bset;
		CountDownLatch endLatch;
		CountDownLatch startLatch;

		public BlockTest(T bset, CountDownLatch endLatch, CountDownLatch startLatch) {
			this.bset = bset;
			this.endLatch = endLatch;
			this.startLatch = startLatch;
		}

		@Override
		public Void call() throws Exception {
			int[] values = { 0, 1, 2, 3, 4, 5, 6, 6, 5, 4, 7, 8, 2, 9, 10 };
			startLatch.await();
			for (int i = 0; i < values.length; ++i) {
				bset.add(values[i]);
			}
			endLatch.countDown();
			System.err.println("count down");
			return null;
		}
	}

	/*************************************************************************
	 * Starts an Adder thread, add SEQUENCE_SIZE+1 many distinct items (plus
	 * some duplicates) and check whether it blocks when then bounded set is
	 * full.
	 *************************************************************************/

	private static final int SEQUENCE_SIZE = 10;


	public static <T extends BoundedSet<Integer>> boolean runTest(Class<T> bsetType) {
		boolean success = true;
	
		T bset;
		try {
			bset = bsetType.getConstructor(int.class).newInstance(SEQUENCE_SIZE);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
			success = false;
			return success;
		}

		Thread.currentThread().setName("Controller");
		final int NUMBER_OF_CYCLES = 2;

		for (int i = 0; i < NUMBER_OF_CYCLES; i++) {
			ExecutorService executer = Executors.newFixedThreadPool(10);
			try {
				System.out.println("\n************************************************");
				System.out.println("** TESTING FOR WAIT STATE ON FULL SET");
				System.out.println("** MAX SIZE: " + SEQUENCE_SIZE);
				System.out.println("** CLASS   : " + bsetType.getName());
				System.out.println("** CYCLE   : " + i);
				System.out.println("************************************************\n");

				CountDownLatch startLatch = new CountDownLatch(1);
				CountDownLatch endLatch = new CountDownLatch(10);
				for (int k = 0; k < 10; ++k) {
					executer.submit(new BlockTest<T>(bset, startLatch, endLatch));
				}
				startLatch.countDown();
				success = !endLatch.await(3, TimeUnit.SECONDS);
				System.out.println("\n** CYCLE " + (success ? "SUCCESSFUL" : "FAILED") + " ********************\n");
			executer.shutdownNow();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return success;
	}

}