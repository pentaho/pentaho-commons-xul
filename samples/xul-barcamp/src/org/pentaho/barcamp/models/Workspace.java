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
