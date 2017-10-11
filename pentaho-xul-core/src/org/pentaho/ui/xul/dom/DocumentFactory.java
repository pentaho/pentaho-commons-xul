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
