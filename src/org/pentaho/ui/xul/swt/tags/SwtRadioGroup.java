package org.pentaho.ui.xul.swt.tags;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;

import org.eclipse.swt.widgets.Control;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.components.XulRadioGroup;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.util.Orient;

public class SwtRadioGroup extends SwtBox implements XulRadioGroup {
  private ButtonGroup buttonGroup = new ButtonGroup();

  public SwtRadioGroup(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super(parent, tagName, Orient.VERTICAL);
  }

  @Override
  public void addComponent(XulComponent c) {
    addComponentToButtonGroup(c);
    super.addComponent(c);
    resetContainer();
    super.layout();

    if (initialized) {
      resetContainer();
      layout();
    }
  }

  protected void addComponentToButtonGroup(XulComponent c) {
    for (XulComponent child : c.getChildNodes()) {
      addComponentToButtonGroup(child);
    }
    if (AbstractButton.class.isAssignableFrom(c.getManagedObject().getClass())) {
      this.buttonGroup.add((AbstractButton) c.getManagedObject());
    }
  }

  public void resetContainer() {


  }

  @Override
  public void replaceChild(XulComponent oldElement, XulComponent newElement) throws XulDomException {
    this.resetContainer();
    super.replaceChild(oldElement, newElement);
  }
}
