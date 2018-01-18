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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.Expression;
import java.util.Collection;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulMenuList;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;

public class SwingMenuList<T> extends AbstractSwingContainer implements XulMenuList<T> {

  private JComboBox combobox;

  private XulDomContainer xulDomContainer;

  private static final Log logger = LogFactory.getLog( SwingMenuList.class );

  private boolean loaded = false;

  private String binding;

  private String previousSelectedItem = null;

  private boolean editable = false;

  public SwingMenuList( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "menulist" );

    this.xulDomContainer = domContainer;

    combobox = new JComboBox( new DefaultComboBoxModel() );
    setManagedObject( combobox );

    combobox.addItemListener( new ItemListener() {

      public void itemStateChanged( ItemEvent e ) {
        if ( e.getStateChange() == ItemEvent.SELECTED && !inLayoutProcess && initialized ) {
          fireSelectedEvents();
        }
      }

    } );

  }

  private boolean suppressLayout = false;
  private boolean inLayoutProcess = false;

  public void layout() {
    inLayoutProcess = true;
    if ( suppressLayout ) {
      inLayoutProcess = false;
      return;
    }
    SwingMenupopup popup = getPopupElement();
    DefaultComboBoxModel model = (DefaultComboBoxModel) this.combobox.getModel();
    model.removeAllElements();

    SwingMenuitem selectedItem = null;

    // capture first child as default selection
    boolean firstChild = true;
    for ( XulComponent item : popup.getChildNodes() ) {
      JMenuItem jmenuItem = (JMenuItem) ( (SwingMenuitem) item ).getManagedObject();
      SwingMenuitem tempItem = (SwingMenuitem) item;
      model.addElement( tempItem );

      if ( tempItem.isSelected() || firstChild ) {
        selectedItem = tempItem;
        firstChild = false;
      }
    }

    inLayoutProcess = false;

    if ( selectedItem != null ) {
      // if first setting it to the currently selected one will not fire event.
      // manually firing here
      if ( model.getSelectedItem() == selectedItem ) {
        fireSelectedEvents();
      }
      model.setSelectedItem( selectedItem );
    }
    initialized = true;
    loaded = true;
  }

  public void onDomReady() {

    if ( combobox.getSelectedItem() != null ) {
      fireSelectedEvents();
    }
  }

  private void fireSelectedEvents() {

    SwingMenuList.this.changeSupport.firePropertyChange( "selectedItem", previousSelectedItem, this.getSelectedItem() );
    SwingMenuList.this.changeSupport.firePropertyChange( "selectedIndex", null, combobox.getSelectedIndex() );
    previousSelectedItem = getSelectedItem();
  }

  public SwingMenupopup getPopupElement() {
    for ( Element comp : getChildNodes() ) {
      if ( SwingMenupopup.class.isAssignableFrom( comp.getClass() ) ) {
        return (SwingMenupopup) comp;
      }
    }
    throw new IllegalStateException( "menulist is missing a menupopup child element" );
  }

  /*
   * Swaps out the managed list. Effectively replaces the SwingMenupopup child component. (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.components.XulMenuList#replaceAll(java.util.List)
   */
  @Deprecated
  public void replaceAllItems( Collection<T> tees ) {
    setElements( tees );
  }

  public String getSelectedItem() {

    if ( editable ) {
      return textComp.getText();
    }
    return ( (SwingMenuitem) this.combobox.getModel().getSelectedItem() ).getLabel();
  }

  public void setSelectedItem( T t ) {
    SwingMenupopup popup = getPopupElement();
    for ( XulComponent item : popup.getChildNodes() ) {
      SwingMenuitem tempItem = (SwingMenuitem) item;
      if ( tempItem.getLabel().equals( extractLabel( t ) ) ) {
        this.combobox.setSelectedItem( tempItem );
      }
    }
  }

  public void setOncommand( final String command ) {
    combobox.addActionListener( new ActionListener() {
      public void actionPerformed( ActionEvent evt ) {

        /*
         * This actionlistener is fired at parse time when elements are added. We'll ignore that call by checking a
         * variable set to true post parse time
         */
        if ( !loaded ) {
          return;
        }
        Document doc = getDocument();
        XulRoot window = (XulRoot) doc.getRootElement();
        final XulDomContainer con = window.getXulDomContainer();

        SwingUtilities.invokeLater( new Runnable() {
          public void run() {
            try {
              con.invoke( command, new Object[] {} );
            } catch ( XulException e ) {
              logger.error( "Error calling oncommand event", e );
            }
          }
        } );
      }
    } );
  }

  public Collection<T> getElements() {
    return (Collection) this.getPopupElement().getChildNodes();
  }

  public String getBinding() {
    return binding;
  }

  public void setBinding( String binding ) {
    this.binding = binding;
  }

  private String extractLabel( T t ) {
    String attribute = getBinding();
    if ( StringUtils.isEmpty( attribute ) ) {
      return t.toString();
    } else {
      String getter = "get" + ( String.valueOf( attribute.charAt( 0 ) ).toUpperCase() ) + attribute.substring( 1 );
      try {
        return new Expression( t, getter, null ).getValue().toString();
      } catch ( Exception e ) {
        throw new RuntimeException( e );
      }
    }
  }

  public void setElements( Collection<T> tees ) {
    this.suppressLayout = true;
    XulMenupopup popup = getPopupElement();
    for ( XulComponent menuItem : popup.getChildNodes() ) {
      popup.removeChild( menuItem );
    }

    for ( T t : tees ) {
      SwingMenuitem item = new SwingMenuitem( null, popup, this.xulDomContainer, null );

      String attribute = getBinding();
      item.setLabel( extractLabel( t ) );

      popup.addChild( item );
    }

    this.suppressLayout = false;
    layout();
  }

  public int getSelectedIndex() {
    return this.combobox.getSelectedIndex();
  }

  public void setSelectedIndex( int idx ) {
    this.combobox.setSelectedIndex( idx );
  }

  @Override
  public void setWidth( int width ) {
    super.setWidth( width );
    Dimension box = new Dimension();
    box.height = this.combobox.getPreferredSize().height;
    box.width = width;
    this.combobox.setMaximumSize( box );
    this.combobox.setPreferredSize( box );
    this.combobox.setMinimumSize( box );
  }

  private String oldValue = "";
  private JTextComponent textComp;

  public void setEditable( boolean editable ) {
    this.editable = editable;
    combobox.setEditable( editable );
    if ( editable ) {
      textComp = (JTextComponent) combobox.getEditor().getEditorComponent();

      textComp.addKeyListener( new KeyListener() {
        public void keyPressed( KeyEvent e ) {
          oldValue = textComp.getText();
        }

        public void keyReleased( KeyEvent e ) {
          if ( oldValue != null && !oldValue.equals( textComp.getText() ) ) {
            SwingMenuList.this.changeSupport.firePropertyChange( "value", oldValue, SwingMenuList.this.getValue() );
            oldValue = textComp.getText();
          } else if ( oldValue == null ) {
            // AWT error where sometimes the keyReleased is fired before keyPressed.
            oldValue = textComp.getText();
          } else {
            logger.debug( "Special key pressed, ignoring" );
          }
        }

        public void keyTyped( KeyEvent e ) {
        }

      } );
    }
  }

  public boolean getEditable() {
    return this.editable;
  }

  public String getValue() {
    return ( textComp != null ) ? textComp.getText() : ( (SwingMenuitem) this.combobox.getModel().getSelectedItem() )
        .getLabel();
  }

  public void setValue( String value ) {
    if ( editable ) {
      ( (JTextComponent) combobox.getEditor().getEditorComponent() ).setText( value );
    }
  }
}
