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
