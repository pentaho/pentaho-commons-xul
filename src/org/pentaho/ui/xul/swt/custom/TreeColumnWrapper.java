package org.pentaho.ui.xul.swt.custom;

import java.awt.Image;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.pentaho.ui.xul.swt.ColumnWidget;

public class TreeColumnWrapper implements ColumnWidget {

  TreeColumn column = null;

  public TreeColumnWrapper(Tree parent){
    column = new TreeColumn(parent, SWT.LEFT); 
  }

  public Item getItem() {
    return column;
  }

  public boolean getMoveable() {
    return column.getMoveable();
  }

  public boolean getResizable() {
    return column.getResizable();
  }

  public String getText() {
    return column.getText();
  }

  public int getWidth() {
    return column.getWidth();
  }

  public void setImage(Image image) {
  }

  public void setMovable(boolean movable) {
    column.setMoveable(movable);
  }

  public void setResizable(boolean resizable) {
    column.setResizable(resizable);
  }

  public void setText(String string) {
    column.setText(string);
  }
  
  public void autoSize(){
    column.pack();
  }
  

  public void setWidth(int width) {
    column.setWidth(width);
  }

}
