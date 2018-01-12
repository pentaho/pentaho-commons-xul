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

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTab;
import org.pentaho.ui.xul.containers.XulTabbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtTab extends AbstractGwtXulContainer implements XulTab {
  private String label;
  private boolean disabled = false;
  private String onclick;
  private String onBeforeSelect;
  private XulTabbox tabbox;

  static final String ELEMENT_NAME = "tab"; //$NON-NLS-1$

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTab();
      }
    } );
  }

  public GwtTab() {
    super( ELEMENT_NAME );
    setManagedObject( "empty" );
  }

  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    super.init( srcEle, container );
    setLabel( srcEle.getAttribute( "label" ) );
    setOnclick( srcEle.getAttribute( "onclick" ) );
    setOnBeforeSelect( srcEle.getAttribute( "pen:onbeforeselect" ) );
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

  }

  public void setOnclick( String onClick ) {
    this.onclick = onClick;

  }

  @Override
  public void layout() {
  }

  /**
   * @param onBeforeSelect
   *          the onBeforeSelect to set
   */
  public void setOnBeforeSelect( String onBeforeSelect ) {
    this.onBeforeSelect = onBeforeSelect;
  }

  /**
   * @return the onBeforeSelect
   */
  public String getOnBeforeSelect() {
    return onBeforeSelect;
  }

}
