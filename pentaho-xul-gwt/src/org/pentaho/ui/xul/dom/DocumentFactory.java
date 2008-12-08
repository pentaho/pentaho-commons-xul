/**
 * 
 */
package org.pentaho.ui.xul.dom;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.gwt.GwtDomDocument;

/**
 * @author OEM
 *
 */
public class DocumentFactory {
  private static Class concreteClass;
  private static Class elementClass;
  
  public static void registerDOMClass(Class clazz){
    DocumentFactory.concreteClass = clazz;
  }

  public static void registerElementClass(Class clazz){
    DocumentFactory.elementClass = clazz;
  }
 
  public static Document createDocument() throws XulException{
    return new GwtDomDocument();
  }
  
//  
//  public static Element createElement(String name, XulComponent xulElement) throws XulException{
//    try{
//      Object element = elementClass.getConstructor(new Class[]{String.class, XulComponent.class}).newInstance(new Object[]{name, xulElement});
//      return (Element) element;
//      
//    } catch(Exception e){
//      throw new XulException(e);
//    }
//  }
//  
//  public static Element createElement(String name) throws XulException{
//    try{
//      Object element = elementClass.getConstructor(new Class[]{String.class}).newInstance(new Object[]{name});
//      return (Element) element;
//      
//    } catch(Exception e){
//      throw new XulException(e);
//    }
//  }
//  
  
}
