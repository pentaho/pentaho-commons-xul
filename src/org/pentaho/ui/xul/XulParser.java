/**
 * 
 */
package org.pentaho.ui.xul;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.pentaho.ui.xul.containers.XulWindow;

/**
 * @author OEM
 *
 */
public class XulParser {
  Document sourceDocument;
  Document xulDocument;
  static Map<String, XulTagHandler> handlers = new HashMap();
  private XulRunner xulRunner;
  
  public enum TAG{
    PAGE,
    BUTTON,
    VBOX,
    HBOX,
    LABEL,
    TEXTBOX
  }
   
  public XulParser(XulRunner xulRunner){
    xulDocument = DocumentFactory.getInstance().createDocument();
    this.xulRunner = xulRunner;
    xulRunner.setDocumentRoot(xulDocument);
  }
  
  public Document parseDocument(Element rootSrc) throws XulException{
    XulWindow root = (XulWindow) parse(rootSrc, null);
    
    //give root reference to runner for service calls
    root.setXulRunner(this.xulRunner);
    
    xulDocument.add((XulElement)root);
    return xulDocument;
  }
  
  public XulElement parse(Element rootSrc, XulContainer parent) throws XulException{
    //parse element
    XulElement root = getElement(rootSrc, parent);
    
    //decend down a level and parse children (root would be a container in the case)
    for(Object child : rootSrc.elements()){
      XulElement childElement = parse( (Element) child, (XulContainer) root);
      
      //TODO: remove once exception handling in place
      if(childElement == null){
        continue;
      }
      root.add(childElement);
      
      if(root instanceof XulContainer) //more of an assert, should be true.
        ((XulContainer) root).add(childElement);
    }
    return root;
  }
  
  protected XulElement getElement(Element srcEle, XulContainer parent) throws XulException{
    XulTagHandler handler = XulParser.handlers.get(srcEle.getName().toUpperCase());
    if(handler == null){
      System.out.println("handler not found");
      return null;
      //throw new XulException(String.format("No handler available for input: %s", srcEle.getName()));
    }
    return handler.parse(srcEle, parent, xulRunner);
  }
  
  public static void registerHandler(String type, XulTagHandler handler){
    
    XulParser.handlers.put(type.toUpperCase(), handler);
    
  }
  
  public Document getDocumentRoot(){
    return this.xulDocument;
  }
  
}
