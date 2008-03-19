package org.pentaho.ui.xul.swt.tags;

import java.awt.Font;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtLabel extends SwtElement implements XulLabel {
  private static final long serialVersionUID = 5202737172518086153L;
  
  private boolean disabled;
  org.eclipse.swt.widgets.Label label;
  
  public SwtLabel(XulElement parent, XulDomContainer container, String tagName){
    super(tagName);
    label = new org.eclipse.swt.widgets.Label((Composite)parent.getManagedObject(), SWT.WRAP);
    managedObject = label;
  }

  public void setFont(Font font) {

  }
  
  /**
   * True parameter for bean-able attribute "value" (XUL attribute)
   * @param text
   */
  public void setValue( String text ) {
    label.setText(text);
  }
  
  public String getValue() {
    return label.getText();
  }
 
  /**
   * Complies with interface XulLabel, but 
   * realize this overrides AbstractElement's 
   * setText().
   */
  public void setText( String text ) {
    setValue(text);
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
