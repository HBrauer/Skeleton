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


public final class Utilities {
    public static int getClientId(Runnable r) {
        return ((HasClientId)r).getClientId();
    }
    public static int getClientId(Callable r) {
        return ((HasClientId)r).getClientId();
    }
    public static boolean isClientCommand(Runnable r) {
        return r instanceof HasClientId;
    }
    public static boolean isClientCommand(Callable r) {
        return r instanceof HasClientId;
    }
}
