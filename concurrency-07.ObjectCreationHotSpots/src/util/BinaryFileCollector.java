/*
 * Created on 23.10.2003
 *
 */
package util;

import java.util.*;
import util.file.*;
import util.html.*;
import cvu.html.*;

/**
 * @author Angelika Langer
 *
 */
public final class BinaryFileCollector {
    private Set pdfFiles = new HashSet();
    
    public SingleTagTokenAttributeProcessor getPdfProcessor() {
        return new pdfProcessor();
    }

    private final class pdfProcessor implements SingleTagTokenAttributeProcessor {
        public String getTagName() {
            return "a";
        }
        public String getAttributeName() {
            return "href";
        }
        public TagToken process(TagToken token) {
            String srcAttribute = ((TagToken)token).getAttribute(getAttributeName());
            if (FileUtility.hasSuffix(srcAttribute,"pdf") || FileUtility.hasSuffix(srcAttribute,"zip")) {
                pdfFiles.add(srcAttribute);
            }
            return token;
        }
    }
    
    public Set getPdfFilenames() {
        return pdfFiles;
    }

}
