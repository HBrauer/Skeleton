package processing;

import java.util.*;

public final class Controller {
	private static final int NumberOfConsumers = 3;
	private static final int NumberOfProducers = 2;

	public static void main(String[] args) throws InterruptedException {
		List<Integer> queue = new ArrayList<Integer>();
		Consumer[] consumers = new Consumer[NumberOfConsumers];
		for (int i = 0; i < consumers.length; i++)
			consumers[i] = new Consumer(queue, i + 1);

		Producer[] producers = new Producer[NumberOfProducers];
		for (int i = 0; i < producers.length; i++) {
			producers[i] = new Producer(queue, 10, i + 1);
		}
		
	
	}
}
