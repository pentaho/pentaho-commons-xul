package org.pentaho.ui.xul.swt.tags;

import java.awt.event.ActionEvent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.widgets.Composite;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.XulWindowContainer;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.tags.SwingButton;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtButton extends SwtElement implements XulButton {

  /**
   * 
   */
  private static final long serialVersionUID = -7218075117194366698L;
  private org.eclipse.swt.widgets.Button button;
  private String label;
  // private String command;
  private boolean disabled;

  public SwtButton(XulElement parent, XulDomContainer container, String tagName) {
    super(tagName);
    button = new org.eclipse.swt.widgets.Button((Composite)parent.getManagedObject(), SWT.NONE);
    managedObject = button;
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


  public void click() {
    // TODO Auto-generated method stub

  }

  public void dblClick() {
    // TODO Auto-generated method stub

  }

  public void setOnClick(final String method) {
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
