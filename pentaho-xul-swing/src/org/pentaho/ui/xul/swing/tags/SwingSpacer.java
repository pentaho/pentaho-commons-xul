/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.Component;

import javax.swing.Box;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulSpacer;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.dom.Element;

/**
 * @author nbaker
 *
 */
public class SwingSpacer extends SwingElement implements XulSpacer{
  private Component strut;
  
  public SwingSpacer(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
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
  

  @Override
  public void setFlex(int flex) {
    super.setFlex(flex);
    managedObject = Box.createGlue();
  }
  public void layout(){
  }
}
