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

import java.awt.CardLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulDeck;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;
import org.pentaho.ui.xul.util.Orient;

public class SwingDeck extends AbstractSwingContainer implements XulDeck {

  protected JPanel container;

  private CardLayout cardLayout;

  private int selectedChildIndex = 0;

  private int numChildren = 0;

  private List<XulComponent> children = new ArrayList<XulComponent>();

  public SwingDeck( Element self, XulComponent parent, XulDomContainer container, String tagName ) {
    this( parent, tagName, Orient.HORIZONTAL );
  }

  public SwingDeck( XulComponent parent, String tagName, Orient orient ) {
    super( tagName );
    cardLayout = new CardLayout();
    container = new JPanel( cardLayout );
    setSelectedIndex( 0 );
    setManagedObject( container );
  }

  @Override
  public void addChild( Element ele ) {
    super.addChild( ele );
  }

  public int getSelectedIndex() {
    return selectedChildIndex;
  }

  public void setSelectedIndex( int index ) {
    int previousVal = selectedChildIndex;
    selectedChildIndex = index;
    cardLayout.show( container, "" + index );
    this.changeSupport.firePropertyChange( "disabled", previousVal, index );
  }

  public void layout() {
    initialized = true;
    numChildren = 0;
    for ( Element e : getChildNodes() ) {
      this.container.add( (Component) ( (XulComponent) e ).getManagedObject(), "" + numChildren );
      numChildren++;
    }
  }

}
