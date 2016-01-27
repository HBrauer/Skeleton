package processing; 


import java.util.concurrent.*;

import processing.Consumer;
import processing.Producer;

public final class Controller {
    private static final int NumberOfConsumers = 3;
    private static final int NumberOfProducers = 2;

    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();
        CountDownLatch productionDoneSignal = new CountDownLatch(NumberOfProducers);
        
        Consumer[] consumers = new Consumer[NumberOfConsumers];
        for (int i=0;i<consumers.length;i++) 
            consumers[i] = new Consumer(queue,i+1);
         
        Producer[] producers = new Producer[NumberOfProducers];
        for (int i=0; i<producers.length; i++)  {
            producers[i] = new Producer(queue,10,productionDoneSignal, i+1);
        }
        try {
            productionDoneSignal.await();
            for (int i=0; i<consumers.length; i++)  {
                consumers[i].shutDown();
            }
        } catch (InterruptedException e) {
            return;
        }
    }
}
