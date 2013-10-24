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
* Copyright (c) 2002-2013 Pentaho Corporation..  All rights reserved.
*/

package org.pentaho.ui.xul.gwt.sample;

import org.pentaho.ui.xul.binding.BindingFactory;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.gwt.binding.GwtBindingFactory;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

public class BindingController extends AbstractXulEventHandler {

  private String firstName;
  private BindingModel model = new BindingModel();
  private XulTextbox textBox;
  BindingFactory bindingFactory;


  @Override
  public String getName() {
    return "controller";
  }

  public void init() {
    bindingFactory = new GwtBindingFactory( document );

    textBox = (XulTextbox) document.getElementById( "nameTextbox" );

    XulLabel lbl = (XulLabel) document.getElementById( "testLabel" );

    bindingFactory.createBinding( model, "firstName", lbl, "value" );

  }

  public void click() {
    model.setFirstName( textBox.getValue() );
  }


  public void setFirstName( String name ) {

    this.firstName = name;
    this.firePropertyChange( "firstName", null, this.firstName );
  }

  public String getFirstName() {
    return this.firstName;
  }


}
