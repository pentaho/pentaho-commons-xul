/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.Font;

import javax.swing.JLabel;

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.swing.SwingElement;

/**
 * @author OEM
 *
 */
public class SwingLabel extends SwingElement implements XulLabel{
  private JLabel label;
  
  public SwingLabel(XulElement parent, XulDomContainer domContainer, String tagName) {
    super("label");
    label = new JLabel();
    managedObject = label;
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.components.XulButton#click()
   */
  public void click() {
    // TODO Auto-generated method stub
    
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.components.XulButton#dblClick()
   */
  public void dblClick() {
    // TODO Auto-generated method stub
    
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.components.XulLabel#setFont(java.awt.Font)
   */
  public void setFont(Font font) {
    // TODO Auto-generated method stub
    
  }
  
  public void layout(){
  }

  
  public void setValue(String value){
    label.setText(value);
    
  }
  public String getValue(){
    return label.getText();
    
  }

}
