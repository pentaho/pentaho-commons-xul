package org.pentaho.ui.xul.swt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulColumns;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;

/**
 * User: nbaker
 * Date: Apr 14, 2009
 */
public class SwtColumn extends AbstractSwtXulContainer implements XulColumns {

  public SwtColumn(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
		super("column");

		setManagedObject("empty");
	}

  @Override
  public void layout(){

  }

}