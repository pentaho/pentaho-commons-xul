package org.pentaho.ui.xul.gwt;

import java.util.HashMap;
import java.util.Map;

import org.pentaho.gwt.widgets.client.utils.MessageBundle;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulEventSource;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.binding.BindingContext;
import org.pentaho.ui.xul.components.XulMessageBox;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.gwt.binding.GwtBindingContext;
import org.pentaho.ui.xul.gwt.util.EventHandlerWrapper;
import org.pentaho.ui.xul.impl.XulEventHandler;

public class GwtXulDomContainer implements XulDomContainer {

  Document document;
  Map<String, XulEventHandler> handlers = new HashMap<String, XulEventHandler>();
  Map<XulEventHandler, EventHandlerWrapper> handlerWrapers = new HashMap<XulEventHandler, EventHandlerWrapper>();
  GwtXulLoader loader;
  
  protected GwtBindingContext bindings;
  
  public GwtXulDomContainer(){
    bindings = new GwtBindingContext();
  }
  
  public void addDocument(Document document) {
    this.document = document;
  }

  public Document getDocumentRoot() {
    return document;
  }

  public XulDomContainer loadFragment(String xulLocation) throws XulException {
    // TODO Auto-generated method stub
    return null;
  }
  
  Map<String, XulEventHandler> eventHandlers = new HashMap<String, XulEventHandler>();
 
  //The following does not work outside of "hosted mode" 
  public void addEventHandler(XulEventHandler handler) {

    //ref: http://google-web-toolkit.googlecode.com/svn/javadoc/1.5/com/google/gwt/core/client/GWT.html#create(java.lang.Class)
    //ref: http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/cf36c64ff48b3e19
    //ref: http://code.google.com/p/google-web-toolkit/issues/detail?id=2243
//    EventHandlerWrapper wrapper = GWT.create(handler.getClass());
//    
//    
//    handler.setXulDomContainer(this);
//    wrapper.setHandler(handler);
//    this.handlerWrapers.put(handler, wrapper);
//    this.handlers.put(handler.getName(), handler);
    throw new UnsupportedOperationException("this method is not supported at this time.");
  }
  
  public void addEventHandler(EventHandlerWrapper wrapper){
    
    XulEventHandler handler = wrapper.getHandler();
    this.handlerWrapers.put(handler, wrapper);
    handler.setXulDomContainer(this);
    this.handlers.put(handler.getName(), handler);
    
  }

  public void addEventHandler(String id, String eventClassName) {
    throw new UnsupportedOperationException("use addEventHandler(XulEventHandler handler)");
  }
  
  public XulEventHandler getEventHandler(String key) throws XulException {
    return handlers.get(key);
  }

  public XulMessageBox createMessageBox(String message) {
    // TODO Auto-generated method stub
    return null;
  }

  public void initialize() {
    // TODO Auto-generated method stub

  }

  public void close() {
    // TODO Auto-generated method stub

  }


  public Document getDocument(int idx) {
    
    return document;
      
  }

  public Map<String, XulEventHandler> getEventHandlers() {
    return eventHandlers;
  }

  public Object getOuterContext() {
    return "";
  }


