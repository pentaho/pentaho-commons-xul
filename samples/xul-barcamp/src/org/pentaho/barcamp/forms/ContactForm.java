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
