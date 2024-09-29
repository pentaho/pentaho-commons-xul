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
