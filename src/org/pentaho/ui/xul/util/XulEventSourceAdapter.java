package org.pentaho.ui.xul.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.pentaho.ui.xul.XulEventSource;

public class XulEventSourceAdapter implements XulEventSource{

  protected PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
  
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    changeSupport.addPropertyChangeListener(listener);
  }
  
  public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
    changeSupport.addPropertyChangeListener(propertyName, listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
    changeSupport.removePropertyChangeListener(listener);
  }
  
  protected void firePropertyChange(String attr, Object previousVal, Object newVal){
    if(previousVal == null && newVal == null){
      return;
    }
    changeSupport.firePropertyChange(attr, previousVal, newVal);
  }
}

  