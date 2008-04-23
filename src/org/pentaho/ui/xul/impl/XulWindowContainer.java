package org.pentaho.ui.xul.impl;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.io.SAXReader;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.components.XulMessageBox;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.swing.SwingXulRunner;

public class XulWindowContainer extends AbstractXulDomContainer {
  private List<Document> windows;
  private XulLoader xulLoader;
  
  public XulWindowContainer() throws XulException {
    super();
    windows = new ArrayList<Document>();
  }
  
  public XulWindowContainer(XulLoader xulLoader) throws XulException{
    this();
    this.xulLoader = xulLoader;
  }

  public Document getDocumentRoot(){
    return this.windows.get(0);
  }

  public void addDocument(Document document){
    this.windows.add(document);
  }

  public XulMessageBox createMessageBox(String message) {

    XulComponent rootElement = this.getDocumentRoot().getRootElement();
    return (XulMessageBox)createInstance(rootElement, "MESSAGEBOX", new Object[]{message});
  }

  public XulMessageBox createErrorMessageBox(String title, String message, Throwable throwable) {
    XulComponent rootElement = this.getDocumentRoot().getRootElement();
    return (XulMessageBox)createInstance(rootElement, "ERRORMESSAGEBOX", new Object[]{title, message, throwable});
  }

  @Override
  public void close() {
    ((XulWindow) this.windows.get(0).getRootElement()).close();
  }

  public boolean isClosed() {
    return ((XulWindow) this.windows.get(0).getRootElement()).isClosed();
  }

  @Override
  public XulDomContainer loadFragment(String xulLocation) throws XulException {
    try{
          
      InputStream in = SwingXulRunner.class.getClassLoader().getResourceAsStream(xulLocation);
      
      if(in == null){
        throw new XulException("loadFragment: input document is null");
      }
      
      
      SAXReader rdr = new SAXReader();
      final org.dom4j.Document  doc = rdr.read(in);
      
      XulDomContainer container = this.xulLoader.loadXulFragment(doc);
      return container;
    } catch(Exception e){
      System.out.println(e.getMessage());
      e.printStackTrace(System.out);
      throw new XulException(e);
    }
  }
  
  public Object createInstance(XulComponent parent, String widgetHandlerName, Object[] params) {
    Object handler = XulParser.handlers.get(widgetHandlerName);

    if (handler == null) {
      //TODO: add logging, discuss Exception handling
      System.out.println("Dialog handler not found: ");
    }
    
    if (parent == null) {
      //TODO: add logging, discuss Exception handling
      System.out.println("Cannot pass null parent XulComponent... ");
      return null;
    }

    Class <?> [] classArgs = null;
    Object[] constructorArgs = null;
    if (params != null){
      int inc = 1;
      classArgs = new Class[params.length+1];
      constructorArgs = new Object[params.length+1];
      classArgs[0] = XulComponent.class;
      constructorArgs[0] = parent;
      for (Object param : params) {
        classArgs[inc]=param.getClass();
        constructorArgs[inc++]=param;
      }
    }

    Class <?> c;
    try {
      c = Class.forName((String) handler);
      Constructor <?> constructor = c.getConstructor(classArgs);
      Object ele = constructor.newInstance(constructorArgs);
      
      return ele;
    } catch (Exception e) {
      //TODO: add logging, discuss Exception handling
      System.out.println(String.format("Error creating new instance: %s",e.getMessage()));
      e.printStackTrace(System.out);
      return null;
    }
  }

}
