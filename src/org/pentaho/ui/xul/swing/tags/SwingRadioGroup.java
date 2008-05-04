/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.GridBagLayout;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulRadioGroup;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.util.Orient;

/**
 * @author aphillips
 *
 */
public class SwingRadioGroup extends SwingElement implements XulRadioGroup {
	private static final Log logger = LogFactory.getLog(SwingRadioGroup.class);
	private ButtonGroup buttonGroup = new ButtonGroup();

  public SwingRadioGroup(XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("radiogroup");
    container = new JPanel(new GridBagLayout());
    
    managedObject = container;
    
    resetContainer();
  }

  @Override
  public void addComponent(XulComponent c) {
    addComponentToButtonGroup(c);

    super.addComponent(c);
  }
  
  protected void addComponentToButtonGroup(XulComponent c) {
    for(XulComponent child : c.getChildNodes()) {
      addComponentToButtonGroup(child);
    }
    if(AbstractButton.class.isAssignableFrom(c.getManagedObject().getClass())) {
      this.buttonGroup.add((AbstractButton)c.getManagedObject());
    }
  }
  
  @Override
  public Orient getOrientation() {
    return Orient.VERTICAL;
  }
}  
  
