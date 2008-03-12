package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.containers.XulBox;
import org.pentaho.ui.xul.swt.Orient;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtBox extends SwtElement implements XulBox {
  private static final long serialVersionUID = 582736100041411600L;

  protected Composite box;

  public SwtBox(XulElement parent, String tagName) {
    this(parent, tagName, Orient.HORIZONTAL);
  }

  public SwtBox(XulElement parent, String tagName, Orient orient) {
    super(tagName);
    box = createNewComposite((Composite) parent.getManagedObject());
    setOrient(orient.toString());
    managedObject = box;

  }

  public void addComponent(XulComponent component) {
    // intentionally do nothing
  }

  protected Composite createNewComposite(Composite parent) {
    return new Composite(parent, SWT.NONE);
  }

}
