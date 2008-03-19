/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import javax.swing.JCheckBox;

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.components.XulCheckbox;
import org.pentaho.ui.xul.swing.SwingElement;

/**
 * @author OEM
 *
 */
public class SwingCheckbox extends SwingElement implements XulCheckbox{
  
  private JCheckBox checkBox;
  
  public SwingCheckbox(XulElement parent, XulDomContainer domContainer, String tagName) {
    super("checkbox");
    checkBox = new JCheckBox();
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
  
  
}