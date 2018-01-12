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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.io.SAXReader;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.binding.DefaultBindingContext;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.util.XmlParserFactoryProducer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class XulWindowContainer extends AbstractXulDomContainer {
  private static final Log logger = LogFactory.getLog( XulWindowContainer.class );
  private List<Document> windows;
  private boolean closed = false;

  public XulWindowContainer() throws XulException {
    super();
    windows = new ArrayList<Document>();
    bindings = new DefaultBindingContext( this );
  }

  public XulWindowContainer( XulLoader xulLoader ) throws XulException {
    this();
    this.xulLoader = xulLoader;
  }

  public Document getDocumentRoot() {
    return this.windows.get( 0 );
  }

  public void addDocument( Document document ) {
    this.windows.add( document );
    document.setXulDomContainer( this );
  }

  @Override
  public void close() {

    //
    // make onclose event calls
    //

    XulRoot rootEle = (XulRoot) this.getDocumentRoot().getRootElement();

    logger.debug( "onclose: " + rootEle.getOnclose() );
    String onclose = rootEle.getOnclose();
    if ( onclose != null ) {
      String[] oncloseCalls = onclose.split( "," );
      for ( String close : oncloseCalls ) {
        close = close.trim();
        try {
          Object result = invoke( close, new Object[] {} );
          if ( result != null && result instanceof Boolean && !( (Boolean) result ).booleanValue() ) {
            logger.debug( "onclose " + close + " returned false, exiting close procedure" );
            return;
          }
        } catch ( XulException e ) {
          logger.error( "Error calling onclose event: " + close, e );
        }
      }
    }

    //
    // close the document windows
    //

    for ( Document wind : this.windows ) {
      XulWindow window = (XulWindow) wind.getRootElement();
      if ( window != null ) {
        window.close();
      }
    }
    closed = true;

    //
    // make onunload event calls
    //

    logger.debug( "onunload: " + rootEle.getOnload() );
    String onunload = rootEle.getOnunload();
    if ( onunload != null ) {
      String[] unloadCalls = onunload.split( "," );
      for ( String unload : unloadCalls ) {
        unload = unload.trim();
        try {
          invoke( unload, new Object[] {} );
        } catch ( XulException e ) {
          logger.error( "Error calling onunload event: " + unload, e );
        }
      }
    }

    //
    // exit the system
    //

    // TODO: This should be refactored into the individual windows themselves,
    // and only the root window should exit the system when closed.

    if ( !ignoreClose ) {
      System.exit( 0 );
    }
  }

  public boolean isClosed() {
    return closed;
  }

  private boolean ignoreClose = false;

  public void ignoreCloseOperation( boolean flag ) {
    ignoreClose = flag;
  }

  @Override
  public XulDomContainer loadFragment( String xulLocation ) throws XulException {
    try {

      InputStream in = getClass().getClassLoader().getResourceAsStream( xulLocation );

      if ( in == null ) {
        throw new XulException( "loadFragment: input document is null" );
      }

      SAXReader rdr = XmlParserFactoryProducer.getSAXReader( null );
      final org.dom4j.Document doc = rdr.read( in );

      XulDomContainer container = this.xulLoader.loadXulFragment( doc );
      in.close();
      return container;
    } catch ( Exception e ) {
      logger.error( "Error Loading Xul Fragment", e );
      throw new XulException( e );
    }
  }

  public XulDomContainer loadFragment( String xulLocation, Object bundle ) throws XulException {
    XulDomContainer container = this.xulLoader.loadXulFragment( xulLocation, (ResourceBundle) bundle );
    return container;
  }

  public Document getDocument( int idx ) {
    return this.windows.get( idx );
  }

  public void loadOverlay( String src ) throws XulException {
    // XulDomContainer container = this.xulLoader.loadXulFragment(src);
    this.xulLoader.processOverlay( src, this.getDocumentRoot(), this );
  }

  public void loadOverlay( String src, Object resourceBundle ) throws XulException {
    this.xulLoader.processOverlay( src, this.getDocumentRoot(), this, resourceBundle );

  }

  public void removeOverlay( String src ) throws XulException {
    this.xulLoader.removeOverlay( src, this.getDocumentRoot(), this );
  }

  public void removeBinding( Binding binding ) {
    this.bindings.remove( binding );
  }

  public void loadFragment( String id, String src ) throws XulException {
    XulComponent c = this.getDocumentRoot().getElementById( id );
    if ( c == null ) {
      throw new IllegalArgumentException( "target element does not exist" );
    }
    try {

      InputStream in = getClass().getClassLoader().getResourceAsStream( src );

      if ( in == null ) {
        throw new XulException( "loadFragment: input document is null" );
      }

      SAXReader rdr = XmlParserFactoryProducer.getSAXReader( null );
      final org.dom4j.Document doc = rdr.read( in );

      XulDomContainer container = this.xulLoader.loadXulFragment( doc );

      c.addChild( container.getDocumentRoot().getRootElement() );

    } catch ( Exception e ) {
      logger.error( "Error Loading Xul Fragment", e );
      throw new XulException( e );
    }
  }
}
