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
