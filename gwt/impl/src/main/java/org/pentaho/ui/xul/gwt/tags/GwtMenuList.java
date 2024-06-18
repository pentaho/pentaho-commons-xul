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

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import org.pentaho.gwt.widgets.client.listbox.CustomListBox;
import org.pentaho.gwt.widgets.client.listbox.ListItem;
import org.pentaho.gwt.widgets.client.utils.string.StringUtils;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.XulEventSource;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulMenuList;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.gwt.binding.GwtBindingContext;
import org.pentaho.ui.xul.gwt.binding.GwtBindingMethod;
import org.pentaho.ui.xul.stereotype.Bindable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GwtMenuList<T> extends AbstractGwtXulContainer implements XulMenuList<T> {

  static final String ELEMENT_NAME = "menulist"; //$NON-NLS-1$
  private boolean editable;
  private Collection<T> elements;

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtMenuList();
      }
    } );
  }

  private int selectedIndex = -1;
  private Label label;
  private CustomListBox listbox;
  private String bindingProperty;
  private boolean loaded = false;
  private boolean suppressLayout = false;
  private boolean inLayoutProcess = false;
  private Object previousSelectedItem = null;
  private String previousValue;
  private String onCommand;
  private SimplePanel wrapper = null;

  public GwtMenuList() {
    super( ELEMENT_NAME );
    wrapper = new SimplePanel() {

      @Override
      public void setHeight( String height ) {
      }

      @Override
      public void setWidth( String width ) {
        super.setWidth( width );
        listbox.setWidth( width );
      }

    };
    wrapper.addStyleName( "gwt-menu-list" );
    wrapper.addStyleName( "flex-row" );
    wrapper.addStyleName( "flex-item-h" );

    listbox = new CustomListBox();
    wrapper.add( listbox );
    setManagedObject( wrapper );

    listbox.addChangeListener( new ChangeListener() {

      public void onChange( Widget arg0 ) {

        /*
         * This actionlistener is fired at parse time when elements are added. We'll ignore that call by checking a
         * variable set to true post parse time
         */
        if ( !loaded ) {
          return;
        }

        fireSelectedEvents();
      }

    } );
  }

  public CustomListBox getNativeListBox() {
    return this.listbox;
  }

  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    super.init( srcEle, container );
    setBinding( srcEle.getAttribute( "pen:binding" ) );
    setOnCommand( srcEle.getAttribute( "oncommand" ) );
    if ( !StringUtils.isEmpty( srcEle.getAttribute( "width" ) ) ) {
      setWidth( Integer.parseInt( srcEle.getAttribute( "width" ) ) );
    }
    if ( !StringUtils.isEmpty( srcEle.getAttribute( "height" ) ) ) {
      setHeight( Integer.parseInt( srcEle.getAttribute( "height" ) ) );
    }

    if ( !StringUtils.isEmpty( srcEle.getAttribute( "editable" ) ) ) {
      setEditable( Boolean.parseBoolean( srcEle.getAttribute( "editable" ) ) );
    }

    if ( !StringUtils.isEmpty( srcEle.getAttribute( "disabled" ) ) ) {
      setDisabled( Boolean.parseBoolean( srcEle.getAttribute( "disabled" ) ) );
    }
  }

  public String getBinding() {
    return bindingProperty;
  }

  @Bindable
  @Override
  public void setDisabled( boolean disabled ) {
    this.listbox.setEnabled( !disabled );
    super.setDisabled( disabled );
  }

  @Bindable
  public Collection getElements() {
    List<Object> vals = new ArrayList<Object>();
    for ( int i = 0; i < listbox.getItems().size(); i++ ) {
      vals.add( listbox.getItems().get( i ).getValue() );
    }
    return vals;
  }

  @Bindable
  public int getSelectedIndex() {
    return listbox.getSelectedIndex();
  }

  @Bindable
  public String getSelectedItem() {
    ListItem selectedItem = listbox.getSelectedItem();

    return ( selectedItem == null ) ? null : listbox.getSelectedItem().getValue().toString();
  }

  public void replaceAllItems( Collection tees ) throws XulDomException {
    throw new RuntimeException( "Not implemented" );
  }

  public void setBinding( String binding ) {
    this.bindingProperty = binding;
  }

  @Bindable
  public void setElements( Collection<T> elements ) {
    this.elements = elements;

    try {
      suppressLayout = true;
      listbox.setSuppressLayout( true );

      XulMenupopup popup = getPopupElement();
      for ( XulComponent menuItem : popup.getChildNodes() ) {
        popup.removeChild( menuItem );
      }
      if ( elements == null || elements.size() == 0 ) {
        this.suppressLayout = false;
        layout();
        return;
      }
      for ( T t : elements ) {
        GwtMenuitem item = new GwtMenuitem( popup );

        String attribute = getBinding();
        if ( !StringUtils.isEmpty( attribute ) ) {
          item.setLabel( extractLabel( t ) );
        } else {
          item.setLabel( t.toString() );
        }
        popup.addChild( item );
      }

      this.suppressLayout = false;

      layout();
    } catch ( Exception e ) {
      System.out.println( e.getMessage() );
      e.printStackTrace();
    }

  }

  public GwtMenupopup getPopupElement() {
    for ( Element comp : getChildNodes() ) {
      if ( comp instanceof GwtMenupopup ) {
        return (GwtMenupopup) comp;
      }
    }
    throw new IllegalStateException( "menulist is missing a menupopup child element" );
  }

  public void setOncommand( final String command ) {
    this.listbox.addChangeListener( new ChangeListener() {

      public void onChange( Widget arg0 ) {

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
        try {
          con.invoke( command, new Object[] {} );
        } catch ( XulException e ) {
          Window.alert( "MenuList onChange error: " + e.getMessage() );
        }
        fireSelectedEvents();
      }

    } );
  }

  @Bindable
  public void setSelectedIndex( int idx ) {
    int previousValue = selectedIndex;
    selectedIndex = idx;
    listbox.setSelectedIndex( idx );
    firePropertyChange( "selectedIndex", previousValue, idx );
  }

  @Bindable
  public void setSelectedItem( T t ) {
    // this is coming in as a string for us.
    // int i=0;
    // for(Object o : getElements()){
    // if(((String) o).equals(extractLabel(t))){
    // setSelectedIndex(i);
    // }
    // i++;
    // }

    int i = 0;
    Collection eles = ( elements != null ) ? elements : getElements();
    for ( Object o : eles ) {
      if ( ( o ).equals( t ) ) {
        setSelectedIndex( i );
      }
      i++;
    }
  }

  private String extractLabel( T t ) {
    String attribute = getBinding();
    if ( StringUtils.isEmpty( attribute ) || !( t instanceof XulEventSource ) ) {
      return t.toString();
    } else {
      try {
        GwtBindingMethod m = GwtBindingContext.typeController.findGetMethod( t, attribute );
        if ( m == null ) {
          System.out.println( "could not find getter method for " + t + "." + attribute );
        }
        return m.invoke( t, new Object[] {} ).toString();

      } catch ( Exception e ) {
        throw new RuntimeException( e );
      }
    }
  }

  private void fireSelectedEvents() {

    int selectedIdx = listbox.getSelectedIndex();
    Object newSelectedItem =
        ( selectedIdx >= 0 && elements != null && selectedIdx < elements.size() ) ? elements.toArray()[selectedIdx]
            : getSelectedItem();

    GwtMenuList.this.changeSupport.firePropertyChange( "selectedItem", previousSelectedItem, newSelectedItem );
    int prevSelectedIndex = selectedIndex;
    selectedIndex = getSelectedIndex();
    GwtMenuList.this.changeSupport.firePropertyChange( "selectedIndex", prevSelectedIndex, selectedIndex );
    previousSelectedItem = newSelectedItem;

    String newVal = listbox.getValue();
    GwtMenuList.this.changeSupport.firePropertyChange( "value", previousValue, newVal );

    previousValue = newVal;

    if ( StringUtils.isEmpty( GwtMenuList.this.getOnCommand() ) == false && prevSelectedIndex != selectedIndex ) {
      try {
        GwtMenuList.this.getXulDomContainer().invoke( GwtMenuList.this.getOnCommand(), new Object[] {} );
      } catch ( XulException e ) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void layout() {

    inLayoutProcess = true;
    if ( suppressLayout ) {
      inLayoutProcess = false;
      return;
    }
    int currentSelectedIndex = getSelectedIndex();
    Object currentSelectedItem = listbox.getSelectedItem();
    GwtMenupopup popup = getPopupElement();
    selectedIndex = -1;
    listbox.setSuppressLayout( true );
    this.listbox.removeAll();

    GwtMenuitem selectedItem = null;

    // capture first child as default selection
    boolean firstChild = true;
    for ( XulComponent item : popup.getChildNodes() ) {
      GwtMenuitem tempItem = ( (GwtMenuitem) item );
      listbox.addItem( tempItem.getLabel() );
      if ( tempItem.isSelected() || firstChild ) {
        selectedItem = tempItem;
        firstChild = false;
      }
    }

    listbox.setSuppressLayout( false );
    inLayoutProcess = false;

    if ( selectedItem != null ) {
      // if first setting it to the currently selected one will not fire event.
      // manually firing here
      if ( this.getSelectedItem().equals( selectedItem.getLabel() ) ) {
        fireSelectedEvents();
      }
      setSelectedItem( (T) selectedItem.getLabel() );
    }
    if ( getSelectedIndex() > -1 ) {
      if ( currentSelectedIndex < listbox.getItems().size() ) {
        int index = getIndexForItem( currentSelectedItem );
        if ( index > 0 ) {
          listbox.setSelectedIndex( currentSelectedIndex );
        } else {
          listbox.setSelectedIndex( listbox.getSelectedIndex() );
        }
      } else {
        listbox.setSelectedIndex( listbox.getSelectedIndex() );
      }
    }
    loaded = true;
  }

  @Override
  public void resetContainer() {
    this.layout();
  }

  public String getOnCommand() {

    return onCommand;
  }

  public void setOnCommand( String command ) {

    this.onCommand = command;
  }

  @Bindable
  public void setEditable( boolean editable ) {
    this.listbox.setEditable( editable );
    this.editable = editable;
  }

  @Bindable
  public boolean getEditable() {
    return editable;
  }

  @Bindable
  public String getValue() {
    return getSelectedItem();
  }

  @Bindable
  public void setValue( String value ) {
    for ( ListItem item : listbox.getItems() ) {
      if ( item.getValue() != null && item.getValue().equals( value ) ) {
        listbox.setSelectedItem( item );
        return;
      }
    }
    listbox.setValue( value );
  }

  @Override
  public void setWidth( int width ) {
    listbox.setWidth( width + "px" );
  }

  @Override
  public void setHeight( int height ) {
    listbox.setHeight( height + "px" );
  }

  private int getIndexForItem( Object obj ) {
    int index = -1;
    if ( obj != null ) {
      for ( ListItem item : listbox.getItems() ) {
        index++;
        if ( item.getValue() != null && item.getValue().equals( obj.toString() ) ) {
          return index;
        }
      }
    }
    return index;
  }
}
