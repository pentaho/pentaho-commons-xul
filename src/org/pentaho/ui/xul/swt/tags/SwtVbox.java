package org.pentaho.ui.xul.swt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulVbox;
import org.pentaho.ui.xul.util.Orient;

public class SwtVbox extends SwtBox implements XulVbox {
  private static final long serialVersionUID = 1151231071964721100L;

  public SwtVbox(XulComponent parent, XulDomContainer container, String tagName) {
    super(parent, tagName, Orient.VERTICAL);
  }

}
