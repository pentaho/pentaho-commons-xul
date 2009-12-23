package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.dom.Element;

public class SwtLabel extends SwtElement implements XulLabel {
  private static final long serialVersionUID = 5202737172518086153L;
  
  private boolean disabled;
  org.eclipse.swt.widgets.Label label;
  
  public SwtLabel(Element self, XulComponent parent, XulDomContainer container, String tagName){
    super(tagName);
    label = new org.eclipse.swt.widgets.Label((Composite)parent.getManagedObject(), SWT.WRAP);
    setManagedObject(label);
  }

  /**
   * True parameter for bean-able attribute "value" (XUL attribute)
   * @param text
   */
  public void setValue( String text ) {
    label.setText(text);
    if(getParent() != null){
      label.getShell().layout(true);
    }
  }
  
  public String getValue() {
    return label.getText();
  }
 
  /**
   * XUL's attribute is "disabled", thus this acts
   * exactly the opposite of SWT. If the property is not 
   * available, then the control is enabled. 
   * @return boolean true if the control is disabled.
   */
  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
    if (!label.isDisposed()) label.setEnabled( !disabled );
  }

}
