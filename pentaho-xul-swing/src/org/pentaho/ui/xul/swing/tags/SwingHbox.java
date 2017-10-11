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

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.containers.XulHbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;
import org.pentaho.ui.xul.swing.ScrollablePanel;
import org.pentaho.ui.xul.util.Orient;

/**
 * @author OEM
 * 
 */
public class SwingHbox extends AbstractSwingContainer implements XulHbox {

  private static final Log logger = LogFactory.getLog( SwingHbox.class );
  private String background;
  private XulDomContainer domContainer;

  public SwingHbox( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "Hbox" );
    this.domContainer = domContainer;
    container = new ScrollablePanel( new GridBagLayout() );
    container.setOpaque( false );
    setManagedObject( container );
    setPadding( 2 );
    resetContainer();

  }

  @Override
  public void removeChild( Element ele ) {
    super.removeChild( ele );
    if ( initialized ) {
      resetContainer();
      layout();
    }
  }

  public void resetContainer() {

    container.removeAll();

    gc = new GridBagConstraints();
    gc.gridx = GridBagConstraints.RELATIVE;
    gc.gridy = 0;
    gc.gridheight = GridBagConstraints.REMAINDER;
    gc.gridwidth = 1;

    int pad = getPadding();
    gc.insets = new Insets( pad, pad, pad, pad );
    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.anchor = GridBagConstraints.NORTHWEST;
    gc.weighty = 1;

  }

  public Orient getOrientation() {
    return Orient.HORIZONTAL;
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

    container.addComponentListener( new ComponentListener() {

      public void componentHidden( ComponentEvent arg0 ) {
      }

      public void componentMoved( ComponentEvent arg0 ) {
      }

      public void componentResized( ComponentEvent arg0 ) {
        container.getGraphics().drawImage( ico.getImage(), 0, 0, container );
      }

      public void componentShown( ComponentEvent arg0 ) {
      }

    } );
  }

}
