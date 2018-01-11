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

import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.containers.XulDialog;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

public class DialogHandler extends AbstractXulEventHandler {

  public void showDialog() {
    XulDialog dialog = (XulDialog) document.getElementById( "dialog" );
    dialog.show();
  }

  public void sayHello() {
    XulTextbox name = (XulTextbox) document.getElementById( "nickname" );
    XulLabel response = (XulLabel) document.getElementById( "helloMsg" );

    response.setValue( "Hello there " + name.getValue() );
  }

  public void printLoadMessage( String msg ) {
    System.out.println( "outter handler called: " + msg );
  }

  public void showIncludedDialog() {
    XulDialog dialog = (XulDialog) document.getElementById( "dialog" );
    dialog.show();
  }
}
