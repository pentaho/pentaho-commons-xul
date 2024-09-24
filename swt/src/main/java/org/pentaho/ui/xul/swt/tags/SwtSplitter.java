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
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulSplitter;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.Orient;

public class SwtSplitter extends AbstractSwtXulContainer implements XulSplitter {

  private SashForm sash;

  public SwtSplitter( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "splitter" );
    int dir = ( getOrientation() == Orient.VERTICAL ) ? SWT.VERTICAL : SWT.HORIZONTAL;
    sash = new SashForm( (Composite) parent.getManagedObject(), dir );
    sash.setBackground( sash.getDisplay().getSystemColor( SWT.COLOR_WIDGET_BACKGROUND ) );
    setManagedObject( sash );

  }

  @Override
  public void layout() {

    int thisFlex = 0;
    int childNum = 0;
    int[] weights = new int[getChildNodes().size()];

    for ( Object child : this.getChildNodes() ) {
      thisFlex = ( (SwtElement) child ).getFlex();
      weights[childNum++] = ( thisFlex <= 0 ) ? 0 : thisFlex;
    }
    super.layout();
    if ( weights.length > 0 ) {
      sash.setWeights( weights );
    }
    sash.setVisible( true );
    sash.layout( true );
  }

  @Override
  public void setOrient( String orientation ) {
    super.setOrient( orientation );
    if ( sash != null ) {
      int dir = ( getOrientation() == Orient.VERTICAL ) ? SWT.VERTICAL : SWT.HORIZONTAL;
      sash.setOrientation( dir );
    }
  }

}
