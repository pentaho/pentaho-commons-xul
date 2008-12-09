package org.pentaho.ui.xul.gwt.util;

import org.pentaho.ui.xul.impl.XulEventHandler;

public interface EventHandlerWrapper {
  void execute(String methodName, Object[] args);
  void setHandler(XulEventHandler handler);
  XulEventHandler getHandler();
}

  