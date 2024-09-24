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
 * Copyright (c) 2002-2018 Hitachi Vantara..  All rights reserved.
 */

package org.pentaho.ui.xul.jface.tags;

import java.beans.Expression;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuList;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;

//TODO: Move creation of combobox to late initialization to support switching from editable to non-editable.
public class JfaceMenuList<T> extends AbstractSwtXulContainer implements XulMenuList<T> {

  private CCombo combobox;

  private XulDomContainer xulDomContainer;

  private static final Log logger = LogFactory.getLog( JfaceMenuList.class );

  private String binding;

  private Object previousSelectedItem = null;
  private String previousValue;

  private JfaceMenupopup popup;

  private JfaceMenuitem selectedItem = null;
  private List elements = null;

  private boolean editable = false;

  private String command;

  private XulComponent parent;

  public JfaceMenuList( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "menulist" );
    this.xulDomContainer = domContainer;
    this.parent = parent;
    setEditable( "true".equals( self.getAttributeValue( "editable" ) ) );
    setupCombobox();
  }

  private void setupCombobox() {
    int style = SWT.DROP_DOWN;
    if ( !editable ) {
      style |= SWT.READ_ONLY;
    }
    combobox = new CCombo( (Composite) parent.getManagedObject(), style | SWT.SINGLE | SWT.LEFT | SWT.BORDER  );

    setManagedObject( combobox );

    combobox.addSelectionListener( new SelectionAdapter() {
      public void widgetSelected( SelectionEvent e ) {
        fireSelectedEvents();
      }
    } );

    combobox.addModifyListener( new ModifyListener() {
      public void modifyText( ModifyEvent modifyEvent ) {
        fireModifiedEvents();
      }
    } );
  }

  @Override
  public void addChild( Element ele ) {
    super.addChild( ele );
    if ( !( ele instanceof XulMenupopup ) ) {
      return;
    }
    popup = (JfaceMenupopup) ele;
    int index = -1;
    int selectedIndex = index;
    for ( XulComponent child : popup.getChildNodes() ) {
      index++;
      if ( !( child instanceof JfaceMenuitem ) ) {
        continue;
      }
      if ( ( (JfaceMenuitem) child ).isSelected() ) {
        combobox.select( index );
        selectedIndex = index;
      }
    }
    setSelectedIndex( selectedIndex );
  }

  public void layout() {
    JfaceMenuitem selectedItem;
    int i = 0;
    int index = -1;
    combobox.removeAll();
    for ( XulComponent item : popup.getChildNodes() ) {
      JfaceMenuitem mItem = (JfaceMenuitem) item;
      if ( mItem.isVisible() ) {
        combobox.add( mItem.getLabel() );
      }
      if ( mItem.isSelected() ) {
        combobox.select( i );
        index = i;
      }
      i++;
    }
    if ( index == -1 ) {
      if ( getEditable() ) {
        setValue( previousValue );
      } else {
        setSelectedIndex( 0 );
      }
    }
    this.combobox.update();
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

  public void setOncommand( final String command ) {
    this.command = command;
  }

  public Collection<T> getElements() {
    List<T> elements = null;
    if ( this.elements == null ) {
      if ( popup == null ) {
        elements = null;
      } else {
        if ( this.getBinding() != null ) {
          elements = (List<T>) new ArrayList<String>();
          for ( XulComponent component : popup.getChildNodes() ) {
            elements.add( (T) component.getAttributeValue( this.getBinding() ) );
          }
        } else {
          elements = (List<T>) popup.getChildNodes();
        }
      }
    } else {
      elements = this.elements;
    }
    return elements;
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
    XulMenuitem menuitem;
    List<XulComponent> menuItems = popup.getChildNodes();
    elements = (List<T>) tees;
    int index = 0;
    int itemCount = menuItems.size();
    // loop over new elements
    for ( T t : tees ) {
      // obtain an item
      if ( index < itemCount ) {
        // item exists, reuse it
        menuitem = (XulMenuitem) menuItems.get( index );
        menuitem.setVisible( true );
      } else {
        // item doesn't exist, create one
        menuitem = popup.createNewMenuitemAtPos( index );
      }
      index++;
      // update item
      menuitem.setLabel( extractLabel( t ) );
    }
    // hide remaining existing items
    for ( ; index < itemCount; index++ ) {
      menuItems.get( index ).setVisible( false );
    }
    layout();
    setValue( previousValue );
  }

  public String getSelectedItem() {
    int idx = combobox.getSelectionIndex();
    return ( idx > -1 && idx < this.combobox.getItemCount() ) ? this.combobox.getItem( idx ) : null;
  }

  public void setSelectedItem( T t ) {
    int index;
    List<T> elements = (List<T>) getElements();
    if ( t == null ) {
      index = -1;
    } else if ( elements == null ) {
      index = combobox.indexOf( t.toString() );
    } else {
      index = elements.indexOf( t );
      if ( index == -1 ) {
        index = combobox.indexOf( t.toString() );
      }
    }
    setSelectedIndex( index );
  }

  public int getSelectedIndex() {
    int selectedIndex;
    Collection elements = getElements();
    if ( elements == null || elements.size() == 0 ) {
      selectedIndex = -1;
    } else {
      selectedIndex = this.combobox.getSelectionIndex();
    }
    return selectedIndex;
  }

  public void setSelectedIndex( int idx ) {
    if ( idx == -1 ) {
      combobox.clearSelection();
    } else {
      combobox.select( idx );
    }
    fireSelectedEvents();
  }

  @Override
  public void removeChild( Element ele ) {
    String selected = this.getSelectedItem();
    super.removeChild( ele );
    if ( selected == null ) {
      return;
    }
    Collection<T> elist = getElements();
    for ( T t : elist ) {
      if ( selected.equals( t.toString() ) ) {
        setSelectedItem( t );
      }
    }
  }

  private void fireSelectedEvents() {
    int idx = getSelectedIndex();
    Collection elements = getElements();

    if ( idx >= 0 ) {
      Object newSelectedItem = ( elements == null ) ? null : elements.toArray()[idx];
      changeSupport.firePropertyChange( "selectedItem", previousSelectedItem, newSelectedItem );
      this.previousSelectedItem = newSelectedItem;
    }

    changeSupport.firePropertyChange( "selectedIndex", null, combobox.getSelectionIndex() );
    if ( JfaceMenuList.this.command != null && getDocument() != null ) { // make sure we're on the dom tree
                                                                         // (initialized)
      invoke( JfaceMenuList.this.command, new Object[] {} );
    }
  }

  private void fireModifiedEvents() {
    String newValue = getValue();
    changeSupport.firePropertyChange( "value", previousValue, newValue );
    previousValue = newValue;
  }

  public void setEditable( boolean editable ) {
    this.editable = editable;
  }

  public boolean getEditable() {
    return editable;
  }

  public String getValue() {
    return combobox.getText();
  }

  public void setValue( String value ) {
    if ( value == null ) {
      value = "";
    }
    combobox.setText( value );
  }

  @Override
  public void setDisabled( boolean disabled ) {
    super.setDisabled( disabled );
    combobox.setEnabled( !disabled );
  }

}
