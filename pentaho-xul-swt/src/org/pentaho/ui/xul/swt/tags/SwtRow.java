package org.pentaho.ui.xul.swt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulColumns;
import org.pentaho.ui.xul.containers.XulRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;

/**
 * User: nbaker
 * Date: Apr 14, 2009
 */
public class SwtRow extends AbstractSwtXulContainer implements XulColumns, XulRow {

  public SwtRow(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
		super("row");

		if(parent != null && parent instanceof SwtRows){
		  setManagedObject(((SwtRows) parent).getGrid().getManagedObject());
		} else {
		  setManagedObject("empty");
		}
	}
  
  @Override
  public void layout(){

  }

}