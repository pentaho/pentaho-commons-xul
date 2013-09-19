/*
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Copyright 2009 Pentaho Corporation.  All rights reserved.
 */

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
