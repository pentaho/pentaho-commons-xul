package org.pentaho.ui.xul.swt.custom;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.pentaho.ui.xul.swt.TableSelection;
import org.pentaho.ui.xul.swt.TabularWidget;

public class TreeWrapper implements TabularWidget {

  private Tree tree; 
  
  public TreeWrapper(TableSelection selection, Composite parent){
    tree = new Tree(parent, selection.getSwtStyle());
    tree.setHeaderVisible(true);
  }

  public void addSelectionListener(SelectionListener listener) {
    tree.addSelectionListener(listener);
  }

  public int getItemHeight() {
    return tree.getItemHeight();
  }
  
  public Composite getComposite(){
    return tree;
  }

}
