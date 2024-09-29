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

import java.awt.CardLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulDeck;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;
import org.pentaho.ui.xul.util.Orient;

public class SwingDeck extends AbstractSwingContainer implements XulDeck {

  protected JPanel container;

  private CardLayout cardLayout;

  private int selectedChildIndex = 0;

  private int numChildren = 0;

  private List<XulComponent> children = new ArrayList<XulComponent>();

  public SwingDeck( Element self, XulComponent parent, XulDomContainer container, String tagName ) {
    this( parent, tagName, Orient.HORIZONTAL );
  }

  public SwingDeck( XulComponent parent, String tagName, Orient orient ) {
    super( tagName );
    cardLayout = new CardLayout();
    container = new JPanel( cardLayout );
    setSelectedIndex( 0 );
    setManagedObject( container );
  }

  @Override
  public void addChild( Element ele ) {
    super.addChild( ele );
  }

  public int getSelectedIndex() {
    return selectedChildIndex;
  }

  public void setSelectedIndex( int index ) {
    int previousVal = selectedChildIndex;
    selectedChildIndex = index;
    cardLayout.show( container, "" + index );
    this.changeSupport.firePropertyChange( "disabled", previousVal, index );
  }

  public void layout() {
    initialized = true;
    numChildren = 0;
    for ( Element e : getChildNodes() ) {
      this.container.add( (Component) ( (XulComponent) e ).getManagedObject(), "" + numChildren );
      numChildren++;
    }
  }

}
