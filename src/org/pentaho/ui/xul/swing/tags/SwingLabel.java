/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.swing.SwingElement;

/**
 * @author OEM
 *
 */
public class SwingLabel extends SwingElement implements XulLabel{
  private JTextArea label;
  
  public SwingLabel(XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("label");
    label = new JTextArea();
    label.setLineWrap(true);
    label.setOpaque(false);
    managedObject = label;
  }
  
  public void layout(){
  }

  
  
  public void setValue(String value){
    label.setText(value);
    
  }
  public String getValue(){
    return label.getText();
    
  }

  public boolean isDisabled() {
    return !label.isEnabled();
  }

  public void setDisabled(boolean dis) {
    label.setEnabled(!dis);
  }

}
