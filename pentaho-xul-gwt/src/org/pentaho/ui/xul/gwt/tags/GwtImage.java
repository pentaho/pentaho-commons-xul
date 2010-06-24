package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.gwt.widgets.client.utils.StringUtils;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulImage;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.stereotype.Bindable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class GwtImage extends AbstractGwtXulComponent implements XulImage {

  static final String ELEMENT_NAME = "image"; //$NON-NLS-1$

  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtImage();
      }
    });
  }

  private Image image;

  public GwtImage() {
    super(ELEMENT_NAME);
    image = new Image();
    setManagedObject(image);
    image.setStyleName("xul-image"); //$NON-NLS-1$
  }

  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    setSrc(srcEle.getAttribute("src")); //$NON-NLS-1$
    setDisabled("true".equals(srcEle.getAttribute("disabled"))); //$NON-NLS-1$//$NON-NLS-2$
  }

  public void layout() {
    image.setTitle(this.getTooltiptext());
    if (StringUtils.isEmpty(this.getTooltiptext()) == false) {
      image.setTitle(this.getTooltiptext());
    }
  }

  @Bindable
  public String getSrc() {
    return image.getUrl();
  }

  @Bindable
  public void setSrc(String src) {
    if(src == null){
      image.setUrl(null);
      return;
    }
    image.setUrl(GWT.getModuleBaseURL() + src);
  }

  @Bindable
  public void setSrc(Object img) {
    if (img instanceof String) {
      setSrc((String) img);
    }
    if (img instanceof Image) {
      setSrc(((Image) img).getUrl());
    } else {
      throw new UnsupportedOperationException();
    }
  }

  public void refresh() {

  }

  public void adoptAttributes(XulComponent component) {
    if (component.getAttributeValue("disabled") != null) { //$NON-NLS-1$
      setDisabled("true".equals(component.getAttributeValue("disabled"))); //$NON-NLS-1$//$NON-NLS-2$
    }
    if (component.getAttributeValue("value") != null) { //$NON-NLS-1$
      setSrc(component.getAttributeValue("src")); //$NON-NLS-1$
    }
  }

  @Override
  @Bindable
  public void setTooltiptext(String tooltip) {
    super.setTooltiptext(tooltip);
    image.setTitle(this.getTooltiptext());
  }
  
  @Override
  @Bindable
  public void setVisible(boolean visible) {
    image.setVisible(visible);
  }
  
  @Override
  @Bindable
  public boolean isVisible() {
    return image.isVisible();
  }

}
