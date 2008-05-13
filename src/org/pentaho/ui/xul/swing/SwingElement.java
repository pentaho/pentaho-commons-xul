/**
 * 
 */
package org.pentaho.ui.xul.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulSplitter;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.impl.AbstractXulComponent;
import org.pentaho.ui.xul.util.Orient;

/**
 * @author OEM
 *
 */
public class SwingElement extends AbstractXulComponent {
  private static final Log logger = LogFactory.getLog(SwingElement.class);

  protected JPanel container;

  protected Orient orientation;

  protected Orient orient = Orient.HORIZONTAL;

  protected GridBagConstraints gc = new GridBagConstraints();

  public SwingElement(String tagName) {
    super(tagName);
  }

  public void resetContainer() {

  }

  public void layout() {
    double totalFlex = 0.0;

    for (XulComponent comp : children) {
      if (comp.getManagedObject() == null) {
        continue;
      }
      if (comp.getFlex() > 0) {
        flexLayout = true;
        totalFlex += comp.getFlex();
      }
    }

    if (flexLayout)
      gc.fill = GridBagConstraints.BOTH;

    double currentFlexTotal = 0.0;
    for (int i = 0; i < children.size(); i++) {
      XulComponent comp = children.get(i);

      if (comp instanceof XulSplitter) {
        JPanel prevContainer = container;
        container = new ScrollablePanel(new GridBagLayout());

        final JSplitPane splitter = new JSplitPane(
            (this.getOrientation() == Orient.VERTICAL) ? JSplitPane.VERTICAL_SPLIT : JSplitPane.HORIZONTAL_SPLIT,
            prevContainer, container);
        splitter.setContinuousLayout(true);

        final double splitterSize = currentFlexTotal / totalFlex;
        splitter.setResizeWeight(splitterSize);
        if (totalFlex > 0) {
          splitter.addComponentListener(new ComponentListener() {
            public void componentHidden(ComponentEvent arg0) {
            }

            public void componentMoved(ComponentEvent arg0) {
            }

            public void componentShown(ComponentEvent arg0) {
            }

            public void componentResized(ComponentEvent arg0) {
              splitter.setDividerLocation(splitterSize);
              splitter.removeComponentListener(this);
            }

          });

        }

        if (!flexLayout) {
          if (this.getOrientation() == Orient.VERTICAL) { //VBox and such
            gc.weighty = 1.0;
          } else {
            gc.weightx = 1.0;
          }

          prevContainer.add(Box.createGlue(), gc);
        }
        managedObject = splitter;
      }

      Object maybeComponent = comp.getManagedObject();
      if (maybeComponent == null || !(maybeComponent instanceof Component)) {
        continue;
      }
      if (this.getOrientation() == Orient.VERTICAL) { //VBox and such
        gc.gridheight = comp.getFlex() + 1;
        gc.gridwidth = GridBagConstraints.REMAINDER;
        gc.weighty = (totalFlex == 0) ? 0 : (comp.getFlex() / totalFlex);
      } else {
        gc.gridwidth = comp.getFlex() + 1;
        gc.gridheight = GridBagConstraints.REMAINDER;
        gc.weightx = (totalFlex == 0) ? 0 : (comp.getFlex() / totalFlex);
      }

      currentFlexTotal += comp.getFlex();

      Component component = (Component) maybeComponent;
      container.add(component, gc);

      if (i + 1 == children.size() && !flexLayout) {

        if (this.getOrientation() == Orient.VERTICAL) { //VBox and such
          gc.weighty = 1.0;
        } else {
          gc.weightx = 1.0;
        }

        container.add(Box.createGlue(), gc);
      }
    }

  }

  public void setOrient(String orientation) {
    this.orientation = Orient.valueOf(orientation.toUpperCase());
  }

  public String getOrient() {
    return orientation.toString();
  }

  public Orient getOrientation() {
    return orientation;
  }

  @Override
  public void replaceChild(XulComponent oldElement, XulComponent newElement) throws XulDomException {

    super.replaceChild(oldElement, newElement);

    int idx = this.children.indexOf(oldElement);
    if (idx == -1) {
      logger.error(oldElement.getName() + " not found in children");
      throw new XulDomException(oldElement.getName() + " not found in children");
    } else {
      this.children.set(idx, newElement);

      container.removeAll();

      layout();
      this.container.revalidate();
    }
  }

  public JComponent getJComponent() {
    return (JComponent) getManagedObject();
  }

  public void setOnblur(final String method) {
    if (getJComponent() != null) {
      getJComponent().addFocusListener(new FocusListener() {

        public void focusLost(FocusEvent e) {
          Document doc = getDocument();
          XulWindow window = (XulWindow) doc.getRootElement();
          XulDomContainer container = window.getXulDomContainer();

          try {
            container.invoke(method, new Object[] {});
          } catch (XulException xe) {
            logger.error("Error calling onblur event", xe);
          }

        }

        public void focusGained(FocusEvent e) {
        }

      });
    }
  }
}
