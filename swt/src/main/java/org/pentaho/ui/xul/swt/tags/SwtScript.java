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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulScript;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtScript extends SwtElement implements XulScript {
  private static final long serialVersionUID = 3919768754393704152L;
  private static final Log logger = LogFactory.getLog( SwtScript.class );

  XulDomContainer windowContainer = null;
  String className = null;

  public SwtScript( Element self, XulComponent parent, XulDomContainer container, String tagName ) {
    super( tagName );
    windowContainer = container;
  }

  public String getSrc() {
    return className;
  }

  public void setSrc( String className ) {
    this.className = className;
    if ( this.getId() != null && className != null ) {
      try {
        windowContainer.addEventHandler( getId(), className );
      } catch ( XulException e ) {
        logger.error( "Error adding event handler", e );
      }
    }
  }

  public void setId( String id ) {
    super.setId( id );
    if ( this.getId() != null && className != null ) {
      try {
        windowContainer.addEventHandler( getId(), className );
      } catch ( XulException e ) {
        logger.error( "Error adding event handler", e );
      }
    }
  }

}
