package org.pentaho.ui.xul.swt.custom;

import java.awt.Image;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.pentaho.ui.xul.swt.ColumnWidget;

public class TableColumnWrapper implements ColumnWidget {

  TableColumn column = null;
  
  public TableColumnWrapper(Table parent){
    column = new TableColumn(parent, SWT.LEFT); 
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

  public void setWidth(int width) {
    column.setWidth(width);
  }

  public void autoSize(){
    column.pack();
  }
}
