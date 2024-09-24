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

package org.pentaho.ui.xul.gwt;

import java.util.HashMap;
import java.util.Map;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;

import com.google.gwt.xml.client.NodeList;

public class GwtXulParser {

  private static final Map<String, GwtXulHandler> handlers = new HashMap<String, GwtXulHandler>();

  public static void registerHandler( String name, GwtXulHandler handler ) {
    handlers.put( name, handler );
  }

  public static boolean isRegistered( String name ) {
    return handlers.get( name ) != null;
  }

  Document xulDocument;
  XulDomContainer xulDomContainer;

  public GwtXulParser() {
  }

  private void resetParser() {
    try {
      xulDocument = GwtDocumentFactory.createDocument();
    } catch ( Exception e ) {
      System.out.println( e.getMessage() );
      e.printStackTrace();
    }
  }

  public void setContainer( XulDomContainer xulDomContainer ) {
    resetParser();
    this.xulDomContainer = xulDomContainer;
    xulDomContainer.addDocument( xulDocument );
  }

  public Document parseDocument( com.google.gwt.xml.client.Element rootSrc ) throws XulException {

    xulDocument.setXulDomContainer( this.xulDomContainer );

    XulComponent root = parse( rootSrc, null );

    // give root reference to runner for service calls
    if ( root instanceof XulWindow ) {
      ( (XulWindow) root ).setXulDomContainer( this.xulDomContainer );
    }

    xulDocument.addChild( root );

    // descend back down firing notification that everything is on the tree.
    notifyDomReady( root );

    return xulDocument;
  }

  private void notifyDomReady( XulComponent node ) {
    node.onDomReady();
    for ( XulComponent c : node.getChildNodes() ) {
      notifyDomReady( c );
    }
  }

  public XulComponent parse( com.google.gwt.xml.client.Element rootSrc, XulContainer parent ) throws XulException {
    // parse element
    XulComponent root = getElement( rootSrc, parent );

    // descend down a level and parse children (root would be a container in the case)
    NodeList children = rootSrc.getChildNodes();
    for ( int i = 0; i < children.getLength(); i++ ) {
      if ( children.item( i ) instanceof com.google.gwt.xml.client.Element ) {
        Element childElement = parse( (com.google.gwt.xml.client.Element) children.item( i ), (XulContainer) root );

        // TODO: remove once exception handling in place
        if ( childElement == null ) {
          continue;
        }

        // Add to the XML DOM tree ...
        root.addChild( childElement );

      }
    }
    if ( root != null && root instanceof AbstractGwtXulComponent ) {
      ( (AbstractGwtXulComponent) root ).layout();
    }

    return root;
  }

  protected XulComponent getElement( com.google.gwt.xml.client.Element srcEle, XulContainer parent )
    throws XulException {

    GwtXulHandler handler = handlers.get( srcEle.getNodeName() );
    if ( handler != null ) {
      AbstractGwtXulComponent gxc = (AbstractGwtXulComponent) handler.newInstance();
      gxc.setXulDomContainer( xulDomContainer );
      gxc.init( srcEle, xulDomContainer );
      return gxc;
    } else {
      System.out.println( "Error: No Handler for type " + srcEle.getNodeName() );
      return null;
    }
  }

  public XulComponent getElement( String name ) throws XulException {
    return getElement( name, null );
  }

  public XulComponent getElement( String name, XulComponent defaultParent ) throws XulException {
    GwtXulHandler handler = handlers.get( name );
    if ( handler != null ) {
      AbstractGwtXulComponent gxc = (AbstractGwtXulComponent) handler.newInstance();
      gxc.setXulDomContainer( xulDomContainer );
      gxc.setParent( defaultParent );
      return gxc;
    } else {
      System.out.println( "Error: No Handler for type " + name );
      return null;
    }
  }

}
