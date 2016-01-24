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
import java.util.concurrent.Callable;

public final class Task implements Callable<List<Integer> > {
    private int[] makeInterval() {
        int[] result = new int[2];
        double x = Math.random()*1000;
        double y = Math.random()*1000;
        if(x<y) {
            result[0] = (int)x;
            result[1] = (int)y;
        }
        else {
            result[1] = (int)x;
            result[0] = (int)y;
        }
        System.out.println(Thread.currentThread().getName()
                +": ... calculating prime numbers in interval ["+result[0]+","+result[1]+"] ...");
        return result;
    }
    private List<Integer> findPrimes(int[] interval) {
        List<Integer> primes = new PrimeNumberCalculator(interval[1]).call();
        Iterator<Integer> iter = primes.iterator();
        while (iter.hasNext()) {
            if (iter.next()<interval[0])
                iter.remove();
        }
        return primes;
    }

    public List<Integer> call() throws Exception {
    	int[] interval = makeInterval();
    	if (interval[1]-interval[0] < 100)
    		return null;
    	if (interval[1]-interval[0] > 600)
    		throw new Exception(Arrays.toString(interval));
        return findPrimes(makeInterval());
    }

}
