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

/**
 * 
 */

package org.pentaho.ui.xul.swing.tags;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulScript;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

/**
 * @author nbaker
 * 
 */
public class SwingScript extends SwingElement implements XulScript {
  private static final Log logger = LogFactory.getLog( SwingScript.class );
  private String id;
  private String src;

  public SwingScript( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "script" );

    try {
      if ( ( self.getAttributeValue( "ID" ) == null ) || ( self.getAttributeValue( "src" ) == null ) ) {
        return;
      }
      domContainer.addEventHandler( self.getAttributeValue( "ID" ), self.getAttributeValue( "src" ) );
      logger.info( "Added new event handler: " + self.getAttributeValue( "ID" ) );
    } catch ( XulException e ) {
      logger.error( "Error adding Event Handler to Window: " + self.getAttributeValue( "ID" ) + " : "
          + self.getAttributeValue( "src" ), e );
    }

  }

  public String getId() {
    return id;
  }

  public void setId( String id ) {
    this.id = id;
  }

  public String getSrc() {
    return src;
  }

  public void setSrc( String className ) {
    this.src = className;
  }

  public void layout() {
  }

}
