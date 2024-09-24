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

package org.pentaho.ui.xul;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Basic PropertyChangeSupport for use in XulEventSource Binding classes
 */
public class XulEventSourceAdapter implements XulEventSource {

  protected transient PropertyChangeSupport changeSupport = new PropertyChangeSupport( this );

  private PropertyChangeSupport getChangeSupport() {
    if ( changeSupport == null ) {
      changeSupport = new PropertyChangeSupport( this );
    }
    return changeSupport;
  }

  public void addPropertyChangeListener( PropertyChangeListener listener ) {
    getChangeSupport().addPropertyChangeListener( listener );
  }

  public void addPropertyChangeListener( String propertyName, PropertyChangeListener listener ) {
    getChangeSupport().addPropertyChangeListener( propertyName, listener );
  }

  public void removePropertyChangeListener( PropertyChangeListener listener ) {
    getChangeSupport().removePropertyChangeListener( listener );
  }

  public void removePropertyChangeListener( String propertyName, PropertyChangeListener listener ) {
    getChangeSupport().removePropertyChangeListener( propertyName, listener );
  }

  protected void firePropertyChange( String attr, Object previousVal, Object newVal ) {
    if ( previousVal == null && newVal == null ) {
      return;
    }
    getChangeSupport().firePropertyChange( attr, previousVal, newVal );
  }
}
