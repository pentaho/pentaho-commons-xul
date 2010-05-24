package org.pentaho.ui.xul.swt.tags;

import java.beans.Expression;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.containers.XulListbox;
import org.pentaho.ui.xul.dnd.DropEffectType;
import org.pentaho.ui.xul.dnd.DropEvent;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;

public class SwtListbox extends AbstractSwtXulContainer implements XulListbox{
  private static final long serialVersionUID = 3064125049914932493L;
  private static final Log logger = LogFactory.getLog(SwtListbox.class);

  private List listBox;
  private boolean disabled = false;
  private String selType;
  private int rowsToDisplay = 0;
  String onSelect = null;
  private XulDomContainer container;

  private String binding;
  private Collection elements;
  private String command;

  private int[] curSelectedIndices = null;
  private int curSelectedIndex = -1;
  private Object prevSelectedObject;
  
  public SwtListbox(Element self, XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    this.container = container;
    
    int style = SWT.BORDER | SWT.V_SCROLL;
    if(self.getAttributeValue("seltype") != null && self.getAttributeValue("seltype").equals("multi")){
      style |= SWT.MULTI; 
    } else {
      style |= SWT.SINGLE;
    }
    listBox = new List((Composite)parent.getManagedObject(), style);
    
    listBox.addSelectionListener(new SelectionAdapter(){

      @Override
      public void widgetSelected(SelectionEvent arg0) {
        fireSelectedEvents();
      }
      
    });
    
    setManagedObject(listBox);
  }
  
  private void fireSelectedEvents(){

    int[] indices = listBox.getSelectionIndices();
    SwtListbox.this.changeSupport.firePropertyChange("selectedIndices", curSelectedIndices, indices);
    curSelectedIndices = indices;

    
    SwtListbox.this.changeSupport.firePropertyChange("selectedIndex", curSelectedIndex, getSelectedIndex());
    curSelectedIndex = getSelectedIndex();
    
    if(elements != null){
      Object newSelectedObject = getSelectedItem();
      SwtListbox.this.changeSupport.firePropertyChange("selectedItem", prevSelectedObject, newSelectedObject);
      prevSelectedObject = newSelectedObject;
      
      Object[] newSelectedObjectList = getSelectedItems();
      SwtListbox.this.changeSupport.firePropertyChange("selectedItems", null, newSelectedObjectList);
    }
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
    if (!listBox.isDisposed()) listBox.setEnabled( !disabled );
  }
  public void setDisabled(String dis) {
    this.disabled = Boolean.parseBoolean(dis);
    if (!listBox.isDisposed()) listBox.setEnabled( !disabled );
  }

  public int getRows() {
    return rowsToDisplay;
  }

  public void setRows(int rowsToDisplay) {
    this.rowsToDisplay = rowsToDisplay;
    if ((!listBox.isDisposed()) && (rowsToDisplay > 0)){
      int ht = rowsToDisplay * listBox.getItemHeight();

      //listBox.setSize(listBox.getSize().x,height);
      if (listBox.getLayoutData() != null){
        ((GridData)listBox.getLayoutData()).heightHint = ht;
        ((GridData)listBox.getLayoutData()).minimumHeight = ht;
      }
    }
  }

  public String getSeltype() {
    return selType;
  }

  /**
   * TODO: PARTIAL IMPL: Because this is needed on construction, 
   * we need to rework this class a bit to allow setting of 
   * multiple selection. 
   */
  public void setSeltype(String selType) {
    this.selType = selType;
    
  }

  public void addItem(Object item) {
    
    // SWT limitation - these can only be strings ...
    
    // We could still attempt to load the object using 
    // its toString() method, but then what you recieved
    // back from getItem() or getSelection() would 
    // be inconsistent with what you put into the list... 
    
    // TODO: Could possibly simulate a model 
    // by holding onto real objects and syncing them
    // with the listbox. 
    
    if (!(item instanceof String)){
      // log error... only strings supported...
    }
    listBox.add((String)item);
    
  }
  
  public void removeItems(){
    listBox.removeAll();
  }

  public String getOnselect() {
    return onSelect;
  }

