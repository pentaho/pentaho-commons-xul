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
