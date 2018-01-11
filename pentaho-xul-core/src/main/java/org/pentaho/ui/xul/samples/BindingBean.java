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

package org.pentaho.ui.xul.samples;

import org.pentaho.ui.xul.XulEventSourceAdapter;

import java.beans.PropertyChangeSupport;
import java.util.List;

public class BindingBean extends XulEventSourceAdapter {

  private String property1, property2;

  private List<String> listProperty;

  private List<BindingBean> bindingBeans;

  protected PropertyChangeSupport changeSupport = new PropertyChangeSupport( this );

  public List<BindingBean> getBindingBeans() {
    return bindingBeans;
  }

  public void setBindingBeans( List<BindingBean> bindingBeans ) {
    this.bindingBeans = bindingBeans;
    firePropertyChange( "bindingBeans", null, bindingBeans );
  }

  public List<String> getListProperty() {
    return listProperty;
  }

  public void setListProperty( List<String> listProperty ) {
    this.listProperty = listProperty;
    firePropertyChange( "listProperty", null, listProperty );
  }

  public String getProperty1() {
    return property1;
  }

  public void setProperty1( String property1 ) {
    Object oldVal = this.property1;
    this.property1 = property1;
    firePropertyChange( "property1", oldVal, property1 );
  }

  public String getProperty2() {
    return property2;
  }

  public void setProperty2( String property2 ) {
    Object oldVal = this.property2;
    this.property2 = property2;
    firePropertyChange( "property2", oldVal, property1 );
  }
}
