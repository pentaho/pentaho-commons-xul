package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulBox;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.Orient;

public class SwtBox extends SwtElement implements XulBox {
  private static final long serialVersionUID = 582736100041411600L;

  protected Composite box;

  public SwtBox(XulComponent parent, XulDomContainer container, String tagName) {
    this(parent, tagName, Orient.HORIZONTAL);
  }

  public SwtBox(XulComponent parent, String tagName, Orient orient) {
    super(tagName);
    box = createNewComposite((Composite) parent.getManagedObject());
    setOrient(orient.toString());
    managedObject = box;

  }

  protected Composite createNewComposite(Composite parent) {
    return new Composite(parent, SWT.NONE);
  }

}
