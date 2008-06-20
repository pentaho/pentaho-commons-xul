/**
 * 
 */
package org.pentaho.ui.xul.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.dom.Attribute;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.DocumentFactory;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.dom.Namespace;

/**
 * @author OEM
 *
 */
public abstract class AbstractXulComponent implements XulComponent {
  private static final long serialVersionUID = -3629792827245143824L;

  private static final Log logger = LogFactory.getLog(AbstractXulComponent.class);

  protected Object managedObject;

  protected Element element;

  protected int flex = 0;

  protected String id;

  protected boolean flexLayout = false;

  protected List<XulComponent> children;

  protected int width;

  protected int height;

  protected boolean initialized = false;

  public AbstractXulComponent(Element element) {
    this.element = element;
    children = new ArrayList<XulComponent>();

  }

  public AbstractXulComponent(String name) {
    try {
      this.element = DocumentFactory.createElement(name, this);
      children = new ArrayList<XulComponent>();
    } catch (XulException e) {
      logger.error(String.format("error creating XulElement (%s)", name), e);
      throw new IllegalArgumentException(String.format("error creating XulElement (%s)", name), e);
    }
  }

  public Object getManagedObject() {
    return managedObject;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.setAttribute("ID", id);
    this.id = id;
  }

  public String getID() {
    return id;
  }

  public void setID(String id) {
    this.setAttribute("ID", id);
    this.id = id;
  }

  public int getFlex() {
    return flex;
  }

  public void setFlex(int flex) {
    this.flex = flex;
  }

  public void addComponent(XulComponent c) {
    children.add(c);
  }

  public void addComponentAt(XulComponent c, int idx) {
    children.add(idx, c);
  }
  public void layout() {
    initialized = true;
  }

  protected void invoke(String method) {
    invoke(method, null);
  }

  protected void invoke(String method, Object[] args) {
    Document doc = getDocument();
    XulRoot window = (XulRoot) doc.getRootElement();
    XulDomContainer con = window.getXulDomContainer();

    try {
      if (args == null) {
        args = new Object[] {};
      }
      con.invoke(method, args);
    } catch (XulException e) {
      logger.error("Error calling oncommand event", e);
    }
  }

  //passthrough DOM methods below
  //TODO: extract methods below into abstract class
  public String getName() {
    return element.getName();
  }

  public void addChild(Element ele) {
    this.element.addChild(ele);
  }

  
  
  public void addChildAt(Element element, int idx) {
    this.element.addChildAt(element, idx);
  }

  public void removeChild(Element ele) {
    this.element.removeChild(ele);

  }

  public List<XulComponent> getChildNodes() {
    return this.element.getChildNodes();
  }

  public Document getDocument() {
    return this.element.getDocument();
  }

  public XulComponent getElementById(String elementId) {
    return this.element.getElementById(elementId);
  }

  public XulComponent getElementByXPath(String path) {
    return this.element.getElementByXPath(path);
  }

  public Object getElementObject() {
    return this.element.getElementObject();
  }

  public List<XulComponent> getElementsByTagName(String tagName) {
    return this.element.getElementsByTagName(tagName);
  }

  public XulComponent getFirstChild() {
    return this.element.getFirstChild();
  }

  public Namespace getNamespace() {
    return this.element.getNamespace();
  }

  public XulComponent getParent() {
    return this.element.getParent();
  }

  public String getText() {
    return this.element.getText();
  }

  public void setAttribute(Attribute attribute) {
    this.element.setAttribute(attribute);
  }

  public void setNamespace(String prefix, String uri) {
    this.element.setNamespace(prefix, uri);

  }

  public List<Attribute> getAttributes() {
    return this.element.getAttributes();
  }

  public String getAttributeValue(String attributeName) {
    return this.element.getAttributeValue(attributeName);
  }

  public void setAttributes(List<Attribute> attributes) {
    this.element.setAttributes(attributes);
  }

  public void setAttribute(String name, String value) {
    this.element.setAttribute(name, value);
  }

  public AbstractXulComponent getXulElement() {
    return this;
  }

  public void replaceChild(XulComponent oldElement, XulComponent newElement) throws XulDomException {
    this.element.replaceChild(oldElement, newElement);
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public int getHeight() {
    return this.height;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getWidth() {
    return this.width;
  }

  public void setOnblur(String method) {
  }

}
