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

package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulGroupbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.util.Orient;

public class SwtGroupbox extends SwtBox implements XulGroupbox {
  private static final long serialVersionUID = 7414282626289178745L;

  SwtLabel label;

  public SwtGroupbox( Element self, XulComponent parent, XulDomContainer container, String tagName ) {
    // XUL groupbox defaults to VERTICAL orientation
    super( self, parent, tagName, container, Orient.VERTICAL );

    // self is not needed for this SwtLabel instance
    label = new SwtLabel( null, this, container, SwtLabel.TAG_NAME );
    this.addChild( label );
  }

  @Override
  protected Composite createNewComposite( Composite c ) {
    return new Group( c, SWT.NONE );
  }

  public void setCaption( String caption ) {
    if ( label != null ) {
      label.setValue( caption );
    }
  }

}
