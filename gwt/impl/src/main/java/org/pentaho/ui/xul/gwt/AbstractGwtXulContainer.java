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

package org.pentaho.ui.xul.gwt;

import java.util.Iterator;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.util.Align;

import com.google.gwt.user.client.ui.Widget;

public abstract class AbstractGwtXulContainer extends AbstractGwtXulComponent implements XulContainer {

  protected Align alignment;
  protected boolean suppressLayout;

  public AbstractGwtXulContainer( String tagName ) {
    super( tagName );
  }

  public void addComponent( XulComponent component ) {
    this.addChild( component );
    if ( initialized == false ) {
      layout();
    }

    if ( initialized ) {
      resetContainer();
      layout();
    }

  }

  public void addComponentAt( XulComponent component, int idx ) {
    this.addChildAt( component, idx );
    if ( initialized ) {
      resetContainer();
      layout();
    }
  }

  public void removeComponent( XulComponent component ) {
    this.removeChild( component );
    if ( initialized ) {
      resetContainer();
      layout();
    }
  }

  @Override
  public void resetContainer() {
    if ( container != null ) {
      Iterator<Widget> iterator = container.iterator();
      while ( iterator.hasNext() ) {
        Widget widget = iterator.next();
        container.remove( widget );
      }
    }
  }

  public void suppressLayout( boolean suppress ) {
    this.suppressLayout = suppress;
  }

}
