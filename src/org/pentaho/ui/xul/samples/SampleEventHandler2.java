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
import org.pentaho.ui.xul.components.XulCheckbox;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.components.XulTextbox;

/**
 * @author OEM
 *
 */
public class SampleEventHandler2 extends XulEventHandler{
 

  public void sayHello(){
    XulTextbox textbox = (XulTextbox) document.elementByID("name");
    XulCheckbox checkbox = (XulCheckbox) document.elementByID("yell");
    XulLabel responseLabel = (XulLabel) document.selectSingleNode("/window/vbox/groupbox/vbox/label");
    
    if(textbox != null){
      System.out.println("found it");
      String name = textbox.getValue();
      
      String response;
      if(name.equals("")){
        response = "What was that?";
      } else {
        response = "Yea nice to meet you "+name;
      }
      
      if(checkbox.getSelected()){
        response = response.toUpperCase();
      }
      
      responseLabel.setText(response);
      
    } else {
      System.out.println("name textbox not found");
    }
  }
 
}
