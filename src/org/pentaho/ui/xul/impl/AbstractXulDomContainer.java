/**
 * 
 */
package org.pentaho.ui.xul.impl;

import groovy.lang.GroovyClassLoader;

import java.awt.EventQueue;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDataModel;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulEventSource;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.binding.BindingContext;
import org.pentaho.ui.xul.components.XulMessageBox;
import org.pentaho.ui.xul.components.XulScript;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.dom.Document;

/**
 * @author nbaker
 *
 */
public abstract class AbstractXulDomContainer implements XulDomContainer {

  private static final Log logger = LogFactory.getLog(AbstractXulDomContainer.class);
  
  protected XulLoader xulLoader;
  protected HashMap<String, XulEventHandler> eventHandlers;
  protected HashMap<String, XulDataModel> models;
  
  protected BindingContext bindings;
  
  
  private Object parentContext;

	public AbstractXulDomContainer() {
    eventHandlers = new HashMap<String, XulEventHandler>();
  }
  
  public AbstractXulDomContainer(XulLoader xulLoader) {
    this();
    this.xulLoader = xulLoader;
  }
  
  
  public XulLoader getXulLoader(){
  	return xulLoader;
  }
  
  private void addGroovyHandler(String id, String location) throws XulException{
    try{
    
      location = location.replace('.', '/').replace("/groovy", ".groovy");
      
      InputStream in = getClass().getClassLoader().getResourceAsStream(
          location
      );
      
      GroovyClassLoader gcl = new GroovyClassLoader();
      Class clazz = gcl.parseClass(in);
      Object script = clazz.newInstance();
      XulEventHandler groovyHandler = (XulEventHandler) script;

      groovyHandler.setXulDomContainer(this);
      eventHandlers.put(id, groovyHandler);
      
    } catch(Exception e){
      throw new XulException(e);
    }
  }
  
