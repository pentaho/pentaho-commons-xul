package org.pentaho.ui.xul.swing.tags;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.containers.XulListbox;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.util.Orient;

public class SwingListbox extends SwingElement implements XulListbox, ListSelectionListener{
  private static final long serialVersionUID = 3064125049914932493L;

  private JList listBox;
  private DefaultListModel model;
  private boolean disabled = false;
  private boolean selType;
  private int rowsToDisplay = 0;
  private String onchange;
  
  public SwingListbox(XulElement parent, XulDomContainer container, String tagName) {
    super(tagName);
    model = new DefaultListModel();
    listBox = new JList(model);
    listBox.setBorder(BorderFactory.createLineBorder(Color.gray));
    listBox.addListSelectionListener(this);
    managedObject = listBox;
  }
  
  public Object getManagedObject(){
    return this.managedObject;
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
    this.listBox.setVisibleRowCount(rowsToDisplay);
  }

  public boolean isSeltype() {
    return selType;
  }

  public void setSeltype(boolean selType) {
    this.selType = selType;
    
  }


  public Orient getOrientation() {
    return null;
  }
  
  public void layout(){
    super.layout();
    for(XulComponent comp : children){
      if(comp instanceof SwingListitem){
        this.model.addElement(comp);
      }
    }
  }

  public String getOnselect() {
    return onchange;
  }

  public void setOnselect(String onchange) {
    this.onchange = onchange;
    
  }
  
  public void valueChanged(ListSelectionEvent e) {
    if(e.getValueIsAdjusting() == true){
      return;
    }
    Document doc = SwingListbox.this.getDocument();
    Element rootElement = doc.getRootElement();
    XulWindow window = (XulWindow) rootElement;
    window.invoke(onchange, new Object[]{});
    
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
    
    // TODO check this logic, untested...
    int[] indices = new int[items.length];
    int index = -1;
    for (Object object : items) {
      indices[++index] = model.indexOf(object); 
    }
    listBox.setSelectedIndices(indices);
  }

  public void addItem(Object item) {
    this.model.addElement(item);
  }
  
  public void removeItems(){
    this.model.removeAllElements();
  }
  

}
