/**
 * 
 */
package org.pentaho.ui.xul.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.components.XulMessageBox;
import org.pentaho.ui.xul.components.XulScript;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;

/**
 * @author OEM
 *
 */
public abstract class AbstractXulDomContainer implements XulDomContainer {

  private static final Log logger = LogFactory.getLog(AbstractXulDomContainer.class);
  
  protected XulLoader xulLoader;
  protected Map<String, XulEventHandler> eventHandlers;

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
  
  public XulLoader getXulLoader(){
  	return xulLoader;
  }
  
  public void addEventHandler(String id, String eventClassName) throws XulException{
   
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
    	logger.error("Event Handler Class Not Found", e);
    	throw new XulException(e);
    } catch(Exception e){
    	logger.error("Error with Backing class creation", e);
    	throw new XulException(e);
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
    logger.info("onload: "+ rootEle.getOnload());
    String onLoad = rootEle.getOnload();
    if(onLoad != null){
    	try{
    		rootEle.invoke(rootEle.getOnload(), new Object[]{});
    	} catch(XulException e){
    		logger.error("Error calling onLoad event: "+onLoad,e);
    	}
    }
  }
  
  public Map<String, XulEventHandler> getEventHandlers(){
  	if(this.eventHandlers.size() > 0){
  		return this.eventHandlers;
  	} else {
      XulComponent rootEle = (XulComponent) this.getDocumentRoot().getRootElement();
  		
      if(this instanceof XulFragmentContainer){
      	//skip first element as it's a wrapper
      	rootEle = rootEle.getFirstChild();
      }
      
      for (XulComponent comp : rootEle.getChildNodes()) {
        if (comp instanceof XulScript) {
        	XulScript script = (XulScript) comp;
          try{
          	this.addEventHandler(script.getId(), script.getSrc());
          } catch(XulException e){
          	logger.error("Error adding Event Handler to Window: "+script.getSrc(), e);
          }
        } 
      }
  		return this.eventHandlers;
  		
  	}
  }

  public void mergeContainer(XulDomContainer container) {
  	Map<String, XulEventHandler> incomingHandlers = container.getEventHandlers();
  	for(Map.Entry<String, XulEventHandler> entry : incomingHandlers.entrySet()){
  		if(! this.eventHandlers.containsKey(entry.getKey())){
  			this.eventHandlers.put(entry.getKey(), entry.getValue());
  			entry.getValue().setXulDomContainer(this);
  		}
  	}
  	//this.eventHandlers.putAll(incomingHandlers);
	}
  
  public abstract Document getDocumentRoot();

  public abstract void addDocument(Document document);
  
  public abstract XulMessageBox createMessageBox(String message);
  
  public abstract XulDomContainer loadFragment(String xulLocation) throws XulException;
  
  public abstract void close();

  public void setEventHandler(String key, XulEventHandler handler) {
    if(eventHandlers.containsKey(key)){
      throw new IllegalArgumentException("Event handler for key: "+key+" already exists");
    }
    handler.setXulDomContainer(this);
    eventHandlers.put(key, handler);  
  }
}
