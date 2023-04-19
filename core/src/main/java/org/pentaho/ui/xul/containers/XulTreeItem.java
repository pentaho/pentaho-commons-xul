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
 * Copyright (c) 2002-2023 Hitachi Vantara..  All rights reserved.
 */

package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;

public interface XulTreeItem extends XulContainer {

  public void setContainer( boolean isContainer );

  public boolean isContainer();

  public void setEmpty( boolean empty );

  public boolean isEmpty();

  public boolean isHierarchical();

  public XulTree getTree();

  public XulTreeRow getRow();

  public void setRow( XulTreeRow row );

  public void remove();

  public void setImage( String src );

  public String getImage();

  public void setExpanded( boolean expanded );

  public boolean isExpanded();

  public void setBoundObject( Object obj );

  public Object getBoundObject();

  public void setClassname( String classname );

  public String getClassname();

  public default void setAltText( String altText ) {
    throw new UnsupportedOperationException( "setAltText( altText ) not implemented" );
  }

  public default String getAltText() {
    throw new UnsupportedOperationException( "getAltText() not implemented" );
  }
}
