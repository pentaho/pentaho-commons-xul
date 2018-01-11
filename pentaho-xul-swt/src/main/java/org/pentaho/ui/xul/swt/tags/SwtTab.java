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
import org.pentaho.ui.xul.components.XulTab;
import org.pentaho.ui.xul.containers.XulTabbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtTab extends SwtElement implements XulTab {

  private String label;
  private boolean disabled = false;
  private String onclick;
  private XulTabbox tabbox;

  public SwtTab( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "tab" );
    setManagedObject( "empty" );
  }

  public boolean isDisabled() {
    return disabled;
  }

  public String getLabel() {
    return label;
  }

  public String getOnclick() {
    return onclick;
  }

  public void getTabbox() {
    if ( tabbox == null ) {
      if ( getParent() != null ) {
        tabbox = (XulTabbox) getParent().getParent();
      }
    }
  }

  public void setDisabled( boolean disabled ) {
    this.disabled = disabled;
    getTabbox();
    if ( tabbox != null ) {
      tabbox.setTabDisabledAt( disabled, getParent().getChildNodes().indexOf( this ) );
    }

  }

  public void setLabel( String label ) {
    this.label = label;
    getTabbox();
    if ( tabbox != null ) {
      ( (SwtTabbox) tabbox ).updateTabState();
    }
  }

  public void setOnclick( String onClick ) {
    this.onclick = onClick;

  }

  @Override
  public void layout() {
  }

  @Override
  public void setVisible( boolean visible ) {
    super.setVisible( visible );
    getTabbox();
    if ( tabbox != null ) {
      ( (SwtTabbox) tabbox ).setTabVisibleAt( visible, getParent().getChildNodes().indexOf( this ) );
      ( (SwtTabbox) tabbox ).layout();
    }
  }

}
