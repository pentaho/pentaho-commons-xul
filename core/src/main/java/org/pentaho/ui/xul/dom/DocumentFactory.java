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

package org.pentaho.ui.xul.dom;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulException;

import java.lang.reflect.Constructor;

/**
 * @author OEM
 * 
 */
public class DocumentFactory {
  private static Class concreteClass;
  private static Class elementClass;
  private static final Log logger = LogFactory.getLog( DocumentFactory.class );

  public static void registerDOMClass( Class clazz ) {
    DocumentFactory.concreteClass = clazz;
  }

  public static void registerElementClass( Class clazz ) {
    DocumentFactory.elementClass = clazz;
  }

  public static Document createDocument() throws XulException {
    try {
      Constructor constructor = concreteClass.getConstructor( new Class[] {} );
      Object concreteDOM = constructor.newInstance( (Object[]) null );
      return (Document) concreteDOM;

    } catch ( Exception e ) {
      logger.error( "Error creating DOM document object: " + e.getMessage(), e );
      throw new XulException( e );
    }
  }

  public static Document createDocument( Object dom ) throws XulException {
    try {
      Class cls = dom.getClass();
      Constructor constructor = concreteClass.getConstructor( new Class[] { cls } );
      Object concreteDOM = constructor.newInstance( dom );
      return (Document) concreteDOM;

    } catch ( Exception e ) {
      logger.error( "Error creating DOM document object: " + e.getMessage(), e );
      throw new XulException( e );
    }
  }

  public static Element createElement( String name, XulComponent xulElement ) throws XulException {
    try {
      Object element =
          elementClass.getConstructor( new Class[] { String.class, XulComponent.class } ).newInstance(
              new Object[] { name, xulElement } );
      return (Element) element;

    } catch ( Exception e ) {
      logger.error( "Error creating DOM document object: " + e.getMessage(), e );
      throw new XulException( e );
    }
  }

  public static Element createElement( String name ) throws XulException {
    try {
      Object element = elementClass.getConstructor( new Class[] { String.class } ).newInstance( new Object[] { name } );
      return (Element) element;

    } catch ( Exception e ) {
      logger.error( "Error creating DOM document object: " + e.getMessage(), e );
      throw new XulException( e );
    }
  }

}
