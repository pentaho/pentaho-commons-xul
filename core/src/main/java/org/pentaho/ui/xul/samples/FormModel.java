/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.ui.xul.samples;

import org.pentaho.ui.xul.XulEventSourceAdapter;

public class FormModel extends XulEventSourceAdapter {

  private String firstName = "";
  private String lastName = "";
  private boolean disabled = true;
  private boolean enabled = true;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName( String firstName ) {
    String previous = this.firstName;
    this.firstName = firstName;
    firePropertyChange( "firstName", previous, firstName );
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName( String lastName ) {
    String previous = this.lastName;
    this.lastName = lastName;
    firePropertyChange( "lastName", previous, lastName );
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled( boolean disabled ) {
    boolean previous = this.disabled;
    this.disabled = disabled;
    firePropertyChange( "disabled", previous, disabled );
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled( boolean enabled ) {
    boolean previous = this.enabled;
    this.enabled = enabled;
    firePropertyChange( "enabled", previous, enabled );
  }

}
