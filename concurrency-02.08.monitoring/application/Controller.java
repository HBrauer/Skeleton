package application;

import java.lang.management.ThreadInfo;
import java.util.Scanner;


public final class Controller {
    private final int NUM_PRODUCERS;
    private final int NUM_CONSUMERS;

    private final Producer[] producers;
    private final Consumer[] consumers;
    
    private static final int NUM_ITEMS = 3;
    
    private static final ThreadGroup producerGroup = new ThreadGroup("Production");
    private static final ThreadGroup consumerGroup = new ThreadGroup("Consumption");
    
    private final BlockingIntStack stack 
        = new BlockingIntStack(5);

    private static class Consumer {
        private final Thread thread;
        private static int count = 0;
        public Consumer(final BlockingIntStack stack) {
          Runnable consumer = new Runnable() {
              public void run() {
                  for (int i=0; i<NUM_ITEMS; i++) {
                    System.out.print("\""+Thread.currentThread().getName()+"\" Id="
                              +Thread.currentThread().getId()+" trying to read from");
                    System.out.println(" queue: "+stack.toString());
                    int res;
                    try {
                        res = stack.pop();
                        System.out.print("\""+Thread.currentThread().getName()+"\" Id="
                                +Thread.currentThread().getId()+" read "+res);
                        System.out.println(" from queue: "+stack.toString());
                    } catch (InterruptedException e) {
                        System.err.println("\""+Thread.currentThread().getName()+"\" Id="
                                +Thread.currentThread().getId()+" INTERRUPT RECEIVED");
                        e.printStackTrace();
                        return;
                    }
                  }
              }
          };
          thread = new Thread(consumerGroup,consumer,"Consumer #"+count++);
          thread.start();
        }
    }
    private static class Producer {
        private final Thread thread;
        private static int count = 0;
        public Producer(final BlockingIntStack stack) {
            Runnable producer = new Runnable() {
              public void run() {
                  for (int i=0; i<NUM_ITEMS; i++) {
                      long id = Thread.currentThread().getId();
                      int item = (int)id+i;
                      System.out.print("\""+Thread.currentThread().getName()+"\" Id="
                              +id+" trying to write "+item);
                      System.out.println(" to queue: "+stack.toString());
                      try {
                        stack.push(item);
                        System.out.print("\""+Thread.currentThread().getName()+"\" Id="
                                +id+" wrote "+item);
                        System.out.println(" to queue: "+stack.toString());
                    } catch (InterruptedException e) {
                        System.err.println("\""+Thread.currentThread().getName()+"\" Id="
                                +id+" INTERRUPT RECEIVED");
                        e.printStackTrace();
                        return;
                    }
                  }
              }
            };
            thread = new Thread(producerGroup,producer,"Producer #"+count++);
            thread.start();
        }
    }  
    
    public Controller(int num_producers_and_consumers) {
        NUM_PRODUCERS = num_producers_and_consumers;
        NUM_CONSUMERS = num_producers_and_consumers;
        producers = new Producer[NUM_PRODUCERS];
        consumers = new Consumer[NUM_CONSUMERS];
    }
    ////////////////////////////////////////////////////////////////////////////
    public void launch() {        
        for (int i=0;i<producers.length/2;i++) {
            producers[i] = new Producer(stack);
        }
        try { Thread.sleep(1000); } catch (Exception e) {}
        for(int i=0; i<consumers.length;i++) {
            consumers[i] = new Consumer(stack);
        }
        try { Thread.sleep(1000); } catch (Exception e) {}
        for (int i=producers.length/2;i<producers.length;i++) {
            producers[i] = new Producer(stack);
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    private boolean okayToKill(ThreadInfo[] infos) {
        if (infos==null) return false;
        StringBuilder buf = new StringBuilder();
        System.out.println();
        System.out.println(">>>>>>>>>>>>  Do you want to kill the following threads? (y/n)");
        buf.append("[ ");
        for (int i=0;i<infos.length;i++) {
            buf.append("\""+infos[i].getThreadName()
                            +"\" Id: "+infos[i].getThreadId());
            buf.append((i==infos.length-1)?" ]\n":", ");
        }
        System.out.println(buf);
        Scanner sc = new Scanner(System.in);
        String reply = sc.next();
        if (reply.equals("n")) {
            System.out.println(">>>>>>>>>>>>  killing aborted");
            return false;
        }
        System.out.print("killing threads "+buf);
        return true;
    }
    public void kill() {
        ThreadInfo[] infos = null;
         ... to be done:  get thread info for the threads to be killed ...
        if (!okayToKill(infos))
            return;
         ... to be done: kill the threads ...
    }
}
