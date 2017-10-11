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

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.components.XulRadioGroup;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;
import org.pentaho.ui.xul.util.Orient;

/**
 * @author aphillips
 * 
 */
public class SwingRadioGroup extends AbstractSwingContainer implements XulRadioGroup {

  private ButtonGroup buttonGroup = new ButtonGroup();

  public SwingRadioGroup( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "radiogroup" );

    this.orientation = Orient.VERTICAL;

    container = new JPanel( new GridBagLayout() );

    setManagedObject( container );

    resetContainer();
  }

  @Override
  public void addChild( Element c ) {
    addComponentToButtonGroup( (XulComponent) c );
    super.addChild( c );
  }

  protected void addComponentToButtonGroup( XulComponent c ) {
    for ( XulComponent child : c.getChildNodes() ) {
      addComponentToButtonGroup( child );
    }
    if ( AbstractButton.class.isAssignableFrom( c.getManagedObject().getClass() ) ) {
      this.buttonGroup.add( (AbstractButton) c.getManagedObject() );
    }
  }

  public void resetContainer() {

    container.removeAll();

    gc = new GridBagConstraints();
    gc.gridy = GridBagConstraints.RELATIVE;
    gc.gridx = 0;
    gc.gridheight = 1;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.insets = new Insets( 2, 2, 2, 2 );
    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.anchor = GridBagConstraints.NORTHWEST;
    gc.weightx = 1;

  }

  @Override
  public void replaceChild( XulComponent oldElement, XulComponent newElement ) throws XulDomException {
    this.resetContainer();
    super.replaceChild( oldElement, newElement );
  }
}
