package org.pentaho.ui.xul.impl;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.dom.Element;

public interface XulElementFactory
{
  XulComponent create (final Element e, final XulComponent c, final XulDomContainer parent, final String tag) throws XulException;
}
