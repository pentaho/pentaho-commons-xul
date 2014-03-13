/*
 * !
 *  * This program is free software; you can redistribute it and/or modify it under the
 *  * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 *  * Foundation.
 *  *
 *  * You should have received a copy of the GNU Lesser General Public License along with this
 *  * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *  * or from the Free Software Foundation, Inc.,
 *  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *  *
 *  * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  * See the GNU Lesser General Public License for more details.
 *  *
 *  * Copyright (c) 2002-2013 Pentaho Corporation..  All rights reserved.
 *
 */

package org.pentaho.auth.controller;

import org.pentaho.auth.model.AuthProvider;
import org.pentaho.auth.model.NamedModelObject;
import org.pentaho.auth.model.NamedProvider;
import org.pentaho.auth.model.NoAuthAuthProvider;
import org.pentaho.auth.model.ObjectListModel;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.XulRunner;
import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.binding.BindingConvertor;
import org.pentaho.ui.xul.binding.BindingFactory;
import org.pentaho.ui.xul.containers.XulDialog;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

import java.lang.reflect.InvocationTargetException;

public class AuthProviderController extends AbstractXulEventHandler {
  private static String XUL_FILE = "org/pentaho/auth/xul/authManager.xul";

  protected BindingConvertor<NamedModelObject, String> selectedItemsNameBinding = new SelectedToStringConvertor();
  protected BindingConvertor<NamedModelObject, Object> selectedItemsItemBinding = new SelectedToItemConvertor();
  private XulDialog xulDialog;
  private BindingFactory bf;
  private XulLoader loader;
  private XulRunner runner;
  private ObjectListModel model = new ObjectListModel();

  String activeOverlay = null;

  public AuthProviderController( XulLoader loader, BindingFactory bindingFactory, XulRunner runner ) {
    this.bf = bindingFactory;
    this.loader = loader;
    this.runner = runner;

    setName( "handler" );

    init();
    bind();
  }

  private boolean init() {
    boolean success = true;

    try {

      setXulDomContainer( this.loader.loadXul( XUL_FILE, null ) );
      this.bf.setDocument( getXulDomContainer().getDocumentRoot() );
      getXulDomContainer().addEventHandler( this );
      this.runner.addContainer( getXulDomContainer() );
      this.xulDialog = ( (XulDialog) getXulDomContainer().getDocumentRoot().getRootElement() );
      this.runner.initialize();

    } catch ( XulException e ) {
      success = false;
    }

    return success;
  }

  public void open() {
    this.xulDialog.show();
  }

  public void setNewOverlay( AuthProvider overlay ) throws XulException {
    String providerOverlay = overlay.getOverlay();

    if ( this.activeOverlay != null ) {
      getXulDomContainer().removeOverlay( this.activeOverlay );
    }

    getXulDomContainer().loadOverlay( providerOverlay );
    this.activeOverlay = providerOverlay;
  }

  public String getNewOverlay() {
    return "";
  }

  private void bind() {
    try {
      this.bf.setBindingType( Binding.Type.ONE_WAY );

      // Loads the authorization types into the "Method" combobox
      this.bf.createBinding( this.model, "possibleTypes", "method_list", "elements" ).fireSourceChanged();

      // When an authorization entry is selected, select entry in model
      this.bf.createBinding( "auth_list", "selectedItem", this.model, "selectedItem" );

      this.bf.setBindingType( Binding.Type.BI_DIRECTIONAL );

      // Change the overlay when the user changes the "Method" in the method combobox
      this.bf.createBinding( this, "newOverlay", "method_list", "selectedItem" ).fireSourceChanged();

      // Syncs elements in the model and lists them in the authorization entry list
      Binding listBinding = this.bf.createBinding( this.model.getModelObjects(), "children", "auth_list", "elements" );
      listBinding.fireSourceChanged();

      // Update the entry name textbox when a new entry is selected in the authorization entry list
      this.bf.createBinding( this.model, "selectedItem", "name", "value", new BindingConvertor[] { this.selectedItemsNameBinding } ).fireSourceChanged();

      // Update the method combobox with the appropriate selection for the selected authorization entry
      this.bf.createBinding( this.model, "selectedItem", "method_list", "selectedItem", new BindingConvertor[] { this.selectedItemsItemBinding } ).fireSourceChanged();

    } catch ( XulException e ) {
      e.printStackTrace();
    } catch ( InvocationTargetException e ) {
      e.printStackTrace();
    }
  }

  public void onAccept() {
    this.xulDialog.hide();
  }

  public void onCancel() {
    this.xulDialog.hide();
  }

  public void addNew() {

    NamedProvider provider = new NamedProvider();
    provider.setName( "New Provider" );
    provider.setItem( new NoAuthAuthProvider() );

    this.model.add( provider );
    this.model.setSelectedItem( provider );

  }

  public void remove() {

    this.model.getModelObjects().remove( this.model.getSelectedItem() );
    this.model.setSelectedItem( null );

  }

  private class SelectedToItemConvertor extends BindingConvertor<NamedModelObject, Object> {
    private SelectedToItemConvertor() {
    }

    public Object sourceToTarget( NamedModelObject value ) {
      if ( value == null ) {
        return null;
      }
      return value.getItem();
    }

    public NamedModelObject targetToSource( Object value ) {
      if ( model.getSelectedItem() != null ) {
        Object useVal = ( (AuthProvider) value ).clone();
        model.setItem( model.getSelectedItem(), useVal);
      }
      return model.getSelectedItem();
    }
  }

  private class SelectedToStringConvertor extends BindingConvertor<NamedModelObject, String> {
    private SelectedToStringConvertor() {
    }

    public String sourceToTarget( NamedModelObject value ) {
      if ( value == null ) {
        return "";
      }
      return value.getName();
    }

    public NamedModelObject targetToSource( String value ) {
      if ( model.getSelectedItem() != null ) {
        model.setName( value );
      }
      return model.getSelectedItem();
    }
  }
}
