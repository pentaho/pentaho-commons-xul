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
