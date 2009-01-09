package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.gwt.widgets.client.toolbar.ToolbarButton;
import org.pentaho.gwt.widgets.client.toolbar.ToolbarToggleButton;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulToolbarbutton;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class GwtToolbarbutton extends AbstractGwtXulComponent implements XulToolbarbutton{

  private ToolbarButton button;

  private String dir, group, image, onclick, tooltip, disabledImage, type, downimage, downimagedisabled;
  
  public GwtToolbarbutton(){
    super("toolbarbutton");
  }
  
  public static void register() {
    GwtXulParser.registerHandler("toolbarbutton", 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtToolbarbutton();
      }
    });
  }
  
  public void init(com.google.gwt.xml.client.Element srcEle) {
    super.init(srcEle);
    setType(srcEle.getAttribute("type"));

    if(this.type != null && this.type.equals("toggle")){
      button = new ToolbarToggleButton(new Image());
    } else {
      button = new ToolbarButton(new Image());
    };
    managedObject = button;
    
    setLabel(srcEle.getAttribute("label"));
    setOnclick(srcEle.getAttribute("onclick"));
    setImage(srcEle.getAttribute("image"));
    setTooltiptext(srcEle.getAttribute("tooltiptext"));
    setDisabled("true".equals(srcEle.getAttribute("disabled")));
    setDisabledImage(srcEle.getAttribute("disabledimage"));
    setDownimage(srcEle.getAttribute("downimage"));
  }
  
  

  public void doClick() {
    button.getCommand().execute();
  }

  public void setLabel(String label) {
    button.setText(label);
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.components.XulButton#setOnClick(java.lang.String)
   */
  public void setOnclick( final String method) {
    this.onclick = method;
    button.setCommand(new Command(){
      public void execute(){
        try{
          GwtToolbarbutton.this.getXulDomContainer().invoke(method, new Object[]{});
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
    button.setImage(new Image(src));
  }

  public void setDisabledImage(String src) {
    this.disabledImage = src;
    button.setDisabledImage(new Image(disabledImage));
  }
  
  public String getDisabledImage(){
    return disabledImage;
  }

  public void setSelected(String selected) {
    
  }

  public void setSelected(boolean selected) {
    if(button instanceof ToolbarToggleButton){
      ((ToolbarToggleButton) button).setSelected(selected,true);
    } else if(button instanceof ToolbarButton){
    }
    
  }
  
  public void setSelected(boolean selected, boolean fireEvent) {
    if(button instanceof ToolbarToggleButton){
      ((ToolbarToggleButton) button).setSelected(selected,fireEvent);
    } else if(button instanceof ToolbarButton){
    }
    
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public void setTooltiptext(String tooltip) {
    button.setToolTip(tooltip);
  }


  public void adoptAttributes(XulComponent component) {

    if(component.getAttributeValue("label") != null){
      setLabel(component.getAttributeValue("label"));
    }
    if(component.getAttributeValue("disabled") != null){
      setDisabled("true".equals(component.getAttributeValue("disabled")));
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
    
    
  }

  public String getDownimage() {
    return this.downimage;
  }

  public String getDownimagedisabled() {
    return this.downimagedisabled;  
  }

  public void setDownimage(String img) {
    this.downimage = img;
    if(img != null && img.length() > 0){
      button.setDownImage(new Image(img));
    }
  }

  public void setDownimagedisabled(String img) {
    this.downimagedisabled = img;
    if(img != null && img.length() > 0){
      button.setDownImageDisabled(new Image(img));
    }
  }
  
  
}

  