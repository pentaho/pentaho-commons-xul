package org.pentaho.ui.xul.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.pentaho.ui.xul.XulElement;

public class SwtElement extends XulElement {
  private static final long serialVersionUID = -4407080035694005764L;

  private Align align = null;
  private Orient orient = Orient.HORIZONTAL;
  private int flex=0;
  
  public SwtElement(String tagName, Object managedObject) {
    super(tagName, managedObject);
  }

  public SwtElement(String tagName) {
    super(tagName);
  }

  public int getFlex() {
    return flex;
  }

  public void setFlex(int flex) {
    this.flex = flex;
  }
  
  public void setAlign(String align){
    this.align = Align.valueOf(align.toUpperCase());
  }
  
  public String getAlign(){
    if (align == null)
      return null;
    return align.toString();
  }
  
  public void setOrient(String orientation){
    orient = Orient.valueOf(orientation.toUpperCase());
  }
  
  public String getOrient(){
    return orient.toString();
  }


  @Override
  public void layout() {
    
    if (!(getManagedObject() instanceof Composite)){
      return;
    }
    
    Composite container = (Composite) getManagedObject();
    
    // Total all flex values.
    // If every child has a flex value, then the GridLayout's columns
    // should be of equal width. everyChildIsFlexing gives us that boolean. 
    int totalFlex = 0;
    int flex = 0;
    boolean everyChildIsFlexing = true;
    
    for(Object child : elements()){
      flex = ((SwtElement)child).getFlex();
      if (flex <= 0){
        everyChildIsFlexing = false;
      }
      totalFlex += flex;
    }
    
    switch (orient) {
      case HORIZONTAL:
        int columnCount = elements().size() + totalFlex;
        container.setLayout(new GridLayout(columnCount, everyChildIsFlexing));
        break;
      case VERTICAL:
        container.setLayout(new GridLayout());
        break;
    }
    
    for(Object child : elements()){
      SwtElement swtChild = (SwtElement)child;
      Control c = (Control) swtChild.getManagedObject();
      GridData data = new GridData();
      data.horizontalSpan = orient.equals(Orient.HORIZONTAL) ? swtChild.getFlex() + 1 : 1 ;
      data.verticalSpan = orient.equals(Orient.VERTICAL) ? swtChild.getFlex() + 1 : 1 ;
      
      if (swtChild.getFlex() > 0){
        data.grabExcessHorizontalSpace=true;
        data.grabExcessVerticalSpace=true;
        
        // In XUL, if align attribute is left off, the 
        // default is to fill available space ...
        
        data.horizontalAlignment=SWT.FILL;
        data.verticalAlignment=SWT.FILL;
      }
      
      if (getAlign() != null){
        Align a = Align.valueOf(getAlign());
        if (orient.equals(Orient.HORIZONTAL)){
          data.verticalAlignment = a.getSwtAlign();
        }else {  //Orient.VERTICAL
          data.horizontalAlignment = a.getSwtAlign();
        }
      }
      
      c.setLayoutData(data);
    }

    
  }

}
