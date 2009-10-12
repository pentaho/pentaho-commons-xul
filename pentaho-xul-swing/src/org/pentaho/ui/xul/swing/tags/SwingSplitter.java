package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulSplitter;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.dom.Element;

public class SwingSplitter extends SwingElement implements XulSplitter{

  public SwingSplitter(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("splitter");
    setManagedObject("empty");
  }
}

  