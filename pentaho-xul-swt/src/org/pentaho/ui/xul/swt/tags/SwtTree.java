package org.pentaho.ui.xul.swt.tags;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.binding.InlineBindingExpression;
import org.pentaho.ui.xul.components.XulTreeCell;
import org.pentaho.ui.xul.components.XulTreeCol;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeChildren;
import org.pentaho.ui.xul.containers.XulTreeCols;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.swt.SwtBinding;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.swt.TableSelection;
import org.pentaho.ui.xul.swt.TabularWidget;
import org.pentaho.ui.xul.swt.custom.TableWrapper;
import org.pentaho.ui.xul.swt.custom.TreeWrapper;

public class SwtTree extends AbstractSwtXulContainer implements XulTree {

  // Tables and trees 
  // share so much of the same API, I wrapped their common methods 
  // into an interface (TabularWidget) and set the component to two
  // separate member variables here so that I don't have to reference 
  // them separately.  
	
	private static final Log logger = LogFactory.getLog(SwtTree.class);
  
	protected TabularWidget widget;

  protected Composite tree;

  protected XulTreeCols columns = null;

  protected XulTreeChildren rootChildren = null;

  protected XulComponent parentComponent = null;
  
  private Object data = null;

  private boolean disabled = false;

  private boolean enableColumnDrag = false;

  private boolean editable = false;
  
  private String onedit;

  private String onSelect = null;

  private int rowsToDisplay = 0;

  private TableSelection selType = TableSelection.SINGLE;

  private boolean isHierarchical = false;
  
  private int activeRow = -1;
  private int activeColumn = -1;
  private XulDomContainer domContainer;

  public SwtTree(Element self, XulComponent parent, XulDomContainer container, String tagName) {
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
    
    domContainer = container;

    //hook up default selection listener for change support

    widget.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        SwtTree.this.changeSupport.firePropertyChange("selectedRows", null, SwtTree.this.getSelectedRows());
      }
    });
  
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
      int ht = rowsToDisplay * widget.getItemHeight();
      if (tree.getLayoutData() != null) {
       // tree.setSize(tree.getSize().x,height);
        ((GridData) tree.getLayoutData()).heightHint = ht;
        ((GridData) tree.getLayoutData()).minimumHeight = ht;
        
        tree.getParent().layout(true);
      }
    }
  }
  
  public enum SELECTION_MODE {
    SINGLE, CELL, MULTIPLE
  };

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
        invoke(method, new Object[] {widget.getSelectionIndex()});
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

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
	
	public int[] getSelectedRows() {
		//TODO: implement Tree Selection
		int[] rows = (this.isHierarchical)? null : ((Table) ((TableWrapper) widget).getComposite()).getSelectionIndices();
		if(rows == null){
		  rows = new int[]{};
		}
		return rows;
	}

	public void addTreeRow(XulTreeRow row) {
		this.addChild(row);
		XulTreeItem item = new SwtTreeItem(this.getRootChildren());
		row.setParentTreeItem(item);
    ((SwtTreeRow)row).layout();
	}

	public void removeTreeRows(int[] rows) {
		// TODO Auto-generated method stub
		
	}

  public void update() {
    if(!this.isHierarchical){
      ((TableWrapper) widget).getComposite().update();
    } else {
      //TODO implement update for tree
    }
      
  }

  public void clearSelection() {
    //TODO implement clearSelection
  }

  public void setSelectedRows(int[] rows) {
    //TODO: impl tree version
    ((Table) ((TableWrapper) widget).getComposite()).setSelection(rows);
  }


  public String getOnedit() {
    return onedit;
  }

  public void setOnedit(String onedit) {
    this.onedit = onedit;  
  }

  public <T> void setElements(Collection<T> elements) {
    this.getRootChildren().removeAll();
    try {
      for (T o : elements) {
        logger.info("row type is " + o.getClass().getName());
        SwtTreeRow row = (SwtTreeRow) this.getRootChildren().addNewRow();

        for (XulComponent col : this.getColumns().getChildNodes()) {
          XulTreeCell cell = new SwtTreeCell(null, row, this.domContainer, "treecell");
          for (InlineBindingExpression exp : ((XulTreeCol) col).getBindingExpressions()) {
            logger.info("applying binding expression [" + exp.getModelAttr() + "] to xul tree cell and model [" + o
                + "]");

            Binding binding = new SwtBinding(o, exp.getModelAttr(), cell, exp.getXulCompAttr());
            domContainer.addBinding(binding);
            binding.fireSourceChanged();
          }
        }
        row.layout();
        

      }
      this.widget.getComposite().getShell().redraw();
      this.widget.getComposite().redraw();
      this.widget.getComposite().getParent().redraw();
      
      //treat as a selection change
      changeSupport.firePropertyChange("selectedRows", null, getSelectedRows());
    } catch (Exception e) {
      logger.error("error adding elements", e);
    }
  }

  public <T> Collection<T> getElements() {
    return null;
  }
}
