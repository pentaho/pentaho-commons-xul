/**
 * 
 */
package org.pentaho.ui.xul.dom.dom4j;

import org.pentaho.ui.xul.XulElement;

/**
 * @author OEM
 *
 */
public class XulElementDom4J extends org.dom4j.tree.BaseElement{
  private XulElement xulElement;
  public XulElementDom4J(){
    super("blank");
  }

  public XulElementDom4J(String name, XulElement element){
    super(name);
    this.xulElement = element;
  }
  
  public XulElement getXulElement(){
    return xulElement;
  }
  
}
