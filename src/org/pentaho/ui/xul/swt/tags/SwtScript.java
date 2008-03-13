package org.pentaho.ui.xul.swt.tags;

import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.XulWindowContainer;
import org.pentaho.ui.xul.components.XulScript;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtScript extends SwtElement implements XulScript {
  private static final long serialVersionUID = 3919768754393704152L;

  XulWindowContainer windowContainer = null;
  String className = null;
  
  public SwtScript(XulElement parent, XulWindowContainer container, String tagName) {
    super(tagName);
    windowContainer = container;
  }

  public String getSrc() {
    return className;
  }

  public void setSrc(String className) {
    this.className = className;
    if (this.getId() != null && className != null){
      windowContainer.addEventHandler(getId(), className);
    }
  }

  @Override
  /**
   *  Can't be guaranteed the order that these attributes 
   *  will be set, so register in both cases
   */
  public void setId(String id) {
    super.setId(id);
    if (this.getId() != null && className != null){
      windowContainer.addEventHandler(getId(), className);
    }
  }

}
