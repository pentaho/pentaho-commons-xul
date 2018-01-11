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

package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulProgressmeter;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtProgressmeter extends SwtElement implements XulProgressmeter {

  protected ProgressBar progressmeter;

  private boolean indeterminate;

  protected XulComponent parent;

  /**
   * SetX methods cannot be called until ProgressBar is created. Save them until then.
   */
  private int initialValue;

  /**
   * SetX methods cannot be called until ProgressBar is created. Save them until then.
   */
  private int initialMaximum;

  /**
   * SetX methods cannot be called until ProgressBar is created. Save them until then.
   */
  private int initialMinimum;

  public SwtProgressmeter( Element self, XulComponent parent, XulDomContainer container, String tagName ) {
    super( tagName );
    this.parent = parent;
    // SWT progress bar not created until mode is known (in layout method)
  }

  protected ProgressBar createNewProgressmeter( Composite parent ) {
    ProgressBar progressmeter = new ProgressBar( parent, isIndeterminate() ? SWT.INDETERMINATE : SWT.NONE );

    progressmeter.setSelection( initialValue );
    progressmeter.setMinimum( initialMinimum );
    progressmeter.setMaximum( initialMaximum );
    return progressmeter;
  }

  public int getMaximum() {
    return progressmeter.getMaximum();
  }

  public int getMinimum() {
    return progressmeter.getMinimum();
  }

  public int getValue() {
    return progressmeter.getSelection();
  }

  public boolean isIndeterminate() {
    return indeterminate;
  }

  public void setIndeterminate( boolean indeterminate ) {
    this.indeterminate = indeterminate;
  }

  public void setMaximum( int value ) {
    if ( null != progressmeter ) {
      progressmeter.setMaximum( value );
    } else {
      initialMaximum = value;
    }
  }

  public void setMinimum( int value ) {
    if ( null != progressmeter ) {
      progressmeter.setMinimum( value );
    } else {
      initialMinimum = value;
    }
  }

  public void setMode( String mode ) {
    indeterminate = MODE_INDETERMINATE.equals( mode );
  }

  public void setValue( final int value ) {
    Display.getDefault().syncExec( new Runnable() {
      public void run() {
        if ( null != progressmeter ) {
          progressmeter.setSelection( value );
        } else {
          initialValue = value;
        }
      }
    } );

  }

  @Override
  public void layout() {
    progressmeter = createNewProgressmeter( (Composite) parent.getManagedObject() );
    setManagedObject( progressmeter );
  }

}
