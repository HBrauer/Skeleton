package calculator;
import java.util.*;

public class Test {
	
	private static void getAndPrintResult(PrimeNumberCalculator calc) {
		List<Integer> primes = null;
		try {
			primes = calc.getResult();
		} catch (Exception e) { System.out.println(e); }
		for (int i=0;i<primes.size();++i)
			System.out.print(primes.get(i)+" ");
	}


	public static void main(String[] args) {
		PrimeNumberCalculator calc = new PrimeNumberCalculator(10000);
		Thread th = new Thread(calc);
		th.start();
		try { th.join(); } catch (Exception e) { System.out.println(e); }
		getAndPrintResult(calc);
	}
}
