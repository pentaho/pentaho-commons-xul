/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JTextPane;
import javax.swing.text.JTextComponent;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

/**
 * @author OEM
 *
 */
public class SwingLabel extends SwingElement implements XulLabel{
  private static final Color DISABLED_TEXT_COLOR = Color.decode("#777777");
  private JTextComponent label;
  
  public SwingLabel(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("label");
    label = new JTextPane();
    label.setEditable(false);
    label.setFocusable(false);
    label.setOpaque(false);
    label.setDisabledTextColor(DISABLED_TEXT_COLOR);
    
    setManagedObject(label);
    Dimension size = label.getPreferredSize();
    size.width = 100;
    label.setMinimumSize(size);
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
    boolean oldValue = !label.isEnabled();
    label.setEnabled(!dis);
    this.firePropertyChange("disabled", oldValue, dis);
  }

}
