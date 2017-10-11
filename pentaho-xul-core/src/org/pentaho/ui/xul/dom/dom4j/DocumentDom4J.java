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

package org.pentaho.ui.xul.dom.dom4j;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;

/**
 * @author NBaker
 * 
 */
public class DocumentDom4J extends ElementDom4J implements Document {

  private org.dom4j.Document document;
  private XulDomContainer container;

  public DocumentDom4J() {
    super();
    this.document = org.dom4j.DocumentHelper.createDocument();
    this.element = this.document.getRootElement();
  }

  public DocumentDom4J( org.dom4j.tree.DefaultDocument document ) {
    super();
    this.document = document;
    this.element = document.getRootElement();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.dom.Document#getRootElement()
   */
  public XulComponent getRootElement() {
    if ( document.getRootElement() != null ) {
      return ( (XulElementDom4J) document.getRootElement() ).getXulElement();
    } else {
      return null;
    }
  }

  @Override
  public void addChild( Element ele ) {
    // TODO Auto-generated method stub
    document.add( (org.dom4j.Element) ele.getElementObject() );
    this.element = (org.dom4j.Element) ele.getElementObject();
  }

  public XulComponent createElement( String elementName ) throws XulException {
    return this.container.getXulLoader().createElement( elementName );
  }

  public void setXulDomContainer( XulDomContainer container ) {
    this.container = container;
  }

  public boolean isRegistered( String elementName ) {
    return this.container.getXulLoader().isRegistered( elementName );
  }

  public void addOverlay( String src ) throws XulException {
    container.loadOverlay( src );
  }

  public void removeOverlay( String src ) throws XulException {
    container.removeOverlay( src );
  }

  public void loadFragment( String id, String src ) throws XulException {
    container.loadFragment( id, src );
  }

  @Deprecated
  public void addBinding( Binding bind ) {
    container.addBinding( bind );
  }

  public void addInitializedBinding( Binding b ) {
    container.addInitializedBinding( b );
  }

  public void invokeLater( Runnable runnable ) {
    container.invokeLater( runnable );
  }

  public void loadPerspective( String id ) {
    container.loadPerspective( id );
  }

}
