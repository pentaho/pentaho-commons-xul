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

package org.pentaho.ui.xul.swt.tags;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulEventSource;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.binding.DefaultBinding;
import org.pentaho.ui.xul.containers.XulListbox;
import org.pentaho.ui.xul.dnd.DropEffectType;
import org.pentaho.ui.xul.dnd.DropEvent;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;

import java.beans.Expression;
import java.util.ArrayList;
import java.util.Collection;

public class SwtListbox extends AbstractSwtXulContainer implements XulListbox {
  private static final long serialVersionUID = 3064125049914932493L;

  private ListViewer listBox;

  private boolean disabled = false;

  private String selType;

  private int rowsToDisplay = 0;

  String onSelect = null;

  private XulDomContainer container;

  private String binding;

  private Collection elements;

  private String command;

  private int[] curSelectedIndices = null;

  private int curSelectedIndex = -1;

  private Object prevSelectedObject;

  private boolean suppressEvents;

  private java.util.List<Binding> elementBindings = new ArrayList<Binding>();

  public SwtListbox( Element self, XulComponent parent, XulDomContainer container, String tagName ) {

    super( tagName );
    this.container = container;

    int style = SWT.BORDER | SWT.V_SCROLL;

    if ( self.getAttributeValue( "seltype" ) != null && self.getAttributeValue( "seltype" ).equals( "multi" ) ) {
      style |= SWT.MULTI;
    } else {
      style |= SWT.SINGLE;
    }

    listBox = new ListViewer( (Composite) parent.getManagedObject(), style );

    listBox.getList().addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent arg0 ) {
        fireSelectedEvents();
      }

    } );

    setManagedObject( listBox );
  }

  private void fireSelectedEvents() {
    if ( this.suppressEvents ) {
      return;
    }

    int[] indices = listBox.getList().getSelectionIndices();
    SwtListbox.this.changeSupport.firePropertyChange( "selectedIndices", curSelectedIndices, indices );
    curSelectedIndices = indices;

    SwtListbox.this.changeSupport.firePropertyChange( "selectedIndex", curSelectedIndex, getSelectedIndex() );
    curSelectedIndex = getSelectedIndex();

    if ( elements != null ) {
      Object newSelectedObject = getSelectedItem();
      SwtListbox.this.changeSupport.firePropertyChange( "selectedItem", prevSelectedObject, newSelectedObject );
      prevSelectedObject = newSelectedObject;

      Object[] newSelectedObjectList = getSelectedItems();
      SwtListbox.this.changeSupport.firePropertyChange( "selectedItems", null, newSelectedObjectList );
    }
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled( boolean disabled ) {
    this.disabled = disabled;
    if ( !listBox.getList().isDisposed() ) {
      listBox.getList().setEnabled( !disabled );
    }
  }

  public void setDisabled( String dis ) {
    this.disabled = Boolean.parseBoolean( dis );
    if ( !listBox.getList().isDisposed() ) {
      listBox.getList().setEnabled( !disabled );
    }
  }

  public int getRows() {
    return rowsToDisplay;
  }

  public void setRows( int rowsToDisplay ) {
    this.rowsToDisplay = rowsToDisplay;
    if ( ( !listBox.getList().isDisposed() ) && ( rowsToDisplay > 0 ) ) {
      int ht = rowsToDisplay * listBox.getList().getItemHeight();

      // listBox.setSize(listBox.getSize().x,height);
      if ( listBox.getList().getLayoutData() != null ) {
        ( (GridData) listBox.getList().getLayoutData() ).heightHint = ht;
        ( (GridData) listBox.getList().getLayoutData() ).minimumHeight = ht;
      }
    }
  }

  public String getSeltype() {
    return selType;
  }

  /**
   * TODO: PARTIAL IMPL: Because this is needed on construction, we need to rework this class a bit to allow setting of
   * multiple selection.
   */
  public void setSeltype( String selType ) {
    this.selType = selType;

  }

  public void addItem( Object item ) {

    listBox.add( item );

  }

  public void removeItems() {
    listBox.getList().removeAll();
  }

  public String getOnselect() {
    return onSelect;
  }

  public void setOnselect( final String method ) {
    onSelect = method;
    listBox.getList().addSelectionListener( new SelectionAdapter() {
      public void widgetSelected( org.eclipse.swt.events.SelectionEvent arg0 ) {
        invoke( method );
      }
    } );
  }

  public Object getSelectedItem() {
    if ( listBox.getList().getSelection() == null || listBox.getList().getSelectionCount() <= 0 ) {
      return null;
    }

    // If there's a bound collection return the model object.
    int selIdx = getSelectedIndex();
    if ( elements != null && selIdx >= 0 && selIdx < elements.size() ) {
      return elements.toArray()[selIdx];
    }
    // otherwise return String value
    return listBox.getList().getSelection()[0];
  }

  public Object[] getSelectedItems() {
    // If there's a bound collection return the model object.
    int[] selIndices = getSelectedIndices();
    if ( elements != null && selIndices.length > 0 ) {
      Object[] returnArray = new Object[selIndices.length];
      Object[] valueArray = elements.toArray();
      for ( int i = 0; i < selIndices.length; i++ ) {
        returnArray[i] = valueArray[selIndices[i]];
      }
      return returnArray;
    }
    // otherwise return String value

    return listBox.getList().getSelection();
  }

  public int getSelectedIndex() {
    return listBox.getList().getSelectionIndex();
  }

  public int[] getSelectedIndices() {
    return listBox.getList().getSelectionIndices();
  }

  public void setSelectedItem( Object item ) {
    setSelectedItems( new Object[] { item } );
  }

  public void setSelectedItems( Object[] items ) {
    if ( elements != null ) {
      // If bound collection get idx and use that.
      int[] indices = new int[items.length];
      int pos = 0;
      for ( Object o : items ) {
        int index = getIndex( o );
        if ( index >= 0 ) {
          indices[pos++] = index;
        } else {
          indices[pos++] = -1;
        }
      }
      setSelectedIndices( indices );
    } else {

      final String[] sel = new String[items.length];
      for ( int i = 0; i < items.length; i++ ) {
        sel[i] = items[i].toString();
      }

      suppressEvents = true;
      listBox.getList().setSelection( sel );
      suppressEvents = false;

      // SWT doesn't seem to fire this event when the selection
      // is made via code, only with a mouse or keyboard action.

      listBox.getList().notifyListeners( SWT.Selection, new Event() );

    }
  }

  private int getIndex( Object o ) {
    Object[] elementArray = elements.toArray();
    for ( int i = 0; i < elementArray.length; i++ ) {
      if ( elementArray[i] == o ) {
        return i;
      }
    }
    return -1;
  }

  public int getRowCount() {
    return ( !listBox.getList().isDisposed() ) ? listBox.getList().getItemCount() : 0;
  }

  public void setSelectedIndex( int index ) {
    if ( index > listBox.getList().getItemCount() ) {
      return;
    }
    if ( listBox.getList().isDisposed() ) {
      // TODO log error ..
      System.out.println( "Warning: attempting to access listbox after disposal." );
    }
    listBox.getList().setSelection( index );
  }

  public void setSelectedindex( String index ) {
    if ( listBox.getList().isDisposed() ) {
      // TODO log error ..
      System.out.println( "Warning: attempting to access listbox after disposal." );
    }
    listBox.getList().setSelection( Integer.parseInt( index ) );
  }

  public <T> Collection<T> getElements() {
    return this.elements;

  }

  public <T> void setElements( Collection<T> elements ) {
    if ( elements != null ) {

      destroyPreviousBindings();

      this.elements = elements;
      this.prevSelectedObject = null;
      this.curSelectedIndex = -1;
      this.curSelectedIndices = null;

      listBox.getList().removeAll();
      for ( T t : elements ) {

        SwtListitem item = null;
        try {
          item = (SwtListitem) container.getDocumentRoot().createElement( "listitem" );
        } catch ( XulException e ) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

        this.addChild( item );
        if (t instanceof XulEventSource ){
          item.setValue( t );
        }
        wireLabel( t, item );
      }
      layout();
    }
  }

  public void setSelectedIndices( int[] indices ) {
    suppressEvents = true;
    listBox.getList().deselectAll();
    listBox.getList().select( indices );
    suppressEvents = false;
    fireSelectedEvents();
  }

  public void setBinding( String binding ) {
    this.binding = binding;
  }

  public String getBinding() {
    return binding;
  }

  public void updateLabel( SwtListitem item ) {
    listBox.update( item.getValue(), null  );
  }

  private <T> void wireLabel( T t, SwtListitem item ) {
    if ( t == null ) {
      return;
    }

    String attribute = getBinding();
    if ( StringUtils.isNotEmpty( attribute ) ) {

      try {

        if (t instanceof XulEventSource ){

          Binding binding = createBinding( (XulEventSource) t, attribute, item, "label" );
          elementBindings.add( binding );
          binding.setBindingType( Binding.Type.ONE_WAY );
          container.addBinding( binding );
          binding.fireSourceChanged();

        } else {

          // do things the old way; backward compatibility to pre-binding code

          String getter = "get" + ( String.valueOf(attribute.charAt(0) ).toUpperCase()) + attribute.substring(1);
          String label =  ""+ ( new Expression(t, getter, null).getValue() );
          item.setValue( label ); // the object was not preserved in history .. it shouldn't be here
          item.setLabel( label );

          }

      } catch ( Exception e ) {
        throw new RuntimeException( e );
      }
    } else {
      item.setLabel( t.toString() );
    }
  }

  private Binding createBinding( XulEventSource source, String prop1, XulEventSource target, String prop2 ) {
    if ( bindingProvider != null ) {
      return bindingProvider.getBinding( source, prop1, target, prop2 );
    }
    return new DefaultBinding( source, prop1, target, prop2 );
  }

  private void destroyPreviousBindings() {

    for ( Binding bind : elementBindings ) {
      bind.destroyBindings();
    }
    elementBindings.clear();

  }
  //
  // SwtListbox supports drag bindings, but not drop bindings
  //

  @Override
  protected java.util.List<Object> getSwtDragData() {
    java.util.List<Object> list = new ArrayList<Object>();
    if ( elements != null && elements instanceof java.util.List ) {
      int[] indices = listBox.getList().getSelectionIndices();
      for ( int i = 0; i < indices.length; i++ ) {
        list.add( ( (java.util.List) elements ).get( indices[i] ) );
      }
    } else {
      for ( String str : listBox.getList().getSelection() ) {
        list.add( str );
      }
    }
    return list;
  }

  @Override
  protected void onSwtDragFinished( DropEffectType effect, DropEvent event ) {
    if ( effect == DropEffectType.MOVE ) {
      if ( elements != null ) {
        throw new UnsupportedOperationException( "Bindings not yet supported in drag with move" );
      } else {

        // remove both from xul and from list
        int[] indices = listBox.getList().getSelectionIndices();
        for ( int i = indices.length - 1; i >= 0; i-- ) {
          removeChild( getChildNodes().get( indices[i] ) );
        }
        listBox.remove( indices );
        // need to link to bindings
      }
    }
  }

  @Override
  protected void onSwtDragDropAccepted( DropEvent event ) {
    if ( elements != null ) {
      throw new UnsupportedOperationException( "Bindings not yet supported on drop" );
    } else {
      java.util.List<Object> data = event.getDataTransfer().getData();
      for ( int i = 0; i < data.size(); i++ ) {
        SwtListitem item = null;
        try {
          item = (SwtListitem) container.getDocumentRoot().createElement( "listitem" );
        } catch ( XulException e ) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        this.addChild( item );
        item.setValue( data.get( i ) );
      }
      layout();
    }
  }

  public void setOndrop( String ondrop ) {
    super.setOndrop( ondrop );
    super.enableDrop();
  }

  public void setOndrag( String ondrag ) {
    super.setOndrag( ondrag );
    // TODO: once listBox is initialized lazily, we also need to move this
    // this exact logic is in setDrageffect because both need to be set for
    // enable dragging
    if ( getDrageffect() != null ) {
      super.enableDrag( DropEffectType.valueOfIgnoreCase( getDrageffect() ) );
    }
  }

  public void setDrageffect( String drageffect ) {
    super.setDrageffect( drageffect );
    // TODO: once listBox is initialized lazily, we also need to move this
    // this exact logic is in setOndrag because both need to be set for
    // enable dragging
    if ( getOndrag() != null ) {
      super.enableDrag( DropEffectType.valueOfIgnoreCase( getDrageffect() ) );
    }
  }

  public String getCommand() {
    return command;
  }

  public void setCommand( final String command ) {
    this.command = command;
    listBox.getList().addMouseListener( new MouseAdapter() {
      public void mouseDoubleClick( MouseEvent arg0 ) {
        invoke( command );
      }
    } );
  }

}
