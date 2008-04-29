/**
 * 
 */
package org.pentaho.ui.xul.dom;

import java.util.List;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomException;

/**
 * @author NBaker
 *
 */
public interface Element {
  public String getText();
  public String getName();
  public Document getDocument();
  public XulComponent getParent();
  public XulComponent getFirstChild();
  public List<XulComponent> getChildNodes();

  public void setNamespace(String prefix, String uri);
  public Namespace getNamespace();

  public XulComponent getElementById(String id);
  public XulComponent getElementByXPath(String path);
  public List<XulComponent> getElementsByTagName(String tagName);
  
  public void addChild(Element element);
  public void removeChild(Element element);
  
  public Object getElementObject();
  
  public List<Attribute> getAttributes();

  public void setAttributes(List<Attribute> attribute);
  public void setAttribute(Attribute attribute);
  public void setAttribute(String name, String value);
  public String getAttributeValue(String attributeName);

  public void replaceChild(XulComponent oldElement, XulComponent newElement) throws XulDomException;
  
}
