/**
 * 
 */
package org.pentaho.ui.xul.dom;

import java.util.List;

import org.pentaho.ui.xul.XulElement;

/**
 * @author OEM
 *
 */
public interface Element {
  public String getText();
  public String getName();
  public Document getDocument();
  public Element getParent();
  public Element getFirstChild();
  public List<Element> getChildNodes();

  public void setNamespace(String prefix, String uri);
  public Namespace getNamespace();

  public Element getElementById(String id);
  public Element getElementByXPath(String path);
  public List<Element> getElementsByTagName(String tagName);
  
  public void addChild(Element element);
  
  public Object getElementObject();
  
  public List<Attribute> getAttributes();

  public void setAttributes(List<Attribute> attribute);
  public void setAttribute(Attribute attribute);
  public void setAttribute(String name, String value);
  public String getAttributeValue(String attributeName);
  
  public XulElement getXulElement();
  
}
