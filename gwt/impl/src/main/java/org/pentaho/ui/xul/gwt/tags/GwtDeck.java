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

package org.pentaho.ui.xul.gwt.tags;

import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Widget;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulDeck;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.gwt.util.Resizable;
import org.pentaho.ui.xul.stereotype.Bindable;
import org.pentaho.ui.xul.util.Orient;

public class GwtDeck extends AbstractGwtXulContainer implements XulDeck {

  static final String ELEMENT_NAME = "deck"; //$NON-NLS-1$

  private enum Property {
    ID, SELECTEDINDEX
  }

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtDeck();
      }
    } );
  }

  protected DeckPanel container;
  int selectedIndex = 0;

  public GwtDeck() {
    this( Orient.HORIZONTAL );
  }

  public GwtDeck( Orient orient ) {
    super( ELEMENT_NAME );
    DeckPanel deckPanel = new DeckPanel();
    deckPanel.addStyleName( "deck-panel" );
    deckPanel.addStyleName( "flex-column" );
    container = deckPanel;
    setManagedObject( container );
  }

  @Override
  public void setAttribute( String name, String value ) {
    super.setAttribute( name, value );
    try {
      Property prop = Property.valueOf( name.replace( "pen:", "" ).toUpperCase() );
      switch ( prop ) {

        case SELECTEDINDEX:
          this.setSelectedIndex( Integer.valueOf( value ) );
          break;
      }
    } catch ( IllegalArgumentException e ) {
      System.out.println( "Could not find Property in Enum for: " + name + " in class" + getClass().getName() );
    }
  }

  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    super.init( srcEle, container );
    if ( srcEle.hasAttribute( "selectedIndex" ) && srcEle.getAttribute( "selectedIndex" ).trim().length() > 0 ) {
      try {
        setSelectedIndex( Integer.parseInt( srcEle.getAttribute( "selectedIndex" ) ) );
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void addChild( Element ele ) {
    super.addChild( ele );
    this.container.add( (Widget) ( (XulComponent) ele ).getManagedObject() );

    // sync with selectedIndex
    if ( this.container.getVisibleWidget() != selectedIndex && selectedIndex < container.getWidgetCount() ) {
      container.showWidget( selectedIndex );
    }
  }

  @Override
  public void removeChild( Element ele ) {
    super.removeChild( ele );
    this.container.remove( (Widget) ( (XulComponent) ele ).getManagedObject() );

    // sync with selectedIndex
    if ( this.container.getVisibleWidget() != selectedIndex && selectedIndex < container.getWidgetCount()
        && selectedIndex > -1 ) {
      container.showWidget( selectedIndex );
    }

  }

  @Bindable
  public int getSelectedIndex() {
    return container.getVisibleWidget();
  }

  @Bindable
  public void setSelectedIndex( int index ) {
    int previousVal = selectedIndex;
    if ( index < container.getWidgetCount() && index >= 0 ) {
      container.showWidget( index );
      notifyOnShow( this );
    }

    selectedIndex = index;
    this.firePropertyChange( "selectedIndex", previousVal, index );
  }

  /**
   * Child XUL elements need to be notified of visibility changes if they implement Resizable.
   * 
   * This method is recursive.
   * 
   * @param ele
   */
  private void notifyOnShow( Element ele ) {

    if ( ele instanceof Resizable ) {
      ( (Resizable) ele ).onResize();
    }
    for ( Element e : ele.getChildNodes() ) {
      notifyOnShow( e );
    }
  }

  public void layout() {

  }

}
