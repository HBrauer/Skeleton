/*
  Based on course material for "Concurrent Java", a seminar 
  prepared and owned by Angelika Langer & Klaus Kreft 
  contact: http://www.AngelikaLanger.com or mailto: contact@AngelikaLanger.com

  © Copyright June 2009 by Angelika Langer & Klaus Kreft.
  Permission to use, copy, and modify this software for any non-profit
  purpose is hereby granted to attendants of the above mentioned seminar 
  without fee, provided that the above copyright notice appears in all 
  copies.  Angelika Langer and Klaus Kreft make no representations about 
  the suitability of this software for any purpose.  It is provided 
  "as is" without express or implied warranty.
*/
package scheduling;

import java.util.concurrent.*;

public final class ScheduledWatchDog implements Runnable {
    private final ServerThread.ClientCtx client;
    private final int id;
    
    public ScheduledWatchDog(int id, ServerThread.ClientCtx client) {
        this.id = id;
        this.client = client;
    }
    public void run() {
       ... to be done ...
    }
}
