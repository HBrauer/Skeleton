/*
  Based on course material for "Concurrent Programming Java", a seminar 
  prepared and owned by Angelika Langer & Klaus Kreft, November 2003
  contact: http://www.langer.camelot.de/ or mailto: langer@camelot.de

  © Copyright by Angelika Langer & Klaus Kreft.
  Permission to use, copy, and modify this software for any non-profit
  purpose is hereby granted to attendants of the above mentioned seminar 
  without fee, provided that the above copyright notice appears in all 
  copies.  Angelika Langer and Klaus Kreft make no representations about 
  the suitability of this software for any purpose.  It is provided 
  "as is" without express or implied warranty.
 */
package search;

import java.util.Collection;
import java.util.Iterator;

import test.FakeModifier;

public class MyCollection {
    private Collection<ElementType> col  = CollectionProvider.makeCollection(); 
    
	public int size() {
		return col.size();
	}

	public String toString() {
		return col.toString();
	}

	public boolean remove(Predicate<ElementType> isInteresting) {
		Iterator<ElementType> iter = col.iterator();
		boolean found = false;
		while (iter.hasNext()) {
			ElementType buf = iter.next();
			if (isInteresting.evaluate(buf)) {
				found = true;
				iter.remove();
			}
		}
		return found;
	}

	public boolean modify(Predicate<ElementType> isInteresting) {
		boolean found = false;
		ElementType buf = null;
		Iterator<ElementType> iter = col.iterator();

		while (iter.hasNext()) {
			try {Thread.sleep(100);} catch (InterruptedException e) {}	// for test purposes only
			buf = iter.next();
			if (isInteresting.evaluate(buf)) {
				found = true;
				break;
			}
		}

		if (found) {				
			// perform a time-consuming modification
			FakeModifier.modify(col,buf);
		}
		return found;
	}
}
