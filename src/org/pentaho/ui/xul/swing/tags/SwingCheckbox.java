/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulCheckbox;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

/**
 * @author OEM
 *
 */
public class SwingCheckbox extends SwingElement implements XulCheckbox{
  
  private JCheckBox checkBox;
  private static final Log logger = LogFactory.getLog(SwingCheckbox.class);
  
  public SwingCheckbox(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("checkbox");
    checkBox = new JCheckBox();
    
    checkBox.addItemListener(new ItemListener(){
      public void itemStateChanged(ItemEvent e) {
        SwingCheckbox.this.changeSupport.firePropertyChange("checked", null, ((JCheckBox) e.getSource()).isSelected());
      }
    });
    
    managedObject = checkBox;
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.components.XulCheckbox#getSelected()
   */
  public boolean getSelected() {
    // TODO Auto-generated method stub
    return checkBox.isSelected();
  }
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.components.XulCheckbox#setSelected(boolean)
   */
  public void setSelected(boolean selected) {
    checkBox.setSelected(selected);
  }
  

  public void layout(){
  }
  
  public void setLabel(String label){
    checkBox.setText(label);
  }

  public String getLabel() {
    return checkBox.getText();
  }

  public boolean isChecked() {
    return checkBox.isSelected();

  }

  public boolean isDisabled() {
    return !checkBox.isEnabled();
  }

  public void setChecked(boolean checked) {
   checkBox.setSelected(checked); 
  }

  public void setDisabled(boolean dis) {
    checkBox.setEnabled(!dis);
  }

  public void setCommand(final String method) {
    checkBox.addChangeListener(new ChangeListener(){
      public void stateChanged(ChangeEvent evt){
        invoke(method);
      }
    });
  }
  
  
}