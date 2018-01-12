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

package org.pentaho.ui.xul.swing.tags;

import javax.swing.JProgressBar;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulProgressmeter;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

/**
 * @author mlowery
 */
public class SwingProgressmeter extends SwingElement implements XulProgressmeter {

  private JProgressBar progressmeter;

  public SwingProgressmeter( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "progressmeter" ); //$NON-NLS-1$
    progressmeter = new JProgressBar();
    setManagedObject( progressmeter );
  }

  public int getMaximum() {
    return progressmeter.getMaximum();
  }

  public int getMinimum() {
    return progressmeter.getMinimum();
  }

  public int getValue() {
    return progressmeter.getValue();
  }

  public boolean isIndeterminate() {
    return progressmeter.isIndeterminate();
  }

  public void setIndeterminate( boolean indeterminate ) {
    progressmeter.setIndeterminate( indeterminate );
  }

  public void setMaximum( int value ) {
    progressmeter.setMaximum( value );
  }

  public void setMinimum( int value ) {
    progressmeter.setMinimum( value );
  }

  public void setValue( int value ) {
    progressmeter.setValue( value );
  }

  public void setMode( String mode ) {
    progressmeter.setIndeterminate( MODE_INDETERMINATE.equals( mode ) );
  }

}
