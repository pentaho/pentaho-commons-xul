package org.pentaho.ui.xul.gwt.tags;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.gwt.widgets.client.utils.StringUtils;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulCaption;
import org.pentaho.ui.xul.components.XulRadioGroup;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.gwt.util.GwtUIConst;
import org.pentaho.ui.xul.util.Orient;

import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GwtRadioGroup extends AbstractGwtXulContainer implements XulRadioGroup {

  static final String ELEMENT_NAME = "radiogroup"; //$NON-NLS-1$
  private CaptionPanel captionPanel; 
  private List<GwtRadio> radios;
  
  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtRadioGroup();
      }
    });
  }

  public GwtRadioGroup() {
    super(ELEMENT_NAME);
    this.radios = new ArrayList<GwtRadio>();
    this.orientation = Orient.VERTICAL;
    captionPanel = new CaptionPanel();
    setManagedObject(captionPanel);
    captionPanel.getElement().getStyle().setProperty("padding", "0px");
    captionPanel.getElement().getStyle().setProperty("margin", "0px");
    
    VerticalPanel vp;
    container = vp = new VerticalPanel();
    container.setWidth("100%");
    vp.setSpacing(GwtUIConst.PANEL_SPACING);    // IE_6_FIX, move to CSS
    vp.setStyleName("vbox");
    
    SimplePanel sp = new SimplePanel();
    sp.getElement().getStyle().setProperty("padding", "4px");
    sp.getElement().getStyle().setProperty("margin", "0px");
    sp.setWidth("95%");
    sp.add(container);
    ((VerticalPanel) container).setStyleName("vbox");
    ((CaptionPanel) getManagedObject()).add(sp);
  }
  
  public void registerRadio(GwtRadio aRadio) {
    this.radios.add(aRadio);
  }

  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    if(!StringUtils.isEmpty(srcEle.getAttribute("width"))) {
      captionPanel.setWidth(srcEle.getAttribute("width") + "px");
    } else {
      captionPanel.setWidth("95%"); 
    }
    if(!StringUtils.isEmpty(srcEle.getAttribute("height"))) {
      captionPanel.setHeight(srcEle.getAttribute("height") + "px");
    } else {
      captionPanel.setHeight("100%");
    }
    GwtRadio.currentGroup = this;
  }
  
  
  
  @Override
  public void addChild(Element element) {
    if(element instanceof XulCaption){
        setCaption(((XulCaption) element).getLabel());  
    }
    super.addChild(element);
  }

  @Override
  public void addChildAt(Element element, int idx) {
    // TODO Auto-generated method stub super.addChildAt(element, idx);
  }

  public String getValue(){
    String value = null;
    for(GwtRadio radio: this.radios) {
      if(radio.isSelected()) {
        value = radio.getValue();
        break;
      }
    }
    return value;
  }
  
  public void fireValueChanged(){
    firePropertyChange("value", null, getValue());
  }
  
  public void resetContainer(){
    
    container.clear();
  }
  
  public void setCaption(String caption){
    captionPanel.setCaptionText(caption);
  }
  
  @Override
  public void layout(){
    super.layout();
    for(XulComponent comp : this.getChildNodes()){
      if(comp instanceof GwtCaption){
        this.setCaption(((GwtCaption) comp).getLabel());
      }
    }
  }
  
  public Orient getOrientation() {
    return Orient.VERTICAL;
  }

  @Override
  public void replaceChild(Element oldElement, Element newElement) {
    this.resetContainer();
    super.replaceChild(oldElement, newElement);
  }
  

  
  public void adoptAttributes(XulComponent component) {

    if(component.getAttributeValue("caption") != null){
      setCaption(component.getAttributeValue("caption"));
    }
  }
  
  @Override
  public void setHeight(int height) {
    captionPanel.setHeight(height + "px");
  }

  @Override
  public void setWidth(int width) {
    captionPanel.setWidth(width + "px");
  }
}
