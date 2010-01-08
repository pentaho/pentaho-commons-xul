package org.pentaho.ui.xul.swt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulVbox;
import org.pentaho.ui.xul.util.Orient;
import org.pentaho.ui.xul.dom.Element;

public class SwtVbox extends SwtBox implements XulVbox {
  private static final long serialVersionUID = 1151231071964721100L;

  public SwtVbox(Element self, XulComponent parent, XulDomContainer container, String tagName) {
    super(parent, tagName, container, Orient.VERTICAL);
  }

}
