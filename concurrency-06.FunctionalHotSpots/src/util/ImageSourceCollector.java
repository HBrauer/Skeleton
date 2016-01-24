/*
 * Created on 22.10.2003
 *
 */
package util;

import java.util.*;

import util.html.*;
import cvu.html.*;

/**
 * @author Angelika Langer
 *
 */
public final class ImageSourceCollector {
    private Set images = new HashSet();
    
    public SingleTagTokenAttributeProcessor getImageSourceProcessor() {
        return new imageProcessor();
    }

    private final class imageProcessor implements SingleTagTokenAttributeProcessor {
        public String getTagName() {
            return "img";
        }
        public String getAttributeName() {
            return "src";
        }
        public TagToken process(TagToken token) {
            String srcAttribute = ((TagToken)token).getAttribute(getAttributeName());
            images.add(srcAttribute);
            return token;
        }
    }
    
    public Set getImageFilenames() {
        return images;
    }

}
