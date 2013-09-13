package org.pentaho.ui.xul;

import java.beans.PropertyChangeListener;

public interface XulEventSource {
  public void addPropertyChangeListener(PropertyChangeListener listener);
  public void removePropertyChangeListener(PropertyChangeListener listener);
}

  