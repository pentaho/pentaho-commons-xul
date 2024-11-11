/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.ui.xul.swt.tags;

import java.beans.PropertyChangeListener;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTreeCell;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtTreeCell extends SwtElement implements XulTreeCell {

  private Object value = null;

  private int index = 0;
  private static final Log logger = LogFactory.getLog( SwtTreeCell.class );

  private String label;

  public SwtTreeCell( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "treecell" );
    setManagedObject( "empty" );
  }

  public SwtTreeCell( XulComponent parent ) {
    super( "treecell" );
    setManagedObject( "empty" );
  }

  public void setSelectedIndex( int index ) {
    Object oldValue = this.index;
    this.index = index;
    this.changeSupport.firePropertyChange( "selectedIndex", oldValue, index );
  }

  public int getSelectedIndex() {
    return index;
  }

  public String getLabel() {
    return label;
  }

  public String getSrc() {
    // TODO Auto-generated method stub
    return null;
  }

  public Object getValue() {
    return value;
  }

  public boolean isEditable() {
    // TODO Auto-generated method stub
    return false;
  }

  public void setEditable( boolean edit ) {
    // TODO Auto-generated method stub

  }

  public void setLabel( String label ) {
    String oldValue = this.label;
    this.label = label;
    this.changeSupport.firePropertyChange( "label", oldValue, label );
  }

  public void setSrc( String srcUrl ) {
    // TODO Auto-generated method stub

  }

  public void setValue( Object value ) {
    Object oldValue = this.value;
    if ( value instanceof String && ( (String) value ).indexOf( "," ) == -1 ) {
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

    this.index = index;
    this.changeSupport.firePropertyChange( "value", oldValue, this.value );
  }

  public void setTreeRowParent( XulTreeRow row ) {

  }

  // TODO: migrate into XulComponent
  @Deprecated
  public void addPropertyChangeListener( String prop, PropertyChangeListener listener ) {
    changeSupport.addPropertyChangeListener( prop, listener );
  }
}
