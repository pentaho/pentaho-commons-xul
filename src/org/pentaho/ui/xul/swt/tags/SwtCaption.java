package org.pentaho.ui.xul.swt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulCaption;
import org.pentaho.ui.xul.containers.XulGroupbox;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtCaption extends SwtElement implements XulCaption {
  
  String label;
  XulComponent parent;
  
  public SwtCaption(XulComponent parent, XulDomContainer container, String tagName){
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
 
}
