package org.pentaho.ui.xul.impl;

import org.pentaho.ui.xul.XulDomContainer;

public interface XulEventHandler {

  /**
   * The name of this event handler instance; maps to event handler literals in the xul document.
   * @return The name of this event handler instance.
   */
  public abstract String getName();

  public abstract void setName(String name);

  public abstract void setXulDomContainer(XulDomContainer xulDomContainer);

  public abstract XulDomContainer getXulDomContainer();

  /**
   * A generic way of returning data from event handlers... can we do better than this?
   * Handle return values from invoked methods? possibly?  
   * @return any data associated with events that have been executed.
   */
  public abstract Object getData();

  /**
   * A generic way of passing data to the event handler. It seems we should maybe accept 
   * parameters instead of doing this. 
   * @param any data events may want to operate on.
   */
  public abstract void setData(Object data);

}