/**
 * 
 */
package org.pentaho.ui.xul.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.DocumentFactory;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.util.XulUtil;

/**
 * @author nbaker
 *
 */
public class XulParser {
  private static final Namespace PENTAHO_NAMESPACE = new Namespace("pen", "http://www.pentaho.org/2008/xul");

  private static final Log logger = LogFactory.getLog(XulParser.class);

  private XulDomContainer xulDomContainer;
  private Map<String,XulElementFactory> xulElementFactory;
  private Document xulDocument;
  private List<ClassLoader> classloaders;

  public XulParser() throws XulException {
    classloaders = new ArrayList<ClassLoader>();
    classloaders.add(this.getClass().getClassLoader());
    xulElementFactory = new HashMap<String, XulElementFactory>();
    try {
      xulDocument = DocumentFactory.createDocument();
    } catch (Exception e) {
      throw new XulException("Error getting Document instance", e);
    }
  }

  public void setContainer(XulDomContainer xulDomContainer) {
    this.xulDomContainer = xulDomContainer;
    xulDocument.setXulDomContainer(xulDomContainer);
    xulDomContainer.addDocument(xulDocument);
  }

  public Document parseDocument(org.dom4j.Element rootSrc) throws XulException {
    
    XulContainer parent = null;
    if(!rootSrc.getName().equalsIgnoreCase("window") &&
        (!rootSrc.getName().equalsIgnoreCase("dialog"))){
      parent = getPlaceHolderRoot();
    }
    XulComponent root = parse(rootSrc, parent);

    //give root reference to runner for service calls and attach root to document
    if(root instanceof XulRoot){
      ((XulRoot)root).setXulDomContainer(this.xulDomContainer);
      xulDocument.addChild(root);
    } else { //fragment parsing, wire up dummy
      ((XulRoot)parent).setXulDomContainer(this.xulDomContainer);
      parent.addChild(root);
      xulDocument.addChild(parent);
    }
    
    // descend back down firing notification that everything is on the tree.
    // do not do this for overlays as they will get the notification when added 
    // to the real document.
    if(rootSrc.getName().equalsIgnoreCase("window") ||
        rootSrc.getName().equalsIgnoreCase("dialog")){
      notifyDomReady(root);
    }
    
    
    return xulDocument;
  }
  
  private void notifyDomReady(XulComponent node){
    node.onDomReady();
    List<XulComponent> childNodes = node.getChildNodes();
    for (int i = 0; i < childNodes.size(); i++)
    {
      XulComponent component = childNodes.get(i);
      notifyDomReady(component);
    }
  }

  public XulContainer getPlaceHolderRoot() throws XulException{
    try{
      XulElementFactory elementFactory = xulElementFactory.get("WINDOW");
      if (elementFactory == null)
        throw new XulException("Unable to find handler for 'window'");

      return (XulContainer) elementFactory.create(null, null, xulDomContainer, "WINDOW");
    } catch (Exception e) {
      throw new XulException(e);
    }
  }

  public XulComponent parse(org.dom4j.Element rootSrc, XulContainer parent) throws XulException {
    //parse element
    XulComponent root = getElement(rootSrc, parent);

    if(root == null){
      return null;
    }
    //descend down a level and parse children (root would be a container in the case)
    for (Object child : rootSrc.elements()) {
      XulComponent childElement = parse((org.dom4j.Element) child, (XulContainer) root);

      //TODO: remove once exception handling in place
      if (childElement == null) {
        continue;
      }

      // Add to the XML DOM tree ...
      root.addChild(childElement);

    }

    // should layout be part of the public API?
    // is this the appropriate place for layout?
    if (root instanceof AbstractXulComponent) {
      ((AbstractXulComponent)root).layout();
    }

    return root;
  }

  protected XulComponent getElement(org.dom4j.Element srcEle, XulContainer parent) throws XulException {

    String handlerName = srcEle.getName().toUpperCase(Locale.ENGLISH);
    Attribute att = srcEle.attribute(new QName("customclass", PENTAHO_NAMESPACE));
    
    // If the custom handler is registered, use it; otherwise, fall back to the original element handler...
    XulElementFactory elementFactory = null;
    if (att != null){
      String potentialHandlerName = att.getValue().toUpperCase(Locale.ENGLISH);
      elementFactory = this.xulElementFactory.get(potentialHandlerName);
    }

    if (elementFactory == null)
    elementFactory = this.xulElementFactory.get(handlerName);

    if (elementFactory == null) {
    	logger.error("handler not found: " + handlerName);
      return null;
    	//throw new XulException(String.format("No handler available for input: %s", srcEle.getName()));
    }

    String tagName = srcEle.getName();
    try {

      //create a generic element representation of the current Dom4J node
      Element domEle = DocumentFactory.createElement(srcEle.getName().toLowerCase());
      List<Attribute> attrs = srcEle.attributes();
      for(Attribute attr : attrs){
        domEle.setAttribute(attr.getName(), attr.getValue());
      }
      
      XulComponent ele = elementFactory.create(domEle, parent, xulDomContainer, tagName);

      //preserve atributes in new Generic Dom node
      for(Attribute attr : attrs){
        ele.setAttribute(attr.getName(), attr.getValue());
      }
      
      Map<String, String> attributesMap = XulUtil.AttributesToMap(srcEle.attributes());
      BeanUtils.populate(ele, attributesMap);
      return ele;
    
    } catch (Exception e) {
      throw new XulException(e);
    }

  }
  
  public XulComponent getElement(String name) throws XulException{
    return getElement(name, null);
  }

  public XulComponent getElement(String name, XulComponent defaultParent) throws XulException{
    XulElementFactory handler = this.xulElementFactory.get(name.toUpperCase(Locale.ENGLISH));

     if (handler == null) {
       logger.error("tag handler not found: " + name);
       throw new XulException(String.format("No handler available for input: %s", name));
     }

     try {
       return handler.create(null, defaultParent, xulDomContainer, name);
     } catch (Exception e) {
       throw new XulException(e);
     }
  }

  public void registerHandler(String type, XulElementFactory handler) throws XulException
  {
    if (handler == null)
      throw new NullPointerException();
    if (type == null)
      throw new NullPointerException();
    xulElementFactory.put(type.toUpperCase(Locale.ENGLISH), handler);
  }
  public void registerHandler(String type, String handler) throws XulException
  {
    if (type == null)
      throw new NullPointerException();
    if (handler == null)
      throw new NullPointerException();
    xulElementFactory.put(type.toUpperCase(Locale.ENGLISH), new ReflectionXulElementFactory(handler, classloaders));
  }

  public Document getDocumentRoot() {
    return this.xulDocument;
  }
  
  /**
   * @throws XulException
   * Resets the state of the parser.
   */
  public void reset() throws XulException{
    try {
      xulDocument = DocumentFactory.createDocument();
    } catch (Exception e) {
      throw new XulException("Error getting Document instance", e);
    }
  }

  public void setClassLoaders(List<ClassLoader> loaders){
    this.classloaders.clear();
    this.classloaders.addAll(loaders);
  }

  public boolean isRegistered(final String elementName)
  {
    return xulElementFactory.containsKey(elementName.toUpperCase(Locale.ENGLISH));
  }
}
