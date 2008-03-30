/**
 * 
 */
package org.pentaho.ui.xul.dom;

import java.lang.reflect.Constructor;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulException;

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
    try{
      Constructor constructor = concreteClass.getConstructor(new Class[]{});
      Object concreteDOM = constructor.newInstance((Object[])null);
      return (Document) concreteDOM;
      
    } catch(Exception e){
      System.out.println("Error creating DOM document object: "+e.getMessage());
      e.printStackTrace(System.out);
      throw new XulException(e);
    }
  }
  
  public static Document createDocument(Object dom) throws XulException{
    try{
      Class cls = dom.getClass();
      Constructor constructor = concreteClass.getConstructor(new Class[]{cls});
      Object concreteDOM = constructor.newInstance(dom);
      return (Document) concreteDOM;
      
      
    } catch(Exception e){
      System.out.println("Error creating DOM document object: "+e.getMessage());
      e.printStackTrace(System.out);
      throw new XulException(e);
    }
  }
  
  public static Element createElement(String name, XulComponent xulElement) throws XulException{
    try{
      Object element = elementClass.getConstructor(new Class[]{String.class, XulComponent.class}).newInstance(new Object[]{name, xulElement});
      return (Element) element;
      
    } catch(Exception e){
      System.out.println("Error creating DOM document object: "+e.getMessage());
      e.printStackTrace(System.out);
      throw new XulException(e);
    }
  }
  
  
}
