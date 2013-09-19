package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.swing.AbstractSwingContainer;
import org.pentaho.ui.xul.containers.XulColumns;
import org.pentaho.ui.xul.containers.XulRows;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;

/**
 * User: nbaker
 * Date: Apr 14, 2009
 */
public class SwingRows extends AbstractSwingContainer implements XulColumns, XulRows {

  public SwingRows(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
		super("rows");

		setManagedObject("empty");
	}
  
  @Override
  public void layout(){

  }

}