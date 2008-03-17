package org.pentaho.ui.xul.swt.tags;

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.XulWindowContainer;
import org.pentaho.ui.xul.containers.XulHbox;
import org.pentaho.ui.xul.swt.Orient;

public class SwtHbox extends SwtBox implements XulHbox {
  private static final long serialVersionUID = 7140735724393002713L;

  public SwtHbox(XulElement parent, XulDomContainer container, String tagName) {
    super(parent, tagName, Orient.HORIZONTAL);
  }

}
