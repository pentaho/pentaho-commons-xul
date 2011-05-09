package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.gwt.widgets.client.buttons.ImageButton;
import org.pentaho.gwt.widgets.client.buttons.RoundedButton;
import org.pentaho.gwt.widgets.client.utils.ButtonHelper;
import org.pentaho.gwt.widgets.client.utils.ButtonHelper.ButtonLabelType;
import org.pentaho.gwt.widgets.client.utils.string.StringUtils;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.stereotype.Bindable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;

public class GwtButton extends AbstractGwtXulComponent implements XulButton {

  static final String ELEMENT_NAME = "button"; //$NON-NLS-1$
  enum DIRECTION {forward, reverse};
  enum ORIENT {horizontal, vertical}
  private String dir, group, image, type, onclick, tooltip, disabledImage;

  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtButton();
      }
    });
  }

  private String className;
  private Button customButton;
  private Button button;

  private ImageButton imageButton;

  private boolean disabled;

  public GwtButton() {
    super(ELEMENT_NAME);
    //programatic creation doesn't call init() create here for them
    button = new Button();
    button.setStylePrimaryName("pentaho-button");
    setManagedObject(button);
  }

  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    if(!StringUtils.isEmpty(srcEle.getAttribute("dir")) && !StringUtils.isEmpty(srcEle.getAttribute("image")) && !StringUtils.isEmpty(srcEle.getAttribute("label"))) {
      
      if(!StringUtils.isEmpty(srcEle.getAttribute("pen:classname"))) {
        /* customButton = new CustomButton(new Image(GWT.getModuleBaseURL() + srcEle.getAttribute("image")),
        srcEle.getAttribute("label"), getButtonLabelOrigin(srcEle.getAttribute("dir"),srcEle.getAttribute("orient")),,srcEle.getAttribute("pen:classname"));*/
        customButton = new Button(ButtonHelper.createButtonLabel(
        new Image(GWT.getModuleBaseURL() + srcEle.getAttribute("image")),
        srcEle.getAttribute("label"), getButtonLabelOrigin(srcEle.getAttribute("dir"),srcEle.getAttribute("orient")),srcEle.getAttribute("pen:classname")));
        
      } else {
     /* customButton = new CustomButton(new Image(GWT.getModuleBaseURL() + srcEle.getAttribute("image")),
          srcEle.getAttribute("label"), getButtonLabelOrigin(srcEle.getAttribute("dir"),srcEle.getAttribute("orient")));*/
      customButton = new Button(ButtonHelper.createButtonLabel(
          new Image(GWT.getModuleBaseURL() + srcEle.getAttribute("image")),
          srcEle.getAttribute("label"), getButtonLabelOrigin(srcEle.getAttribute("dir"),srcEle.getAttribute("orient"))));
      }
      setManagedObject(customButton);
      button = null;
    } else if (!StringUtils.isEmpty(srcEle.getAttribute("image"))) {
      //we create a button by default, remove it here
      button = null;
      imageButton = new ImageButton();
      SimplePanel sp = new SimplePanel();
      setManagedObject(sp);
      sp.add(imageButton);
      imageButton.setHeight("");
      imageButton.setWidth("");
    } else {
      button = new Button();
      button.setStylePrimaryName("xul-button");
      setManagedObject(button);
    }

    super.init(srcEle, container);
    if (!StringUtils.isEmpty(srcEle.getAttribute("label"))) {
      setLabel(srcEle.getAttribute("label"));
    }
    if (!StringUtils.isEmpty(srcEle.getAttribute("onclick"))) {
      setOnclick(srcEle.getAttribute("onclick"));
    }
    if (!StringUtils.isEmpty(srcEle.getAttribute("image"))) {
      setImage(srcEle.getAttribute("image"));
    }
    if (!StringUtils.isEmpty(srcEle.getAttribute("tooltiptext"))) {
      setTooltiptext(srcEle.getAttribute("tooltiptext"));
    }
    if (!StringUtils.isEmpty(srcEle.getAttribute("pen:disabledimage"))) {
      this.setDisabledImage(srcEle.getAttribute("pen:disabledimage"));
    }
    if (!StringUtils.isEmpty(srcEle.getAttribute("pen:classname"))) {
      this.setClassName(srcEle.getAttribute("pen:classname"));
    }
    
    setDisabled("true".equals(srcEle.getAttribute("disabled")));
  }

  public void setLabel(String label) {
    this.setAttribute("label", label);
    if (button != null) {
      button.setText(label);
    }
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.components.XulButton#setOnClick(java.lang.String)
   */
  public void setOnclick(final String method) {
    this.onclick = method;
    
    ClickHandler handler = new ClickHandler(){
      public void onClick(ClickEvent event) {
        if(!GwtButton.this.disabled) {
          try {
            GwtButton.this.getXulDomContainer().invoke(method, new Object[] {});
          } catch (XulException e) {
            e.printStackTrace();
          }
        }
      }
    };


    if (button != null) {
      button.addClickHandler(handler);
    } else if (imageButton != null) {
      imageButton.addClickHandler(handler);
    } else if (customButton != null) {
      customButton.addClickHandler(handler);
    }
  }

  @Bindable
  public String getLabel() {
    return (button != null) ? button.getText() : null;
  }

  @Bindable
  public boolean isDisabled() {
    return disabled;
  }

  @Bindable
  public void setDisabled(boolean dis) {
    this.disabled = dis;
    if (button != null) {
      if(dis){
        button.addStyleDependentName("disabled");
      } else {
        button.removeStyleDependentName("disabled");
      }
    } else if (imageButton != null) {
      imageButton.setEnabled(!dis);
    } else if (customButton != null) {
      customButton.setEnabled(!dis);
    }
  }

  public void doClick() {

    //button.click();  This was not working for me, TODO: investigate programatic click
    if (onclick == null) {
      return;
    }
    try {
      GwtButton.this.getXulDomContainer().invoke(onclick, new Object[] {});
    } catch (XulException e) {
      e.printStackTrace();
    }
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
    if (imageButton == null) {
      button = null;
      imageButton = new ImageButton();
      SimplePanel sp = new SimplePanel();
      setManagedObject(sp);
      sp.add(imageButton);
      imageButton.setHeight("");
      imageButton.setWidth("");
    }    
    if (imageButton != null) {
      src = GWT.getModuleBaseURL() + src;
      this.image = src;
      this.imageButton.setEnabledUrl(src);
      this.imageButton.setDisabledUrl(src);
    }
  }

  @Bindable
  public void setDisabledImage(String src) {
    src = GWT.getModuleBaseURL() + src;
    this.disabledImage = src;
    this.imageButton.setDisabledUrl(src);
  }

  @Bindable
  public void setSelected(String selected) {
    button.setFocus(Boolean.parseBoolean(selected));
    //TODO: implement selected with button group;
  }

  @Bindable
  public void setSelected(boolean selected) {
    if (button != null) {
      button.setFocus(selected);
    } else if (imageButton != null) {
      imageButton.setFocus(selected);
    } else if (customButton != null) {
      customButton.setFocus(selected);
    }
  }

  public void setType(String type) {

    // TODO Auto-generated method stub 

  }

  @Override
  @Bindable
  public void setTooltiptext(String tooltip) {
    super.setTooltiptext(tooltip);
    if (button != null) {
      button.setTitle(tooltip);
    } else if (imageButton != null) {
      imageButton.setTitle(tooltip);
    } else if (customButton != null) {
      customButton.setTitle(tooltip);
    }

  }

  public void layout() {
    super.layout();
    if (imageButton != null) {
      imageButton.setHeight("");
      imageButton.setWidth("");
    }
  }

  public void adoptAttributes(XulComponent component) {

    if (component.getAttributeValue("label") != null) {
      setLabel(component.getAttributeValue("label"));
    }
    if (component.getAttributeValue("onclick") != null) {
      setOnclick(component.getAttributeValue("onclick"));
    }
    if (component.getAttributeValue("image") != null) {
      setImage(component.getAttributeValue("image"));
    }
    if (component.getAttributeValue("tooltiptext") != null) {
      setTooltiptext(component.getAttributeValue("tooltiptext"));
    }
    if (component.getAttributeValue("disabled") != null) {
      setDisabled("true".equals(component.getAttributeValue("disabled")));
    }
  }
  
  @Override
  public void setVisible(boolean visible) {
    super.setVisible(visible);

    if (button != null) {
      button.setVisible(visible);
    } else if (imageButton != null) {
      imageButton.setVisible(visible);
    } else if (customButton != null) {
      customButton.setVisible(visible);
    }
  }

  private ButtonLabelType getButtonLabelOrigin(String direction, String orient) {
    if(direction == null || direction.length() <= 0) {
      direction = DIRECTION.forward.toString();
    }
    if(orient == null || orient.length() <= 0) {
      orient = ORIENT.horizontal.toString();
    }
    
    if(direction != null && orient != null) {
      if(direction.equals(DIRECTION.forward.toString()) && orient.equals(ORIENT.vertical.toString())) {
        return ButtonLabelType.TEXT_ON_BOTTOM;
      } else if(direction.equals(DIRECTION.reverse.toString()) && orient.equals(ORIENT.vertical.toString())) {
        return ButtonLabelType.TEXT_ON_TOP;
      } else if(direction.equals(DIRECTION.reverse.toString()) && orient.equals(ORIENT.horizontal.toString())) {
        return ButtonLabelType.TEXT_ON_LEFT;
      } else if(direction.equals(DIRECTION.forward.toString()) && orient.equals(ORIENT.horizontal.toString())) {
        return ButtonLabelType.TEXT_ON_RIGHT;
      }
    } return ButtonLabelType.NO_TEXT;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getClassName() {
    return className;
  }
}
