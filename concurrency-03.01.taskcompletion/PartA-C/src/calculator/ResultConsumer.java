package calculator;

import java.util.*;

public class ResultConsumer {
	private void processResult (List<Integer> primes) {
		if (primes != null) {
			System.out.println(Thread.currentThread().getName()+": ... printing prime numbers ...");
			for (int i=0;i<primes.size();++i)
				System.out.print(primes.get(i)+" ");
		}
	}
	public void someMethod() {
		List<Integer> result;
		ResultProducer resultProducer = new ResultProducer();
		
		// start result producer
		... to be done ...
		
		// retrieve result from producer
		... to be done ...
		
		// process result
		processResult(result);
	}
}