  @Deprecated
  public void addEventHandler(String id, String eventClassName) throws XulException{
   
    // Allow overrides of eventHandlers
    //if(eventHandlers.containsKey(id)){ //if already registered
    //  return;
    //}
    
    if(eventClassName.indexOf("groovy") > -1){
      addGroovyHandler(id, eventClassName);
      return;
    }
    
    try{
      Class cls = Class.forName(eventClassName);
      AbstractXulEventHandler eventHandler = (AbstractXulEventHandler) cls.newInstance();
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
    XulRoot rootEle = (XulRoot) this.getDocumentRoot().getRootElement();
    logger.info("onload: "+ rootEle.getOnload());
    String onLoad = rootEle.getOnload();
    if(onLoad != null){
      if(onLoad.indexOf(',') > 0){ //comma separated list of onload calls
        String[] loadCalls = onLoad.split(",");
        for(String load : loadCalls){
        	load = load.trim();
          try{
        		invoke(load, new Object[]{});
        	} catch(XulException e){
        		logger.error("Error calling onLoad event: "+load,e);
        	}
        }
      } else { //single onLoad event
        try{
          invoke(rootEle.getOnload(), new Object[]{});
        } catch(XulException e){
          logger.error("Error calling onLoad event: "+onLoad,e);
        }
      }
    }
  }
  
  public Map<String, XulEventHandler> getEventHandlers(){
  	//if(this.eventHandlers.size() > 0){
  		return this.eventHandlers;
  	//} else {
  	  //called when someone merges a fragment container into another container
  	  
  	  /*================= Pretty sure this is never called ==================*/
  	  
//      XulComponent rootEle = getDocumentRoot().getRootElement();
//  		
//      if(this instanceof XulFragmentContainer){
//      	//skip first element as it's a wrapper
//      	rootEle = rootEle.getFirstChild();
//      }
//      
//      for (XulComponent comp : rootEle.getChildNodes()) {
//        if (comp instanceof XulScript) {
//        	XulScript script = (XulScript) comp;
//          try{
//          	this.addEventHandler(script.getId(), script.getSrc());
//          } catch(XulException e){
//          	logger.error("Error adding Event Handler to Window: "+script.getSrc(), e);
//          }
//        } 
//      }
//  		return this.eventHandlers;
//  		
//  	}
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
  
  public abstract XulDomContainer loadFragment(String xulLocation) throws XulException;
  
  public abstract void close();

  public void addEventHandler(XulEventHandler handler) {
    handler.setXulDomContainer(this);
    eventHandlers.put(handler.getName(), handler);
  }
  
  private Object[] getArgs(String methodCall){
    if(methodCall.indexOf("()") > -1){
      return null;
    }
    String argsList = methodCall.substring(methodCall.indexOf("(")+1, methodCall.indexOf(")"));
    String[] stringArgs = argsList.split(",");
    Object[] args = new Object[ stringArgs.length ];
    int i=-1;
    for(String obj : stringArgs){
      i++;
      obj = obj.trim();
      try{
        Integer num = Integer.valueOf(obj);
        args[i] = num;
        continue;
      } catch(NumberFormatException e){
        try{
          Double num = Double.valueOf(obj);
          args[i] = num;
          continue;
        } catch(NumberFormatException e2){
          try{
            if(obj.indexOf('\'') == -1 && obj.indexOf('\"') == -1){
              throw new IllegalArgumentException("Not a string");
            }
            String str = obj.replaceAll("'", "");
            str = str.replaceAll("\"", "");
            args[i] = str;
            continue;
          } catch(IllegalArgumentException e4){
            try{
              Boolean flag = Boolean.parseBoolean(obj);
              args[i] = flag;
              continue;
            } catch(NumberFormatException e3){
              logger.error("Error parsing event call argument: "+obj, e3);
              continue;
            }
          }
        }
      }
    }
    return args;
    
  }
  
  private Class unBoxPrimative(Class clazz){
    if (clazz == Boolean.class) {
      return Boolean.TYPE;
    } else if (clazz == Integer.class) {
      return Integer.TYPE;
    } else if (clazz == Float.class) {
      return Float.TYPE;
    } else if (clazz == Double.class) {
      return Double.TYPE;
    } else if (clazz == Short.class) {
      return Short.TYPE;
    } else if (clazz == Long.class) {
      return Long.TYPE;
    } else {
      return clazz;
    }
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.containers.XulWindow#invoke(java.lang.String, java.lang.Object[])
   */
  public Object invoke(String method, Object[] args) throws XulException {

  try {
      if (method == null || method.indexOf('.') == -1) {
        throw new XulException("method call does not follow the pattern [EventHandlerID].methodName()");
      }

      String eventID = method.substring(0, method.indexOf("."));
      String methodName = method.substring(method.indexOf(".")+1);
      
      Object[] arguments = getArgs(methodName);
      if(arguments != null){
        return invoke(method.substring(0,method.indexOf("("))+"()", arguments);
      } else {
        methodName = methodName.substring(0,methodName.indexOf("("));
      }
      
      XulEventHandler evt = getEventHandler(eventID);
      if(args.length > 0){
        Class[] classes = new Class[args.length];
        
        for(int i=0; i<args.length; i++){
          classes[i] = unBoxPrimative(args[i].getClass());
        }
        
        Method m = evt.getClass().getMethod(methodName, classes);
        return m.invoke(evt, args);
      } else {
        Method m = evt.getClass().getMethod(methodName, new Class[0]);
        return m.invoke(evt, args);
      }
    } catch (Exception e) {
      logger.error("Error invoking method: " + method, e);
      throw new XulException("Error invoking method: " + method, e);
    }
  }
  
  @Deprecated
  public void addBinding(Binding binding){
    bindings.add(binding);
  }
  
  public void addInitializedBinding(Binding binding){
    bindings.addInitializedBinding(binding);
  }
  
  public void registerBinding(XulComponent comp, String expr){
    //bindings.add(comp, expr);
  }

  @Deprecated
  public Binding createBinding(XulEventSource source, String sourceAttr, String targetId, String targetAttr){
    Binding bind = new Binding(this.getDocumentRoot(), source, sourceAttr, targetId, targetAttr);
    bindings.add(bind);
    return bind;
    
  }

  @Deprecated
  public Binding createBinding(String source, String sourceAttr, String targetId, String targetAttr){
    Binding bind = new Binding(this.getDocumentRoot(), source, sourceAttr, targetId, targetAttr);
    bindings.add(bind);
    return bind;
  }
  

  public boolean isRegistered(String widgetHandlerName){
    Object handler = this.getXulLoader().isRegistered(widgetHandlerName);
    return (handler != null);
  }
  public Object getOuterContext() {
    return parentContext;
  }

  public void setOuterContext(Object context) {
    parentContext = context;
  }

  public void invokeLater(Runnable runnable) {
    XulRoot rootEle = (XulRoot) this.getDocumentRoot().getRootElement();
    rootEle.invokeLater(runnable);
    
  }
  
}
