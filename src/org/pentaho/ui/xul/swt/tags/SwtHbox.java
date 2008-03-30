package org.pentaho.ui.xul.swt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulHbox;
import org.pentaho.ui.xul.util.Orient;

public class SwtHbox extends SwtBox implements XulHbox {
  private static final long serialVersionUID = 7140735724393002713L;

  public SwtHbox(XulComponent parent, XulDomContainer container, String tagName) {
    super(parent, tagName, Orient.HORIZONTAL);
  }

}
