package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.swing.AbstractSwingContainer;
import org.pentaho.ui.xul.containers.XulColumns;
import org.pentaho.ui.xul.containers.XulRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;

/**
 * User: nbaker
 * Date: Apr 14, 2009
 */
public class SwingRow extends AbstractSwingContainer implements XulColumns, XulRow {

  public SwingRow(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
		super("row");

		setManagedObject("empty");
	}
  
  @Override
  public void layout(){

  }

}