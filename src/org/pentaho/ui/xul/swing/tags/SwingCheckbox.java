/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.Container;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.components.XulCheckbox;
import org.pentaho.ui.xul.components.XulGrid;

/**
 * @author OEM
 *
 */
public class SwingCheckbox extends XulElement implements XulCheckbox{
  private JCheckBox checkBox;
  
  public SwingCheckbox(String label){
    super("checkbox");
    checkBox = new JCheckBox(label);
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
  
}