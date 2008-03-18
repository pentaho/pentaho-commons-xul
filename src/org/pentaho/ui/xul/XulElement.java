/**
 * 
 */
package org.pentaho.ui.xul;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.pentaho.ui.xul.dom.Attribute;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.DocumentFactory;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.dom.Namespace;
import org.pentaho.ui.xul.swt.Orient;



/**
 * @author OEM
 *
 */
public abstract class XulElement implements XulComponent, Element {
  private static final long serialVersionUID = -3629792827245143824L;

  protected Object managedObject;
  protected Element element;
  
  protected int flex = 0;
  protected String id;
  protected boolean flexLayout = false;
  protected  GridBagConstraints gc;

  protected List<XulComponent> children;


  public XulElement(Element element){
    this.element = element;
    children = new ArrayList<XulComponent>();
    
    
  }
  
  public XulElement(String name){
    try{
      this.element = DocumentFactory.createElement(name, this);
      children = new ArrayList<XulComponent>();
    } catch(XulException e) {
      //TODO: add logging and perhaps throw illegalArgumentException or rethrow XulEx.
      System.out.println(String.format("error creating XulElement (%s)", name));
    }
  }
  
  public XulElement(String tagName, Object managedObject){
    try{
      this.element = DocumentFactory.createElement(tagName, this);
      this.managedObject = managedObject;
      children = new ArrayList<XulComponent>();
    } catch(XulException e) {
      //TODO: add logging and perhaps throw illegalArgumentException or rethrow XulEx.
      System.out.println(String.format("error creating XulElement (%s)", tagName));
    }
  }
  
  public Object getManagedObject(){
    return managedObject;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.setAttribute("ID", id);
    this.id = id;
  }

  

  public int getFlex() {
    return flex;
  }

  public void setFlex(int flex) {
    this.flex = flex;
  }
  

  public void addComponent(XulComponent c){
    children.add(c);
  }
  
  
  public void layout(){
  
  }

  
  
  //passthrough DOM methods below
  //TODO: extract methods below into abstract class
  public String getName() {
    return element.getName();
  }

  public void addChild(Element element) {
    this.element.addChild(element);
    
  }

  public void removeChild(Element element) {
    this.element.removeChild(element);
    
  }

  public List<Element> getChildNodes() {
    return this.element.getChildNodes();
  }

  public Document getDocument() {
    return this.element.getDocument();
  }

  public Element getElementById(String id) {
    return this.element.getElementById(id);
  }

  public Element getElementByXPath(String path) {
    return this.element.getElementByXPath(path);
  }

  public Object getElementObject() {
    return this.element.getElementObject();
  }

  public List<Element> getElementsByTagName(String tagName) {
    return this.element.getElementsByTagName(tagName);
  }

  public Element getFirstChild() {
    return this.element.getFirstChild();
  }

  public Namespace getNamespace() {
    return this.element.getNamespace();
  }

  public Element getParent() {
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
  
  public List<Attribute> getAttributes(){
    return this.element.getAttributes();
  }
  public String getAttributeValue(String attributeName){
    return this.element.getAttributeValue(attributeName);
  }
  
  
  public void setAttributes(List<Attribute> attributes) {
      this.element.setAttributes(attributes);
  }

  public void setAttribute(String name, String value) {
     this.element.setAttribute(name, value);
  }
  
  public XulElement getXulElement(){
    return this;
  }
  
}
