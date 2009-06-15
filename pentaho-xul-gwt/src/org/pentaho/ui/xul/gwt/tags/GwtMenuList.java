package org.pentaho.ui.xul.gwt.tags;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.pentaho.gwt.widgets.client.listbox.CustomListBox;
import org.pentaho.gwt.widgets.client.listbox.ListItem;
import org.pentaho.gwt.widgets.client.utils.StringUtils;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.XulEventSource;
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
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class GwtMenuList<T> extends AbstractGwtXulContainer implements XulMenuList<T> {

  static final String ELEMENT_NAME = "menulist"; //$NON-NLS-1$
  private boolean editable;

  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtMenuList();
      }
    });
  }
  
  private Label label;
  private CustomListBox listbox;
  private String bindingProperty;
  private boolean loaded = false;
  private boolean suppressLayout = false;
  private boolean inLayoutProcess = false;
  private String previousSelectedItem = null;
  private String onCommand;
  private SimplePanel wrapper = null;
  public GwtMenuList() {
    super(ELEMENT_NAME);
    wrapper = new SimplePanel() {

      @Override
      public void setHeight(String height) {
      }

      @Override
      public void setWidth(String width) {
        super.setWidth(width);
        listbox.setWidth(width);
      }
      
    };
    listbox = new CustomListBox();
    wrapper.add(listbox);
    managedObject = wrapper;
    
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
  
  public CustomListBox getNativeListBox(){
    return this.listbox;
  }
  
  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    setBinding(srcEle.getAttribute("pen:binding"));

    setOnCommand(srcEle.getAttribute("oncommand"));
  }
  
  public String getBinding() {
    return bindingProperty;
  }

  public Collection getElements() {
    List<Object> vals = new ArrayList<Object>();
    for(int i=0; i<listbox.getItems().size(); i++){
      vals.add(listbox.getItems().get(i).getValue());
    }
    return vals;
  }

  public int getSelectedIndex() {
    return listbox.getSelectedIndex();
  }

  public String getSelectedItem() {
    ListItem selectedItem = listbox.getSelectedItem();
    
    return (selectedItem == null) ? null : listbox.getSelectedItem().getValue().toString();
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
      listbox.setSuppressLayout(true);
      
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
      
      listbox.setSuppressLayout(false);
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
    if (StringUtils.isEmpty(attribute) || !(t instanceof XulEventSource)) {
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

  private int selectedIndex = -1;
  private void fireSelectedEvents(){
    
    GwtMenuList.this.changeSupport.firePropertyChange("selectedItem", previousSelectedItem, (String) getSelectedItem());
    int prevSelectedIndex = selectedIndex;
    selectedIndex = getSelectedIndex();
    GwtMenuList.this.changeSupport.firePropertyChange("selectedIndex", prevSelectedIndex, selectedIndex);
    previousSelectedItem = (String) getSelectedItem();
      
      if(StringUtils.isEmpty(GwtMenuList.this.getOnCommand()) == false && prevSelectedIndex != selectedIndex){
        try {
          GwtMenuList.this.getXulDomContainer().invoke(GwtMenuList.this.getOnCommand(), new Object[] {});
        } catch (XulException e) {
          e.printStackTrace();
        }
      }
  }

  @Override
  public void layout() {

    inLayoutProcess = true;
    if(suppressLayout){
      inLayoutProcess = false;
      return;
    }
    int currentSelectedIndex = getSelectedIndex();
    Object currentSelectedItem = listbox.getSelectedItem();
    GwtMenupopup popup = getPopupElement();
    selectedIndex = -1;
    listbox.setSuppressLayout(true);
    this.listbox.removeAll();

    GwtMenuitem selectedItem = null;
    
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

    listbox.setSuppressLayout(false);
    inLayoutProcess = false;

    if (selectedItem != null) {
      //if first setting it to the currently selected one will not fire event.
      //manually firing here
      if(this.getSelectedItem().equals(selectedItem.getLabel())){
        fireSelectedEvents();
      }
      setSelectedItem((T) selectedItem.getLabel());
    }
    if(getSelectedIndex() > -1){
      if(currentSelectedIndex < listbox.getItems().size()) {
        int index = getIndexForItem(currentSelectedItem);
        if(index > 0) {
         listbox.setSelectedIndex(currentSelectedIndex);
        } else {
          listbox.setSelectedIndex(listbox.getSelectedIndex());
        }
      } else {
        listbox.setSelectedIndex(listbox.getSelectedIndex());
      }
    }
    loaded = true;
  }

  @Override
  public void resetContainer(){
    this.layout();
  }

  public String getOnCommand() {
  
    return onCommand;
  }

  public void setOnCommand(String command) {
  
    this.onCommand = command;
  }

  public void setEditable(boolean editable) {
    this.listbox.setEditable(editable);
    this.editable = editable;
  }

  public boolean getEditable() {
    return editable;
  }

  public String getValue() {
    return getSelectedItem();
  }

  public void setValue(String value) {
    for(ListItem item : listbox.getItems()){
      if(item.getValue().equals(value)){
        listbox.setSelectedItem(item);
        return;
      }
    }
    listbox.setValue(value);
  }
  
  private int getIndexForItem(Object obj) {
    int index = -1;
    if(obj != null){
      for(ListItem item:listbox.getItems()) {
        index++;
        if(item.getValue() != null && item.getValue().equals(obj.toString())) {
          return index;
        }
      }
    }
    return index;
  }
}
