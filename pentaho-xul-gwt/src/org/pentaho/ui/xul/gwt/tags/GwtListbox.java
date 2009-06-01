package org.pentaho.ui.xul.gwt.tags;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.pentaho.gwt.widgets.client.utils.StringUtils;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.containers.XulListbox;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.gwt.binding.GwtBindingContext;
import org.pentaho.ui.xul.gwt.binding.GwtBindingMethod;
import org.pentaho.ui.xul.util.Orient;

import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class GwtListbox extends AbstractGwtXulContainer implements XulListbox, ChangeListener {

  static final String ELEMENT_NAME = "listbox"; //$NON-NLS-1$
  private int selectedIndex = -1;

  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtListbox();
      }
    });
  }

  private Collection boundElements;
  
  private ListBox listBox;

  private boolean disabled = false;

  private String selType;

  private int rowsToDisplay = 0;

  private String onselect;

  private String binding;
  
  private XulDomContainer container;

  public GwtListbox() {
    super(ELEMENT_NAME);
    managedObject = listBox = new ListBox();
    listBox.addChangeListener(this);
  }

  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    this.container = container;
    setBinding(srcEle.getAttribute("pen:binding")); //$NON-NLS-1$
    setRows(2);
    if (srcEle.hasAttribute("rows") && srcEle.getAttribute("rows").trim().length() > 0) { //$NON-NLS-1$ //$NON-NLS-2$
      try {
        setRows(Integer.parseInt(srcEle.getAttribute("rows"))); //$NON-NLS-1$
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    setOnselect(srcEle.getAttribute("onselect"));
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(boolean disabled) {
    this.listBox.setEnabled(!disabled);
  }

  public int getRows() {
    return rowsToDisplay;
  }

  public void setRows(int rowsToDisplay) {
    this.rowsToDisplay = rowsToDisplay;
    this.listBox.setVisibleItemCount(rowsToDisplay);
  }

  public String getSeltype() {
    return selType;
  }

  public void setSeltype(String selType) {
    this.selType = selType;

  }

  public Orient getOrientation() {
    return null;
  }

  public void layout() {
    // super.layout();
    listBox.clear();
    List<XulComponent> children = getChildNodes();
    for (int i = 0; i < children.size(); i++) {

      if (children.get(i) instanceof GwtListitem) {
        GwtListitem item = (GwtListitem) children.get(i);
        listBox.addItem(item.getLabel(), "" + i);
      }
    }
  }

  public String getOnselect() {
    return onselect;
  }

  public void setOnselect(String onchange) {
    this.onselect = onchange;
  }

  public void onChange(Widget sender) {
    Document doc = getDocument();
    Element rootElement = doc.getRootElement();
    XulWindow window = (XulWindow) rootElement;
    try {
      if(onselect != null && onselect.length() > 0) {
        this.getXulDomContainer().invoke(onselect, new Object[] {new Integer(listBox.getSelectedIndex())});
      }
    } catch (XulException e) {
      e.printStackTrace();
    }
    
    this.setSelectedIndex(listBox.getSelectedIndex());
    
  }

  public Object getSelectedItem() {
    return getItem(listBox.getSelectedIndex());
  }

  private Object getItem(int index) {
    return getChildNodes().get(Integer.parseInt(listBox.getValue(index)));
  }

  public Object[] getSelectedItems() {
    List<Object> items = new ArrayList<Object>();
    for (int i = 0; i < listBox.getItemCount(); i++) {
      if (listBox.isItemSelected(i)) {
        items.add(getItem(i));
      }
    }
    Object[] objs = new Object[items.size()];
    for (int i = 0; i < items.size(); i++) {
      objs[i] = items.get(i);
    }
    return objs;
  }

  private int getItemIndex(Object item) {
    GwtListitem listitem = (GwtListitem) item;
    for (int i = 0; i < listBox.getItemCount(); i++) {
      GwtListitem currItem = (GwtListitem) getItem(i);
      if (currItem.getLabel().equals(listitem.getLabel())) {
        return i;
      }
    }
    return -1;
  }

  public void setSelectedItem(Object item) {
    listBox.setSelectedIndex(getItemIndex(item));
  }

  public void setSelectedItems(Object[] items) {

    for (int i = 0; i < listBox.getItemCount(); i++) {
      GwtListitem currItem = (GwtListitem) getItem(i);
      boolean selected = false;
      for (int j = 0; j < items.length && !selected; j++) {
        GwtListitem item = (GwtListitem) items[j];
        if (currItem.getLabel().equals(item.getLabel())) {
          selected = true;
        }
      }
      listBox.setItemSelected(i, selected);
    }
  }

  public void addItem(Object item) {
    // these need to stay in sync with the dom!
    throw new UnsupportedOperationException();
  }

  public void removeItems() {
    throw new UnsupportedOperationException();
  }

  public int getRowCount() {
    return listBox.getItemCount();
  }

  public int getSelectedIndex() {
    return listBox.getSelectedIndex();
  }

  public int[] getSelectedIndices() {
    List<Integer> selectedIndices = new ArrayList<Integer>();
    for (int i = 0; i < listBox.getItemCount(); i++) {
      if (listBox.isItemSelected(i)) {
        selectedIndices.add(new Integer(i));
      }
    }
    int indices[] = new int[selectedIndices.size()];
    for (int i = 0; i < selectedIndices.size(); i++) {
      indices[i] = selectedIndices.get(i).intValue();
    }
    return indices;
  }

  public void setDisabled(String dis) {
    this.disabled = Boolean.parseBoolean(dis);
  }

  public <T> Collection<T> getElements() {
    return (Collection<T>) boundElements;
  }

  public <T> void setElements(Collection<T> elements) {
    boundElements = elements;

    for (XulComponent child:this.getChildNodes()) {
      this.removeChild(child);
    }
    for (T t : elements) {
      String attribute = getBinding();
      if (attribute != null && attribute.length() > 0) {
        try {
          GwtListitem item = (GwtListitem) container.getDocumentRoot().createElement("listitem");
          item.setLabel(extractLabel(t));
          this.addChild(item);
        } catch(XulException e) {
          
        }
      }
    }

    layout();
    if(elements.size() > 0) {
      setSelectedIndex(0);
    }
  }

  public void setSelectedIndex(int index) {
    int oldValue = this.selectedIndex;
    this.selectedIndex = index;
    this.listBox.setSelectedIndex(index);
    
    this.firePropertyChange("selectedIndex", oldValue, index);
    
  }

  public void setSelectedIndices(int[] indices) {

    // TODO Auto-generated method stub 

  }

  public void adoptAttributes(XulComponent component) {

    if (component.getAttributeValue("selectedindex") != null) {//$NON-NLS-1$
      setSelectedIndex(Integer.parseInt(component.getAttributeValue("selectedindex")));//$NON-NLS-1$
    }
  }

  public String getBinding() {
    return binding;
  }

  public void setBinding(String binding) {
    this.binding = binding;
  }

  private <T> String extractLabel(T t) {
    String attribute = getBinding();
    if (StringUtils.isEmpty(attribute)) {
      return t.toString();
    } else {
      try {
        GwtBindingMethod m = GwtBindingContext.typeController.findGetMethod(t, attribute);
        if(m == null){
          System.out.println("could not find getter method for "+t+"."+attribute+"; returning toString"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
          return t.toString();
          
        }
        return m.invoke(t, new Object[]{}).toString();
        
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
  
  

}
