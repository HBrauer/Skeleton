package deadlink;

import java.io.File;
import cvu.html.TagToken;
import util.file.*;

public final class LabelProcessor extends SupportedSingleTagTokenAttributeProcessor {
    private String htmlFileName = null;

    public LabelProcessor(String htmlFileName) {
        this.htmlFileName = htmlFileName;
    }        
    public LabelProcessor(DeadLinkDetector detector) {
        super(detector);
        this.htmlFileName = support.getPageDescription().getFilename();
    }
    public LabelProcessor(DeadLinkDetector detector, File fil) {
        super(detector);
        if (fil != null) 
            this.htmlFileName = FileUtility.changeRealPathToSymbolicPath(fil.getPath());
        else
            this.htmlFileName = support.getPageDescription().getFilename();
    }

	public TagToken process(TagToken token) {
        String nameAttribute = ((TagToken)token).getAttribute(getAttributeName());
        recordLabel(nameAttribute);
        return token;
	}
    public String getTagName() {
        return "a";
    }
    public String getAttributeName() {
        return "name";
    }
    private void recordLabel(String nameAttribute) {
        LabelChecker.addLabel(htmlFileName,nameAttribute);
    }
}
