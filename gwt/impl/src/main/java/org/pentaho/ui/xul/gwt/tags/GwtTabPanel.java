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

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.components.XulTabpanel;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.util.Orient;

import com.google.gwt.user.client.ui.VerticalPanel;

public class GwtTabPanel extends AbstractGwtXulContainer implements XulTabpanel {

  static final String ELEMENT_NAME = "tabpanel";

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, GwtTabPanel::new );
  }

  protected VerticalPanel verticalPanel;

  public GwtTabPanel() {
    this( Orient.VERTICAL );
  }

  public GwtTabPanel( Orient orient ) {
    super( ELEMENT_NAME );

    this.orientation = orient;
    verticalPanel = GwtVbox.createManagedPanel();
    verticalPanel.addStyleName( "xul-" + ELEMENT_NAME );

    container = verticalPanel;
    setManagedObject( container );
  }

  public void resetContainer() {
    for ( int i = 0; i < verticalPanel.getWidgetCount(); i++ ) {
      verticalPanel.remove( i );
    }

    verticalPanel = GwtVbox.createManagedPanel();
  }

  @Override
  public void replaceChild( XulComponent oldElement, XulComponent newElement ) throws XulDomException {
    this.resetContainer();
    super.replaceChild( oldElement, newElement );
  }

}
