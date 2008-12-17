/**
 * 
 */
package org.pentaho.ui.xul.gwt;

import java.beans.PropertyChangeListener;
import java.util.List;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulEventSourceAdapter;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.gwt.tags.GwtScript;
import org.pentaho.ui.xul.util.Align;
import org.pentaho.ui.xul.util.Orient;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author OEM
 *
 */
public abstract class AbstractGwtXulComponent extends GwtDomElement implements XulComponent {

  protected XulDomContainer xulDomContainer;
  protected Panel container;
  protected Orient orientation;
  protected Object managedObject;
  protected int flex = 0;
  protected String id;
  protected boolean flexLayout = false;
  protected String insertbefore, insertafter;
  protected int position = -1;
  
  protected String bgcolor, onblur, tooltiptext;
  protected int height, width, padding;
  protected boolean disabled, removeElement;
  private XulEventSourceAdapter xulEventSourceAdapter = new XulEventSourceAdapter();

  
  public AbstractGwtXulComponent(String name) {
    super(name);
  }

//  public AbstractGwtXulComponent(String tagName, Object managedObject) {
//    super(tagName);
//    this.managedObject = managedObject;
//    children = new ArrayList<XulComponent>();
//  }
  
  public void init(com.google.gwt.xml.client.Element srcEle) {
    if (srcEle.hasAttribute("id")) {
      setId(srcEle.getAttribute("id"));
    }
    
    if (srcEle.hasAttribute("orient") && srcEle.getAttribute("orient").trim().length() > 0) {
      // TODO: setOrient should live in an interface somewhere???
      setOrient(srcEle.getAttribute("orient"));
    }
    
    if (srcEle.hasAttribute("flex") && srcEle.getAttribute("flex").trim().length() > 0) {
      try {
        setFlex(Integer.parseInt(srcEle.getAttribute("flex")));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    
    if (hasAttribute(srcEle, "width")) {
      try {
        setWidth(Integer.parseInt(srcEle.getAttribute("width")));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (hasAttribute(srcEle,"height")) {
      try {
        setHeight(Integer.parseInt(srcEle.getAttribute("height")));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    if (hasAttribute(srcEle,"position")) {
      try {
        setPosition(Integer.parseInt(srcEle.getAttribute("position")));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    
    if (srcEle.hasAttribute("insertbefore") && srcEle.getAttribute("insertbefore").trim().length() > 0) {
      setInsertbefore(srcEle.getAttribute("insertbefore"));
    }

    if (srcEle.hasAttribute("insertafter") && srcEle.getAttribute("insertafter").trim().length() > 0) {
      setInsertafter(srcEle.getAttribute("insertafter"));
    }
    if (srcEle.hasAttribute("removeelement") && srcEle.getAttribute("removeelement").trim().length() > 0) {
      setRemoveelement("true".equals(srcEle.getAttribute("removeelement")));
    }    
    
  }
  
  private boolean hasAttribute(com.google.gwt.xml.client.Element ele, String attr){
    return (ele.hasAttribute(attr) && ele.getAttribute(attr).trim().length() > 0);
  }
  
  public void setXulDomContainer(XulDomContainer xulDomContainer) {
    this.xulDomContainer = xulDomContainer;
  }

  public XulDomContainer getXulDomContainer() {
    return xulDomContainer;
  }
  
  public void layout(){
    if(this instanceof XulContainer == false){
      //Core version of parser doesn't call layout unless the node is a container...
      return;
    }
    double totalFlex = 0.0;
    
    for(XulComponent comp : this.getChildNodes()) {
      
      if(comp.getManagedObject() == null){
        continue;
      }
      if(comp.getFlex() > 0){
        flexLayout = true;
        totalFlex += comp.getFlex();
      }
    }
    
//    if(flexLayout)
//      gc.fill = GridBagConstraints.BOTH;

    System.out.println("ORIENTATION of " + getId() + " (" + getName() + ") : " + this.getOrientation());
    
    List<XulComponent> nodes = this.getChildNodes();
    

    XulContainer thisContainer = (XulContainer) this;
    

    if(!flexLayout && thisContainer.getAlign() != null){
      SimplePanel fillerPanel = new SimplePanel();
      switch(thisContainer.getAlign()){
        case END:
          container.add(fillerPanel);
          if (this.getOrientation() == Orient.VERTICAL) { //VBox and such
            ((VerticalPanel) container).setCellHeight(fillerPanel, "100%");
          } else {
            ((HorizontalPanel) container).setCellWidth(fillerPanel, "100%");
          }
          break;
        case CENTER:
          container.add(fillerPanel);
          
          if (this.getOrientation() == Orient.VERTICAL) { //VBox and such
            ((VerticalPanel) container).setCellHeight(fillerPanel, "50%");
          } else {
            ((HorizontalPanel) container).setCellWidth(fillerPanel, "50%");
          }
          break;
      } 
    }
    
    for(int i=0; i<children.size(); i++){
      XulComponent comp = nodes.get(i);
    
      Object wrappedWidget = comp.getManagedObject();
      if(wrappedWidget == null || !(wrappedWidget instanceof Widget)){
        continue;
      }
      Widget component = (Widget) wrappedWidget;
      if(component != null){
        container.add(component);
      }
      if(flexLayout){
        component.setHeight("100%");
        component.setWidth("100%");
        
        int componentFlex = comp.getFlex();
        if(componentFlex == 0){
          continue;
        }
        String percentage = Math.round((componentFlex/totalFlex) *100)+"%";
        if(this.getOrientation() == Orient.VERTICAL){ //VBox
          ((VerticalPanel) container).setCellHeight(component, percentage);
        } else {                                      //HBox 
          ((HorizontalPanel) container).setCellWidth(component, percentage);
        }
      } else {
        if(this.getOrientation() == Orient.VERTICAL){ //VBox
          component.setWidth("100%");
        } else {                                      //HBox 
          component.setHeight("100%"); 
        }
      }
      
      if (i + 1 == children.size() && !flexLayout) {
        
      }
    }
    if(!flexLayout && thisContainer.getAlign() != null){
      SimplePanel fillerPanel = new SimplePanel();
      switch(thisContainer.getAlign()){
        case START:
          container.add(fillerPanel);
          if (this.getOrientation() == Orient.VERTICAL) { //VBox and such
            ((VerticalPanel) container).setCellHeight(fillerPanel, "100%");
          } else {
            ((HorizontalPanel) container).setCellWidth(fillerPanel, "100%");
          }
          break;
        case CENTER:
          container.add(fillerPanel);
          
          if (this.getOrientation() == Orient.VERTICAL) { //VBox and such
            ((VerticalPanel) container).setCellHeight(fillerPanel, "50%");
          } else {
            ((HorizontalPanel) container).setCellWidth(fillerPanel, "50%");
          }
          break;
        case END:
         break;
      } 
    }
    
  }
  
  public Orient getOrientation(){
    return this.orientation;
  }
  
  public void setOrient(String orientation){
    this.orientation = Orient.valueOf(orientation.toUpperCase());
  }
  
  public String getOrient(){
    return orientation.toString();
  }
  
  public Object getManagedObject() {
    return managedObject;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.setAttribute("id", id);
    this.id = id;
  }

  public int getFlex() {
    return flex;
  }

  public void setFlex(int flex) {
    this.flex = flex;
  }

  public void addComponent(XulComponent c) {
    throw new UnsupportedOperationException("addComponent not supported");
  }

  public String getBgcolor() {
    return this.bgcolor;
  }

  public int getHeight() {
    return height;
  }

  public String getID() {
   return this.id;   
  }

  public String getOnblur() {
    return onblur;  
  }

  public int getPadding() {
    return padding;
  }

  public String getTooltiptext() {
    return tooltiptext;
  }

  public int getWidth() {
    return width;  
  }

  public boolean isDisabled() {
   return disabled;   
  }

  public void setBgcolor(String bgcolor) {
    this.bgcolor = bgcolor;
  }

  public void setDisabled(boolean disabled) {
   this.disabled = disabled;   
  }

  public void setHeight(int height) {
    this.height = height;  
  }

  public void setID(String id) {
    this.id = id;  
  }

  public void setOnblur(String method) {
    this.onblur = method;  
  }

  public void setPadding(int padding) {
    this.padding = padding;
  }

  public void setTooltiptext(String tooltip) {
    this.tooltiptext = tooltip;
  }

  public void setWidth(int width) {
    this.width = width;  
  }
  


  public void addComponentAt(XulComponent component, int idx) {
    throw new UnsupportedOperationException("addComponent not supported");
  }

  public void removeComponent(XulComponent component) {
    
        // TODO Auto-generated method stub 
      
  }

  public String getInsertbefore() {
  
    return insertbefore;
  }

  public void setInsertbefore(String insertbefore) {
  
    this.insertbefore = insertbefore;
  }

  public String getInsertafter() {
  
    return insertafter;
  }

  public void setInsertafter(String insertafter) {
  
    this.insertafter = insertafter;
  }

  public int getPosition() {
  
    return position;
  }

  public void setPosition(int position) {
  
    this.position = position;
  }

  public boolean getRemoveelement() {
    return removeElement;
  }

  public void setRemoveelement(boolean flag) {
    this.removeElement = flag;
  }
  

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    xulEventSourceAdapter.addPropertyChangeListener(listener);
    
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
    xulEventSourceAdapter.removePropertyChangeListener(listener);
    
  }
  
}
