package calculator;

public class EventConsumer {
	private volatile int cnt = 0;
	private void processEvent (Integer prime) {
		if (cnt++ == 0)
			System.out.println(Thread.currentThread().getName()+": ... printing prime number ...");
		System.out.print(prime+" ");
	}
	... to be done ...
}
