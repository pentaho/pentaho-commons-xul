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
