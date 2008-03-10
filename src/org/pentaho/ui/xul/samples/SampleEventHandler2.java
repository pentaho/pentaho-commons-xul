/**
 * 
 */
package org.pentaho.ui.xul.samples;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.pentaho.ui.xul.XulEventHandler;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.XulWindowContainer;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.components.XulTextbox;

/**
 * @author OEM
 *
 */
public class SampleEventHandler2 extends XulEventHandler{
 

  public void sayHello(){
    XulTextbox textbox = (XulTextbox) document.selectSingleNode("/window/vbox/hbox/textbox");

    if(textbox != null){
      System.out.println("found it");
      String name = textbox.getValue();
      
      XulLabel responseLabel = (XulLabel) document.selectSingleNode("/window/vbox/label");
      
      if(name.equals("")){
        responseLabel.setText("What was that?");
      } else {
        responseLabel.setText("Yea nice to meet you "+name);
      }
        
      
    } else {
      System.out.println("name textbox not found");
    }
  }
 
}
