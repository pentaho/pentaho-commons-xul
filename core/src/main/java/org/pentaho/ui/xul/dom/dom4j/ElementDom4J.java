/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/

/**
 * 
 */

package org.pentaho.ui.xul.dom.dom4j;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.dom.Attribute;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.dom.Namespace;

import java.util.ArrayList;
import java.util.List;

/**
 * @author OEM
 * 
 */
public class ElementDom4J implements Element {
  protected org.dom4j.Element element;

  private static final Log logger = LogFactory.getLog( ElementDom4J.class );

  public ElementDom4J() {
  }

  public ElementDom4J( String name, XulComponent xulElement ) {
    this.element = new XulElementDom4J( name.toLowerCase(), xulElement );

  }

  public ElementDom4J( String name ) {
    this.element = new XulElementDom4J( name.toLowerCase() );

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.dom.Element#getChildNodes()
   */
  public List<XulComponent> getChildNodes() {
    ArrayList<XulComponent> list = new ArrayList<XulComponent>();

    List elements = element.elements();
    for ( Object ele : elements ) {
      list.add( ( (XulElementDom4J) ele ).getXulElement() );

    }
    return list;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.dom.Element#getDocument()
   */
  public Document getDocument() {
    try {
      // org.dom4j.Document doc = element.getDocument();
      org.dom4j.Document doc = element.getDocument();
      if ( doc == null ) {
        return null;
      }
      XulElementDom4J o = (XulElementDom4J) doc.getRootElement();
      return ( (XulRoot) o.getXulElement() ).getXulDomContainer().getDocumentRoot();
      // if(doc == null){
      // throw new XulDomException("Element Document is null, getDocument most likely called during parse time");
      // }
      // return DocumentFactory.createDocument(doc);
    } catch ( Exception e ) {
      logger.warn( "Could not get document node : " + e.getMessage(), e );
      return null;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.dom.Element#getElementById(java.lang.String)
   */
  public XulComponent getElementById( String id ) {
    XulElementDom4J ele = (XulElementDom4J) element.getDocument().elementByID( id );
    if ( ele == null ) {
      return null;
    }
    return (XulComponent) ele.getXulElement();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.dom.Element#getElementByXPath(java.lang.String)
   */
  public XulComponent getElementByXPath( String path ) {
    org.dom4j.Node ele = element.selectSingleNode( path );
    if ( ele != null ) {
      return ( (XulElementDom4J) ele ).getXulElement();
    }
    return null;
  }

  // @SuppressWarnings("unused")
  // private void printTree(org.dom4j.Element ele, int indent){
  // System.out.println(getIndent(indent)+ele.getName());
  // ++indent;
  // for(Object childEle : ele.elements()){
  // printTree((org.dom4j.Element) childEle, indent);
  // }
  // }
  //
  // private String getIndent(int indent){
  // StringBuilder sb = new StringBuilder(10);
  // for(int i=0; i< indent; i++){
  // sb.append("  ");
  // }
  // return sb.toString();
  // }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.dom.Element#getElementsByTagName(java.lang.String)
   */
  public List<XulComponent> getElementsByTagName( String tagName ) {
    ArrayList<XulComponent> list = new ArrayList<XulComponent>();

    List elements = element.elements( tagName );
    for ( Object ele : elements ) {
      list.add( ( (XulElementDom4J) ele ).getXulElement() );

    }
    return list;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.dom.Element#getFirstChild()
   */
  public XulComponent getFirstChild() {
    return ( (XulElementDom4J) element.elements().get( 0 ) ).getXulElement();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.dom.Element#getName()
   */
  public String getName() {
    return element.getName();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.dom.Element#getNamespace()
   */
  public Namespace getNamespace() {
    return new Namespace( element.getNamespace().getURI(), element.getNamespace().getPrefix() );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.dom.Element#getParent()
   */
  public XulComponent getParent() {
    if ( element.getParent() == null ) {
      return null;
    }
    return ( (XulElementDom4J) element.getParent() ).getXulElement();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.dom.Element#getText()
   */
  public String getText() {
    return element.getText();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.dom.Element#setNamespace(java.lang.String, java.lang.String)
   */
  public void setNamespace( String prefix, String uri ) {
    element.addNamespace( prefix, uri );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.dom.Element#setAttribute(org.pentaho.ui.xul.dom.Attribute)
   */
  public void setAttribute( Attribute attribute ) {

    setAttribute( attribute.getName(), attribute.getValue() );

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.dom.Element#addChild(org.pentaho.ui.xul.dom.Element)
   */
  public void addChild( Element ele ) {
    org.dom4j.Element dElement = (org.dom4j.Element) ele.getElementObject();

    org.dom4j.Element dElementparent = dElement.getParent();
    if ( dElementparent != null ) {
      dElementparent.remove( dElement );
    }

    this.element.add( dElement );

  }

  public void addChildAt( Element element, int idx ) {
    org.dom4j.Element dElement = (org.dom4j.Element) element.getElementObject();

    org.dom4j.Element dElementparent = dElement.getParent();
    if ( dElementparent != null ) {
      dElementparent.remove( dElement );
    }

    this.element.elements().add( idx, dElement );

  }

  public Object getElementObject() {
    return this.element;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.dom.Element#getAttributes()
   */
  public List<Attribute> getAttributes() {
    ArrayList<Attribute> list = new ArrayList<Attribute>();

    List elements = element.attributes();
    for ( Object ele : elements ) {
      org.dom4j.Attribute baseAttrib = (org.dom4j.Attribute) ele;
      list.add( new Attribute( baseAttrib.getName(), baseAttrib.getValue() ) );

    }
    return list;
  }

  public String getAttributeValue( String attributeName ) {
    return this.element.attributeValue( attributeName );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.dom.Element#setAttributes(java.util.List)
   */
  public void setAttributes( List<Attribute> attributes ) {
    for ( Attribute attrib : attributes ) {
      this.setAttribute( attrib.getName(), attrib.getValue() );
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.dom.Element#setAttribute(java.lang.String, java.lang.String)
   */
  public void setAttribute( String name, String value ) {
    // ID attribute must be upper-case
    if ( name.equals( "id" ) || name.equals( "ID" ) ) {
      element.addAttribute( "ID", value );
    } else {
      element.addAttribute( name, value );
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.dom.Element#removeChild(org.pentaho.ui.xul.dom.Element)
   */
  public void removeChild( Element ele ) {
    element.remove( (org.dom4j.Element) ele.getElementObject() );

  }

  public void replaceChild( XulComponent oldElement, XulComponent newElement ) {

    List contentOfParent = element.content();
    int index = contentOfParent.indexOf( (org.dom4j.Element) oldElement.getElementObject() );
    contentOfParent.set( index, (org.dom4j.Element) newElement.getElementObject() );

  }

  public void setComponent( XulComponent c ) {
    ( (XulElementDom4J) this.element ).setXulElement( c );
  }

}
