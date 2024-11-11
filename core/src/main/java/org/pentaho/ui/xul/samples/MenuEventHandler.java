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

import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.binding.DefaultBinding;
import org.pentaho.ui.xul.components.XulMenuList;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

public class MenuEventHandler extends AbstractXulEventHandler {

  public void init() {
    XulMenuList list = (XulMenuList) document.getElementById( "list3" );

    Binding bind = new DefaultBinding( list, "value", this, "value" );
    bind.setBindingType( Binding.Type.ONE_WAY );
    document.addBinding( bind );

    bind = new DefaultBinding( list, "selectedIndex", this, "selectedIndex" );
    bind.setBindingType( Binding.Type.ONE_WAY );
    document.addBinding( bind );

    bind = new DefaultBinding( list, "selectedItem", this, "selectedItem" );
    bind.setBindingType( Binding.Type.ONE_WAY );
    document.addBinding( bind );
  }

  public void setSelectedItem( String val ) {
    System.out.println( "menulist selectedvalue: " + val );
  }

  public void setValue( String val ) {
    System.out.println( "menulist value: " + val );
  }

  public void setSelectedIndex( int idx ) {
    System.out.println( "menulist selected index: " + idx );
  }

  public void changeState() {
    System.out.println( "ChangeState fired" );
  }

  public Object getData() {
    return null;
  }

  public void setData( Object data ) {
  }

}
