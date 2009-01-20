package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.containers.XulDialog;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.util.Orient;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class GwtDialog extends AbstractGwtXulContainer implements XulDialog {

  public static void register() {
    GwtXulParser.registerHandler("dialog", 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtDialog();
      }
    });
  }
  
  DialogBox dialog = null;
  private XulDomContainer xulContainer;
  
  public GwtDialog() {
    super("dialog");
    this.orientation = Orient.VERTICAL;
  }
  
  // we don't add ourselves to the main screen
  public void layout() {}
  
  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    this.xulContainer = container;
    setButtons(srcEle.getAttribute("buttons"));
    setOndialogaccept(srcEle.getAttribute("ondialogaccept"));
    setOndialogcancel(srcEle.getAttribute("ondialogcancel"));
    setOndialogextra1(srcEle.getAttribute("ondialogextra1"));
    setOndialogextra2(srcEle.getAttribute("ondialogextra2"));

    setButtonlabelaccept(srcEle.getAttribute("buttonlabelaccept"));
    setButtonlabelcancel(srcEle.getAttribute("buttonlabelcancel"));
    setButtonlabelextra1(srcEle.getAttribute("buttonlabelextra1"));
    setButtonlabelextra2(srcEle.getAttribute("buttonlabelextra2"));    
    
    setButtonalign(srcEle.getAttribute("buttonalign"));
    setTitle(srcEle.getAttribute("title"));
    setOnclose(srcEle.getAttribute("onclose"));
    setOnload(srcEle.getAttribute("onload"));
    setOnunload(srcEle.getAttribute("onunload"));
  }

  public String getButtonalign() {
    return getAttributeValue("buttonalign");
  }

  public String getButtonlabelaccept() {
    return getAttributeValue("buttonlabelaccept");
  }

  public String getButtonlabelcancel() {
    return getAttributeValue("buttonlabelcancel");
  }

  public String getButtonlabelextra1() {
    return getAttributeValue("buttonlabelextra1");
  }

  public String getButtonlabelextra2() {
    return getAttributeValue("buttonlabelextra2");
  }

  public String getButtons() {
    return getAttributeValue("buttons");
  }

  public String getOndialogaccept() {
    return getAttributeValue("ondialogaccept");
  }

  public String getOndialogcancel() {
    return getAttributeValue("ondialogcancel");
  }

  public String getOndialogextra1() {
    return getAttributeValue("ondialogextra1");
  }

  public String getOndialogextra2() {
    return getAttributeValue("ondialogextra2");
  }

  public void hide() {
    if (dialog != null) {
      dialog.hide();
    }
  }

  public boolean isHidden() {
    return dialog == null || !dialog.isVisible();
  }

  public void setButtonalign(String align) {
    this.setAttribute("buttonalign", align);
  }

  public void setButtonlabelaccept(String label) {
    this.setAttribute("buttonlabelaccept", label);
  }

  public void setButtonlabelcancel(String label) {
    this.setAttribute("buttonlabelcancel", label);
  }

  public void setButtonlabelextra1(String label) {
    this.setAttribute("buttonlabelextra1", label);
  }

  public void setButtonlabelextra2(String label) {
    this.setAttribute("buttonlabelextra2", label);
  }

  public void setButtons(String buttons) {
    this.setAttribute("buttons", buttons);
  }

  public void setOndialogaccept(String command) {
    this.setAttribute("ondialogaccept", command);
  }

  public void setOndialogcancel(String command) {
    this.setAttribute("ondialogcancel", command);
  }

  public void setOndialogextra1(String command) {
    this.setAttribute("ondialogextra1", command);
  }

  public void setOndialogextra2(String command) {
    this.setAttribute("ondialogextra2", command);
  }

  public void setVisible(boolean visible) {
    if (visible) {
      show();
    } else {
      hide();
    }
  }

  public void show() {

    if (dialog != null) {
      // create a new dialog if necessary
      dialog.show();
      return;
    }
    
    
    dialog = new DialogBox();
    dialog.setWidth(getWidth()+"px");
    dialog.setHeight(getHeight()+"px");
    
    dialog.setTitle(getTitle());
    dialog.setText(getTitle());
    // implement the buttons
    VerticalPanel panel = new VerticalPanel();
    SimplePanel wrapper = new SimplePanel();
    
    VerticalPanel contentPanel = new VerticalPanel();
    contentPanel.setStyleName("dialog-content");
    contentPanel.setWidth("100%");
    wrapper.add(contentPanel);
    panel.add(wrapper);
    panel.setCellWidth(contentPanel, "100%");
    panel.setStyleName("dialog");
    
    
    String buttons = getButtons();
    HorizontalPanel buttonPanel = null;
    if (buttons != null && buttons.trim().length() > 0) {
      buttonPanel = new HorizontalPanel();
      
      String buttonStr[] = buttons.split(",");
      for (String button : buttonStr) {
        final String buttonVal = button.trim();
        
        try{
          XulButton buttonObj = (XulButton) this.xulContainer.getDocumentRoot().createElement("button");
          buttonObj.setLabel(getAttributeValue("buttonlabel" + buttonVal));
          
          buttonObj.setOnclick(getAttributeValue("ondialog" + buttonVal));
          buttonPanel.add((Widget)buttonObj.getManagedObject());
        } catch(XulException e){
          System.out.println("Error creating button: "+e.getMessage());
          e.printStackTrace();
        }
        
        
      }
    }
    
    if (buttonPanel != null) {
      //wrap buttonPanel in another one to style top border
      HorizontalPanel buttonPanelWrapper = new HorizontalPanel();
      
      buttonPanelWrapper.setStyleName("dialog-button-panel");
      buttonPanelWrapper.add(buttonPanel);
      buttonPanelWrapper.setWidth("100%");
      panel.add(buttonPanelWrapper);
      panel.setCellWidth(buttonPanelWrapper, "100%");
      String buttonalign = getButtonalign();
      if ("center".equals(buttonalign)) {
        buttonPanelWrapper.setCellHorizontalAlignment(buttonPanel, HorizontalPanel.ALIGN_CENTER);
      } else if ("left".equals(buttonalign)) {
        buttonPanelWrapper.setCellHorizontalAlignment(buttonPanel, HorizontalPanel.ALIGN_LEFT);
      } else if ("right".equals(buttonalign)) {
        buttonPanelWrapper.setCellHorizontalAlignment(buttonPanel, HorizontalPanel.ALIGN_RIGHT);
      }
      
    }
    
    // render dialog contents
    container = contentPanel;

    super.layout();
    panel.setSpacing(1);
    panel.setHeight("100%");
    panel.setWidth("100%");
    dialog.add(panel);
//    dialog.setWidth("100px");
//    dialog.setHeight("100px");
    
    // display dialog
    dialog.center();
    dialog.show();
    
  }

  public String getOnclose() {
    return getAttributeValue("onclose");
  }

  public String getOnload() {
    return getAttributeValue("onload");
  }

  public String getOnunload() {
    return getAttributeValue("onunload");
  }

  public Object getRootObject() {
    return dialog;
  }

  public String getTitle() {
    return getAttributeValue("title");
  }

  public void invokeLater(Runnable runnable) {
    // TODO Auto-generated method stub
    
  }

  public void setOnclose(String onclose) {
    this.setAttribute("onclose", onclose);
  }

  public void setOnload(String onload) {
    this.setAttribute("onload", onload);
  }

  public void setOnunload(String onunload) {
    this.setAttribute("onunload", onunload);
  }

  public void setTitle(String title) {
    this.setAttribute("title", title);
    
  }

  public void adoptAttributes(XulComponent component) {
    // TODO Auto-generated method stub
    
  }
  
}
