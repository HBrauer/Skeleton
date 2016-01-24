/**
 * 
 */
package calculator;

import java.util.Iterator;
import java.util.List;

public class EventProducer implements Runnable {
	private final int SIZE = 10000;
	private List<Integer> primes = PrimeNumberCalculator.calculate(SIZE);
	private Iterator<Integer> iterator = primes.iterator();
	
	private Integer produceResult() {
		if (iterator.hasNext()) {
			return iterator.next();
		} else {
			return null;
		}
	}
	public void run() {
        ... to be done ...
	}
}