package org.pentaho.ui.xul.impl;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.pentaho.core.util.CleanXmlHelper;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.components.XulMessageBox;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
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
    Object handler = XulParser.handlers.get("MESSAGEBOX");

    if (handler == null) {
      //TODO: add logging, discuss Exception handling
      System.out.println("Dialog handler not found: ");
      
    }

    XulComponent rootElement = (XulComponent)this.getDocumentRoot().getRootElement();
    
    Class<?> c;
    try {
      c = Class.forName((String) handler);
      Constructor<?> constructor = c
          .getConstructor(new Class[] { XulComponent.class, String.class });
      XulMessageBox ele = (XulMessageBox) constructor.newInstance(rootElement, message);
      
      return ele;
    } catch (Exception e) {
      //TODO: add logging, discuss Exception handling
      System.out.println(String.format("Error Creating Message Dialog: %s",e.getMessage()));
      e.printStackTrace(System.out);
      return null;
    }
  }

  @Override
  public void close() {
    ((XulWindow) this.windows.get(0).getRootElement()).close();
  }

  @Override
  public XulDomContainer loadFragment(String xulLocation) throws XulException {
    try{
          
      InputStream in = SwingXulRunner.class.getClassLoader().getResourceAsStream(xulLocation);
      
      if(in == null){
        throw new XulException("loadFragment: input document is null");
      }
      
      org.dom4j.Document doc = CleanXmlHelper.getDocFromStream(in);
      
      XulDomContainer container = this.xulLoader.loadXulFragment(doc);
      return container;
    } catch(Exception e){
      System.out.println(e.getMessage());
      e.printStackTrace(System.out);
      throw new XulException(e);
    }
  }
}
