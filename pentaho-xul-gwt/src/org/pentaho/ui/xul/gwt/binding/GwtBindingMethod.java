package org.pentaho.ui.xul.gwt.binding;

import org.pentaho.ui.xul.XulException;

public interface GwtBindingMethod {
  public Object invoke(Object obj, Object[] args) throws XulException;
}

  