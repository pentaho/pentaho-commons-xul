/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/


package org.pentaho.barcamp.models;

import org.pentaho.ui.xul.XulEventSourceAdapter;
import org.pentaho.ui.xul.stereotype.Bindable;

public class Contact extends XulEventSourceAdapter {

  private String firstName, lastName, email;

  public Contact() {

  }

  public Contact(String firstName, String lastName, String email) {
    setFirstName(firstName);
    setLastName(lastName);
    setEmail(email);
  }

  @Bindable
  public String getFirstName() {
    return firstName;
  }

  @Bindable
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Bindable
  public String getLastName() {
    return lastName;
  }

  @Bindable
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Bindable
  public String getEmail() {
    return email;
  }

  @Bindable
  public void setEmail(String email) {
    this.email = email;
  }


}
