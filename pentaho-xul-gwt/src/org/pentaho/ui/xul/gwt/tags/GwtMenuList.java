package org.pentaho.ui.xul.gwt.tags;

import java.beans.Expression;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.pentaho.gwt.widgets.client.utils.StringUtils;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulMenuList;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.gwt.binding.GwtBindingContext;
import org.pentaho.ui.xul.gwt.binding.GwtBindingMethod;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class GwtMenuList<T> extends AbstractGwtXulContainer implements XulMenuList<T> {

  static final String ELEMENT_NAME = "menulist"; //$NON-NLS-1$
  
  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtMenuList();
      }
    });
  }
  
  private Label label;
  private ListBox listbox;
  private String bindingProperty;
  private boolean loaded = false;
  private boolean suppressLayout = false;
  private boolean inLayoutProcess = false;
  private String previousSelectedItem = null;
  
  public GwtMenuList() {
    super(ELEMENT_NAME);
    managedObject = listbox = new ListBox();
    
    listbox.addChangeListener(new ChangeListener(){

      public void onChange(Widget arg0) {

        /*
         * This actionlistener is fired at parse time when elements are added.
         * We'll ignore that call by checking a variable set to true post parse time
         */
        if (!loaded) {
          return;
        }
        fireSelectedEvents();
      }
      
    });  
  }
  
  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    setBinding(srcEle.getAttribute("pen:binding"));
  }
  
  public String getBinding() {
    return bindingProperty;
  }

  public Collection getElements() {
    List<String> vals = new ArrayList<String>();
    for(int i=0; i<listbox.getItemCount(); i++){
      vals.add(listbox.getItemText(i));
    }
    return vals;
  }

  public int getSelectedIndex() {
    return listbox.getSelectedIndex();  
  }

  public T getSelectedItem() {
    return (T) listbox.getItemText(listbox.getSelectedIndex());
  }

  public void replaceAllItems(Collection tees) throws XulDomException {
    throw new RuntimeException("Not implemented");  
  }

  public void setBinding(String binding) {
    this.bindingProperty = binding;
  }

  public void setElements(Collection<T> elements) {

    try{
      suppressLayout = true;
      XulMenupopup popup = getPopupElement();
      for (XulComponent menuItem : popup.getChildNodes()) {
        popup.removeChild(menuItem);
      }
      if(elements == null || elements.size() == 0){
        this.suppressLayout = false;
        layout();
        return;
      }
      for (T t : elements) {
        GwtMenuitem item = new GwtMenuitem(popup);
  
        String attribute = getBinding();
        if (!StringUtils.isEmpty(attribute)) {
          item.setLabel(extractLabel(t));
        }
  
        popup.addChild(item);
      }
  
      this.suppressLayout = false;
      layout();
    } catch(Exception e){
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
      
  }

  public GwtMenupopup getPopupElement() {
    for (Element comp : getChildNodes()) {
      if (comp instanceof GwtMenupopup) {
        return (GwtMenupopup) comp;
      }
    }
    throw new IllegalStateException("menulist is missing a menupopup child element");
  }
  
  public void setOncommand(final String command) {
    this.listbox.addChangeListener(new ChangeListener(){

      public void onChange(Widget arg0) {

        /*
         * This actionlistener is fired at parse time when elements are added.
         * We'll ignore that call by checking a variable set to true post parse time
         */
        if (!loaded) {
          return;
        }
        Document doc = getDocument();
        XulRoot window = (XulRoot) doc.getRootElement();
        final XulDomContainer con = window.getXulDomContainer();
        try{
          con.invoke(command, new Object[] {});
        } catch(XulException e){
          Window.alert("MenuList onChange error: "+e.getMessage());
        }
        fireSelectedEvents();
      }
      
    });  
  }

  public void setSelectedIndex(int idx) {
    listbox.setSelectedIndex(idx);
  }

  public void setSelectedItem(T t) {
    //this is coming in as a string for us.
//    int i=0;
//    for(Object o : getElements()){
//      if(((String) o).equals(extractLabel(t))){
//        setSelectedIndex(i);
//      }
//      i++;
//    }
    int i=0;
    for(Object o : getElements()){
      if(((String) o).equals((String)t)){
        setSelectedIndex(i);
      }
      i++;
    }
  }

  private String extractLabel(T t) {
    String attribute = getBinding();
    if (StringUtils.isEmpty(attribute)) {
      return t.toString();
    } else {
      try {
        GwtBindingMethod m = GwtBindingContext.typeController.findGetMethod(t, attribute);
        if(m == null){
          System.out.println("could not find getter method for "+t+"."+attribute);
        }
        return m.invoke(t, new Object[]{}).toString();
        
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }


  public void adoptAttributes(XulComponent component) {
      
  }


  private void fireSelectedEvents(){

    GwtMenuList.this.changeSupport.firePropertyChange("selectedItem", previousSelectedItem, (String) getSelectedItem());
    GwtMenuList.this.changeSupport.firePropertyChange("selectedIndex", null, getSelectedIndex());
      previousSelectedItem = (String) getSelectedItem();
  }

  @Override
  public void layout() {
    inLayoutProcess = true;
    if(suppressLayout){
      inLayoutProcess = false;
      return;
    }
    GwtMenupopup popup = getPopupElement();
    this.listbox.clear();

    GwtMenuitem selectedItem = null;

    System.out.println("Popup children size: "+popup.getChildNodes().size());
    
    //capture first child as default selection
    boolean firstChild = true;
    for (XulComponent item : popup.getChildNodes()) {
      GwtMenuitem tempItem = ((GwtMenuitem) item);
      listbox.addItem(tempItem.getLabel());
      if (tempItem.isSelected() || firstChild) {
        selectedItem = tempItem;
        firstChild = false;
      }
    }
    
    inLayoutProcess = false;

    if (selectedItem != null) {
      //if first setting it to the currently selected one will not fire event.
      //manually firing here
      if(this.getSelectedItem().equals(selectedItem.getLabel())){
        fireSelectedEvents();
      }
      setSelectedItem((T) selectedItem.getLabel());
    }
    loaded = true;
  }

  @Override
  public void resetContainer(){
    this.layout();
  }
 
}
