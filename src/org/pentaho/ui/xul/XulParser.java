/**
 * 
 */
package org.pentaho.ui.xul;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.DocumentFactory;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.utilites.XulUtil;

/**
 * @author OEM
 *
 */
public class XulParser {
  Document sourceDocument;
  Document xulDocument;
  static Map<String, Object> handlers = new HashMap<String, Object>();
  
  private XulWindowContainer xulWindowContainer;
  
  public enum TAG{
    PAGE,
    BUTTON,
    VBOX,
    HBOX,
    LABEL,
    TEXTBOX
  }
   
  public XulParser(XulWindowContainer xulWindowContainer) throws XulException{
    try{
      xulDocument = DocumentFactory.createDocument();
    } catch(Exception e){
      throw new XulException(e);
    }
    this.xulWindowContainer = xulWindowContainer;
    xulWindowContainer.addDocument(xulDocument);
  }
  
  public Document parseDocument(org.dom4j.Element rootSrc) throws XulException{
    XulWindow root = (XulWindow) parse(rootSrc, null);
    
    //give root reference to runner for service calls
    root.setXulWindowContainer(this.xulWindowContainer);
    
    xulDocument.addChild((Element) root);
    return xulDocument;
  }
  
  public XulElement parse(org.dom4j.Element rootSrc, XulContainer parent) throws XulException{
    //parse element
    XulElement root = getElement(rootSrc, parent);
    
    //descend down a level and parse children (root would be a container in the case)
    for(Object child : rootSrc.elements()){
      XulElement childElement = parse( (org.dom4j.Element) child, (XulContainer) root);
      
      //TODO: remove once exception handling in place
      if(childElement == null){
        continue;
      }
 
      // Add to the XML DOM tree ...
      root.addChild(childElement);
      
      // ... then add to the UI component tree.
      if(root instanceof XulContainer) //more of an assert, should be true.
        ((XulContainer) root).addComponent(childElement);
    }
    if(root != null){
      root.layout();
    }
    
    return root;
  }
  
  protected XulElement getElement(org.dom4j.Element srcEle, XulContainer parent) throws XulException{
      
    Object handler = handlers.get(srcEle.getName().toUpperCase());
    
    if (handler == null) {
      System.out.println("handler not found: " + srcEle.getName().toUpperCase());
      return null;
      //throw new XulException(String.format("No handler available for input: %s", srcEle.getName()));
    }
    
    if (handler instanceof XulTagHandler) {
      return ((XulTagHandler)handler).parse(srcEle, parent, xulWindowContainer);
    } else {  // handler is String class name of the class we want to create an instance of 
      String tagName = srcEle.getName();
      Class <?> c;
      try {
        c = Class.forName((String)handler);
        Constructor <?> constructor = c.getConstructor(new Class [] {XulElement.class, XulWindowContainer.class, String.class});
        XulElement ele =  (XulElement)constructor.newInstance(parent, xulWindowContainer, tagName);

        Map <String, String> attributesMap = XulUtil.AttributesToMap(srcEle.attributes());
        BeanUtils.populate(ele, attributesMap);
        return ele;
      } catch (Exception e) {
        throw new XulException(e);
      }
    }

  }
  
  public void registerHandler(String type, XulTagHandler handler){
    
    handlers.put(type.toUpperCase(), handler);
    
  }

  public static void registerHandler(String type, String handler) {

    XulParser.handlers.put(type.toUpperCase(), handler);

  }
  
  public Document getDocumentRoot(){
    return this.xulDocument;
  }
  
}
