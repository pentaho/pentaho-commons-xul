package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeChildren;
import org.pentaho.ui.xul.containers.XulTreeCols;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.swt.TableSelection;
import org.pentaho.ui.xul.swt.TabularWidget;
import org.pentaho.ui.xul.swt.custom.TableWrapper;
import org.pentaho.ui.xul.swt.custom.TreeWrapper;

public class SwtTree extends SwtElement implements XulTree {

  // Tables and trees 
  // share so much of the same API, I wrapped their common methods 
  // into an interface (TabularWidget) and set the component to two
  // separate member variables here so that I don't have to reference 
  // them separately.  

  protected TabularWidget widget;

  protected Composite tree;

  protected XulTreeCols columns = null;

  protected XulTreeChildren rootChildren = null;

  protected XulComponent parentComponent = null;

  private boolean disabled = false;

  private boolean enableColumnDrag = false;

  private boolean editable = false;

  private String onSelect = null;

  private int rowsToDisplay = 0;

  private TableSelection selType = TableSelection.SINGLE;

  private boolean isHierarchical = false;
  
  private int activeRow = -1;
  private int activeColumn = -1;

  public SwtTree(XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    this.parentComponent = parent;

    // According to XUL spec, in order for a hierarchical tree to be rendered, a 
    // primary column must be identified AND at least one treeitem must be listed as a container. 

    XulComponent primaryColumn = getElementByXPath("//treecol[@primary='true']");
    XulComponent isaContainer = getElementByXPath("//treeitem[@container='true']");

    isHierarchical = (primaryColumn != null) && (isaContainer != null);

    widget = isHierarchical ? new TreeWrapper(selType, (Composite) parent.getManagedObject(), this) : 
                              new TableWrapper(selType, (Composite) parent.getManagedObject(), this);

    tree = widget.getComposite();

    managedObject = tree;
  }

  public boolean isHierarchical() {
    return isHierarchical;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
    if (!tree.isDisposed())
      tree.setEnabled(!disabled);
  }

  public int getRows() {
    return rowsToDisplay;
  }

  public void setRows(int rowsToDisplay) {
    this.rowsToDisplay = rowsToDisplay;
    if ((!tree.isDisposed()) && (rowsToDisplay > 0)) {
      int height = rowsToDisplay * widget.getItemHeight();
      if (tree.getLayoutData() != null) {
        tree.setSize(tree.getSize().x,height);
        ((GridData) tree.getLayoutData()).heightHint = height;
        ((GridData) tree.getLayoutData()).minimumHeight = height;
        tree.getParent().layout(true);
      }
    }
  }

  public String getSeltype() {
    return selType.toString();
  }

  public void setSeltype(String selType) {
    if (selType.equalsIgnoreCase(getSeltype())) {
      return; // nothing has changed
    }
    this.selType = TableSelection.valueOf(selType.toUpperCase());
    tree.dispose();
  }

  public TableSelection getTableSelection() {
    return selType;
  }

  public boolean isEditable() {
    return editable;
  }

  public boolean isEnableColumnDrag() {
    return enableColumnDrag;
  }

  public void setEditable(boolean edit) {
    editable = edit;
  }

  public void setEnableColumnDrag(boolean drag) {
    enableColumnDrag = drag;
  }

  public String getOnselect() {
    return onSelect;
  }

  public void setOnselect(final String method) {
    if (method == null) {
      return;
    }
    onSelect = method;

    if (tree.isDisposed()) {
      return;
    }

    widget.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        Element rootElement = getDocument().getRootElement();
        XulWindow window = (XulWindow) rootElement;
        window.invoke(method, new Object[] {new Integer(widget.getSelectionIndex())});
      }
    });
  }

  @Override
  /**
   * Override here because the SELTYPE attribute requires new 
   * construction; We can't ever guarantee the order of the setters, and
   * I think this is the most efficient way to reconstruct the tree... 
   * May fall down if there are other ways to get to the managed object. 
   */
  public Object getManagedObject() {
    if (tree.isDisposed()) {
      widget = isHierarchical ? new TreeWrapper(selType, (Composite) parentComponent.getManagedObject(), this)
          : new TableWrapper(selType, (Composite) parentComponent.getManagedObject(), this);
      tree = widget.getComposite();
      managedObject = tree;

      setOnselect(onSelect);
      setDisabled(disabled);
      setEnableColumnDrag(enableColumnDrag);
      setEditable(editable);
      setRows(rowsToDisplay);
    }

    return managedObject;
  }

  public int getWidth() {
    return tree.getSize().x;
  }

  public void setColumns(XulTreeCols columns) {
    this.columns = columns;
  }

  public XulTreeCols getColumns() {
    return columns;
  }

  public XulTreeChildren getRootChildren() {
    if (rootChildren == null){
      rootChildren = new SwtTreeChildren(this);
    }
    return rootChildren;
  }

  public void setRootChildren(XulTreeChildren rootChildren) {
    this.rootChildren = rootChildren;
  }

  public int[] getActiveCellCoordinates() {
    return new int[]{activeRow, activeColumn};
  }

  public void setActiveCellCoordinates(int row, int column) {
    activeRow = row;
    activeColumn = column;
    
  }

  public Object[][] getValues() {
    return widget.getValues();
  }
	
	public int[] getSelectedRows() {
		//TODO: implement Tree Selection
		return (this.isHierarchical)? null : ((Table) widget).getSelectionIndices();
	}

	public void addTreeRow(XulTreeRow row) {
		// TODO Auto-generated method stub
		
	}

	public void removeTreeRows(int[] rows) {
		// TODO Auto-generated method stub
		
	}

  /* =================================================================================== */

}
