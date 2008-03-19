/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.Component;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.components.XulSpacer;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.util.Orient;

/**
 * @author OEM
 *
 */
public class SwingSpacer extends SwingElement implements XulSpacer{
  private Component strut;
  
  public SwingSpacer(XulElement parent, XulDomContainer domContainer, String tagName) {
    super("spacer");

  }
  public void setWidth(int size){
    strut = Box.createHorizontalStrut(size);
    managedObject = strut;
  }
  public void setHeight(int size){
    strut = Box.createVerticalStrut(size);
    managedObject = strut;
  }

  public void layout(){
  }
}
