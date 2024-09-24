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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulScale;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.Orient;

public class SwtScale extends SwtElement implements XulScale {

  private Scale scale;
  private Composite parentComposite;
  private int inc, min, max, pageInc, value;
  private String dir;

  public SwtScale( Element self, XulComponent parent, XulDomContainer container, String tagName ) {
    super( tagName );
    parentComposite = (Composite) parent.getManagedObject();
  }

  @Override
  public void layout() {
    int hvOrient = ( getOrientation() == Orient.VERTICAL ) ? SWT.VERTICAL : SWT.HORIZONTAL;
    this.scale = new Scale( parentComposite, hvOrient );
    this.scale.addListener( SWT.Selection, new Listener() {
      public void handleEvent( Event event ) {
        int perspectiveValue = scale.getSelection();
        setValue( perspectiveValue );
      }
    } );
    setManagedObject( scale );
    setMin( getMin() );
    setMax( getMax() );
    setPageincrement( getPageincrement() );
    setValue( getValue() );
    setDir( getDir() );
  }

  public String getDir() {
    return dir;
  }

  public int getInc() {
    return inc;
  }

  public int getMax() {
    return max;
  }

  public int getMin() {
    return min;
  }

  public int getPageincrement() {
    return pageInc;
  }

  public int getValue() {
    return scale.getSelection();
  }

  public void setDir( String direction ) {
    this.dir = direction;
  }

  public void setInc( int increment ) {
    this.inc = increment;
    if ( scale != null ) {
      scale.setIncrement( inc );
    }
  }

  public void setMax( int max ) {
    this.max = max;
    if ( scale != null ) {
      scale.setMaximum( max );
    }
  }

  public void setMin( int min ) {
    this.min = min;
    if ( scale != null ) {
      scale.setMinimum( min );
    }
  }

  public void setPageincrement( int increment ) {
    this.pageInc = increment;
    if ( scale != null ) {
      scale.setPageIncrement( pageInc );
    }
  }

  public void setValue( int value ) {

    int prevVal = this.value;
    this.value = value;
    if ( scale != null ) {
      scale.setSelection( value );
    }
    this.changeSupport.firePropertyChange( "value", prevVal, value );
  }

}
