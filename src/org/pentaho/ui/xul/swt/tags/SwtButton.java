package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtButton extends SwtElement implements XulButton {
  private static final long serialVersionUID = -7218075117194366698L;

  protected org.eclipse.swt.widgets.Button button;
  private String label;
  private boolean disabled;

  public SwtButton(XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    button = createNewButton((Composite)parent.getManagedObject());
    managedObject = button;
  }
  
  protected Button createNewButton(Composite parent) {
    return new Button(parent, SWT.NONE);
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
    if (!button.isDisposed()) button.setEnabled( !disabled );
  }

  public void setOnclick(final String method) {
    button.addSelectionListener(new SelectionAdapter(){
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent arg0){
        Element rootElement = getDocument().getRootElement();
        XulWindow window = (XulWindow) rootElement;
        window.invoke(method, new Object[]{});
      }
    });

  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
    button.setText(label);
  }

}
