package org.pentaho.ui.xul.swt;

import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.util.Align;

public class AbstractSwtXulContainer extends SwtElement implements XulContainer{

  protected Align alignment;
  
  public AbstractSwtXulContainer(String tagName){
    super(tagName);
  }

  public Align getAlign() {
    return alignment;
  }

  public void setAlign(String align) {
    try{
      this.alignment = Align.valueOf(align.toUpperCase());
    } catch(Exception e){
      System.out.println("could not parse ["+align+"] as Align value");
      
    }
  }
  
  
  
  
}
  