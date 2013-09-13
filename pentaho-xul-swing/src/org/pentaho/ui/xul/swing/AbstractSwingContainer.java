package org.pentaho.ui.xul.swing;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.util.Align;

public abstract class AbstractSwingContainer extends SwingElement implements XulContainer{

  protected Align alignment;
  protected boolean suppressLayout;
  
  public AbstractSwingContainer(String tagName){
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

  public void suppressLayout(boolean suppress) {
    this.suppressLayout = suppress;
  }
  
  
}

  