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
public class SampleEventHandler extends AbstractXulEventHandler {

  public void sayHello() {

    XulTextbox textbox = (XulTextbox) document.getElementById( "name" );
    XulCheckbox checkbox = (XulCheckbox) document.getElementById( "yell" );
    XulLabel responseLabel = (XulLabel) document.getElementById( "response" );

    if ( textbox != null ) {
      System.out.println( "found it" );
      String name = textbox.getValue();

      String response;
      if ( name.equals( "" ) ) {
        response = "You should really type in your name";
      } else {
        response = "Hello there " + name;
      }

      if ( checkbox.isChecked() ) {
        response = response.toUpperCase();
      }

      responseLabel.setValue( response );
    } else {
      System.out.println( "name textbox not found" );
    }
  }

  public void onload() {
    XulTextbox text = (XulTextbox) document.getElementById( "textbox1" );
    text.setValue( "new value" );
  }

  public int testNumber = -1;

  public void onload( int number ) {
    testNumber = number;
  }

  public double testDouble = -1.0;

  public void onload( double number ) {
    testDouble = number;
  }

  public String testString = "fail";

  public void onload( String str ) {
    testString = str;
  }

  public boolean testBool = false;

  public void onload( boolean flag ) {
    testBool = flag;
  }
}
