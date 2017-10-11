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

/**
 * 
 */

package org.pentaho.ui.xul.samples;

import org.pentaho.ui.xul.components.XulCheckbox;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

/**
 * @author OEM
 * 
 */
public class SampleEventHandler2 extends AbstractXulEventHandler {

  public void sayHello() {
    XulTextbox textbox = (XulTextbox) document.getElementById( "name" );
    XulCheckbox checkbox = (XulCheckbox) document.getElementById( "yell" );
    XulLabel responseLabel = (XulLabel) document.getElementByXPath( "/window/vbox/groupbox/vbox/label" );

    if ( textbox != null ) {
      System.out.println( "found it" );
      String name = textbox.getValue();

      String response;
      if ( name.equals( "" ) ) {
        response = "What was that?";
      } else {
        response = "Yea nice to meet you " + name;
      }

      if ( checkbox.isChecked() ) {
        response = response.toUpperCase();
      }

      responseLabel.setValue( response );

    } else {
      System.out.println( "name textbox not found" );
    }
  }

  public Object getData() {
    // TODO Auto-generated method stub
    return null;
  }

  public void setData( Object data ) {
    // TODO Auto-generated method stub

  }

}
