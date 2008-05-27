package org.pentaho.ui.xul.samples;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.pentaho.ui.xul.XulEventSource;
import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;
import org.pentaho.ui.xul.util.XulDataForm;

public class FormModel extends XulDataForm {

  private String firstName = "";
  private String lastName = "";
  private boolean disabled = true;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    String previous = this.firstName;
    this.firstName = firstName;
    firePropertyChange("firstName", previous, firstName);
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    String previous = this.lastName;
    this.lastName = lastName;
    firePropertyChange("lastName", previous, lastName);
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(Boolean disabled) {
    boolean previous = this.disabled;
    this.disabled = disabled;
    firePropertyChange("disabled", previous, disabled);
  }

}

  