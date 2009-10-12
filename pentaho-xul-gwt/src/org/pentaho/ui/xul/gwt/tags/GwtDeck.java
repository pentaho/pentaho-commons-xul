package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulDeck;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.gwt.util.Resizable;
import org.pentaho.ui.xul.util.Orient;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.ResizableWidget;

public class GwtDeck extends AbstractGwtXulContainer implements XulDeck {

  static final String ELEMENT_NAME = "deck"; //$NON-NLS-1$
  
  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtDeck();
      }
    });
  }
  
  protected DeckPanel container;
  int selectedIndex = 0;
  public GwtDeck() {
    this(Orient.HORIZONTAL);
  }

  public GwtDeck(Orient orient) {
    super(ELEMENT_NAME);
    container = new DeckPanel();
    setManagedObject(container);
  }
  
  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    if (srcEle.hasAttribute("selectedIndex") && srcEle.getAttribute("selectedIndex").trim().length() > 0) {
      try {
        setSelectedIndex(Integer.parseInt(srcEle.getAttribute("selectedIndex")));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  
  
  @Override
  public void addChild(Element ele) {
     super.addChild(ele);
     this.container.add((Widget) ((XulComponent)ele).getManagedObject());
     
     // sync with selectedIndex
     if (this.container.getVisibleWidget() != selectedIndex 
         && selectedIndex < container.getWidgetCount()) {
       container.showWidget(selectedIndex);
     }
  }
  
  @Override
  public void removeChild(Element ele) {
    super.removeChild(ele);
    this.container.remove((Widget) ((XulComponent)ele).getManagedObject());
    
    // sync with selectedIndex
    if (this.container.getVisibleWidget() != selectedIndex 
        && selectedIndex < container.getWidgetCount()) {
      container.showWidget(selectedIndex);
    }    
    
  }

  public int getSelectedIndex() {
    return container.getVisibleWidget();
  }

  public void setSelectedIndex(int index) {
    int previousVal = selectedIndex;
    if (index < container.getWidgetCount() && index >= 0) {
      container.showWidget(index);

      Widget card = container.getWidget(index);
      notifyOnShow(this);
    }
    
    selectedIndex = index;
    this.firePropertyChange("selectedIndex", previousVal, index);
  }
  
  /**
   * Child XUL elements need to be notified of visibility changes if they implement Resizable.
   * 
   * This method is recursive.
   * 
   * @param ele
   */
  private void notifyOnShow(Element ele){
    
    
    if(ele instanceof Resizable){
      ((Resizable) ele).onResize();
    }
    for(Element e : ele.getChildNodes()){
      notifyOnShow(e);
    }
  }

  public void layout() {
    
  }
  
  
  public void adoptAttributes(XulComponent component) {

    if(component.getAttributeValue("selectedindex") != null){
      setSelectedIndex(Integer.parseInt(component.getAttributeValue("selectedindex")));
    }
  }


}
