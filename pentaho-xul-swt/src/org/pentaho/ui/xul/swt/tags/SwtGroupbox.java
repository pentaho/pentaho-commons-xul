package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulGroupbox;
import org.pentaho.ui.xul.util.Orient;
import org.pentaho.ui.xul.dom.Element;


public class SwtGroupbox extends SwtBox implements XulGroupbox {
  private static final long serialVersionUID = 7414282626289178745L;

  public SwtGroupbox(Element self, XulComponent parent, XulDomContainer container, String tagName) {
    // XUL groupbox defaults to VERTICAL orientation
    super(parent, tagName, container, Orient.VERTICAL);
  }

  @Override
  protected Composite createNewComposite(Composite c) {
    return new Group(c, SWT.NONE);
  }
  
  public void setCaption(String caption) {
    ((Group)box).setText(caption);    
  }

}
