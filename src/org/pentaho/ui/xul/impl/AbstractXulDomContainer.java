/**
 * 
 */
package org.pentaho.ui.xul.impl;

import java.util.HashMap;
import java.util.Map;

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.components.XulMessageBox;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;

/**
 * @author OEM
 *
 */
public abstract class AbstractXulDomContainer implements XulDomContainer {

  private XulLoader xulLoader;
  private Map<String, XulEventHandler> eventHandlers;

  public AbstractXulDomContainer() {
    eventHandlers = new HashMap<String, XulEventHandler>();
  }
  
  public AbstractXulDomContainer(XulLoader xulLoader) {
    this();
    this.xulLoader = xulLoader;
  }
  
  public Document remoteCall(XulServiceCall serviceUrl){
    return null;
  }
  
  
  public void addEventHandler(String id, String eventClassName){
   
    // Allow overrides of eventHandlers
    //if(eventHandlers.containsKey(id)){ //if already registered
    //  return;
    //}
    
    try{
      Class cls = Class.forName(eventClassName);
      XulEventHandler eventHandler = (XulEventHandler) cls.newInstance();
      eventHandler.setXulDomContainer(this);
      eventHandlers.put(id, eventHandler);
      
    } catch(ClassNotFoundException e){
      System.out.println("backing class not found");
      e.printStackTrace(System.out);
    } catch(Exception e){
      System.out.println("Error with Backing class creation");
      e.printStackTrace(System.out);
    }
  }
  
  public XulEventHandler getEventHandler(String key) throws XulException{
    if(eventHandlers.containsKey(key)){
      return eventHandlers.get(key);
    } else {
      throw new XulException(String.format("Could not find Event Handler with the key : %s", key));
    }
  }
  
  public void initialize(){
    XulWindow rootEle = (XulWindow) this.getDocumentRoot().getRootElement();
    System.out.println("onload: "+ rootEle.getOnload());
    String onLoad = rootEle.getOnload();
    if(onLoad != null){
      rootEle.invoke(rootEle.getOnload(), new Object[]{});
    }
  }
  
  public abstract Document getDocumentRoot();

  public abstract void addDocument(Document document);
  
  public abstract XulMessageBox createMessageBox(String message);
  
  public abstract XulDomContainer loadFragment(String xulLocation) throws XulException;
  
  public abstract void close();
}
