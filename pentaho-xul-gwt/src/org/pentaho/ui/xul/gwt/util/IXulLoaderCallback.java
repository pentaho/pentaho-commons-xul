package org.pentaho.ui.xul.gwt.util;

import org.pentaho.ui.xul.gwt.GwtXulRunner;

public interface IXulLoaderCallback {
  public void xulLoaded(GwtXulRunner runner);
  public void overlayLoaded();
  public void overlayRemoved();
}

  
  