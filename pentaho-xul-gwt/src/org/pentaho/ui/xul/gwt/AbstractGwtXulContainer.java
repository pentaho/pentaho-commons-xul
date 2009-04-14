package org.pentaho.ui.xul.gwt;

import java.util.Iterator;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.util.Align;

import com.google.gwt.user.client.ui.Widget;

public abstract class AbstractGwtXulContainer extends AbstractGwtXulComponent implements XulContainer{

  protected Align alignment;
  
  public AbstractGwtXulContainer(String tagName){
    super(tagName);
  }
  
  public void addComponent(XulComponent component) {
    this.addChild(component);
    if(initialized == false){
      layout();
    }

  if (initialized) {
    resetContainer();
    layout();
  }

  }
  
  public void addComponentAt(XulComponent component, int idx) {
    this.addChildAt(component, idx);
    if (initialized) {
      resetContainer();
      layout();
    }
  }

 
  public void removeComponent(XulComponent component) {
    this.removeChild(component);
    if (initialized) {
      resetContainer();
      layout();
    }
  }
  
  @Override
  public void resetContainer() {
    if(container != null) {
      Iterator<Widget> iterator = container.iterator();
      while(iterator.hasNext()) {
        Widget widget = iterator.next();
        container.remove(widget);
      }
    }
  }
}

  