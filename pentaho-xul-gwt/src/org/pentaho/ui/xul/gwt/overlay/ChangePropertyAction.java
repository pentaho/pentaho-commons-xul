package org.pentaho.ui.xul.gwt.overlay;

import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;

/**
 * User: nbaker
 * Date: 10/25/11
 */
public class ChangePropertyAction implements IOverlayAction {

  private final Element ele;
  private final String property;
  private final String value;
  private String prevVal;

  public ChangePropertyAction(Element ele, String property, String value){

    this.ele = ele;
    this.property = property;
    this.value = value;
  }

  public void perform() {
    prevVal = ele.getAttributeValue(property);
    ele.setAttribute(property, value);

  }

  public void remove() {
    ele.setAttribute(property, prevVal);
  }
}
