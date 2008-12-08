/**
 * 
 */
package org.pentaho.ui.xul.dom.dom4j;

import org.pentaho.ui.xul.XulComponent;

/**
 * @author OEM
 *
 */
public class XulElementDom4J extends org.dom4j.tree.BaseElement{
  private static final long serialVersionUID = -2463576912677723967L;

  private XulComponent xulElement;
  public XulElementDom4J(){
    super("blank");
  }

  public XulElementDom4J(String name, XulComponent element){
    super(name);
    this.xulElement = element;
  }
  
  public XulElementDom4J(String name){
    super(name);
  }
  
  public XulComponent getXulElement(){
    return xulElement;
  }
  
  public void setXulElement(XulComponent c){
    this.xulElement = c;
  }
}
