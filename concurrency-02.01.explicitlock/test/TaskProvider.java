package test;

import search.ElementType;
import search.MyCollection;
import search.Predicate;

class TaskProvider {
    private MyCollection col;
    
    public TaskProvider(MyCollection c) { col = c; }
    
    public Runnable getModifier() {
    	return new Runnable() {
		public void run() {
			for (int i=0; i<col.size()/2; i++) {
				final int toBeModified = i;
				try {					
				     System.out.println(Thread.currentThread().getName()+" attempt modifying: "+toBeModified);
				     if (col.modify(new Predicate<ElementType>() {
				    	 public boolean evaluate(ElementType buf) {
				 			try {Thread.sleep(10);} catch (InterruptedException e) {}
				    		 int val = buf.getImmutablePart();	
				    		 return val == toBeModified;
				    	 }
				     }))
				        System.out.println(Thread.currentThread().getName()+" successfully modified: "+toBeModified);
				     else 
				        System.out.println(Thread.currentThread().getName()+" cannot modify: "+toBeModified+" (element does not exist in collection)");
				} catch (Exception e) {
					System.out.println(Thread.currentThread().getName()+" failed modifying: "+toBeModified+" (exception occurred)");
					e.printStackTrace();
				}
			}
		}
	};
    }
    public Runnable getRemover() {
    	return new Runnable() {
		public void run() {
			for (int i=0; i<col.size()/2; i++) {
				final int toBeRemoved = i*2;
				try {					 
				     System.out.println(Thread.currentThread().getName()+" attempt removing: "+toBeRemoved);
		    	 
				     if (col.remove(new Predicate<ElementType>() {
				    	 public boolean evaluate(ElementType buf) {
				    		 int val = buf.getImmutablePart();	
				    		 return val == toBeRemoved;
				    	 }
				     }))
				        System.out.println(Thread.currentThread().getName()+" successfully removed: "+toBeRemoved);
				     else 
				        System.out.println(Thread.currentThread().getName()+" cannot remove: "+toBeRemoved+" (element does not exist in collection)");
				} catch (Exception e) {
					System.out.println(Thread.currentThread().getName()+" failed removing: "+toBeRemoved+" (exception occurred)");
					e.printStackTrace();
				}
			}
		}
	};
    }
  }