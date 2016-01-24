/*
  Based on course material for "Concurrent Programming Java", a seminar 
  prepared and owned by Angelika Langer & Klaus Kreft, November 2011
  contact: http://www.AngelikaLanger.com

  © Copyright 2000-2011 by Angelika Langer & Klaus Kreft.
  Permission to use, copy, and modify this software for any non-profit
  purpose is hereby granted to attendants of the above mentioned seminar 
  without fee, provided that the above copyright notice appears in all 
  copies.  Angelika Langer and Klaus Kreft make no representations about 
  the suitability of this software for any purpose.  It is provided 
  "as is" without express or implied warranty.
*/
package scheduling;

import java.util.concurrent.*;
import java.util.*;

public final class ServerThread {
    
    public static interface ClientCtx {
        public long ping() throws InterruptedException; 
    }
    
    private static class ClientCtxImpl implements ClientCtx {
        private BlockingQueue<Integer> sendQ;
        private BlockingQueue<Long> receiveQ;            
        public ClientCtxImpl(BlockingQueue<Integer> send, BlockingQueue<Long> receive) {
            sendQ = send;
            receiveQ = receive;
        }        
        public long ping() throws InterruptedException {            
            if(!sendQ.offer(0,100,TimeUnit.MILLISECONDS))
                // if timeout
                return -1;
            Long ret = receiveQ.poll(100,TimeUnit.MILLISECONDS);
            if (ret == null)
                // if timeout
                return -1;
            return ret;
        }
    }
    
    private final int TIME_INTERVAL = 20000;
    private final int id;
    private final ClientCtx clientCtx;
    
    public ClientCtx getClientCtx() {
        return clientCtx;
    }

    /*
     * This is a fake server thread.  It is implemented so that it will 
     * die after some random time.
     * Don't worry about its functionality; it is provided for test 
     * purposes only. 
     */
    public ServerThread(int serverId) {
        final BlockingQueue<Integer> intQ = new ArrayBlockingQueue<Integer>(1);
        final BlockingQueue<Long> longQ = new LinkedBlockingQueue<Long>();
        
        clientCtx = new ClientCtxImpl(intQ, longQ);
        id = serverId;
        
        Runnable r = new Runnable() {
            long end = System.currentTimeMillis() + TIME_INTERVAL + (ThreadLocalRandom.current().nextInt(0,TIME_INTERVAL)) % TIME_INTERVAL;
            
            public void run() {
            	for (;;) {
                    try {
                        intQ.take();
                        long now = System.currentTimeMillis();
                        longQ.put(now);
                        
                        if (now > end)
                            break;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    } 
                }
            }
        };
        
        Thread t = new Thread(r,"server-"+id);
        t.setDaemon(true);
        t.start();
    }
}
