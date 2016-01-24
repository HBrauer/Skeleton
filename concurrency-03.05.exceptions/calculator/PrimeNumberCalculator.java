package calculator;
import java.util.*;

public class PrimeNumberCalculator implements Runnable {
	public static class ServiceDeniedException extends RuntimeException {
		private int trigger = 0;
		public ServiceDeniedException(int i) { trigger = i; }
		public int getTrigger() { return trigger; }
		public String getMessage() {
			return "trigger["+trigger+"]";
		}
	}
	private static void triggerException(int i) throws ServiceDeniedException {
		Random generator = new Random();
		int trigger = generator.nextInt();
		if (i>100 && (trigger % i) == 0) {
			throw new ServiceDeniedException(i);
		}
	}
	
	private List<Integer> primeNumbers = new ArrayList<Integer>();
	private int upperBound = 0;
	
	public PrimeNumberCalculator(int i) { upperBound = i; }
	public List<Integer> getResult() { return primeNumbers; }
	
	private boolean[] sieve(int upperBound) {
		boolean[] elems = new boolean[upperBound];
	
		for (int i=0; i<upperBound; i++)
			elems[i] = true;
			
		for (int i=2; i<upperBound; ) {
			
			for (int j=2; i*j < upperBound; j++)
				elems[i*j] = false;
			
			int k=0;
			for (k=i+1; k<upperBound; k++)	
			   if (elems[k])  {
			   	i=k;
			   	break;
			   }
			if (k==upperBound)  break; 
		}
		return elems;
	}
	
	public void run() {
		boolean[] elems = null;
		elems = sieve(upperBound);
		
		for (int i=2;i<elems.length;i++) {
//			if(elems[i]) primeNumbers.add(i);  // should work: autoboxing
			if(elems[i]) {
				primeNumbers.add(new Integer(i));
				try {triggerException(i);}
				catch (RuntimeException e) {
					throw e;
				}
			}
		}
	}

}
