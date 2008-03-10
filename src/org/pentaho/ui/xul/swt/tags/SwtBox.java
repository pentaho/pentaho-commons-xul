package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.containers.XulBox;
import org.pentaho.ui.xul.swt.Orient;

public class SwtBox extends XulElement implements XulBox {
  private static final long serialVersionUID = 582736100041411600L;

  protected Composite box;
  // XUL Box defaults to Horizontal
  private Orient orient = Orient.HORIZONTAL;

  public SwtBox(XulElement parent, String tagName) {
    this(parent, tagName, Orient.HORIZONTAL);
  }

  public SwtBox(XulElement parent, String tagName, Orient orient) {
    super(tagName);
    box = createNewComposite((Composite) parent.getManagedObject());
    this.orient = orient;
    setOrient(getOrient());
    managedObject = box;

  }

  public void setOrient(String orientation){
    orient = Orient.valueOf(orientation.toUpperCase());
    switch (orient) {
    case HORIZONTAL:
      box.setLayout(new RowLayout());
      break;
    case VERTICAL:
      box.setLayout(new RowLayout(SWT.VERTICAL));
      break;
    }
  }
  
  public String getOrient(){
    return orient.toString();
  }

  public void add(XulComponent component) {
    // intentionally do nothing
  }

  protected Composite createNewComposite(Composite parent) {
    return new Composite(parent, SWT.NONE);
  }
}
