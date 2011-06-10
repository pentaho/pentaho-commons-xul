package org.pentaho.ui.xul.gwt.tags;


import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Widget;
import org.pentaho.gwt.widgets.client.text.ToolTip;
import org.pentaho.gwt.widgets.client.utils.StringUtils;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.stereotype.Bindable;

public class GwtLabel extends AbstractGwtXulComponent implements XulLabel {
  
  static final String ELEMENT_NAME = "label"; //$NON-NLS-1$
  private String onclick;
  private XulDomContainer domContainer;
  private boolean multiline = false;

  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtLabel();
      }
    });
  }
  
  private Label label;
  private boolean pre;

  public GwtLabel() {
    super(ELEMENT_NAME);
    label = new Label();
    setManagedObject(label);
    label.setStyleName("xul-label");

//    label.setWordWrap(true);
  }

  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    domContainer = container;
    super.init(srcEle, container);
    setValue(srcEle.getAttribute("value"));
    setDisabled("true".equals(srcEle.getAttribute("disabled")));
    setPre("true".equals(srcEle.getAttribute("pre")));
    setMultiline("true".equals(srcEle.getAttribute("multiline")));

    if(StringUtils.isEmpty(srcEle.getAttribute("onclick")) == false){
      setOnclick(srcEle.getAttribute("onclick"));
    }

  }

  private void setPre(boolean b) {
    this.pre = b;
  }

  public void layout(){
    label.setTitle(this.getTooltiptext());
    if(onclick != null){
      label.addStyleName("hyperlink");
    }
    label.setWordWrap(multiline);

    if(StringUtils.isEmpty(this.getTooltiptext()) == false){
      
      // ToolTip creation is wrapped in a passthrough listener. This delayed instantiation works around a problem with the
      // underlying GWT widgets that throw errors positioning when the GWT app is loaded in a frame that's not visible.
      label.addMouseListener(new MouseListener(){
        ToolTip tt;
        
        private void verifyTTCreated(){
          if(tt == null){
            tt = new ToolTip(GwtLabel.this.getTooltiptext(), 1000);
          }
        }

        public void onMouseDown(Widget sender, int x, int y) {
          verifyTTCreated();
          tt.onMouseDown(sender, x, y);
        }

        public void onMouseEnter(Widget sender) {
          verifyTTCreated();
          tt.onMouseEnter(sender);
        }

        public void onMouseLeave(Widget sender) {
          verifyTTCreated();
          tt.onMouseLeave(sender);
        }

        public void onMouseMove(Widget sender, int x, int y) {
          verifyTTCreated();
          tt.onMouseMove(sender, x, y);
        }

        public void onMouseUp(Widget sender, int x, int y) {
          verifyTTCreated();
          tt.onMouseUp(sender, x, y);
        }
        
        
      });
    }

    label.addMouseUpHandler(new MouseUpHandler(){
      public void onMouseUp(MouseUpEvent mouseUpEvent) {
        if(onclick != null){
          try {
            domContainer.invoke(onclick, new String[]{});
          } catch (XulException e) {
            Window.alert("Error executing command: " + onclick);
          }
        }
      }
    });
  }

  @Bindable
  public void setValue(String value){
    if (!pre) {
      label.setText(value);
    } else {
      String style = "";
      if (getWidth() > 0 || getHeight() > 0)
        style = "style=\"";
        if(getWidth() > 0) {
          style += "width:" + getWidth() + "px;";
        }
        if (getHeight() > 0) {
          style += "height:" + getHeight() + "px;";
        }
      style += "\"";
      label.getElement().setInnerHTML("<div class='label-scroll-panel xul-pre' " + style + "> <pre class='xul-pre' " + style +">" + value + "</pre></div>");
      replaceScrollbars("label-scroll-panel");
    }
  }
  
  @Bindable
  public String getValue(){
    return label.getText();
  }

  public boolean isDisabled() {
    return disabled;
//    return !label.isEnabled();
  }

  public void setDisabled(boolean dis) {
    if (dis) {
      label.addStyleDependentName("disabled");
    } else {
      label.removeStyleDependentName("disabled");
    }
    this.disabled = dis;
  }
  

  
  public void adoptAttributes(XulComponent component) {
    
    if(component.getAttributeValue("disabled") != null){
      setDisabled("true".equals(component.getAttributeValue("disabled")));
    }
    if(component.getAttributeValue("value") != null){
      setValue(component.getAttributeValue("value"));
    }
  }

  @Override
  @Bindable
  public void setTooltiptext(String tooltip) {
    super.setTooltiptext(tooltip);
    label.setTitle(this.getTooltiptext());
  }

  public String getOnclick() {
    return onclick;
  }

  public void setOnclick(String onclick) {
    this.onclick = onclick;
  }
  
  public boolean isMultiline() {
    return this.multiline;
  }

  public void setMultiline(boolean multi) {
    this.multiline = multi;
  }
}
