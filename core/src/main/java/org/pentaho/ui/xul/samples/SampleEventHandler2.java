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
