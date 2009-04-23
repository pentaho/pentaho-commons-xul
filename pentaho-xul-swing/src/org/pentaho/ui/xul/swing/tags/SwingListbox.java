package org.pentaho.ui.xul.swing.tags;

import java.awt.Color;
import java.awt.Dimension;
import java.beans.Expression;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulListbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.util.Orient;

public class SwingListbox extends AbstractSwingContainer implements XulListbox, ListSelectionListener{
  private static final long serialVersionUID = 3064125049914932493L;

  private JList listBox;
  private DefaultListModel model;
  private boolean disabled = false;
  private String selType;
  private int rowsToDisplay = 0;
  private String onselect;
  private JScrollPane scrollPane;
  private static final Log logger = LogFactory.getLog(SwingListbox.class);
  private XulDomContainer xulDomContainer;

  private String binding;

  private int[] curSelectedIndices = new int[0];
  
  public SwingListbox(Element self, XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    model = new DefaultListModel();
    listBox = new JList(model);
    scrollPane = new JScrollPane(listBox);
    listBox.setBorder(BorderFactory.createLineBorder(Color.gray));
    listBox.addListSelectionListener(this);
    managedObject = scrollPane;
    this.xulDomContainer = container;
  }
  
  public Object getManagedObject(){
    return this.managedObject;
  }

  public boolean isDisabled() {
    return !this.listBox.isEnabled();
  }

  public void setDisabled(boolean disabled) {
    this.listBox.setEnabled(!disabled);
  }
  public void setDisabled(String disabled) {
    this.listBox.setEnabled(!Boolean.parseBoolean(disabled));
  }

  public int getRows() {
    return rowsToDisplay;
  }

  public void setRows(int rowsToDisplay) {
    this.rowsToDisplay = rowsToDisplay;
    this.listBox.setVisibleRowCount(rowsToDisplay);
  }

  public String getSeltype() {
    return selType;
  }

  public void setSeltype(String selType) {
    this.selType = selType;
    SEL_TYPE sel = SEL_TYPE.valueOf(selType.toUpperCase());
    if(sel == SEL_TYPE.SINGLE){
      this.listBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    } else {
      this.listBox.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }
    
  }

  public Orient getOrientation() {
    return null;
  }
  
  public void layout(){
    this.model.clear();
    for(Element comp : getChildNodes()){
      if(comp instanceof SwingListitem){
        this.model.addElement(comp);
        logger.info("added swingListitem to model");
      }
    }
    this.scrollPane.setMinimumSize(new Dimension(this.width, this.height));
    if(this.selectedIndex > -1){
      this.listBox.setSelectedIndex(selectedIndex);
    }
    initialized = true;
  }

  public String getOnselect() {
    return onselect;
  }

  public void setOnselect(String onchange) {
    this.onselect = onchange;
  }
  
  public void valueChanged(ListSelectionEvent e) {
    if(e.getValueIsAdjusting() == true){
      return;
    }
    if(onselect != null && initialized){
      invoke(onselect);
    }
    fireSetSelectedIndices(listBox.getSelectedIndices());
  }

  public Object getSelectedItem() {
    return this.listBox.getSelectedValue();
  }

  public Object[] getSelectedItems() {
    return listBox.getSelectedValues();
  }

  public void setSelectedItem(Object item) {
   listBox.setSelectedValue(item, true);
  }

  public void setSelectedItems(Object[] items) {
    int[] indices = new int[items.length];
    int index = -1;
    for (Object object : items) {
      indices[++index] = model.indexOf(object); 
    }
    setSelectedIndices(indices);
  }
  
  public void setSelectedIndices(int[] indices) {
    listBox.setSelectedIndices(indices);
    fireSetSelectedIndices(indices);
  }
  
  private void fireSetSelectedIndices(int[] indices) {
    if(!Arrays.equals(curSelectedIndices, indices)) {
      this.changeSupport.firePropertyChange("selectedIndices", curSelectedIndices, indices);
      this.changeSupport.firePropertyChange("selectedIndex", curSelectedIndices, getSelectedIndex());
      curSelectedIndices = indices;
    }
  }
  
  public void addItem(Object item) {
    this.model.addElement(item);
  }
  
  public void removeItems(){
    this.model.removeAllElements();
    for(XulComponent c : this.getChildNodes()){
      this.removeChild(c);
    }
  }

  public int getRowCount() {
    return model.getSize();
  }

  public int getSelectedIndex() {
    return listBox.getSelectedIndex();
  }

  public int[] getSelectedIndices() {
    return listBox.getSelectedIndices();
  }

  private int selectedIndex = -1;
  public void setSelectedindex(String idx) {
    selectedIndex = Integer.parseInt(idx);
    listBox.setSelectedIndex(Integer.parseInt(idx));
  }
  
  public void setSelectedIndex(int index) {
    selectedIndex = index;
    listBox.setSelectedIndex(index);
  }

  
  private Collection boundElements;
  public <T> Collection<T> getElements() {
    return (Collection<T>) boundElements;
  }

  public <T> void setElements(Collection<T> elements) {
    boundElements = elements;
    
    logger.info("SetElements on listbox called: collection has "+elements.size()+" rows");
    
    this.model.removeAllElements();
    this.removeItems();
    for (T t : elements) {
      SwingListitem item = new SwingListitem(null, this, this.xulDomContainer, null);

      String attribute = getBinding();
      if (!StringUtils.isEmpty(attribute)) {
        item.setLabel(extractLabel(t));
      }

      this.addChild(item);
    }

    layout();
    
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
      String getter = "get" + (String.valueOf(attribute.charAt(0)).toUpperCase()) + attribute.substring(1);
      try {
        return new Expression(t, getter, null).getValue().toString();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

}
