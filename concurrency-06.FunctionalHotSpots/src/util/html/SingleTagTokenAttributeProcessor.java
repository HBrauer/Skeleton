/*
 * Created on 22.10.2003
 *
 */
package util.html;

import cvu.html.*;

/**
 * @author Angelika Langer
 *
 */
public interface SingleTagTokenAttributeProcessor {
    public TagToken process(TagToken token);
    public String getTagName();
    public String getAttributeName();
}
