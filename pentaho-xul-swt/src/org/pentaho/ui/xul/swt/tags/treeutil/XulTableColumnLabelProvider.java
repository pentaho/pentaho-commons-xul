package org.pentaho.ui.xul.swt.tags.treeutil;

import java.util.Vector;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.components.XulTreeCell;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.util.ColumnType;

public class XulTableColumnLabelProvider implements ITableLabelProvider {

  private static final String UNCHECKED = "UNCHECKED";
  private static final String CHECKED = "CHECKED";

  private XulTree tree
  ;
  public XulTableColumnLabelProvider(XulTree tree) {
    this.tree = tree;
    if (JFaceResources.getImageRegistry().getDescriptor(CHECKED) == null) {
      JFaceResources.getImageRegistry().put(UNCHECKED,
          makeImage(((TableViewer) tree.getManagedObject()).getControl().getShell(), false));
      JFaceResources.getImageRegistry().put(CHECKED,
          makeImage(((TableViewer) tree.getManagedObject()).getControl().getShell(), true));
    }
  }

  public String getColumnText(Object obj, int i) {

    final int colIdx = i;
    XulTreeCell cell = ((XulTreeItem) obj).getRow().getCell(colIdx);
    if (cell == null) {
      return "";
    }

    switch (tree.getColumns().getColumn(colIdx).getColumnType()) {
    case CHECKBOX:
      return cell.getLabel() != null ? cell.getLabel() : cell.getLabel();
    case COMBOBOX:
    case EDITABLECOMBOBOX:
      Vector vec = (Vector) cell.getValue();
      if(vec != null  && vec.size() > cell.getSelectedIndex()){
        return "" + vec.get(cell.getSelectedIndex());
      } else {
        return "";
      }
    case TEXT:
      return cell.getLabel() != null ? cell.getLabel() : "";
    default:
      return cell.getLabel() != null ? cell.getLabel() : "";
    }

  }

  public void addListener(ILabelProviderListener ilabelproviderlistener) {
  }

  public void dispose() {
  }

  public boolean isLabelProperty(Object obj, String s) {
    return false;
  }

  public void removeListener(ILabelProviderListener ilabelproviderlistener) {
  }

  public Image getColumnImage(Object row, int col) {
    if (tree.getColumns().getColumn(col).getColumnType() != ColumnType.CHECKBOX) {
      return null;
    }

    if (isSelected(row, col)) {
      return JFaceResources.getImageRegistry().getDescriptor(CHECKED)
          .createImage();
    } else {
      return JFaceResources.getImageRegistry().getDescriptor(UNCHECKED)
          .createImage();
    }
  }

  private Image makeImage(Shell shell, boolean type) {
    Shell placeholder = new Shell(shell, SWT.NO_TRIM);
    Button btn = new Button(placeholder, SWT.CHECK);
    btn.setSelection(type);
    Point bsize = btn.computeSize(SWT.DEFAULT, SWT.DEFAULT);
    btn.setSize(bsize);
    placeholder.setSize(bsize);
    btn.setLocation(0, 0);
    placeholder.open();

    GC gc = new GC(btn);
    Image image = new Image(shell.getDisplay(), bsize.x, bsize.y);
    gc.copyArea(image, 0, 0);
    gc.dispose();

    placeholder.close();

    return image;
  }

  private boolean isSelected(Object row, int col) {
    XulTreeRow r = ((XulTreeItem) row).getRow();
    if(r == null || r.getChildNodes().size() < col){
      return false;
    }
    XulTreeCell c = r.getCell(col);
    if(c == null){
      return false;
    }
    Object val = c.getValue();
    if(val == null || val instanceof Boolean == false){
      return false;
    }
    return (Boolean) c.getValue();
  }
}