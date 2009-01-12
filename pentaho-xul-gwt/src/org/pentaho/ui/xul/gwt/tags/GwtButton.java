package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;

public class GwtButton extends AbstractGwtXulComponent implements XulButton {
  
  static final String ELEMENT_NAME = "button"; //$NON-NLS-1$
  private String dir, group, image, type, onclick, tooltip;
  
  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtButton();
      }
    });
  }
  
  private Button button;
  
  public GwtButton() {
    super(ELEMENT_NAME);
    managedObject = button = new Button();
  }
  
  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    setLabel(srcEle.getAttribute("label"));
    setOnclick(srcEle.getAttribute("onclick"));
    setImage(srcEle.getAttribute("image"));
    setTooltiptext(srcEle.getAttribute("tooltiptext"));
    setDisabled("true".equals(srcEle.getAttribute("disabled")));
  }
  
  public void setLabel(String label){
    this.setAttribute("label", label);
    button.setText(label);
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.components.XulButton#setOnClick(java.lang.String)
   */
  public void setOnclick( final String method) {
    this.onclick = method;
    button.addClickListener(new ClickListener(){
      public void onClick(Widget sender) {try{
        GwtButton.this.getXulDomContainer().invoke(method, new Object[]{});
      } catch(XulException e){
        e.printStackTrace();
      }
      }
    });
  }

  public String getLabel() {
    return button.getText();
  }

  public boolean isDisabled() {
    return ! button.isEnabled();
  }

  public void setDisabled(boolean dis) {
    button.setEnabled(!dis);
  }

  public void doClick() {
    
    //button.click();  This was not working for me, TODO: investigate programatic click
    if(onclick == null){
      return;
    }
    try{
      GwtButton.this.getXulDomContainer().invoke(onclick, new Object[]{});
    } catch(XulException e){
      e.printStackTrace();
    }
  }

  
  public String getDir() {
    return dir;  
  }

  public String getGroup() {
    return group;
  }

  public String getImage() {
    return image;  
  }

  public String getOnclick() {
    return onclick;  
  }

  public String getType() {
    return type;
  }

  public boolean isSelected() {
    return false;  
  }

  public void setDir(String dir) {
    this.dir = dir;
  }

  public void setGroup(String group) {
    this.group = group;
    //TODO: implement button group
  }

  public void setImage(String src) {
    this.image = src;
    //TODO: implement images on buttons
  }

  public void setSelected(String selected) {
     button.setFocus(Boolean.parseBoolean(selected));
     //TODO: implement selected with button group;
  }

  public void setSelected(boolean selected) {
    button.setFocus(selected);  
  }

  public void setType(String type) {
    
        // TODO Auto-generated method stub 
      
  }

  public void adoptAttributes(XulComponent component) {

    if(component.getAttributeValue("label") != null){
      setLabel(component.getAttributeValue("label"));
    }
    if(component.getAttributeValue("onclick") != null){
      setOnclick(component.getAttributeValue("onclick"));
    }
    if(component.getAttributeValue("image") != null){
      setImage(component.getAttributeValue("image"));
    }
    if(component.getAttributeValue("tooltiptext") != null){
      setTooltiptext(component.getAttributeValue("tooltiptext"));
    }
    if(component.getAttributeValue("disabled") != null){
      setDisabled("true".equals(component.getAttributeValue("disabled")));
    }
  }
  
}
