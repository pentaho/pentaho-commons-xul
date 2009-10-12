package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.gwt.widgets.client.toolbar.ToolbarButton;
import org.pentaho.gwt.widgets.client.toolbar.ToolbarToggleButton;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulToolbarbutton;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.stereotype.Bindable;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;

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
  
  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    setType(srcEle.getAttribute("type"));

    if(this.type != null && this.type.equals("toggle")){
      button = new ToolbarToggleButton(new Image());
    } else {
      button = new ToolbarButton(new Image());
    };
    setManagedObject(button);
    
    setLabel(srcEle.getAttribute("label"));
    setOnclick(srcEle.getAttribute("onclick"));
    setImage(srcEle.getAttribute("image"));
    setTooltiptext(srcEle.getAttribute("tooltiptext"));
    setDisabled("true".equals(srcEle.getAttribute("disabled")));
    setDisabledImage(srcEle.getAttribute("disabledimage"));
    setDownimage(srcEle.getAttribute("downimage"));
    String vis = srcEle.getAttribute("pen:visible");
    if(vis != null && !vis.equals("")){
      setVisible("true".equals(vis));
    }
    
  }
  
  

  public void doClick() {
    button.getCommand().execute();
  }

  @Bindable
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

  @Bindable
  public String getLabel() {
    return button.getText();
  }

  @Bindable
  public boolean isDisabled() {
    return ! button.isEnabled();
  }

  @Bindable
  public void setDisabled(boolean dis) {
    button.setEnabled(!dis);
  }
  
  public String getDir() {
    return dir;  
  }

  public String getGroup() {
    return group;
  }

  @Bindable
  public String getImage() {
    return image;  
  }

  public String getOnclick() {
    return onclick;  
  }

  public String getType() {
    return type;
  }

  @Bindable
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

  @Bindable
  public void setImage(String src) {
    this.image = src;
    if(src != null && src.length() > 0){
      Image i = new Image(src);
      // WebDriver support.. give the image a direct id we can use as a hook
      i.getElement().setId(this.getId().concat("_img"));
      button.setImage(i);
    }
  }

  @Bindable
  public void setDisabledImage(String src) {
    this.disabledImage = src;
    if(src != null && src.length() > 0){
      button.setDisabledImage(new Image(disabledImage));
    }
  }
  
  public String getDisabledImage(){
    return disabledImage;
  }

  @Bindable
  public void setSelected(String selected) {
    
  }

  @Bindable
  public void setSelected(boolean selected) {
    if(button instanceof ToolbarToggleButton){
      ((ToolbarToggleButton) button).setSelected(selected,true);
    } else if(button instanceof ToolbarButton){
    }
    
  }
  
  @Bindable
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
  @Bindable
  public void setTooltiptext(String tooltip) {
    super.setTooltiptext(tooltip);
    if(button != null){
      button.setToolTip(tooltip);
    }
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
    if(component.getAttributeValue("pen:visible") != null){
      setVisible("true".equals(component.getAttributeValue("pen:visible")));
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

  @Override
  @Bindable
  public void setVisible(boolean visible) {
    super.setVisible(visible);
    button.setVisible(visible);
  }
  
}

  