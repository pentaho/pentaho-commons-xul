package org.pentaho.ui.xul.gwt;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.util.Align;

public abstract class AbstractGwtXulContainer extends AbstractGwtXulComponent implements XulContainer{

  protected Align alignment;
  
  public AbstractGwtXulContainer(String tagName){
    super(tagName);
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

  