package test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

import random.AtomicPseudoRandom;
import random.Random;
import random.ReentrantLockPseudoRandom;

public final class Test {
	private static volatile long dummyResult = 0;	
	private static AtomicLong benchmark = new AtomicLong();
	private static ThreadLocal<StringBuilder> localData 
	=     new ThreadLocal<StringBuilder>() {
	      protected StringBuilder initialValue() {
	          return new StringBuilder();
	        }
	      };
	private static void processThreadLocalData() {
		/*
		 * Experiment with low and moderate levels of simulated work in each 
		 * iteration. With a low level of thread-local computation, the lock or 
		 * atomic variable experiences heavy contention; with more thread-local 
		 * computation, the lock or atomic variable experiences less contention 
		 * since it is accessed less often by each thread.
		 */
		final int LOCAL_COMPUTATION = 100;
		for (int j=0;j<LOCAL_COMPUTATION;j++) {
			localData.set(localData.get().reverse());
		}
	}
	private static void useRandomNumberGenerator(Random randomNumberGenerator, int s) {
		dummyResult += randomNumberGenerator.nextInt(s*100);
	}
	
	private static void test(Random randomNumberGenerator,boolean measure) throws InterruptedException {
		final int NUM_THREADS = 5;
		final int REPETITION = 1000000;
		final CountDownLatch endSignal = new CountDownLatch(NUM_THREADS);
		final long previous = benchmark.longValue();

		for (int threadNumber=1;threadNumber< NUM_THREADS+1;threadNumber++) {
			final int number = threadNumber;
			Runnable task = new Runnable() {
				public void run() {
					try {
						long start = System.nanoTime();
						for (int i=1;i<REPETITION;i++) {
							processThreadLocalData();
							useRandomNumberGenerator(randomNumberGenerator,number);
						}
						long end = System.nanoTime();
						benchmark.addAndGet(end-start);
						endSignal.countDown();
					} catch (Throwable t) {
						t.printStackTrace();
					}
				}
			};
			new Thread(task,"Thread-"+threadNumber).start();
		}
		endSignal.await();
		System.err.println(randomNumberGenerator.getClass().getName()+": \t"+
				(benchmark.longValue()-previous)/1000000+" msec");
	
	}

	private static void benchmark(Random... randomNumberGenerators) throws InterruptedException {	
		System.err.print("warming up ");
		boolean measure = false;  // warm-up
		for (int i=0;i<10;i++) {
			for(Random r : randomNumberGenerators) {
				test(r, measure);
				System.err.print(".");
			}
		}
		System.err.println();
		
		measure = true;         // measure
		for(Random r : randomNumberGenerators) {
			test(r, measure);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		benchmark( 
		           new ReentrantLockPseudoRandom(19),
		           new AtomicPseudoRandom(19)
		         );
	}
}
