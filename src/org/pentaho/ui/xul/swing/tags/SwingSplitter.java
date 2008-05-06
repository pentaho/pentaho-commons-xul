package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulSplitter;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingSplitter extends SwingElement implements XulSplitter{

  public SwingSplitter(XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("splitter");
    managedObject = "empty";
  }
}

  