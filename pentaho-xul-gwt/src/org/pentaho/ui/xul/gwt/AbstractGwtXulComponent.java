/**
 * 
 */
package org.pentaho.ui.xul.gwt;

import java.beans.PropertyChangeListener;
import java.util.List;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulEventSourceAdapter;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.gwt.tags.GwtScript;
import org.pentaho.ui.xul.util.Orient;

import com.google.gwt.user.client.ui.Panel;
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
    double totalFlex = 0.0;
    
    for(XulComponent comp : this.getChildNodes()) {
      
      if (comp instanceof GwtScript) {
        GwtScript script = (GwtScript) comp;
        try{
          this.xulDomContainer.addEventHandler(script.getId(), script.getSrc());
        } catch (XulException e) {
          e.printStackTrace();
        }
      }
      
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
    for(int i=0; i<children.size(); i++){
      XulComponent comp = nodes.get(i);
    
      Object maybeComponent = comp.getManagedObject();
      if(maybeComponent == null || !(maybeComponent instanceof Widget)){
        continue;
      }
      Widget component = (Widget) maybeComponent;
      if(component != null){
        container.add(component);
      }
//      if(this.getOrientation() == Orient.VERTICAL){ //VBox and such
//        //if (comp.getFlex() > 0) {
//          System.out.println("Setting height for " + comp.getId() + " to : " + Math.round(comp.getFlex() * 100 / totalFlex) + "%");
//          
//          if (container instanceof CellPanel) {
//            //((CellPanel)container).setCellHeight(component, "" + Math.round(comp.getFlex() * 100 / totalFlex) + "%");
//            ((CellPanel)container).setCellWidth(component, "100%");
//            //component.setHeight("100%");
//            component.setWidth("100%");
//          } else {
//            //component.setHeight("" + Math.round(comp.getFlex() * 100 / totalFlex) + "%");
//            component.setWidth("100%");
//          }
//        //}
//      } else {
//        //if (comp.getFlex() > 0) {
//          System.out.println("Setting width for " + comp.getId() + " to : " + Math.round(comp.getFlex() * 100 / totalFlex) + "%");
//          if (container instanceof CellPanel) {
//            //((CellPanel)container).setCellWidth(component, "" + Math.round(comp.getFlex() * 100 / totalFlex) + "%");
//            ((CellPanel)container).setCellHeight(component, "100%");
//            component.setHeight("100%");
//            //component.setWidth("100%");
//          } else {
//            //component.setWidth("" + Math.round(comp.getFlex() * 100 / totalFlex) + "%");
//            component.setHeight("100%");
//          }
        //}
//        gc.gridwidth = comp.getFlex()+1;
//        gc.gridheight = GridBagConstraints.REMAINDER;
//        gc.weightx = (totalFlex == 0)? 0 : (comp.getFlex()/totalFlex);
//      }
      
      
       // , gc);

//      if(i+1 == children.size() && !flexLayout){
//
//        if(this.getOrientation() == Orient.VERTICAL){ //VBox and such
//          gc.weighty = 1.0;
//        } else {
//          gc.weightx = 1.0;
//        }
//        
//        container.add(Box.createGlue(), gc);
//      }
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
  
  public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
    xulEventSourceAdapter.addPropertyChangeListener(propertyName, listener);
  }
}
