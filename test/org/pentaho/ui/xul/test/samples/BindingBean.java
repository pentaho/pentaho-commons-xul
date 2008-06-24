package org.pentaho.ui.xul.test.samples;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.pentaho.ui.xul.XulEventSource;

public class BindingBean implements XulEventSource {

  private String property1, property2;


  protected PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    changeSupport.addPropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
    changeSupport.removePropertyChangeListener(listener);
  }

  protected void firePropertyChange(String attr, Object previousVal, Object newVal) {
    changeSupport.firePropertyChange(attr, previousVal, newVal);
  }

  public String getProperty1() {
    return property1;
  }

  public void setProperty1(String property1) {
    this.property1 = property1;
    firePropertyChange("property1", null, property1);
  }

  public String getProperty2() {
    return property2;
  }

  public void setProperty2(String property2) {
    this.property2 = property2;
    firePropertyChange("property2", null, property1);
  }

}
