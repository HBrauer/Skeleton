package calculator;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class PrimeNumberCalculator implements Runnable {
	private final List<Integer> primeNumbers = new CopyOnWriteArrayList<>();
	private int size;
	
	private volatile boolean cancelled = false;

	public PrimeNumberCalculator(int i) {
		size = i;
	}

	public void run() {
		Thread.currentThread().setName("PrimeNumberCalculator");
		System.out.println(Thread.currentThread().getName() + ": ... calculating prime numbers ...");
		boolean[] sieve = new boolean[size];

		// set all positions in sieve to true,
		// i.e. mark all numbers as potential prime numbers
		for (int i = 0; i < size; i++)
			sieve[i] = true;
	
		for (int currentPrime = 2; currentPrime < size && !Thread.currentThread().isInterrupted();) {

			// eliminate from sieve all multiples of the current prime number
			for (int j = 2; currentPrime * j < size; j++)
				sieve[currentPrime * j] = false;

			// find next prime number
			int nextPrime;
			for (nextPrime = currentPrime + 1; nextPrime < size; nextPrime++)
				if (sieve[nextPrime]) {
					currentPrime = nextPrime;
					break;
				}
			if (nextPrime == size){
				break;
			}
		}
         
		cancelled = Thread.currentThread().isInterrupted();
		
		for (int i = 2; i < sieve.length; i++) {
			if (sieve[i])
				primeNumbers.add(new Integer(i));
		}
	}

	public List<Integer> getResult() {
		return Collections.unmodifiableList(primeNumbers);
	}

	public boolean isCancelled() {

		return cancelled;
	}
}
