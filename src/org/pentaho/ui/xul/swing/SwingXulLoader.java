/**
 * 
 */
package org.pentaho.ui.xul.swing;

import org.dom4j.Document;
import org.dom4j.Element;
import org.pentaho.core.util.CleanXmlHelper;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.XulParser;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.swing.tags.SwingWindowHandler;
import org.pentaho.ui.xul.swing.tags.SwingWindow;

/**
 * @author OEM
 *
 */
public class SwingXulLoader implements XulLoader {

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulLoader#loadXul(org.w3c.dom.Document)
   */
  public SwingXulRunner loadXul(Document xulDocument) throws IllegalArgumentException, XulException{

    
    SwingXulRunner runner = new SwingXulRunner();
    XulParser parser = new XulParser(runner);
    parser.parseDocument(xulDocument.getRootElement());
    return runner;
  }
  

}
