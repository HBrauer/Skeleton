package application;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock; 
import java.util.concurrent.locks.ReentrantLock;

public final class BlockingIntStack {
    private Lock fullLock = new ReentrantLock();
    private Lock emptyLock = new ReentrantLock();
    private Condition fullCon = fullLock.newCondition();
    private Condition emptyCon = emptyLock.newCondition();

    private final int[] array;
    private int cnt = 0;

    public BlockingIntStack(int sz) {
        array = new int[sz];
    }

    public void push(int element) throws InterruptedException {
        fullLock.lockInterruptibly();
        try {
            while (cnt == array.length) {
                fullCon.await();
            }
            Thread.sleep(1000);
            emptyLock.lockInterruptibly();
            try { // <<< nested synchronized block
                array[cnt++] = element;
                emptyCon.signal();
            } finally {
                emptyLock.unlock();
            }
            
        } finally {
            fullLock.unlock();
        }
    }

    public int pop() throws InterruptedException {
        emptyLock.lockInterruptibly();
        try {
            while (cnt == 0) {
                emptyCon.await();
            }
            Thread.sleep(1000);
            fullLock.lockInterruptibly();
            try { // <<< nested synchronized block
                int tmp = array[--cnt];
                fullCon.signal();
                return (tmp);
            } finally {
                fullLock.unlock();
            }
        } finally {
            emptyLock.unlock();
        }
    }
    public String toString() {
        return Arrays.toString(array);
    }
}
