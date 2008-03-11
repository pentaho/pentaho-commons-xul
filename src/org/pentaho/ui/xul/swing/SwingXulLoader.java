/**
 * 
 */
package org.pentaho.ui.xul.swing;

import org.dom4j.Document;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.XulParser;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.XulWindowContainer;
import org.pentaho.ui.xul.swing.SwingXulRunner;
import org.pentaho.ui.xul.swing.tags.SwingButtonHandler;
import org.pentaho.ui.xul.swing.tags.SwingHboxHandler;
import org.pentaho.ui.xul.swing.tags.SwingLabelHandler;
import org.pentaho.ui.xul.swing.tags.SwingScriptHandler;
import org.pentaho.ui.xul.swing.tags.SwingTextboxHandler;
import org.pentaho.ui.xul.swing.tags.SwingVboxHandler;
import org.pentaho.ui.xul.swing.tags.SwingWindowHandler;

/**
 * @author OEM
 *
 */
public class SwingXulLoader implements XulLoader {

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.XulLoader#loadXul(org.w3c.dom.Document)
   */
  public XulRunner loadXul(Document xulDocument) throws IllegalArgumentException, XulException{
    
    XulWindowContainer container = new XulWindowContainer();
    XulParser parser = new XulParser(container);

    //attach Renderers
    parser.registerHandler("WINDOW", new SwingWindowHandler());
    parser.registerHandler("BUTTON", new SwingButtonHandler());
    parser.registerHandler("VBOX", new SwingVboxHandler());
    parser.registerHandler("HBOX", new SwingHboxHandler());
    parser.registerHandler("LABEL", new SwingLabelHandler());
    parser.registerHandler("TEXTBOX", new SwingTextboxHandler());
    parser.registerHandler("SCRIPT", new SwingScriptHandler());
    
    parser.parseDocument(xulDocument.getRootElement());
    
    XulRunner runner = new SwingXulRunner();
    runner.addContainer(container);
    
    return runner;
  }
  

}
