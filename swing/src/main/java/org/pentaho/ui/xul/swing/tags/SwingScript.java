/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/


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
