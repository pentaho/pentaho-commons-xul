/**
 * 
 */
package org.pentaho.ui.xul.dom.dom4j;

import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
/**
 * @author OEM
 *
 */
public class DocumentDom4J extends ElementDom4J implements Document {

  private org.dom4j.Document document;
  public DocumentDom4J(){
    super();
    this.document = org.dom4j.DocumentHelper.createDocument();
    this.element = this.document.getRootElement();
  }
  
  public DocumentDom4J(org.dom4j.tree.DefaultDocument document){
    super();
    this.document = document;
    this.element = document.getRootElement();
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.dom.Document#getRootElement()
   */
  public Element getRootElement() {
    // TODO Auto-generated method stub
    return ((XulElementDom4J) document.getRootElement()).getXulElement();
  }

  @Override
  public void addChild(Element element) {
    // TODO Auto-generated method stub
    document.add((org.dom4j.Element) element.getElementObject());
    this.element = (org.dom4j.Element) element.getElementObject();
  }
  
}
