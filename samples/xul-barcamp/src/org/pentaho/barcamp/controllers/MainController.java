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

package org.pentaho.barcamp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.barcamp.forms.ContactForm;
import org.pentaho.barcamp.models.Contact;
import org.pentaho.barcamp.models.Workspace;
import org.pentaho.ui.xul.binding.*;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.containers.XulDialog;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;
import org.pentaho.ui.xul.impl.DefaultXulOverlay;
import org.pentaho.ui.xul.stereotype.Bindable;
import org.pentaho.ui.xul.XulOverlay;
import org.pentaho.ui.xul.XulDomContainer;

public class MainController extends AbstractXulEventHandler {

  private XulTree contactTable;
  private XulDialog contactDialog;
  private XulTextbox searchBox;
  private XulTextbox firstNameBox;
  private XulTextbox lastNameBox;
  private XulTextbox emailBox;
  private XulButton removeBtn;

  private BindingFactory bindings;

  private ContactForm form = new ContactForm();
  private Workspace workspace = new Workspace();
  
  @Bindable
  public void init() {
    
    // Grab references that we care about
    contactTable = (XulTree) document.getElementById("contact_tree");
    contactDialog = (XulDialog) document.getElementById("contact_dialog");
    firstNameBox = (XulTextbox) document.getElementById("firstNameTxt");
    lastNameBox = (XulTextbox) document.getElementById("lastNameTxt");
    emailBox = (XulTextbox) document.getElementById("emailTxt");
    searchBox = (XulTextbox) document.getElementById("searchString");
    removeBtn = (XulButton) document.getElementById("removeBtn");

    // Dialog Bindings
    bindings.createBinding(form, "firstName", firstNameBox, "value");
    bindings.createBinding(form, "lastName", lastNameBox, "value");
    bindings.createBinding(form, "email", emailBox, "value");

    // Bind Search String
    bindings.createBinding(workspace, "searchString", searchBox, "value");

    // Following bindings created will be one-way
    bindings.setBindingType(Binding.Type.ONE_WAY);


    // Bind List of Contacts to Table
    Binding contactListBinding = bindings.createBinding(workspace, "contactList", contactTable, "elements");

    document.invokeLater(new Runnable() {
      public void run() {
        //do something long
      }
    }
    );

    // Bind the selected row of table to Workspace
    bindings.createBinding(contactTable, "selectedItem", workspace, "selectedContact");

    // Bind the selected row of the table to the remove button disabled state
    bindings.createBinding(contactTable, "selectedRows", removeBtn, "disabled",
        new BindingConvertor<int[], Boolean>() {

          @Override
          public Boolean sourceToTarget(int[] value) {
            return (value == null || value.length == 0);
          }

          @Override
          public int[] targetToSource(Boolean notUsed) {
            // Ignored
            return null;
          }
        }
    );

    try {
      // The following 'flashes' the UI with start-up values, generally not needed if bindings are setup before models
      // are hydrated
      contactListBinding.fireSourceChanged();

      // Uncomment the following to add an overlay to the document.
      // This works for Swing and SWT, Shandor will unify the overlay calls to enable overlay loading from the
      // event handlers.
      //document.addOverlay("overlay.xul");


    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Bindable
  public void add() {
    form.reset();
    contactDialog.show();
  }

  @Bindable
  public void remove() {
    Contact selectedContact = workspace.getSelectedContact();
    workspace.removeContact(selectedContact);
  }

  @Bindable
  public void clearSearch() {
    workspace.clearSearch();
  }

  @Bindable
  public void saveDialog() {
    Contact contact = form.getContact();
    workspace.addContact(contact);
    contactDialog.hide();
  }

  @Bindable
  public void cancelDialog() {
    contactDialog.hide();
  }

  public void setBindingFactory(BindingFactory bf) {
    this.bindings = bf;
  }

  public String getName() {
    return "controller";
  }
}
