package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.containers.XulColumn;
import org.pentaho.ui.xul.containers.XulColumns;
import org.pentaho.ui.xul.containers.XulGrid;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtColumns extends AbstractGwtXulContainer implements XulColumns {

  public static void register() {
    GwtXulParser.registerHandler("columns",  //$NON-NLS-1$
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtColumns();
      }
    });
  }
  
  public GwtColumns() {
    super("columns"); //$NON-NLS-1$
    this.managedObject = "empty";
  }
  
  public void addColumn(XulColumn column) {
    super.addChild(column);
  }

  public XulColumn getColumn(int index) {
    return (XulColumn) this.getChildNodes().get(index);
  }

  public int getColumnCount() {
    return this.getChildNodes().size();
  }

  public XulGrid getGrid() {
    return (XulGrid) getParent();
  }


  public void adoptAttributes(XulComponent component) {
    // TODO Auto-generated method stub
    
  }
  
  public void layout(){

  }

}
