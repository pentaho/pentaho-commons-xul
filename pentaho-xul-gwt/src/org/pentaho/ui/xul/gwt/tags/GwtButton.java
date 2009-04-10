package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.gwt.widgets.client.buttons.ImageButton;
import org.pentaho.gwt.widgets.client.buttons.RoundedButton;
import org.pentaho.gwt.widgets.client.utils.StringUtils;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class GwtButton extends AbstractGwtXulComponent implements XulButton {

  static final String ELEMENT_NAME = "button"; //$NON-NLS-1$

  private String dir, group, image, type, onclick, tooltip, disabledImage;

  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtButton();
      }
    });
  }

  private RoundedButton button;

  private ImageButton imageButton;

  private boolean disabled;

  public GwtButton() {
    super(ELEMENT_NAME);
    //programatic creation doesn't call init() create here for them
    managedObject = button = new RoundedButton();
  }

  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    if (!StringUtils.isEmpty(srcEle.getAttribute("image"))) {
      //we create a button by default, remove it here
      button = null;
      imageButton = new ImageButton();
      SimplePanel sp = new SimplePanel();
      managedObject = sp;
      sp.add(imageButton);
      imageButton.setHeight("");
      imageButton.setWidth("");
    } else {
      managedObject = button = new RoundedButton();
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
    ClickListener listener = new ClickListener() {
      public void onClick(Widget sender) {
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
      button.addClickListener(listener);
    } else {
      imageButton.addClickListener(listener);
    }
  }

  public String getLabel() {
    return (button != null) ? button.getText() : null;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(boolean dis) {
    this.disabled = dis;
    if (button != null) {
      button.setEnabled(!dis);
    } else {
      imageButton.setEnabled(!dis);
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
    src = GWT.getModuleBaseURL() + src;
    this.image = src;
    this.imageButton.setEnabledUrl(src);
    this.imageButton.setDisabledUrl(src);
  }

  public void setDisabledImage(String src) {
    src = GWT.getModuleBaseURL() + src;
    this.disabledImage = src;
    this.imageButton.setDisabledUrl(src);
  }

  public void setSelected(String selected) {
    button.setFocus(Boolean.parseBoolean(selected));
    //TODO: implement selected with button group;
  }

  public void setSelected(boolean selected) {
    if (button != null) {
      button.setFocus(selected);
    } else {
      imageButton.setFocus(selected);
    }
  }

  public void setType(String type) {

    // TODO Auto-generated method stub 

  }

  @Override
  public void setTooltiptext(String tooltip) {
    super.setTooltiptext(tooltip);
    if (button != null) {
      button.setTitle(tooltip);
    } else {
      imageButton.setTitle(tooltip);
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

}
