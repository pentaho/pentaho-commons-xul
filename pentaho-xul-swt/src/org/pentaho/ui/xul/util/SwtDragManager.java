package org.pentaho.ui.xul.util;

import org.eclipse.swt.dnd.DragSourceEvent;
import org.pentaho.ui.xul.dnd.DropEvent;

/**
 * User: nbaker
 * Date: Aug 24, 2010
 */
public class SwtDragManager {

  private static SwtDragManager instance = new SwtDragManager();
  private DropEvent currentDropEvent;
  private SwtDragManager(){

  }

  public static SwtDragManager getInstance(){
    return instance;
  }

  public DropEvent getCurrentDropEvent() {
    return currentDropEvent;
  }

  public void setCurrentDropEvent(DropEvent currentDropEvent) {
    this.currentDropEvent = currentDropEvent;
  }
}
