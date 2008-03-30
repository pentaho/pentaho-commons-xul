/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.Dimension;

import javax.swing.JTextField;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.swing.SwingElement;

/**
 * @author nbaker
 *
 */
public class SwingTextbox extends SwingElement implements XulTextbox  {
  private JTextField textField;
  
  public SwingTextbox(XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("textbox");
    textField = new JTextField();
    textField.setPreferredSize(new Dimension(150,18));
    managedObject = textField;
  }

  public String getValue(){
    return textField.getText();
  }

  public void setValue(String text){
    textField.setText(text);
  }
  
  public void layout(){
  }

  public int getMaxlength() {
    return 0;
  }

  public boolean isDisabled() {
    return !textField.isEnabled();
  }

  public void setDisabled(boolean dis) {
    textField.setEnabled(!dis);
  }

  public void setMaxlength(int length) {
  }

  public boolean isMultiline() {
    return false;
  }

  public void setMultiline(boolean multi) {
    
  }

}
