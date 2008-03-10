/**
 * 
 */
package org.pentaho.ui.xul;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.utilites.XulUtil;

/**
 * @author OEM
 *
 */
public class XulParser {
  Document sourceDocument;

  Document xulDocument;

  static Map<String, Object> handlers = new HashMap<String, Object>();

  private XulRunner xulRunner;

  public enum TAG {
    PAGE, BUTTON, VBOX, HBOX, LABEL, TEXTBOX
  }

  public XulParser(XulRunner xulRunner) {
    xulDocument = DocumentFactory.getInstance().createDocument();
    this.xulRunner = xulRunner;
    xulRunner.setDocumentRoot(xulDocument);
  }

  public Document parseDocument(Element rootSrc) throws XulException {
    XulWindow root = (XulWindow) parse(rootSrc, null);

    //give root reference to runner for service calls
    root.setXulRunner(this.xulRunner);

    xulDocument.add((XulElement) root);
    return xulDocument;
  }

  public XulElement parse(Element rootSrc, XulContainer parent) throws XulException {
    //parse element
    XulElement root = getElement(rootSrc, parent);

    //descend down a level and parse children (root would be a container in the case)
    for (Object child : rootSrc.elements()) {
      XulElement childElement = parse((Element) child, (XulContainer) root);

      //TODO: remove once exception handling in place
      if (childElement == null) {
        continue;
      }
      root.add(childElement);

      if (root instanceof XulContainer) //more of an assert, should be true.
        ((XulContainer) root).add(childElement);
    }
    return root;
  }

  protected XulElement getElement(Element srcEle, XulContainer parent) throws XulException {
    
    Object handler = XulParser.handlers.get(srcEle.getName().toUpperCase());
    
    if (handler == null) {
      System.out.println("handler not found");
      return null;
      //throw new XulException(String.format("No handler available for input: %s", srcEle.getName()));
    }
    
    if (handler instanceof XulTagHandler) {
      return ((XulTagHandler)handler).parse(srcEle, parent, xulRunner);
    } else {  // handler is String class name of the class we want to create an instance of 
      String tagName = srcEle.getName();
      Class c;
      try {
        c = Class.forName((String)handler);
        Constructor constructor = c.getConstructor(new Class [] {XulElement.class, String.class});
        XulElement ele =  (XulElement)constructor.newInstance(parent, tagName);

        Map attributesMap = XulUtil.AttributesToMap(srcEle.attributes());
        BeanUtils.populate(ele, attributesMap);
        return ele;
      } catch (Exception e) {
        throw new XulException(e);
      }

    }
  }

  public static void registerHandler(String type, XulTagHandler handler) {

    XulParser.handlers.put(type.toUpperCase(), handler);

  }

  public static void registerHandler(String type, String handler) {

    XulParser.handlers.put(type.toUpperCase(), handler);

  }

  public Document getDocumentRoot() {
    return this.xulDocument;
  }

}
