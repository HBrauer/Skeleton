package web;

import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

import executor.TrackingExecutor;
import annotations.GuardedBy;

public abstract class WebCrawler {
	private static long TIMEOUT = 10;
	private static TimeUnit UNIT = TimeUnit.MILLISECONDS;
    private volatile ExecutorService exec;
    @GuardedBy("this")
    private final Set<URL> urlsToCrawl = new HashSet<URL>();
    
    public WebCrawler(Set<URL> urls) {
    	for (URL url : urls)
    	   urlsToCrawl.add(url);
    }
    
    public synchronized void start() {
        exec = Executors.newCachedThreadPool();
        for (URL url : urlsToCrawl) submitCrawlTask(url);
        urlsToCrawl.clear();
    }

    public synchronized void stop() throws InterruptedException {
        try {
        	// save tasks that were not started due to shutdown
            saveUncrawled(exec.shutdownNow());
            exec.awaitTermination(TIMEOUT, UNIT);
            // TODO: save tasks that were cancelled due to shutdown
        } finally {
            exec = null;
        }
    }

    protected abstract List<URL> processPage(URL url);

    private void saveUncrawled(List<Runnable> uncrawled) {
        for (Runnable task : uncrawled)
            urlsToCrawl.add(((CrawlTask) task).getPage());
    }
    private void submitCrawlTask(URL u) {
        exec.execute(new CrawlTask(u));
    }
    private class CrawlTask implements Runnable {
        private final URL url;
        public CrawlTask(URL u) { url = u; }
        public URL getPage() { return url; }
        public void run() {
            for (URL link : processPage(url)) {
                if (Thread.currentThread().isInterrupted())
                    return;
                submitCrawlTask(link);
            }
        }       
    }
}
