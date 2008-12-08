package org.pentaho.ui.xul.gwt.util;

import org.pentaho.ui.xul.impl.XulEventHandler;

public interface EventHandlerWrapper {
  void execute(String methodName);
  void setHandler(XulEventHandler handler);
  XulEventHandler getHandler();
}

  