/*
 * Created on 24.08.2004
 *
 */
package deadlink;

import util.html.SingleTagTokenAttributeProcessor;

/**
 * @author Angelika Langer
 *
 */
public abstract class SupportedSingleTagTokenAttributeProcessor 
    implements SingleTagTokenAttributeProcessor {
        protected DeadLinkDetector.ProcessorSupport support;
        
        protected SupportedSingleTagTokenAttributeProcessor() {
        }
        protected SupportedSingleTagTokenAttributeProcessor(DeadLinkDetector detector) {
            detector.giveSupportTo(this);
        }
        public void receiveSupport(DeadLinkDetector.ProcessorSupport support) {
            this.support = support;
        }
}
