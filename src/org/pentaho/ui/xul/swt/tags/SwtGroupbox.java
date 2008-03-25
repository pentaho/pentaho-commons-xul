package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.containers.XulGroupbox;
import org.pentaho.ui.xul.util.Orient;


public class SwtGroupbox extends SwtBox implements XulGroupbox {
  private static final long serialVersionUID = 7414282626289178745L;

  public SwtGroupbox(XulElement parent, XulDomContainer container, String tagName) {
    // XUL groupbox defaults to VERTICAL orientation
    super(parent, tagName, Orient.VERTICAL);
  }

  @Override
  protected Composite createNewComposite(Composite c) {
    return new Group(c, SWT.NONE);
  }
  
  public void setCaption(String caption) {
    ((Group)box).setText(caption);    
  }

}
