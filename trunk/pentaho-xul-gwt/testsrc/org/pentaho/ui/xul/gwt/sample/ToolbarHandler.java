/**
 * 
 */
package org.pentaho.ui.xul.gwt.sample;

import org.pentaho.ui.xul.components.XulCheckbox;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

import com.google.gwt.user.client.Window;

/**
 * @author OEM
 *
 */
public class ToolbarHandler extends AbstractXulEventHandler {
 

  public void sayHello(){
    Window.alert("hello");
  }


  public String getName(){
    return "toolbarHandler";
  }
  
}
