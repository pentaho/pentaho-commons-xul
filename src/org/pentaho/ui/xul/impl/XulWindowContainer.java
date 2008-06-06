package org.pentaho.ui.xul.impl;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.io.SAXReader;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.binding.BindingContext;
import org.pentaho.ui.xul.components.XulMessageBox;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;


public class XulWindowContainer extends AbstractXulDomContainer {  
	private static final Log logger = LogFactory.getLog(XulWindowContainer.class);
  private List<Document> windows;
  private boolean closed = false;
  
  public XulWindowContainer() throws XulException {
    super();
    windows = new ArrayList<Document>();
    bindings = new BindingContext(this);
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
    document.setXulDomContainer(this);
  }

  @Override
  public void close() {
  	for(Document wind : this.windows){
  		((XulWindow) wind.getRootElement()).close();
  	}
  	closed = true;
  }

  public boolean isClosed() {
    return closed;
  }

  @Override
  public XulDomContainer loadFragment(String xulLocation) throws XulException {
    try{
          
      InputStream in = getClass().getClassLoader().getResourceAsStream(xulLocation);
      
      if(in == null){
        throw new XulException("loadFragment: input document is null");
      }
      
      
      SAXReader rdr = new SAXReader();
      final org.dom4j.Document  doc = rdr.read(in);
      
      XulDomContainer container = this.xulLoader.loadXulFragment(doc);
      return container;
    } catch(Exception e){
    	logger.error("Error Loading Xul Fragment",e);
      throw new XulException(e);
    }
  }
  

  public XulDomContainer loadFragment(String xulLocation, ResourceBundle res) throws XulException {
    XulDomContainer container = this.xulLoader.loadXulFragment(xulLocation, res);
    return container;  
  }

	public Document getDocument(int idx) {
		return this.windows.get(idx);
	}

  public void loadOverlay(String src) throws XulException{
//    XulDomContainer container = this.xulLoader.loadXulFragment(src);
    this.xulLoader.processOverlay(src, this.getDocumentRoot(), this);
  }

  public void removeOverlay(String src) throws XulException {
    this.xulLoader.removeOverlay(src, this.getDocumentRoot(), this);
  }
}
