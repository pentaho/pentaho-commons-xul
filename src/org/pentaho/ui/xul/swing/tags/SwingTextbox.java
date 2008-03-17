/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTextField;

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.swing.SwingElement;

/**
 * @author OEM
 *
 */
public class SwingTextbox extends SwingElement implements XulTextbox  {
  private JTextField textField;
  
  public SwingTextbox(XulElement parent, XulDomContainer domContainer, String tagName) {
    super("textbox");
    textField = new JTextField();
    textField.setPreferredSize(new Dimension(150,18));
    managedObject = textField;
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.components.XulLabel#setFont(java.awt.Font)
   */
  public void setFont(Font font) {
    // TODO Auto-generated method stub
    
  }
  
  public String getValue(){
    return textField.getText();
  }

  public void setValue(String text){
    textField.setText(text);
  }
  
  public void layout(){
  }

}
