package random;

import java.util.concurrent.atomic.AtomicInteger;
import annotations.ThreadSafe;

@ThreadSafe
public class AtomicPseudoRandom extends PseudoRandom implements Random {
    private AtomicInteger seed;

    public AtomicPseudoRandom(int seed) { 
        ... to be done ...
    }

    public int nextInt(int n) {
    	... to be done ...
    }
}


