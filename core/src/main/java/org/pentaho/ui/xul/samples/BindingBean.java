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
