/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.containers.XulHbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.ScrollablePanel;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.util.Orient;

/**
 * @author OEM
 *
 */
public class SwingHbox extends SwingElement implements XulHbox {

  private static final Log logger = LogFactory.getLog(SwingHbox.class);

  public SwingHbox(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("Hbox");

    children = new ArrayList<XulComponent>();

    container = new ScrollablePanel(new GridBagLayout());
    container.setOpaque(false);
    managedObject = container;

    resetContainer();

  }

  @Override
  public void addComponent(XulComponent c) {
    super.addComponent(c);
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

  public void resetContainer() {

    container.removeAll();

    gc = new GridBagConstraints();
    gc.gridx = GridBagConstraints.RELATIVE;
    gc.gridy = 0;
    gc.gridheight = GridBagConstraints.REMAINDER;
    gc.gridwidth = 1;
    gc.insets = new Insets(2, 2, 2, 2);
    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.anchor = GridBagConstraints.NORTHWEST;
    gc.weighty = 1;

  }

  public Orient getOrientation() {
    return Orient.HORIZONTAL;
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
