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

import java.util.ArrayList;
import java.util.List;

import org.pentaho.ui.xul.XulEventSourceAdapter;
import org.pentaho.ui.xul.stereotype.Bindable;

public class Workspace extends XulEventSourceAdapter {

  private String searchString;

  private Contact selectedContact;

  private List<Contact> contactList = new ArrayList<Contact>();

  {
    contactList.add(new Contact("Nick", "Baker", "nbaker@pentaho.com"));
    contactList.add(new Contact("Tom", "Jones", "tj@gmail.com"));
    contactList.add(new Contact("Wil", "Gorman", "wgorman@pentaho.com"));
    contactList.add(new Contact("Joe", "Tester", "tester@gmail.com"));
  }

  @Bindable
  public Contact getSelectedContact() {
    return selectedContact;
  }

  @Bindable
  public void setSelectedContact(Contact selectedContact) {
    Contact prevVal = this.selectedContact;
    this.selectedContact = selectedContact;

    // Need this to support two-way bindings
    firePropertyChange("selectedContact", prevVal, selectedContact);
  }

  @Bindable
  public List<Contact> getContactList() {
    return contactList;
  }


  public void addContact(Contact contact) {
    this.contactList.add(contact);
    firePropertyChange("contactList", null, contactList);
  }

  public void removeContact(Contact contact) {
    this.contactList.remove(contact);
    firePropertyChange("contactList", null, contactList);
  }

  @Bindable
  public String getSearchString() {
    return searchString;
  }
  
  @Bindable
  public void setSearchString(String searchString) {
    String prevVal = this.searchString;
    this.searchString = searchString;
    firePropertyChange("searchString", prevVal, searchString);
    search();
  }

  public void search() {
    List<Contact> results = new ArrayList();

    for (Contact c : contactList) {
      if (c.getFirstName().contains(searchString)
          || c.getLastName().contains(searchString)
          || c.getEmail().contains(searchString)) {
        results.add(c);
      }
    }

    firePropertyChange("contactList", null, results);
  }

  public void clearSearch() {
    this.setSearchString("");
    firePropertyChange("contactList", null, contactList);
  }

}
