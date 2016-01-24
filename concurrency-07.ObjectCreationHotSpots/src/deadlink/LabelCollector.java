package deadlink;

import util.html.*;

public final class LabelCollector {
	public static void collect(String absolutePathName) {
       
    //  System.out.println("COLLECT - FILE: "+absolutePathName);
               
        TagTokenVisitor tagProcessor = new TagTokenVisitor();
        tagProcessor.register(new AttributeProcessorImplementation(new LabelProcessor(absolutePathName)));
        new HmtlFileProcessor(tagProcessor).processHtmlFile(absolutePathName,null); 
	}
}
