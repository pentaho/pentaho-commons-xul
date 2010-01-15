package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtLabel extends SwtElement implements XulLabel {
  private static final long serialVersionUID = 5202737172518086153L;
  
  private boolean disabled;
  CLabel cLabel;
  Label label;
  
  public SwtLabel(Element self, XulComponent parent, XulDomContainer container, String tagName){
    super(tagName);
    
   
    String multi = (self != null ) ? self.getAttributeValue("multiline") : null;
    if(multi != null && multi.equals("true")){
      label = new Label((Composite)parent.getManagedObject(), SWT.WRAP);
      setManagedObject(label);
    } else {
      cLabel = new CLabel((Composite)parent.getManagedObject(), SWT.NONE);
      setManagedObject(cLabel);
    }
  }

  /**
   * True parameter for bean-able attribute "value" (XUL attribute)
   * @param text
   */
  public void setValue( String text ) {
  	if(text == null) {
  		text = "";
  	}
  	if(label != null){
      label.setText(text);
      if(getParent() != null){
        label.getShell().layout(true);
      }
  	} else {
  	  cLabel.setText(text);
  	}
  }
  
  public String getValue() {
    return (label != null) ? label.getText() : cLabel.getText();
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
    if(label != null){
      if (!label.isDisposed()){
        label.setEnabled( !disabled );
      }
    } else {
      cLabel.setEnabled( ! disabled);
    }
  }

}
