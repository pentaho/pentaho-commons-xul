package org.pentaho.ui.xul.swing.tags;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.XulWindowContainer;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.swt.Orient;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwingListbox extends SwingElement implements XulContainer{
  private static final long serialVersionUID = 3064125049914932493L;

  private JList listBox;
  private DefaultListModel model;
  private boolean disabled = false;
  private boolean selType;
  private int rowsToDisplay = 0;
  
  public SwingListbox(XulElement parent, XulDomContainer container, String tagName) {
    super(tagName);
    model = new DefaultListModel();
    listBox = new JList(model);
    listBox.setBorder(BorderFactory.createLineBorder(Color.gray));
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
  

}
