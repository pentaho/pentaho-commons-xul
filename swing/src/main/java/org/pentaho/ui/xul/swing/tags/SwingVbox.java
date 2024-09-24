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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.containers.XulVbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;
import org.pentaho.ui.xul.swing.ScrollablePanel;
import org.pentaho.ui.xul.util.Orient;

/**
 * @author nbaker
 * 
 */
public class SwingVbox extends AbstractSwingContainer implements XulVbox {

  private String background;
  private Image backgroundImage;
  private static final Log logger = LogFactory.getLog( SwingVbox.class );
  private XulDomContainer domContainer;

  public SwingVbox( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "Vbox" );
    this.domContainer = domContainer;
    this.orientation = Orient.VERTICAL;

    container = new ScrollablePanel( new GridBagLayout() ) {

      @Override
      public void paintComponent( Graphics g ) {
        if ( backgroundImage != null ) {
          g.drawImage( backgroundImage, 0, 0, container );
        }
      }

    };
    container.setOpaque( false );
    setManagedObject( container );
    setPadding( 2 );

  }

  public void resetContainer() {

    container.removeAll();

    gc = new GridBagConstraints();
    gc.gridy = GridBagConstraints.RELATIVE;
    gc.gridx = 0;
    gc.gridheight = 1;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    int pad = getPadding();
    gc.insets = new Insets( pad, pad, pad, pad );
    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.anchor = GridBagConstraints.NORTHWEST;
    gc.weightx = 1;

  }

  @Override
  public void replaceChild( XulComponent oldElement, XulComponent newElement ) throws XulDomException {
    this.resetContainer();
    super.replaceChild( oldElement, newElement );
  }

  @Override
  public void layout() {
    resetContainer();
    if ( getBgcolor() != null ) {
      container.setOpaque( true );
      container.setBackground( Color.decode( getBgcolor() ) );
    }
    super.layout();
  }

  public String getBackground() {
    return background;
  }

  public void setBackground( String src ) {
    this.background = src;
    URL url = SwingImage.class.getClassLoader().getResource( this.domContainer.getXulLoader().getRootDir() + src );

    // Then try to see if we can get the fully qualified file
    if ( url == null ) {
      try {
        url = new File( src ).toURL();
      } catch ( MalformedURLException e ) {
        // do nothing and let the null url get caught below.
      }
    }

    if ( url == null ) {
      logger.error( "Could not find resource: " + src );
      return;
    }
    final ImageIcon ico = new ImageIcon( url );
    backgroundImage = ico.getImage();

  }
}
