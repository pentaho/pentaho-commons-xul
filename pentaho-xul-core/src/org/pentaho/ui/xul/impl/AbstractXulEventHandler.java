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

package org.pentaho.ui.xul.impl;

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulEventSource;
import org.pentaho.ui.xul.XulEventSourceAdapter;
import org.pentaho.ui.xul.dom.Document;

/**
 * @author OEM
 * 
 */
public abstract class AbstractXulEventHandler extends XulEventSourceAdapter implements XulEventHandler {
  protected XulDomContainer xulDomContainer;
  protected Document document;
  protected String name;

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.impl.XulEventHandler#getName()
   */
  public String getName() {
    return name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.impl.XulEventHandler#setName(java.lang.String)
   */
  public void setName( String name ) {
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.impl.XulEventHandler#setXulDomContainer(org.pentaho.ui.xul.XulDomContainer)
   */
  public void setXulDomContainer( XulDomContainer xulDomContainer ) {
    this.xulDomContainer = xulDomContainer;
    this.document = xulDomContainer.getDocumentRoot();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.impl.XulEventHandler#getXulDomContainer()
   */
  public XulDomContainer getXulDomContainer() {
    return this.xulDomContainer;
  }

  public Object getData() {
    return null;
  }

  public void setData( Object data ) {
  }

  /*
   * Convenience methods for data binding
   */
  @Deprecated
  public void bind( XulEventSource model, String modelPropertyName, String xulComponentElementId,
      String xulComponentPropertyName ) {
    getXulDomContainer().createBinding( model, modelPropertyName, xulComponentElementId, xulComponentPropertyName );
  }

  @Deprecated
  public void bind( String srcXulComponentElementId, String srcXulComponentPropertyName,
      String destXulComponentElementId, String destXulComponentPropertyName ) {
    getXulDomContainer().createBinding( srcXulComponentElementId, srcXulComponentPropertyName,
        destXulComponentElementId, destXulComponentPropertyName );
  }
}
