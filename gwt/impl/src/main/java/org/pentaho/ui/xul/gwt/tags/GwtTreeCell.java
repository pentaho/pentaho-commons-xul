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

import java.beans.PropertyChangeListener;
import java.util.Vector;

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTreeCell;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.stereotype.Bindable;

public class GwtTreeCell extends AbstractGwtXulComponent implements XulTreeCell {

  private Object value;
  private int selectedIndex;
  private XulTreeRow parentRow;
  // track that bindings have been added to this cell;
  private boolean selIdxBindingsAdded;
  private boolean valBindingsAdded;
  private boolean labelBindingsAdded;
  private String label = "";

  public static void register() {
    GwtXulParser.registerHandler( "treecell", new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTreeCell();
      }
    } );
  }

  public GwtTreeCell() {
    super( "treecell" );
  }

  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    super.init( srcEle, container );
    setLabel( srcEle.getAttribute( "label" ) );
    setValue( srcEle.getAttribute( "value" ) );
  }

  @Bindable
  public String getLabel() {
    return this.label;
  }

  @Bindable
  public int getSelectedIndex() {
    return selectedIndex;
  }

  public String getSrc() {
    // TODO Auto-generated method stub
    return null;
  }

  @Bindable
  public Object getValue() {
    return this.value;
  }

  @Bindable
  public boolean isEditable() {
    // TODO Auto-generated method stub
    return false;
  }

  @Bindable
  public void setEditable( boolean edit ) {
    // TODO Auto-generated method stub

  }

  @Bindable
  public void setLabel( String label ) {
    String prevVal = this.label;
    this.label = label;
    this.firePropertyChange( "label", prevVal, label );
  }

  @Bindable
  public void setSelectedIndex( int index ) {
    int oldVal = this.selectedIndex;
    this.selectedIndex = index;
    this.firePropertyChange( "selectedIndex", oldVal, index );

  }

  public void setSrc( String srcUrl ) {
    // TODO Auto-generated method stub

  }

  public void setTreeRowParent( XulTreeRow row ) {
    this.parentRow = row;

  }

  @Bindable
  public void setValue( Object value ) {
    Object previousVal = this.value;

    if ( value instanceof String && ( (String) value ).indexOf( "," ) == -1
        && ( value.equals( "false" ) || value.equals( "true" ) ) ) {
      // String and not a comma separated list
      this.value = Boolean.parseBoolean( ( (String) value ) );
    } else if ( value instanceof String && ( (String) value ).indexOf( "," ) > -1 ) {
      // String and a comma separated list
      String[] list = ( (String) value ).split( "," );
      Vector<String> vec = new Vector<String>();
      for ( String item : list ) {
        vec.add( item );
      }
      this.value = vec;

    } else if ( value instanceof Boolean ) {
      this.value = (Boolean) value;
    } else {
      this.value = value;
    }
    this.firePropertyChange( "value", previousVal, this.value );
    if ( parentRow != null ) {
      if ( "true".equals( value ) || ( ( value instanceof Boolean ) && ( true == (Boolean) value ) ) ) {
        parentRow.getCell( 0 ).setValue( true );
      }
    }
  }

  private boolean disabled = false;

  @Override
  @Bindable
  public boolean isDisabled() {
    return disabled;
  }

  @Override
  @Bindable
  public void setDisabled( boolean disabled ) {
    boolean prevVal = this.disabled;
    this.disabled = disabled;
    super.setDisabled( disabled );
    this.firePropertyChange( "disabled", prevVal, disabled );
  }

  public boolean selectedIndexBindingsAdded() {
    return selIdxBindingsAdded;
  }

  public void setSelectedIndexBindingsAdded( boolean bindingsAdded ) {
    this.selIdxBindingsAdded = bindingsAdded;
  }

  public boolean valueBindingsAdded() {
    return valBindingsAdded;
  }

  public void setValueBindingsAdded( boolean bindingsAdded ) {
    valBindingsAdded = bindingsAdded;
  }

  public void setLabelBindingsAdded( boolean bindingsAdded ) {
    labelBindingsAdded = bindingsAdded;
  }

  public boolean labelBindingsAdded() {
    return labelBindingsAdded;
  }

  // TODO: migrate into XulComponent
  @Deprecated
  public void addPropertyChangeListener( String prop, PropertyChangeListener listener ) {
    changeSupport.addPropertyChangeListener( prop, listener );
  }
}
