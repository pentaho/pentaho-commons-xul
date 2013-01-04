package org.pentaho.ui.xul.impl;

import java.lang.reflect.Constructor;
import java.util.List;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.dom.Element;

public class ReflectionXulElementFactory implements XulElementFactory
{
  private String handler;
  private List<ClassLoader> classloaders;
  private Constructor constructor;

  public ReflectionXulElementFactory(final String handler,
                                     final List<ClassLoader> classloaders) throws XulException
  {
    this.handler = handler;
    this.classloaders = classloaders;
    this.constructor = getContructor(handler);
  }

  private Constructor getContructor(String className) throws XulException
  {
      Class c = null;
      Throwable lastException = null;
      for(ClassLoader loader : classloaders){
        try{
          c = loader.loadClass(className);
          if (c != null) {
            break;
          }
        } catch(ClassNotFoundException e){
          lastException = e;
        }
      }
      if (c == null && lastException != null) {
        throw new XulException(lastException);
      }

      try {
        return c.getConstructor(new Class[] { Element.class, XulComponent.class, XulDomContainer.class, String.class });
      } catch (NoSuchMethodException e1) {
        throw new XulException(e1);
      }

    }

  public XulComponent create(final Element e, final XulComponent c, final XulDomContainer parent, final String tag)
      throws XulException
  {
    try
    {
      return (XulComponent) constructor.newInstance(e, c, parent, tag);
    }
    catch (Exception e1)
    {
      throw new XulException(e1);
    }
  }
}
