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


package org.pentaho.ui.xul.swing.tags;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.containers.XulStatusbar;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;
import org.pentaho.ui.xul.swing.ScrollablePanel;
import org.pentaho.ui.xul.util.Orient;

public class SwingStatusbar extends AbstractSwingContainer implements XulStatusbar {
  private static final Log logger = LogFactory.getLog( SwingStatusbar.class );

  public SwingStatusbar( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "statusbar" );

    container = new ScrollablePanel( new GridBagLayout() );
    container.setBackground( Color.decode( "#EFEFEF" ) );
    container.setBorder( BorderFactory.createMatteBorder( 1, 0, 0, 0, Color.gray ) );

    setManagedObject( container );

    resetContainer();

  }

  public void resetContainer() {

    container.removeAll();

    gc = new GridBagConstraints();
    gc.gridx = GridBagConstraints.RELATIVE;
    gc.gridy = 0;
    gc.gridheight = GridBagConstraints.REMAINDER;
    gc.gridwidth = 1;
    gc.insets = new Insets( 0, 0, 0, 0 );
    gc.fill = GridBagConstraints.BOTH;
    gc.anchor = GridBagConstraints.CENTER;
    gc.weighty = 1;

  }

  public Orient getOrientation() {
    return Orient.HORIZONTAL;
  }

  @Override
  public void layout() {
    resetContainer();
    initialized = true;
    super.layout();
  }

  @Override
  public void replaceChild( XulComponent oldElement, XulComponent newElement ) throws XulDomException {
    this.resetContainer();
    super.replaceChild( oldElement, newElement );
  }

}
