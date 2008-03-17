package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.XulWindowContainer;
import org.pentaho.ui.xul.swt.Orient;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtListbox extends SwtElement implements XulContainer{
  private static final long serialVersionUID = 3064125049914932493L;

  private List listBox;
  private boolean disabled = false;
  private boolean selType;
  private int rowsToDisplay = 0;
  
  public SwtListbox(XulElement parent, XulDomContainer container, String tagName) {
    super(tagName);
    listBox = new List((Composite)parent.getManagedObject(), SWT.BORDER | SWT.SINGLE);
    managedObject = listBox;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
    if (!listBox.isDisposed()) listBox.setEnabled( !disabled );
  }

  public int getRows() {
    return rowsToDisplay;
  }

  public void setRows(int rowsToDisplay) {
    this.rowsToDisplay = rowsToDisplay;
    
  }

  public boolean isSeltype() {
    return selType;
  }

  public void setSeltype(boolean selType) {
    this.selType = selType;
    
  }

  public void addComponent(XulComponent component) {
    // TODO do nothing
    
  }

  public Orient getOrientation() {
    return null;
  }

}
