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

package org.pentaho.ui.xul.gwt.dom;

import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.gwt.GwtDomDocument;

/**
 * @author OEM
 * 
 */
public class GWTDocumentFactory {
  private static Class concreteClass;
  private static Class elementClass;

  public static void registerDOMClass( Class clazz ) {
    GWTDocumentFactory.concreteClass = clazz;
  }

  public static void registerElementClass( Class clazz ) {
    GWTDocumentFactory.elementClass = clazz;
  }

  public static Document createDocument() throws XulException {
    return new GwtDomDocument();
  }

  //
  // public static Element createElement(String name, XulComponent xulElement) throws XulException{
  // try{
  // Object element = elementClass.getConstructor(new Class[]{String.class, XulComponent.class}).newInstance(new
  // Object[]{name, xulElement});
  // return (Element) element;
  //
  // } catch(Exception e){
  // throw new XulException(e);
  // }
  // }
  //
  // public static Element createElement(String name) throws XulException{
  // try{
  // Object element = elementClass.getConstructor(new Class[]{String.class}).newInstance(new Object[]{name});
  // return (Element) element;
  //
  // } catch(Exception e){
  // throw new XulException(e);
  // }
  // }
  //

}
