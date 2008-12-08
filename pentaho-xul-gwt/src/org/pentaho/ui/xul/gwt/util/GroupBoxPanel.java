package org.pentaho.ui.xul.gwt.util;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class GroupBoxPanel extends SimplePanel {
  private Element legend;  
  
  public GroupBoxPanel() {  
    Element fieldset = DOM.createFieldSet();  
    this.legend = DOM.createLegend();  
    DOM.appendChild(fieldset, legend);  
    //setElement(fieldset);  
//    this.addStyleName("green");
  }  
 
  public String getCaption() {  
    return DOM.getInnerText(this.legend);  
//    return "caption";
  }  

  public void setCaption(String caption) {  
    DOM.setInnerText(this.legend, caption);  
  }  
}
