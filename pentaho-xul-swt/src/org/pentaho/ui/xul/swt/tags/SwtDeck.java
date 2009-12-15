package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulDeck;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.Orient;

public class SwtDeck extends AbstractSwtXulContainer implements XulDeck {

  protected Composite box;

  protected StackLayout layout;

  private int selectedChildIndex = 0;

  private XulDomContainer domContainer;
  public SwtDeck(Element self, XulComponent parent, XulDomContainer container, String tagName) {
    this(parent, tagName, Orient.HORIZONTAL);
    domContainer = container;
  }

  public SwtDeck(XulComponent parent, String tagName, Orient orient) {
    super(tagName);
    box = new Composite((Composite) parent.getManagedObject(), SWT.NONE);

    layout = new StackLayout();
    box.setLayout(layout);
    setSelectedIndex(0);

    setManagedObject(box);

  }
  
  public SwtVbox createVBoxCard(){
    return new SwtVbox(null, this, domContainer, "vbox");
  }
  

  @Override
  public void addChild(Element e) {
    super.addChild(e);
    box.layout();
  }

  public int getSelectedIndex() {
    return selectedChildIndex;
  }

  public void setSelectedIndex(int index) {
    selectedChildIndex = index;
    if (!getChildNodes().isEmpty()) {
      XulComponent control = getChildNodes().get(selectedChildIndex);
      layout.topControl = (Control) control.getManagedObject();
      layout();
    }
  }

  @Override
  public void layout() {
    box.layout();
    if (!getChildNodes().isEmpty()) {
      XulComponent control = getChildNodes().get(getSelectedIndex());
      layout.topControl = (Control) control.getManagedObject();
    }
  }

}
