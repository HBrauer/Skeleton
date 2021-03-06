/*
 * Created on 22.10.2003
 *
 */
package util.html;

import java.util.*;
import java.io.*;

import util.SiteDescription;
import util.file.FileUtility;

import cvu.html.*;

/**
 * @author Angelika Langer
 * 
 */
public class HmtlFileProcessor {
	private TagTokenProcessor processor;

	public HmtlFileProcessor(TagTokenProcessor processor) {
		this.processor = processor;
	}

	public void processPage(String page) {
		SiteDescription siteDesc = SiteDescription.getSiteDescription();
		String htmlFilename = siteDesc.getBodyFilenameForPage(page);
		String tmpHtmlFilename = siteDesc.getTemporaryBodyFilenameForPage(page);
		FileUtility.makeDirectories(new File(tmpHtmlFilename));

		processHtmlFile(htmlFilename, tmpHtmlFilename);
	}

	public void processHtmlFile(String inputFilename, String outputFilename) {

		PrintWriter output = null;
		if (outputFilename != null) {
			try {
				output = new PrintWriter(new OutputStreamWriter(
						new FileOutputStream(outputFilename)));
			} catch (Exception e) {
				System.err.println(e);
			}
		}

		HTMLTokenizer ht = new HTMLTokenizer(inputFilename);
		Enumeration e = ht.getTokens();

		while (e.hasMoreElements()) {
			Object token = e.nextElement();

			if (token instanceof TagToken) {
				token = processor.process((TagToken) token);
				if (output != null)
					output.println(token.toString());
			} else {
				if (output != null)
					output.print(token.toString());
			}
		}
		if (output != null) {
			output.flush();
			output.close();
		}
	}
}
