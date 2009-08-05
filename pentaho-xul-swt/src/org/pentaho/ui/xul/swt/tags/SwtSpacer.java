/**
 * 
 */
package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulSpacer;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;

/**
 * @author nbaker
 *
 */
public class SwtSpacer extends SwtElement implements XulSpacer{
  private Label strut;
  
  public SwtSpacer(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("spacer");
    strut = new Label((Composite) parent.getManagedObject(), SWT.NONE);
    strut.setSize(5,5);
    managedObject = strut;
  }
  public void setWidth(int size){
    int y = strut.getSize().y;    
    strut.setSize(size, y);
  }
  public void setHeight(int size){
    int x = strut.getSize().x;
    strut.setSize(x, size);
  }
  

  @Override
  public void setFlex(int flex) {
    super.setFlex(flex);
  }
  public void layout(){
  }
}
