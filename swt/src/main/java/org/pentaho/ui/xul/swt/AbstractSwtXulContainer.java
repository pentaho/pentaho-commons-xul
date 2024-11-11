/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.ui.xul.swt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.util.Align;

public class AbstractSwtXulContainer extends SwtElement implements XulContainer {

  protected Align alignment = Align.START;
  protected boolean suppressLayout;
  protected static final Log logger = LogFactory.getLog( AbstractSwtXulContainer.class );

  public AbstractSwtXulContainer( String tagName ) {
    super( tagName );
  }

  public String getAlign() {
    return alignment.toString();
  }

  public void setAlign( String align ) {
    try {
      this.alignment = Align.valueOf( align.toUpperCase() );
    } catch ( Exception e ) {
      logger.warn( "could not parse [" + align + "] as Align value" );

    }
  }

  @Deprecated
  public void addComponent( XulComponent component ) {
    this.addChild( component );
  }

  @Deprecated
  public void addComponentAt( XulComponent component, int idx ) {
    this.addChildAt( component, idx );
  }

  @Deprecated
  public void removeComponent( XulComponent component ) {
    this.removeChild( component );
  }

  public void suppressLayout( boolean suppress ) {
    this.suppressLayout = suppress;
  }

}
