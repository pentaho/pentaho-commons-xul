package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.components.XulTreeCol;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeCols;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtTreeCols extends AbstractGwtXulComponent implements XulTreeCols {

  public static void register() {
    GwtXulParser.registerHandler("treecols", 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTreeCols();
      }
    });
  }
  
  public GwtTreeCols() {
    super("treecols");
  }
  
  @Override
  public void layout() {
    // getTree().setColumns(this);
  }
  
  public void addColumn(XulTreeCol column) {
    // TODO Auto-generated method stub
    
  }

  public XulTreeCol getColumn(int index) {
    return (XulTreeCol) this.children.get(index);
  }

  public int getColumnCount() {
    return this.children.size();
  }

  public XulTree getTree() {
    return (XulTree) getParent();
  }

  public boolean isHierarchical() {
    // TODO Auto-generated method stub
    return false;
  }

  public void adoptAttributes(XulComponent component) {
    // TODO Auto-generated method stub
    
  }

}
