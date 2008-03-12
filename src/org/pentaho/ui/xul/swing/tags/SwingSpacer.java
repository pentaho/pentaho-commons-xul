/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.Component;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.components.XulSpacer;

/**
 * @author OEM
 *
 */
public class SwingSpacer extends XulElement implements XulSpacer{
  private Component strut;
  public SwingSpacer(int size, int orientation){
    super("spacer");
    if(orientation == BoxLayout.Y_AXIS){
      strut = Box.createVerticalStrut(size);
    } else {
      strut = Box.createHorizontalStrut(size);
    }
    managedObject = strut;
        
  }
  public void setSize(int size){
    
  }

  public void layout(){
  }
}
