package random;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import annotations.ThreadSafe;

@ThreadSafe
public class ReentrantLockPseudoRandom extends PseudoRandom implements Random {
    private final Lock lock = new ReentrantLock(false);
    private int seed;

    public ReentrantLockPseudoRandom(int seed) {
        this.seed = seed;
    }

    public int nextInt(int n) {
        int s = seed;
        lock.lock();
        try {
            seed = calculateNext(s);
        } finally {
            lock.unlock();
        }
        int remainder = s % n;
        return remainder > 0 ? remainder : remainder + n;
    }
}
