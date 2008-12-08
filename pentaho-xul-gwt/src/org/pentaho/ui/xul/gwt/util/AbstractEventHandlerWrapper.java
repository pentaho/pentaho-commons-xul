package org.pentaho.ui.xul.gwt.util;

import org.pentaho.ui.xul.impl.XulEventHandler;

public abstract class AbstractEventHandlerWrapper implements EventHandlerWrapper{
  protected XulEventHandler handler;
  public void setHandler(XulEventHandler handler) {
      this.handler = handler;
  }
  
}

  