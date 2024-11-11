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


/**
 * 
 */

package org.pentaho.ui.xul.components;

import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomException;

import java.util.Collection;

/**
 * @author aphillips
 * 
 */
public interface XulMenuList<T> extends XulContainer {

  public void replaceAllItems( Collection<T> tees ) throws XulDomException;

  public String getSelectedItem();

  public void setSelectedItem( T t );

  public void setSelectedIndex( int idx );

  public int getSelectedIndex();

  public void setOncommand( String command );

  public void setElements( Collection<T> elements );

  public Collection<T> getElements();

  public void setBinding( String binding );

  public String getBinding();

  public void setEditable( boolean editable );

  public boolean getEditable();

  /**
   * Returns the user entered value in the case of an editable menulist
   * 
   * @return String user entered value
   */
  public String getValue();

  /**
   * Sets teh value of the menulist if it's editable.
   * 
   * @param value
   */
  public void setValue( String value );
}
