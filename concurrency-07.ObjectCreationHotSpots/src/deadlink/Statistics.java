/*
 * Created on 25.11.2003
 *
 */
package deadlink;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;


final class Statistics {
	    private int totalNumberOfLinks = 0;
	    private int numberOfDeadLinks = 0;
	    private int numberOfRelativeLinks = 0;
	    private int numberOfLocalLabels = 0;
	    private int numberOfHttpLinks = 0;
        private int numberOfMailtoLinks = 0;
        private HashMap deadLinks = null;
        private long startTimeElapsed;
        
        public Statistics(HashMap deadLinks) {
            this.deadLinks = deadLinks;
            this.startTimeElapsed = System.currentTimeMillis();  // milliseconds
        }
        public void incrementTotalNumberOfLinks() {
            totalNumberOfLinks++;
        }
        public void incrementNumberOfDeadLinks() {
            numberOfDeadLinks++;
        }
        public void incrementNumberOfRelativeLinks() {
            numberOfRelativeLinks++;
        }
        public void incrementNumberOfLocalLabels() {
            numberOfLocalLabels++;
        }
        public void incrementNumberOfHttpLinks() {
            numberOfHttpLinks++;
        }
        public void incrementNumberOfMailtoLinks() {
            numberOfMailtoLinks++;
        }     
	    public String toString() {
	    	long executionTimeElapsed = System.currentTimeMillis() - startTimeElapsed;
	    	
	    	StringBuffer buf = new StringBuffer("-- statistics for dead link detection --");
	    	buf.append('\n');
        	buf.append("total number of links    : "+totalNumberOfLinks);
        	buf.append('\n');
        	buf.append("number of local labels   : "+numberOfLocalLabels);
        	buf.append('\n');
        	buf.append("number of http links     : "+numberOfHttpLinks);
        	buf.append('\n');
            buf.append("number of mailto links   : "+numberOfMailtoLinks);
            buf.append('\n');
        	buf.append("number of relative links : "+numberOfRelativeLinks);
        	buf.append('\n');
        	buf.append("number of dead links     : "+deadLinks.size());
        	buf.append('\n');
//      	buf.append("number of dead links     : "+numberOfDeadLinks);
//		    buf.append('\n');
//		    if (numberOfDeadLinks != deadLinks.size()) 
//	    		buf.append(">>> dead link appears multiply:  counter="+numberOfDeadLinks+"  listsize="+deadLinks.size());
		    buf.append('\n');
		    buf.append("elapsed execution time   : "+executionTimeElapsed+" ms");
		    buf.append('\n');
		    buf.append("milliseconds per link    : "+(executionTimeElapsed / totalNumberOfLinks)+" ms");
		    return buf.toString();
       }
}