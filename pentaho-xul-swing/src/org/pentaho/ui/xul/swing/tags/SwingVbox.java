/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.containers.XulVbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;
import org.pentaho.ui.xul.swing.ScrollablePanel;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.util.Orient;

/**
 * @author nbaker
 *
 */
public class SwingVbox extends AbstractSwingContainer implements XulVbox {

  public SwingVbox(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("Vbox");
    this.orientation = Orient.VERTICAL;

    container = new ScrollablePanel(new GridBagLayout());
    container.setOpaque(false);
    managedObject = container;

    resetContainer();

  }

  public void resetContainer() {

    container.removeAll();

    gc = new GridBagConstraints();
    gc.gridy = GridBagConstraints.RELATIVE;
    gc.gridx = 0;
    gc.gridheight = 1;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.insets = new Insets(2, 2, 2, 2);
    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.anchor = GridBagConstraints.NORTHWEST;
    gc.weightx = 1;

  }

  @Override
  public void addComponent(XulComponent component) {
    super.addComponent(component);
    if (initialized) {
      resetContainer();
      layout();
    }
  }

  @Override
  public void removeChild(Element ele) {
    super.removeChild(ele);
    children.remove(ele);
    if (initialized) {
      resetContainer();
      layout();
    }
  }
  
  @Override
  public void replaceChild(XulComponent oldElement, XulComponent newElement) throws XulDomException {
    this.resetContainer();
    super.replaceChild(oldElement, newElement);
  }

  @Override
  public void layout() {
    if(getBgcolor() != null){
      container.setOpaque(true);
      container.setBackground(Color.decode(getBgcolor()));
    }
    super.layout();
  }
}
