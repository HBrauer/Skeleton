/*
 * Created on 07.10.2004
 *
 */
package deadlink;

import java.io.File;

import util.SiteDescription;


public final class PageDescription {
    private String pageName;
    private SiteDescription siteDescr;

    public PageDescription(File htmlFile) {
        this.pageName     = null;
        this.siteDescr    = null;
    }    
    public PageDescription(String pageName) {
        this.pageName     = pageName;
        this.siteDescr    = SiteDescription.getSiteDescription();
    } 
    public String getFilename() {
        return siteDescr.getBodyFilenameForPage(pageName);
    }
}