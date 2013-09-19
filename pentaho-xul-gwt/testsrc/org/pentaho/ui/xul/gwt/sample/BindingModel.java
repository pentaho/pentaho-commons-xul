package org.pentaho.ui.xul.gwt.sample;

import org.pentaho.ui.xul.XulEventSourceAdapter;

public class BindingModel extends XulEventSourceAdapter{

  private String firstName;

  public String getFirstName() {
  
    return firstName;
  }

  public void setFirstName(String firstName) {
  
    this.firstName = firstName;
    this.firePropertyChange("firstName", null, firstName);
  }
  
  
}

  