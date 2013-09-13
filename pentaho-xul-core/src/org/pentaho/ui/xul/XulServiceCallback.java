package org.pentaho.ui.xul;

public interface XulServiceCallback<T> {
  public void success(T retVal);
  public void error(String message, Throwable error);
}

  