/*!
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
* Foundation.
*
* You should have received a copy of the GNU Lesser General Public License along with this
* program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
* or from the Free Software Foundation, Inc.,
* 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU Lesser General Public License for more details.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
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
