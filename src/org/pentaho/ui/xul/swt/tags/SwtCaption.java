package org.pentaho.ui.xul.swt.tags;

import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.components.XulCaption;
import org.pentaho.ui.xul.containers.XulGroupbox;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtCaption extends SwtElement implements XulCaption {
  
  String label;
  XulElement parent;
  
  public SwtCaption(XulElement parent, String tagName){
    super(tagName);
    this.parent = parent;
  }

  public void setLabel( String text ) {
    label=text;
    ((XulGroupbox)parent).setCaption(label);
  }

  public String getLabel() {
    return label;
  }
 
  public String getCaption() {
    return label;
  }

}
