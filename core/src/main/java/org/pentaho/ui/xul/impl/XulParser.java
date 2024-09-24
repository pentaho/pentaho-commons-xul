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

/**
 * 
 */

package org.pentaho.ui.xul.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.DocumentFactory;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.util.XulUtil;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nbaker
 * 
 */
public class XulParser {
  Document xulDocument;
  private static final Log logger = LogFactory.getLog( XulParser.class );

  public Map<String, Object> handlers = new HashMap<String, Object>();
  private Map<String, Constructor<?>> constructorCache = new HashMap<String, Constructor<?>>();

  private XulDomContainer xulDomContainer;

  private List<ClassLoader> classloaders = new ArrayList<ClassLoader>();
  {
    classloaders.add( this.getClass().getClassLoader() );
  }

  public XulParser() throws XulException {
    try {
      xulDocument = DocumentFactory.createDocument();
    } catch ( Exception e ) {
      throw new XulException( "Error getting Document instance", e );
    }
  }

  public void setContainer( XulDomContainer xulDomContainer ) {
    this.xulDomContainer = xulDomContainer;
    xulDocument.setXulDomContainer( xulDomContainer );
    xulDomContainer.addDocument( xulDocument );
  }

  public Document parseDocument( org.dom4j.Element rootSrc ) throws XulException {

    XulContainer parent = null;
    if ( !rootSrc.getName().equalsIgnoreCase( "window" ) && ( !rootSrc.getName().equalsIgnoreCase( "dialog" ) ) ) {
      parent = getPlaceHolderRoot();
    }
    XulComponent root = parse( rootSrc, parent );

    // give root reference to runner for service calls and attach root to document
    if ( root instanceof XulRoot ) {
      ( (XulRoot) root ).setXulDomContainer( this.xulDomContainer );
      xulDocument.addChild( root );
    } else { // fragment parsing, wire up dummy
      ( (XulRoot) parent ).setXulDomContainer( this.xulDomContainer );
      parent.addChild( root );
      xulDocument.addChild( parent );
    }

    // descend back down firing notification that everything is on the tree.
    // do not do this for overlays as they will get the notification when added
    // to the real document.
    if ( rootSrc.getName().equalsIgnoreCase( "window" ) || rootSrc.getName().equalsIgnoreCase( "dialog" ) ) {
      notifyDomReady( root );
    }

    return xulDocument;
  }

  private void notifyDomReady( XulComponent node ) {
    node.onDomReady();
    for ( XulComponent c : node.getChildNodes() ) {
      notifyDomReady( c );
    }
  }

  private Constructor<?> getContructor( String className ) throws XulException {
    Constructor<?> con = constructorCache.get( className );

    if ( con != null ) {
      return con;
    }

    Class<?> c = null;
    Throwable lastException = null;
    for ( ClassLoader loader : classloaders ) {
      try {
        c = loader.loadClass( className );
        if ( c != null ) {
          break;
        }
      } catch ( ClassNotFoundException e ) {
        lastException = e;
      }
    }
    if ( c == null && lastException != null ) {
      throw new XulException( lastException );
    }

    try {
      Constructor<?> constructor =
          c.getConstructor( new Class[] { Element.class, XulComponent.class, XulDomContainer.class, String.class } );
      constructorCache.put( className, constructor );
      return constructor;
    } catch ( NoSuchMethodException e1 ) {
      throw new XulException( e1 );
    }

  }

  public XulContainer getPlaceHolderRoot() throws XulException {
    try {
      Object handlerClassName = handlers.get( "WINDOW" );
      if ( handlerClassName == null ) {
        throw new XulException( "Could not find a tag handler for window" );
      }
      XulWindow ele =
          (XulWindow) getContructor( handlerClassName.toString() ).newInstance( null, null, xulDomContainer, "window" );
      return ele;
    } catch ( Exception e ) {
      throw new XulException( e );
    }
  }

  public XulComponent parse( org.dom4j.Element rootSrc, XulContainer parent ) throws XulException {
    // parse element
    XulComponent root = getElement( rootSrc, parent );

    if ( root == null ) {
      return null;
    }
    // descend down a level and parse children (root would be a container in the case)
    for ( Object child : rootSrc.elements() ) {
      XulComponent childElement = parse( (org.dom4j.Element) child, (XulContainer) root );

      // TODO: remove once exception handling in place
      if ( childElement == null ) {
        continue;
      }

      // Add to the XML DOM tree ...
      root.addChild( childElement );

    }
    if ( root != null ) {
      // should layout be part of the public API?
      // is this the appropriate place for layout?
      if ( root instanceof AbstractXulComponent ) {
        ( (AbstractXulComponent) root ).layout();
      }
    }

    return root;
  }

  protected XulComponent getElement( org.dom4j.Element srcEle, XulContainer parent ) throws XulException {

    String handlerName = srcEle.getName().toUpperCase();
    Attribute att =
        srcEle.attribute( new QName( "customclass", new Namespace( "pen", "http://www.pentaho.org/2008/xul" ) ) );

    // If the custom handler is registered, use it; otherwise, fall back to the original element handler...
    if ( att != null ) {
      String potentialHandlerName = att.getValue().toUpperCase();
      if ( handlers.get( potentialHandlerName ) != null ) {
        handlerName = potentialHandlerName;
      }
    }

    Object handler = handlers.get( handlerName );

    if ( handler == null ) {
      logger.error( "handler not found: " + handlerName );
      return null;
      // throw new XulException(String.format("No handler available for input: %s", srcEle.getName()));
    }

    String tagName = srcEle.getName();
    try {

      Constructor<?> constructor = getContructor( (String) handler );

      // create a generic element representation of the current Dom4J node
      Element domEle = DocumentFactory.createElement( srcEle.getName().toLowerCase() );
      List<Attribute> attrs = srcEle.attributes();
      for ( Attribute attr : attrs ) {
        domEle.setAttribute( attr.getName(), attr.getValue() );
      }

      XulComponent ele = (XulComponent) constructor.newInstance( domEle, parent, xulDomContainer, tagName );

      // preserve atributes in new Generic Dom node
      for ( Attribute attr : attrs ) {
        ele.setAttribute( attr.getName(), attr.getValue() );
      }

      Map<String, String> attributesMap = XulUtil.AttributesToMap( srcEle.attributes() );
      BeanUtils.populate( ele, attributesMap );
      return ele;

    } catch ( Exception e ) {
      throw new XulException( e );
    }

  }

  public XulComponent getElement( String name ) throws XulException {
    return getElement( name, null );
  }

  public XulComponent getElement( String name, XulComponent defaultParent ) throws XulException {
    Object handler = handlers.get( name.toUpperCase() );

    if ( handler == null ) {
      logger.error( "tag handler not found: " + name );
      throw new XulException( String.format( "No handler available for input: %s", name ) );
    }
    try {

      Constructor<?> constructor = getContructor( (String) handler );

      XulComponent ele = (XulComponent) constructor.newInstance( null, defaultParent, xulDomContainer, name );
      return ele;
    } catch ( Exception e ) {
      throw new XulException( e );
    }
  }

  public void registerHandler( String type, String handler ) {

    handlers.put( type.toUpperCase(), handler );

  }

  public Document getDocumentRoot() {
    return this.xulDocument;
  }

  /**
   * @throws XulException
   *           Resets the state of the parser.
   */
  public void reset() throws XulException {
    try {
      xulDocument = DocumentFactory.createDocument();
    } catch ( Exception e ) {
      throw new XulException( "Error getting Document instance", e );
    }
  }

  public void setClassLoaders( List<ClassLoader> loaders ) {
    this.classloaders = loaders;
  }

}
