package processing; 


import java.util.concurrent.*;

public final class Controller {
    private static final int NumberOfConsumers = 3;
    private static final int NumberOfProducers = 2;
    private static final int POISON = Integer.MAX_VALUE;

    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();
        
        Consumer[] consumers = new Consumer[NumberOfConsumers];
        for (int i=0;i<consumers.length;i++) 
            consumers[i] = new Consumer(queue,POISON,NumberOfProducers,i+1);
         
        Producer[] producers = new Producer[NumberOfProducers];
        for (int i=0; i<producers.length; i++)  {
            producers[i] = new Producer(queue,10,POISON,NumberOfConsumers,i+1);
        }
    }
}
