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
public class SampleEventHandler extends XulEventHandler{
 

  public void sayHello(){
    
    XulTextbox textbox = (XulTextbox) document.getElementById("name");
    XulCheckbox checkbox = (XulCheckbox) document.getElementById("yell");
    XulLabel responseLabel = (XulLabel) document.getElementById("response");
    
    if(textbox != null){
      System.out.println("found it");
      String name = textbox.getValue();
      
      
      String response;
      if(name.equals("")){
        response = "You should really type in your name";
      } else {
        response = "Hello there "+name;
      }
      
      if(checkbox.isChecked()){
        response = response.toUpperCase();
      }
      
      responseLabel.setValue(response);
    } else {
      System.out.println("name textbox not found");
    }
  }
 
}
