/*
  Based on course material for "Concurrent Java", a seminar 
  prepared and owned by Angelika Langer & Klaus Kreft, January 2014
  contact: http://www.AngelikaLanger.com 

  © Copyright 1995 - 2014 by Angelika Langer & Klaus Kreft.
  Permission to use, copy, and modify this software for any non-profit
  purpose is hereby granted to attendants of the above mentioned seminar 
  without fee, provided that the above copyright notice appears in all 
  copies.  Angelika Langer and Klaus Kreft make no representations about 
  the suitability of this software for any purpose.  It is provided 
  "as is" without express or implied warranty.
*/
package calculator;
import java.util.*;

public class PrimeNumberCalculator implements Runnable {
	private List<Integer> primeNumbers;
	private int size;
	
	public PrimeNumberCalculator(int i) { size = i; }
	
	public void run() {
		primeNumbers = 	calculate(size);
	}
	public synchronized List<Integer> getResult() { return primeNumbers; }
	
	public static List<Integer> calculate(int size) {
		List<Integer> primeNumbers = new ArrayList<Integer>();
		System.out.println(Thread.currentThread().getName()+": ... calculating prime numbers ...");
		boolean[] elems = new boolean[size];
	
		for (int i=0; i<size; i++)
			elems[i] = true;
			
		for (int i=2; i<size; ) {
			
			for (int j=2; i*j < size; j++)
				elems[i*j] = false;
			
			int k=0;
			for (k=i+1; k<size; k++)	
			   if (elems[k])  {
			   	i=k;
			   	break;
			   }
			if (k==size)  break; 
		}
		
		for (int i=2;i<elems.length;i++) {
			if(elems[i]) primeNumbers.add(new Integer(i));
		}
		return primeNumbers;
	}
}
