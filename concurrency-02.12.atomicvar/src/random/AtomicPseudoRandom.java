package random;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import annotations.ThreadSafe;

@ThreadSafe
public class AtomicPseudoRandom extends PseudoRandom implements Random {
	private AtomicInteger seed;

	public AtomicPseudoRandom(int seed) {
		this.seed = new AtomicInteger(seed);
	}

	public int nextInt(int n) {
		int oldSeed;
		int newSeed;
		do {
			oldSeed = seed.get();
			newSeed = PseudoRandom.calculateNext(oldSeed);
		} while (!seed.compareAndSet(oldSeed, newSeed));
		int remainder = oldSeed % n;
		return remainder > 0 ? remainder : remainder + n;
	}
}
