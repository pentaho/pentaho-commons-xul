/**
 * 
 */
package org.pentaho.ui.xul.samples;

import org.pentaho.ui.xul.components.XulCheckbox;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

/**
 * @author OEM
 *
 */
public class SampleEventHandler extends AbstractXulEventHandler{
 

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

  @Override
  public Object getData() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setData(Object data) {
    // TODO Auto-generated method stub
    
  }

}
