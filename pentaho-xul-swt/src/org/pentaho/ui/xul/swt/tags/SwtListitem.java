package org.pentaho.ui.xul.swt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulListitem;
import org.pentaho.ui.xul.containers.XulListbox;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.dom.Element;

public class SwtListitem extends SwtElement implements XulListitem {

  String label=null;
  
  // SWT list boxes only support String items
  String value=null;
  XulComponent parent;
  boolean isSelected = false; 
  
  public SwtListitem(Element self, XulComponent parent, XulDomContainer container, String tagName){
    super(tagName);
    this.parent = parent;
  }

  public void setLabel( String text ) {
    XulComponent p = getParent() != null ? getParent() : parent;
    label=text;
    ((XulListbox) p).addItem(label);
  }

  public String getLabel() {
    return label;
  }

  public Object getValue() {
    return value;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean selected) {
    isSelected = selected;
    if (label != null){
      ((XulListbox)parent).setSelectedItem(label);
    }else if (value != null){
      ((XulListbox)parent).setSelectedItem(value);
    }
  }

  /** 
   * In other implementations of listbox, you can associate a String and an
   * object with the listitem... not so in SWT.. so, for this impl, 
   * the value and the label should ALWAYS be the same.  
   */
  public void setValue(Object value) {

    // SWT only supports String items.
    if (value instanceof String){
      this.value = (String)value;
      
      // If the label attribute is missing, we 
      // need to figure out a way to add this child to the list ...
      if (label == null){
        setLabel(this.value);
      }
    }
  }
  

}
