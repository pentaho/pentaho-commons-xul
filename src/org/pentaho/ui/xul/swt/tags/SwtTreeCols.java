package org.pentaho.ui.xul.swt.tags;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTreeCol;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeCols;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.dom.Element;

public class SwtTreeCols extends SwtElement implements XulTreeCols {
  
  protected XulTree parentTree = null;
  
  protected List <XulTreeCol> columns;
  
  public SwtTreeCols(Element self, XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    this.parentTree = (XulTree)parent;
    columns = new ArrayList <XulTreeCol>();
    
    Composite tree = (Composite)this.parentTree.getManagedObject();

    tree.getShell().addListener(XulWindow.EVENT_ON_LOAD, new Listener() {
      public void handleEvent(Event e) {
        // In order to get the proper size of the table for 
        // relative column sizing, we need this listener... this is the earliest 
        // possible moment when the table size is determined. 
        layout();
      }
    });
    
    parentTree.setColumns(this);
  }
  
  public void addColumn(XulTreeCol column){
    columns.add(column);
  }
  
  public XulTreeCol getColumn(int index){
    XulTreeCol colToReturn = null;
    if (index < columns.size()){
      colToReturn = columns.get(index);
    }
    return colToReturn;
  }
  
  public int getColumnCount(){
    return columns.size();
  }
  
  public boolean isHierarchical(){
    return parentTree.isHierarchical();
  }
  
  public XulTree getTree(){
    return parentTree;
  }

  @Override
  public void layout() {
    super.layout();

    int totalFlex = 0;
    
    for (XulTreeCol col : columns) {
     totalFlex += col.getFlex();
    }
    
    // No one is flexing, use the SWT method of sizing to the 
    // text in the columns ...
    if (totalFlex == 0){

      for (XulTreeCol col : columns) {
        col.autoSize();
       }
   
    }else{ // use flex values to determine relative size...
    
      int treeWidth = parentTree.getWidth();
      
      // if we get here, and the tree hasn't been sized
      // yet, no sense in trying to size the columns...
      if (treeWidth <=0){
        return;
      }
      
      for (XulTreeCol col : columns) {
        if (col.getFlex() > 0){
          float relativeWidth = ((float)col.getFlex()/(float)totalFlex);
          col.setWidth((int)(relativeWidth * treeWidth - 7));
        }else {
          col.autoSize();
        }
      }
      
    }
  }
  

}
