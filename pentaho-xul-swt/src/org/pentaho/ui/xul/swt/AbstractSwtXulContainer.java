package org.pentaho.ui.xul.swt;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.util.Align;

public class AbstractSwtXulContainer extends SwtElement implements XulContainer{

  protected Align alignment = Align.CENTER;
  
  public AbstractSwtXulContainer(String tagName){
    super(tagName);
  }

  public String getAlign() {
    return alignment.toString();
  }

  public void setAlign(String align) {
    try{
      this.alignment = Align.valueOf(align.toUpperCase());
    } catch(Exception e){
      System.out.println("could not parse ["+align+"] as Align value");
      
    }
  }
  

  @Deprecated
  public void addComponent(XulComponent component) {
    this.addChild(component);
  }
  
  @Deprecated
  public void addComponentAt(XulComponent component, int idx) {
    this.addChildAt(component, idx);  
  }

  @Deprecated
  public void removeComponent(XulComponent component) {
    this.removeChild(component);
  }
  
  
}
  