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


package org.pentaho.ui.xul.swing;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.util.Align;

public abstract class AbstractSwingContainer extends SwingElement implements XulContainer {

  protected Align alignment;
  protected boolean suppressLayout;

  public AbstractSwingContainer( String tagName ) {
    super( tagName );
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
