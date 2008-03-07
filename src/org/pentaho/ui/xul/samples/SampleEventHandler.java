/**
 * 
 */
package org.pentaho.ui.xul.samples;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.pentaho.ui.xul.XulEventHandler;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.components.XulTextbox;

/**
 * @author OEM
 *
 */
public class SampleEventHandler implements XulEventHandler{
  
  XulRunner xulRunner;
  private Document document;

  public void sayHello(){
    XulTextbox textbox = (XulTextbox) document.selectSingleNode("/window/vbox/hbox/textbox");

    if(textbox != null){
      System.out.println("found it");
      String name = textbox.getValue();
      
      XulLabel responseLabel = (XulLabel) document.selectSingleNode("/window/vbox/label");
      
      if(name.equals(""))
        responseLabel.setText("You should really type in your name");
      else
        responseLabel.setText("Hello there "+name);
        
      
    } else {
      System.out.println("name textbox not found");
    }
  }

  public XulRunner getXulRunner() {
    return xulRunner;
  }

  public void setXulRunner(XulRunner xulRunner) {
    this.xulRunner = xulRunner;
    this.document = xulRunner.getDocumentRoot();
  }
  
  
}
