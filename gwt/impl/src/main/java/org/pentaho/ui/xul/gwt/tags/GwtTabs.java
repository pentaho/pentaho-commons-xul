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

package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.components.XulTab;
import org.pentaho.ui.xul.containers.XulTabs;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtTabs extends AbstractGwtXulContainer implements XulTabs {
  static final String ELEMENT_NAME = "tabs"; //$NON-NLS-1$

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTabs();
      }
    } );
  }

  public GwtTabs() {
    super( ELEMENT_NAME );
    setManagedObject( "empty" );
  }

  public XulTab getTabByIndex( int index ) {
    return (GwtTab) this.getChildNodes().get( index );
  }

  @Override
  public void addChild( Element comp ) {
    super.addChild( comp );
    if ( initialized ) {
      ( (GwtTabbox) getParent() ).layout();
    }
  }

  @Override
  public void addChildAt( Element comp, int pos ) {
    super.addChildAt( comp, pos );
    if ( initialized ) {
      ( (GwtTabbox) getParent() ).layout();
    }
  }

  public int getTabCount() {
    return this.getChildNodes().size();
  }

}