  private Object[] getArgs(String methodCall){
    if(methodCall.endsWith("()")){
      return null;
    }
    String argsList = methodCall.substring(methodCall.indexOf("(")+1, methodCall.lastIndexOf(")"));
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
              continue;
            }
          }
        }
      }
    }
    return args;
    
  }

  private Class unBoxPrimative(Class clazz){
    return clazz;
  }
  
  public Object invoke(String method, Object[] args) throws XulException {
    try {
      if (method == null || method.indexOf('.') == -1) {
        throw new XulException("method call does not follow the pattern [EventHandlerID].methodName()");
      }

      String eventID = method.substring(0, method.indexOf("."));
      String methodName = method.substring(method.indexOf(".")+1);
      
      if(args == null || args.length == 0){
        Object[] arguments = getArgs(methodName);
        if(arguments != null){
          return invoke(method.substring(0,method.indexOf("("))+"()", arguments);
        } 
      }
      methodName = methodName.substring(0,methodName.indexOf("("));
      
      EventHandlerWrapper wrapper = this.handlerWrapers.get(this.handlers.get(eventID));
      
      if(args.length > 0){
//        Class[] classes = new Class[args.length];
//        
//        for(int i=0; i<args.length; i++){
//          classes[i] = unBoxPrimative(args[i].getClass());
//        }
        //Add in parameter support
        wrapper.execute(methodName, args);
        return null;
      } else {
        wrapper.execute(methodName, new Object[]{});
        return null;
      }
    } catch (Exception e) {
      throw new XulException("Error invoking method: " + method, e);
    }
  }

  public void invokeLater(Runnable runnable) {
    
        // TODO Auto-generated method stub 
      
  }

  public boolean isClosed() {
    return false;
  }

  public boolean isRegistered(String widgetHandlerName) {
    return this.loader.isRegistered(widgetHandlerName);
  }

  public void loadFragment(String id, String src) throws XulException {
  }

  public void mergeContainer(XulDomContainer container) {
    
        // TODO Auto-generated method stub 
      
  }
  
  private void applyOverlay(Document doc){
    this.document = getDocumentRoot();
    
    for(XulComponent overlay : doc.getChildNodes()){
      for(XulComponent child: overlay.getChildNodes()){
          
        XulComponent sourceDocumentNodeMatch;
        String childId = child.getId();
        if(childId != null && (sourceDocumentNodeMatch = this.document.getElementById(childId)) != null){
          

          if(child.getRemoveelement()){
            //punching out existing element NOTE: this is a non-reversable operation presently
            sourceDocumentNodeMatch.getParent().removeChild(sourceDocumentNodeMatch);
            continue;
          }
          
          //Override any existing attributes
          sourceDocumentNodeMatch.adoptAttributes(child);
          
          //Process all the children of the overlay and add them to the proper location in the existing document.
          for(XulComponent overlayChild : child.getChildNodes()){
            int position = overlayChild.getPosition();
            String insertBefore = overlayChild.getInsertbefore();
            String insertAfter = overlayChild.getInsertafter();
            
            XulContainer sourceContainer = ((XulContainer) sourceDocumentNodeMatch);
            
            String id = overlayChild.getId();
            XulComponent existingElement = null;
            if(id != null && !id.equals("")){
              existingElement = sourceContainer.getElementById(id);
            }
            if(existingElement != null){
              existingElement.adoptAttributes(overlayChild);
            } else {
            
              //change Components document reference.
              ((AbstractGwtXulComponent) overlayChild).setXulDomContainer(this);
              
              if(position > -1){
                sourceContainer.addChildAt(overlayChild, position);
              } else if(insertBefore != null){
                XulComponent relativeTo = document.getElementById(insertBefore);
                if(relativeTo != null && sourceDocumentNodeMatch.getChildNodes().contains(relativeTo)){
                  int relativePos = sourceDocumentNodeMatch.getChildNodes().indexOf(relativeTo);
                  relativePos--;
                  Math.abs(relativePos);
                  sourceContainer.addChildAt(overlayChild, relativePos);
                } else {
                  sourceContainer.addChild(overlayChild);
                }
              } else if(insertAfter != null){
                XulComponent relativeTo = document.getElementById(insertAfter);
                if(relativeTo != null && sourceDocumentNodeMatch.getChildNodes().contains(relativeTo)){
                  int relativePos = sourceDocumentNodeMatch.getChildNodes().indexOf(relativeTo);
                  relativePos++;
                  sourceContainer.addChildAt(overlayChild, relativePos);
                } else {
                  sourceContainer.addChild(overlayChild);
                }
              } else {
                sourceContainer.addChild(overlayChild);
              }
            }
            
          }
          
        }
      }
    }
  }
  
  public void loadOverlay(com.google.gwt.xml.client.Document overlayDoc, MessageBundle bundle) throws XulException {
    XulDomContainer overlayContainer = this.loader.loadXul(overlayDoc, bundle);
    applyOverlay(overlayContainer.getDocumentRoot());
    
  }
  
  public void loadOverlay(com.google.gwt.xml.client.Document overlayDoc) throws XulException {
    XulDomContainer overlayContainer = this.loader.loadXul(overlayDoc);
    applyOverlay(overlayContainer.getDocumentRoot());
    
  }
  
  public void removeOverlay(com.google.gwt.xml.client.Document overlayDoc) throws XulException {
    XulDomContainer overlayContainer = this.loader.loadXul(overlayDoc);
    
    for(XulComponent child : overlayContainer.getDocumentRoot().getChildNodes()){
      String id = child.getId();
      if(id == null | id.equals("")){
        continue;
      }
      XulComponent insertedNode = document.getElementById(id);
      if(insertedNode != null){
        insertedNode.getParent().removeChild(insertedNode);
      }
    }
  }

  public void setOuterContext(Object context) {
    
        // TODO Auto-generated method stub 
      
  }
  
  public void setLoader(GwtXulLoader loader){
    this.loader = loader;
  }

  public XulLoader getXulLoader() {
    return loader;
  }

  public void addBinding(Binding binding) {

    bindings.add(binding);
    
  }

  public void addInitializedBinding(Binding b) {
    // TODO Auto-generated method stub
    
  }

  public Binding createBinding(XulEventSource source, String sourceAttr, String targetId, String targetAttr) {
    // TODO Auto-generated method stub
    return null;
  }

  public Binding createBinding(String source, String sourceAttr, String targetId, String targetAttr) {
    // TODO Auto-generated method stub
    return null;
  }

  public XulDomContainer loadFragment(String xulLocation, Object bundle) throws XulException {
    // TODO Auto-generated method stub
    return null;
  }

  public void loadOverlay(String src) throws XulException {
    throw new RuntimeException("not yet implemented");
    
  }

  public void removeBinding(Binding binding) {
    throw new RuntimeException("not yet implemented");
    
  }

  public void removeOverlay(String src) throws XulException {
    throw new RuntimeException("not yet implemented");
    
  }

}
