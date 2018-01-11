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

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTabpanel;
import org.pentaho.ui.xul.containers.XulTabbox;
import org.pentaho.ui.xul.containers.XulTabpanels;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;

public class SwtTabpanels extends AbstractSwtXulContainer implements XulTabpanels {

  private XulTabbox tabbox;

  public SwtTabpanels( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "tabpanels" );
    tabbox = (XulTabbox) parent;
    setManagedObject( "empty" );
  }

  protected XulTabbox getTabbox() {
    return tabbox;
  }

  public XulTabpanel getTabpanelByIndex( int index ) {
    return (XulTabpanel) this.getChildNodes().get( index );
  }

  @Override
  public void removeChild( Element ele ) {
    int idx = getChildNodes().indexOf( ele );
    super.removeChild( ele );
    ( (XulTabbox) getParent() ).removeTabpanel( idx );
  }

  @Override
  public void addChild( Element c ) {
    super.addChild( c );
    if ( getParent() != null ) {
      ( (XulTabbox) getParent() ).addTabpanel( this.getChildNodes().indexOf( c ) );
    }
  }

  @Override
  public void addChildAt( Element c, int pos ) {
    super.addChildAt( c, pos );
    if ( getParent() != null ) {
      ( (XulTabbox) getParent() ).addTabpanel( pos );
      ( (SwtTabbox) getParent() ).layout();
    }
  }

  @Override
  public void layout() {
  }
}
