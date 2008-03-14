/**
 * 
 */
package org.pentaho.ui.xul.dom.dom4j;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.dom.Attribute;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.DocumentFactory;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.dom.Namespace;

/**
 * @author OEM
 *
 */
public class ElementDom4J implements Element{
  protected org.dom4j.Element element;
  
  public ElementDom4J(){
  }
  
  public ElementDom4J(String name, XulElement xulElement){
    this.element = new XulElementDom4J(name.toLowerCase(), xulElement);

  }
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.dom.Element#getChildNodes()
   */
  public List<Element> getChildNodes() {
    ArrayList<Element> list = new ArrayList<Element>();
    
    List elements = element.elements();
    for(Object ele : elements){
      list.add( ((XulElementDom4J) ele).getXulElement() );
      
    }
    return list;
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.dom.Element#getDocument()
   */
  public Document getDocument() {
    try{
      return DocumentFactory.createDocument(element.getDocument());
    } catch(Exception e){
      System.out.println("Could not get document node : "+e.getMessage());
      e.printStackTrace(System.out);
      return null;
    }
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.dom.Element#getElementById(java.lang.String)
   */
  public Element getElementById(String id) {
    org.dom4j.Element ele = element.elementByID(id);
    return ((XulElementDom4J) ele).getXulElement();
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.dom.Element#getElementByXPath(java.lang.String)
   */
  public Element getElementByXPath(String path) {
    org.dom4j.Node ele = element.selectSingleNode(path);
    return ((XulElementDom4J) ele).getXulElement();
  }
  
  private void printTree(org.dom4j.Element ele, int indent){
    System.out.println(getIndent(indent)+ele.getName());
    ++indent;
    for(Object childEle : ele.elements()){
      printTree((org.dom4j.Element) childEle, indent);
    }
  }
  
  private String getIndent(int indent){
    StringBuilder sb = new StringBuilder(10);
    for(int i=0; i< indent; i++){
      sb.append("  ");
    }
    return sb.toString();
  }
  

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.dom.Element#getElementsByTagName(java.lang.String)
   */
  public List<Element> getElementsByTagName(String tagName) {
    ArrayList<Element> list = new ArrayList<Element>();
    
    List elements = element.elements(tagName);
    for(Object ele : elements){
      list.add(((XulElementDom4J) ele).getXulElement());
      
    }
    return list;
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.dom.Element#getFirstChild()
   */
  public Element getFirstChild() {
    return ((XulElementDom4J)  element.elements().get(0)).getXulElement();
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.dom.Element#getName()
   */
  public String getName() {
    return element.getName();
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.dom.Element#getNamespace()
   */
  public Namespace getNamespace() {
    return new Namespace(element.getNamespace().getURI(), element.getNamespace().getPrefix());
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.dom.Element#getParent()
   */
  public Element getParent() {
    return ((XulElementDom4J) element.getParent()).getXulElement();
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.dom.Element#getText()
   */
  public String getText() {
    return element.getText();
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.dom.Element#setNamespace(java.lang.String, java.lang.String)
   */
  public void setNamespace(String prefix, String uri) {
    element.addNamespace(prefix,uri);
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.dom.Element#setAttribute(org.pentaho.ui.xul.dom.Attribute)
   */
  public void setAttribute(Attribute attribute) {
    
    setAttribute(attribute.getName(), attribute.getValue());
    
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.dom.Element#addChild(org.pentaho.ui.xul.dom.Element)
   */
  public void addChild(Element element) {
    this.element.add((org.dom4j.Element) element.getElementObject());
    
  }
  
  public Object getElementObject(){
    return this.element;
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.dom.Element#getAttributes()
   */
  public List<Attribute> getAttributes() {
    ArrayList<Attribute> list = new ArrayList<Attribute>();
    
    List elements = element.attributes();
    for(Object ele : elements){
      org.dom4j.Attribute baseAttrib = (org.dom4j.Attribute) ele;
      list.add(new Attribute(baseAttrib.getName(), baseAttrib.getValue())); 
      
    }
    return list;
  }
  

  public String getAttributeValue(String attributeName){
    return this.element.attributeValue(attributeName);
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.dom.Element#setAttributes(java.util.List)
   */
  public void setAttributes(List<Attribute> attributes) {
    for(Attribute attrib : attributes){
      this.setAttribute(attrib.getName(), attrib.getValue());
    }
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.dom.Element#setAttribute(java.lang.String, java.lang.String)
   */
  public void setAttribute(String name, String value) {
    //ID attribute must be upper-case
    if(name.equals("id")){
      element.addAttribute("ID", value);
    } else {
      element.addAttribute(name, value);
    }
  }
  
  public XulElement getXulElement(){
    if(this.element instanceof XulElementDom4J)
      return ((XulElementDom4J) this.element).getXulElement();
    else 
      return null;
  }

}
