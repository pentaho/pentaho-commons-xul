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

package org.pentaho.ui.xul.swing.tags;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulImage;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingImage extends SwingElement implements XulImage {

  private String src;
  private static final Log logger = LogFactory.getLog( SwingImage.class );
  private XulDomContainer container;
  private JPanel panel;
  private ImageIcon ico = null;
  private Image image;
  private JLabel lbl = null;

  public SwingImage( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "image" );
    panel = new JPanel( new BorderLayout() );
    panel.setOpaque( false );
    container = domContainer;
    setManagedObject( panel );
  }

  public String getSrc() {
    return src;
  }

  public void setSrc( String src ) {

    // First try the relative path
    URL url = SwingImage.class.getClassLoader().getResource( this.container.getXulLoader().getRootDir() + src );

    // Then try to see if we can get the fully qualified file
    if ( url == null ) {
      try {
        File f = new File( src );
        if ( f.exists() ) {
          url = f.toURL();
        }
      } catch ( MalformedURLException e ) {
        // do nothing and let the null url get caught below.
      }
    }

    if ( url == null ) {
      logger.error( "Could not find resource: " + src );
      return;
    }
    ico = new ImageIcon( url );
    if ( ico == null ) {
      logger.error( "Image could not be found: " + ico );
    }
    this.src = src;
    layout();
  }

  public void setSrc( Object img ) {
    if ( img instanceof String ) {
      setSrc( (String) img );
      return;
    }
    this.image = (Image) img;
    ico = new ImageIcon( this.image );
    if ( lbl != null ) {
      lbl.setIcon( ico );
    }
    logger.info( "set new Image Src" );
  }

  public void setVisible( boolean visible ) {
    super.setVisible( visible );
    layout();
  }

  @Override
  public void layout() {
    this.panel.removeAll();

    if ( ico == null || !visible ) {
      return;
    }

    // If scaling in place resize ImageIcon
    if ( getHeight() > 0 && getWidth() > 0 ) {
      ico = new ImageIcon( ico.getImage().getScaledInstance( getWidth(), getHeight(), Image.SCALE_AREA_AVERAGING ) );
    }
    lbl = new JLabel( ico );
    lbl.setOpaque( false );

    this.panel.add( lbl, BorderLayout.CENTER );

    // Set panel to sizing
    if ( getHeight() > 0 && getWidth() > 0 ) {
      this.panel.setMinimumSize( new Dimension( this.getWidth(), this.getHeight() ) );
    }

    panel.revalidate();
  }

  public void refresh() {
    this.panel.updateUI();
  }

}
