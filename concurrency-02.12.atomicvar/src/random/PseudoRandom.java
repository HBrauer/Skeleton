package random;

public class PseudoRandom {
	public static int calculateNext(int y) { 
		  y ^= (y << 11); 
		  y ^= (y >>> 17); 
		  y ^= (y << 13); 
		  return y; 
	} 
}
