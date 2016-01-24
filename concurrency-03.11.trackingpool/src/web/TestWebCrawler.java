package web;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public final class TestWebCrawler extends WebCrawler {

	public TestWebCrawler(Set<URL> urls) {
		super(urls);
	}

	/*
	 * nonsensical fake implementation of page processing:
	 * simply adds a character to the URL and returns the modified URL
	 */
	protected List<URL> processPage(URL url) {
		System.out.println(url);
		List<URL> urls = new ArrayList<URL>();
		try {
			urls.add(new URL(url + "x"));
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			return null;
		}
		try {			
			Thread.sleep(100);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
		return urls;
	}

	public static void main(String[] args) throws MalformedURLException {
		Set<URL> urlsToCrawl = new HashSet<URL>();
		urlsToCrawl.add(new URL("http://www.oose.de/"));
		WebCrawler crawler =  new TestWebCrawler(urlsToCrawl);
		/*
		 * repeatedly start and stop the crawler to see whether it resumes
		 * after a stop and processes the remaining pages when restarted
		 */
		for (int i = 0; i < 3; i++) {
			crawler.start();
			try {
				Thread.sleep(1000);
				crawler.stop();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
