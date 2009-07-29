package org.pentaho.ui.xul.gwt.util;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class GroupBoxPanel extends Widget {
  private Element legend;  
  Element fieldset = DOM.createFieldSet();
  public GroupBoxPanel() {  
    this.legend = DOM.createLegend();  
    DOM.appendChild(fieldset, legend);  
    setElement(fieldset);  
    //this.addStyleName("green");
  }  
 
  public String getCaption() {  
    return DOM.getInnerText(this.legend);  
//    return "caption";
  }  

  public void setCaption(String caption) {  
    DOM.setInnerText(this.legend, caption);  
  }  
  
  public void add(Widget w) {
    DOM.appendChild(fieldset, w.getElement());
  }
}
