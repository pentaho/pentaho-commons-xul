package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulLine;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

import javax.swing.JSeparator;
import java.awt.*;

/**
 * User: nbaker
 * Date: 6/24/13
 */
public class SwingLine extends SwingElement implements XulLine {
  public SwingLine(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("line");
    JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
    sep.setMinimumSize(new Dimension(10, 10));

    setManagedObject(sep);

  }

}
