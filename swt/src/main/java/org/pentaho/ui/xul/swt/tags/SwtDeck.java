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
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulDeck;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.util.Orient;

public class SwtDeck extends AbstractSwtXulContainer implements XulDeck {

  protected Composite box;

  protected StackLayout layout;

  private int selectedChildIndex = 0;

  private XulDomContainer domContainer;

  public SwtDeck( Element self, XulComponent parent, XulDomContainer container, String tagName ) {
    this( parent, tagName, Orient.HORIZONTAL );
    domContainer = container;
  }

  public SwtDeck( XulComponent parent, String tagName, Orient orient ) {
    super( tagName );
    box = new Composite( (Composite) parent.getManagedObject(), SWT.NONE );

    layout = new StackLayout();
    box.setLayout( layout );
    setSelectedIndex( 0 );

    setManagedObject( box );

  }

  public SwtVbox createVBoxCard() {
    return new SwtVbox( null, this, domContainer, "vbox" );
  }

  @Override
  public void addChild( Element e ) {
    super.addChild( e );
    box.layout();
  }

  public int getSelectedIndex() {
    return selectedChildIndex;
  }

  public void setSelectedIndex( int index ) {
    selectedChildIndex = index;
    layout();
  }

  @Override
  public void layout() {
    if ( !getChildNodes().isEmpty() ) {
      XulComponent control = getChildNodes().get( getSelectedIndex() );
      layout.topControl = (Control) control.getManagedObject();

      if ( layout.topControl instanceof Composite ) {
        ( (Composite) layout.topControl ).layout( true );
      }

    }
    box.layout( true );

  }

}
