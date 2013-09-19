package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.swing.AbstractSwingContainer;
import org.pentaho.ui.xul.containers.XulColumns;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;

import java.awt.*;

/**
 * User: nbaker
 * Date: Apr 14, 2009
 */
public class SwingColumns extends AbstractSwingContainer implements XulColumns {

  public SwingColumns(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
		super("columns");

		setManagedObject("empty");
	}

  @Override
  public void layout(){

  }

}
