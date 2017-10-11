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

package org.pentaho.barcamp.forms;

import org.pentaho.barcamp.models.Contact;
import org.pentaho.ui.xul.XulEventSourceAdapter;
import org.pentaho.ui.xul.stereotype.Bindable;

public class ContactForm extends XulEventSourceAdapter {

  private String firstName, lastName, email;
  private Contact contact;

  @Bindable
  public String getEmail() {
    return email;
  }

  @Bindable
  public String getFirstName() {
    return firstName;
  }

  @Bindable
  public String getLastName() {
    return lastName;
  }

  @Bindable
  public void setEmail(String email) {
    String prevVal = this.email;
    this.email = email;

    firePropertyChange("email", prevVal, email);
  }

  @Bindable
  public void setFirstName(String firstName) {
    String prevVal = this.firstName;
    this.firstName = firstName;

    firePropertyChange("firstName", prevVal, firstName);
  }

  @Bindable
  public void setLastName(String lastName) {
    String prevVal = this.lastName;
    this.lastName = lastName;

    firePropertyChange("lastName", prevVal, lastName);
  }

  public void setContact(Contact contact) {
    setEmail(contact.getEmail());
    setFirstName(contact.getFirstName());
    setLastName(contact.getLastName());
  }

  public Contact getContact() {
    saveForm();
    return contact;
  }

  private void saveForm() {
    contact.setEmail(this.getEmail());
    contact.setFirstName(this.getFirstName());
    contact.setLastName(this.getLastName());
  }

  public void reset() {
    setEmail(null);
    setFirstName(null);
    setLastName(null);
    contact = new Contact();
  }

}
