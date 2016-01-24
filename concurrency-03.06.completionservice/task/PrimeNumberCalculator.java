/*
  Based on course material for "Concurrent Java", a seminar 
  prepared and owned by Angelika Langer & Klaus Kreft, February 2007
  contact: http://www.AngelikaLanger.com 

  © Copyright by Angelika Langer & Klaus Kreft.
  Permission to use, copy, and modify this software for any non-profit
  purpose is hereby granted to attendants of the above mentioned seminar 
  without fee, provided that the above copyright notice appears in all 
  copies.  Angelika Langer and Klaus Kreft make no representations about 
  the suitability of this software for any purpose.  It is provided 
  "as is" without express or implied warranty.
*/
package task;
import java.util.*;
import java.util.concurrent.*;

public class PrimeNumberCalculator implements Callable<List<Integer>> {
	private int size;
	
	public PrimeNumberCalculator(int i) { size = i; }
	
	public List<Integer> call() {
//		System.out.println(Thread.currentThread().getName()+": ... calculating prime numbers ...");
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
		
        List<Integer> primeNumbers = new ArrayList<Integer>();
		for (int i=2;i<elems.length;i++) {
			if(elems[i]) primeNumbers.add(new Integer(i));
		}	
        return primeNumbers;
	}
}
