package calculator;

import java.util.*;


public class Test {

	private static void getAndPrintResult(PrimeNumberCalculator calc) {
		System.out.println(Thread.currentThread().getName() + ": ... printing prime numbers ...");
		List<Integer> primes = null;
		boolean cancelled = calc.isCancelled();
		if (cancelled)
			System.out.println(">>> prime number calculation was aborted due to interrupt request");
		try {
			primes = calc.getResult();
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("Number of Results: " + primes.size());
		for (int i = 0; i < primes.size(); ++i) {
			System.err.print(primes.get(i) + " ");
		}
	
	}

	public static void main(String[] args) {
		Thread.currentThread().setName("Tester");

		/*******************************************
		 * start prime number calculation
		 ******************************************/
		// use this for part a) and b) of the lab
		PrimeNumberCalculator calc = new PrimeNumberCalculator(100000);
		Thread worker = new Thread(calc);
		worker.start();

		/*******************************************
		 * start sniper
		 ******************************************/
		Thread sniper = new Thread(new Sniper(200, worker, false));

		// use this constructor for part b) of the lab
		// Thread sniper = new Thread(new Sniper(200,worker,true));

		// use this constructor for part c) of the lab
		// Thread sniper = new Thread(new Sniper(200,future));

		sniper.setDaemon(true);
		sniper.start();

		try {
			worker.join();
		} catch (Exception e) {
			System.out.println(e);
		}

		getAndPrintResult(calc);
	}
}
