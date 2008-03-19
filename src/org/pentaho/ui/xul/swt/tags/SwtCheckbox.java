package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.components.XulCheckbox;

public class SwtCheckbox extends SwtButton implements XulCheckbox {

  public SwtCheckbox(XulElement parent, XulDomContainer container, String tagName) {
    super(parent, container, tagName);
  }

  @Override
  protected Button createNewButton(Composite parent) {
    return new Button(parent, SWT.CHECK);
  }
  public boolean isChecked() {
    return button.getSelection();
  }

  public void setChecked(boolean checked) {
    button.setSelection(checked);
  }


}
