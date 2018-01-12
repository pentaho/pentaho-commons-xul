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
