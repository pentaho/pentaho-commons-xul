package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulDeck;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.Orient;
import org.pentaho.ui.xul.dom.Element;

public class SwtDeck extends SwtElement implements XulDeck {

  protected Composite box;
  protected StackLayout layout;
  private int selectedChildIndex = 0; 

  public SwtDeck(Element self, XulComponent parent, XulDomContainer container, String tagName) {
    this(parent, tagName, Orient.HORIZONTAL);
  }

  public SwtDeck(XulComponent parent, String tagName, Orient orient) {
    super(tagName);
    box = new Composite((Composite) parent.getManagedObject(), SWT.NONE);
    
    layout = new StackLayout();
    box.setLayout(layout);
    setSelectedIndex(0);
    
    managedObject = box;

  }
  public int getSelectedIndex() {
    return selectedChildIndex;
  }

  public void setSelectedIndex(int index) {
    selectedChildIndex = index;
    if ((children != null) && (!children.isEmpty())){
      XulComponent control = children.get(selectedChildIndex); 
      layout.topControl = (Control)control.getManagedObject();
      layout();
    }
  }

  @Override
  public void layout() {
    box.layout();
  }

}
