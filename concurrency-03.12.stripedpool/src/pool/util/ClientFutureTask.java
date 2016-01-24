/*
  Based on course material for "Concurrent Programming Java", a seminar 
  prepared and owned by Angelika Langer & Klaus Kreft, November 2012
  contact: http://www.AngelikaLanger.com

  © Copyright 1995-2013 by Angelika Langer & Klaus Kreft.
  Permission to use, copy, and modify this software for any non-profit
  purpose is hereby granted to attendants of the above mentioned seminar 
  without fee, provided that the above copyright notice appears in all 
  copies.  Angelika Langer and Klaus Kreft make no representations about 
  the suitability of this software for any purpose.  It is provided 
  "as is" without express or implied warranty.
*/
package pool.util;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;


public class ClientFutureTask<V> extends FutureTask<V> implements HasClientId {
    private int clientId;
    private Object task;  // for debugging purposes only
    
    public ClientFutureTask(Runnable task, V result) {
        super(task,result);
        clientId = Utilities.getClientId(task);
        this.task = task;
    }
    public ClientFutureTask(Callable task) {
        super(task);
        clientId = Utilities.getClientId(task);
        this.task = task;
    }
    public int getClientId() {
        return clientId;
    }
    public String toString() {  // for debugging purposes only
        return task.toString();
    }
}