  public void setOnselect(final String method) {
    onSelect = method;
    listBox.addSelectionListener(new SelectionAdapter(){
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent arg0){
        invoke(method);
      }
    });
  }

  public Object getSelectedItem() {
    if (listBox.getSelection()==null || 
        listBox.getSelectionCount()<=0){
      return null;
    }

    // If there's a bound collection return the model object.
    int selIdx = getSelectedIndex();
    if(elements != null && selIdx >= 0 && selIdx < elements.size()){
      return elements.toArray()[selIdx];
    }
    // otherwise return String value
    return listBox.getSelection()[0];
  }

  public Object[] getSelectedItems() {
    // If there's a bound collection return the model object.
    int[] selIndices = getSelectedIndices();
    if(elements != null && selIndices.length > 0){
      Object[] returnArray = new Object[selIndices.length];
      Object[] valueArray  = elements.toArray();
      for(int i=0;i<selIndices.length;i++) {
        returnArray[i] = valueArray[selIndices[i]];
      }
      return returnArray;
    }
    // otherwise return String value

    return listBox.getSelection();
  }
  
  public int getSelectedIndex(){
    return listBox.getSelectionIndex();
  }
  
  public int[] getSelectedIndices(){
    return listBox.getSelectionIndices();
  }

  public void setSelectedItem(Object item) {
    setSelectedItems(new Object[] {item});
  }

  public void setSelectedItems(Object[] items) {
    if(elements != null){
      // If bound collection get idx and use that.
      int[] indices = new int[items.length];
      int pos = 0;
      for(Object o : items){
        int index = getIndex(o);
        if(index >= 0) {
          indices[pos++] = index;  
        } else {
          indices[pos++] = -1;
        }
      }
      setSelectedIndices(indices);
    } else {
      for (Object object : items) {
        if (!(object instanceof String)){
          // TODO  log error... only strings supported...
        }
      }
      String[] sel = new String[items.length];
      System.arraycopy(items, 0, sel, 0, items.length);
      listBox.setSelection(sel);
      
      // SWT doesn't seem to fire this event when the selection
      // is made via code, only with a mouse or keyboard action.

      listBox.notifyListeners(SWT.Selection, new Event());
      
    }
  }

  private int getIndex(Object o) {
    Object[] elementArray = elements.toArray();
    for(int i=0;i<elementArray.length;i++) {
      if(elementArray[i] == o) {
        return i;
      }
    }
    return -1;
  }
  public int getRowCount() {
    return (!listBox.isDisposed()) ? listBox.getItemCount() : 0;
  }

  public void setSelectedIndex(int index) {
    if(index > listBox.getItemCount()){
      return;
    }
    if (listBox.isDisposed()){
      // TODO log error .. 
    }
    listBox.setSelection(index);
  }
  
  public void setSelectedindex(String index) {
    if (listBox.isDisposed()){
      // TODO log error .. 
    }
    listBox.setSelection(Integer.parseInt(index));
  }

  public <T> Collection<T> getElements() {
    return this.elements;
      
  }

  public <T> void setElements(Collection<T> elements) {
    if(elements != null) {
      logger.info("SetElements on listbox called: collection has "+elements.size()+" rows");
      
      this.elements = elements;
      this.prevSelectedObject = null;
      this.curSelectedIndex = -1;
      this.curSelectedIndices = null;
      
      listBox.removeAll();
      for (T t : elements) {
        SwtListitem item = null;
        try {
          item = (SwtListitem) container.getDocumentRoot().createElement("listitem");
        } catch (XulException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
  //      SwtListitem item = new SwtListitem(null, this, container, null);
        this.addChild(item);
        item.setLabel(extractLabel(t));
      }
      layout();
    }
  }

  public void setSelectedIndices(int[] indices) {
    listBox.deselectAll();
    listBox.select(indices);
    fireSelectedEvents();
  }

  public void setBinding(String binding) {
    this.binding = binding;
  }

  public String getBinding() {
    return binding;
  }
  
  private <T> String extractLabel(T t) {
    if(t == null){
      return "";
    }
    String attribute = getBinding();
    if (StringUtils.isEmpty(attribute)) {
      return t.toString();
    } else {
      String getter = "get" + (String.valueOf(attribute.charAt(0)).toUpperCase()) + attribute.substring(1);
      try {
        return ""+ (new Expression(t, getter, null).getValue());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  //
  // SwtListbox supports drag bindings, but not drop bindings
  //
  
  @Override
  protected java.util.List<Object> getSwtDragData() {
    java.util.List<Object> list = new ArrayList<Object>();
    if (elements != null && elements instanceof java.util.List) {
      int[] indices = listBox.getSelectionIndices();
      for (int i = 0; i < indices.length; i++) {
        list.add(((java.util.List)elements).get(indices[i]));
      }
    } else {
      for (String str: listBox.getSelection()) {
        list.add(str);
      }
    }
    return list;
  }
  
  @Override
  protected void onSwtDragFinished(DropEffectType effect) {
    if (effect == DropEffectType.MOVE) {
      if (elements != null) {
        throw new UnsupportedOperationException("Bindings not yet supported in drag with move");
      } else {

        // remove both from xul and from list
        int[] indices = listBox.getSelectionIndices();
        for (int i = indices.length - 1; i >= 0; i--) {
          removeChild(getChildNodes().get(indices[i]));
        }
        listBox.remove(indices);
        // need to link to bindings
      }
    }
  }
  
  @Override
  protected void onSwtDragDropAccepted(DropEvent event) {
    if (elements != null) {
      throw new UnsupportedOperationException("Bindings not yet supported on drop");
    } else {
      java.util.List<Object> data = event.getDataTransfer().getData();
      for (int i = 0; i < data.size(); i++) { 
        SwtListitem item = null;
        try {
          item = (SwtListitem) container.getDocumentRoot().createElement("listitem");
        } catch (XulException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        this.addChild(item);
        item.setLabel(data.get(i).toString());
      }
      layout();
    }
  }

  public void setOndrop(String ondrop) {
    super.setOndrop(ondrop);
    super.enableDrop();
  }
  
  public void setOndrag(String ondrag) {
    super.setOndrag(ondrag);
    // TODO: once listBox is initialized lazily, we also need to move this
    // this exact logic is in setDrageffect because both need to be set for 
    // enable dragging 
    if (getDrageffect() != null) {
      super.enableDrag(DropEffectType.valueOfIgnoreCase(getDrageffect()));
    }
  }
  
  public void setDrageffect(String drageffect) {
    super.setDrageffect(drageffect);
    // TODO: once listBox is initialized lazily, we also need to move this
    // this exact logic is in setOndrag because both need to be set for 
    // enable dragging 
    if (getOndrag() != null) {
      super.enableDrag(DropEffectType.valueOfIgnoreCase(getDrageffect()));
    }
  }

  public String getCommand() {
    return command;
  }

  public void setCommand(final String command) {
    this.command = command;
    listBox.addMouseListener(new MouseAdapter(){
      public void mouseDoubleClick(MouseEvent arg0) {
        invoke(command);
      }
    });
  }
  
}
