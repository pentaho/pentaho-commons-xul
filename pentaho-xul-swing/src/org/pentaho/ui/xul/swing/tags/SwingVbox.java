/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

  private String background;
  private Image backgroundImage;
  private static final Log logger = LogFactory.getLog(SwingVbox.class);
  private XulDomContainer domContainer;

  public SwingVbox(Element self, XulComponent parent,
      XulDomContainer domContainer, String tagName) {
    super("Vbox");
    this.domContainer = domContainer;
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
  public void replaceChild(XulComponent oldElement, XulComponent newElement)
      throws XulDomException {
    this.resetContainer();
    super.replaceChild(oldElement, newElement);
  }

  @Override
  public void layout() {
    if (getBgcolor() != null) {
      container.setOpaque(true);
      container.setBackground(Color.decode(getBgcolor()));
    }
    super.layout();
  }

  public String getBackground() {
    return background;
  }

  public void setBackground(String src) {
    this.background = src;
    URL url = SwingImage.class.getClassLoader().getResource(
        this.domContainer.getXulLoader().getRootDir() + src);

    // Then try to see if we can get the fully qualified file
    if (url == null) {
      try {
        url = new File(src).toURL();
      } catch (MalformedURLException e) {
        // do nothing and let the null url get caught below.
      }
    }

    if (url == null) {
      logger.error("Could not find resource: " + src);
      return;
    }
    final ImageIcon ico = new ImageIcon(url);

    container.addComponentListener(new ComponentListener() {

      public void componentHidden(ComponentEvent arg0) {

      }

      public void componentMoved(ComponentEvent arg0) {
      }

      public void componentResized(ComponentEvent arg0) {
        container.getGraphics().drawImage(ico.getImage(), 0, 0, container);
        container.repaint();

      }

      public void componentShown(ComponentEvent arg0) {

      }

    });

  }
}
