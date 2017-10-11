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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.containers.XulGroupbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;
import org.pentaho.ui.xul.swing.ScrollablePanel;
import org.pentaho.ui.xul.util.Orient;

/**
 * @author OEM
 * 
 */
public class SwingGroupbox extends AbstractSwingContainer implements XulGroupbox {
  private static final Log logger = LogFactory.getLog( SwingGroupbox.class );

  public SwingGroupbox( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "groupbox" );
    this.orientation = Orient.VERTICAL;

    container = new ScrollablePanel( new GridBagLayout() );
    container.setBorder( BorderFactory.createTitledBorder( "" ) );
    setManagedObject( container );
    setPadding( 2 );

  }

  public void resetContainer() {

    container.removeAll();

    if ( this.getOrientation() == Orient.VERTICAL ) {

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

    } else {

      gc = new GridBagConstraints();
      gc.gridy = 0;
      gc.gridx = GridBagConstraints.RELATIVE;
      gc.gridheight = GridBagConstraints.REMAINDER;
      gc.gridwidth = 1;
      gc.insets = new Insets( 2, 2, 2, 2 );
      gc.fill = GridBagConstraints.VERTICAL;
      gc.anchor = GridBagConstraints.NORTHWEST;
      gc.weighty = 1;

    }

  }

  public void setCaption( String caption ) {
    container.setBorder( BorderFactory.createTitledBorder( caption ) );

  }

  @Override
  public void setOrient( String orientation ) {
    this.orientation = Orient.valueOf( orientation.toUpperCase() );
  }

  @Override
  public void layout() {
    resetContainer();
    super.layout();
    for ( Element comp : getChildNodes() ) {
      if ( comp instanceof SwingCaption ) {
        this.setCaption( ( (SwingCaption) comp ).getLabel() );
      }
    }

    int width = this.container.getSize().width;
    int height = this.container.getSize().height;
    if ( this.getWidth() > 0 ) {
      width = this.getWidth();
    }
    if ( this.getHeight() > 0 ) {
      height = this.getHeight();
    }
    this.container.setSize( width, height );
  }

  @Override
  public void removeChild( Element ele ) {
    super.removeChild( ele );
    if ( initialized ) {
      resetContainer();
      layout();
    }
  }

  @Override
  public void replaceChild( XulComponent oldElement, XulComponent newElement ) throws XulDomException {
    this.resetContainer();
    super.replaceChild( oldElement, newElement );
  }

}
