package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.containers.XulGroupbox;
import org.pentaho.ui.xul.swt.Orient;


public class SwtGroupbox extends SwtBox implements XulGroupbox {
  private static final long serialVersionUID = 7414282626289178745L;

  private String label;
  
  public SwtGroupbox(XulElement parent, String tagName) {
    // XUL groupbox defaults to VERTICAL orientation
    super(parent, tagName, Orient.VERTICAL);
  }

  @Override
  protected Composite createNewComposite(Composite c) {
    return new Group(c, SWT.NONE);
  }

  public String getLabel() {
    return label;
  }

  //Changed it to setCaption to conform with the Xul element model <groupbox><caption label=''>...
  @Deprecated
  public void setLabel(String text) {
    label = text;
    ((Group)box).setText(text);
  }
  
  public void addComponent(XulComponent component) {
    // intentionally do nothing here
  }

  public void setCaption(String caption) {
    ((Group)box).setText(caption);    
  }

}
