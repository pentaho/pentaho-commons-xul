package org.pentaho.ui.xul.swt.tags;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.TreeViewerEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeColumn;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.binding.BindingConvertor;
import org.pentaho.ui.xul.binding.DefaultBinding;
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
import org.pentaho.ui.xul.swt.TableSelection;
import org.pentaho.ui.xul.swt.tags.treeutil.XulTableColumnLabelProvider;
import org.pentaho.ui.xul.swt.tags.treeutil.XulTableColumnModifier;
import org.pentaho.ui.xul.swt.tags.treeutil.XulTableContentProvider;
import org.pentaho.ui.xul.swt.tags.treeutil.XulTreeColumnModifier;
import org.pentaho.ui.xul.swt.tags.treeutil.XulTreeContentProvider;
import org.pentaho.ui.xul.swt.tags.treeutil.XulTreeLabelProvider;
import org.pentaho.ui.xul.swt.tags.treeutil.XulTreeTextCellEditor;
import org.pentaho.ui.xul.util.ColumnType;
import org.pentaho.ui.xul.util.TreeCellEditor;
import org.pentaho.ui.xul.util.TreeCellRenderer;

public class SwtTree extends AbstractSwtXulContainer implements XulTree {

  // Tables and trees
  // share so much of the same API, I wrapped their common methods
  // into an interface (TabularWidget) and set the component to two
  // separate member variables here so that I don't have to reference
  // them separately.

  private static final Log logger = LogFactory.getLog(SwtTree.class);

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
  
  private ColumnType[] currentColumnTypes = null;

  private int activeRow = -1;
  private int activeColumn = -1;
  private XulDomContainer domContainer;

  private TableViewer table;
  private TreeViewer tree;
  private int selectedIndex = -1;

  protected boolean controlDown;

  private int[] selectedRows;

  public SwtTree(Element self, XulComponent parent, XulDomContainer container,
      String tagName) {
    super(tagName);
    this.parentComponent = parent;

    // According to XUL spec, in order for a hierarchical tree to be rendered, a
    // primary column must be identified AND at least one treeitem must be
    // listed as a container.

    // Following code does not work with the current instantiation routine. When
    // transitioned to onDomReady() instantiation this should work.

    domContainer = container;

  }

  @Override
  public void layout() {

    XulComponent primaryColumn = this
        .getElementByXPath("//treecol[@primary='true']");
    XulComponent isaContainer = this
        .getElementByXPath("treechildren/treeitem[@container='true']");

    isHierarchical = (primaryColumn != null) || (isaContainer != null);

    if (isHierarchical) {
      int style = (this.selType == TableSelection.MULTIPLE) ? SWT.MULTI : SWT.None;
      
      tree = new TreeViewer((Composite) parentComponent.getManagedObject(), style);
      setManagedObject(tree);
    } else {
      table = new TableViewer((Composite) parentComponent.getManagedObject(),
          SWT.MULTI | SWT.H_SCROLL
          | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
      setManagedObject(table);
    }
    if (isHierarchical) {
      setupTree();
    } else {
      setupTable();
    }
    this.initialized = true;

  }

  private void setupTree() {

    tree.setCellEditors(new CellEditor[]{new XulTreeTextCellEditor(tree.getTree())});
    tree.setCellModifier(new XulTreeColumnModifier(this));

    tree.setLabelProvider(new XulTreeLabelProvider(this));
    tree.setContentProvider(new XulTreeContentProvider(this));

    tree.setInput(this);
    tree.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
    tree.setColumnProperties(new String[] { "0" });
    //TreeViewerColumn tc = new TreeViewerColumn(tree, SWT.LEFT);
    
    
    TreeViewerEditor.create(tree, new 
        ColumnViewerEditorActivationStrategy(tree){

          @Override
          protected boolean isEditorActivationEvent(ColumnViewerEditorActivationEvent event) {
            return super.isEditorActivationEvent(event);
          }
      
    }, ColumnViewerEditor.DEFAULT);

    tree.getTree().addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent event) {
        switch (event.keyCode) {
          case SWT.CTRL:
            SwtTree.this.controlDown = true;
            break;
          case SWT.ESC:
            // End editing session
            tree.getTree().deselectAll();
            setSelectedRows(new int[]{});
            break;
          }
      }

      @Override
      public void keyReleased(KeyEvent event) {
        switch (event.keyCode) {
          case SWT.CTRL:
            SwtTree.this.controlDown = false;
            break;
        }
      }
      
    });
    
    // Add a focus listener to clear the contol down selector
    tree.getTree().addFocusListener(new FocusListener(){

      public void focusGained(FocusEvent arg0) {}

      public void focusLost(FocusEvent arg0) {
        if(tree.getCellEditors()[0].isActivated() == false){
         SwtTree.this.controlDown = false;
        }
      }
      
    });
    
    tree.addSelectionChangedListener(new ISelectionChangedListener() {
      public void selectionChanged(SelectionChangedEvent event) {
        // if the selection is empty clear the label
        if (event.getSelection().isEmpty()) {
          SwtTree.this.setSelectedIndex(-1);
          return;
        }
        if (event.getSelection() instanceof IStructuredSelection) {
          IStructuredSelection selection = (IStructuredSelection) event
              .getSelection();

          int[] selected = new int[selection.size()];
          
          int i=0;
          for(Object o : selection.toArray()){
            XulTreeItem selectedItem = (XulTreeItem) o;
            selected[i++] = findSelectedIndex(new SearchBundle(),getRootChildren(), selectedItem).curPos;
          }
          
          if(selected.length == 0){
            setSelectedIndex(-1);
          } else {
            setSelectedIndex(selected[0]);
          }

          if(SwtTree.this.controlDown && Arrays.equals(selected, selectedRows) && 
              tree.getCellEditors()[0].isActivated() == false){
            tree.getTree().deselectAll();
            setSelectedRows(new int[]{});
          } else {
            setSelectedRows(selected);
          }
          
        }
      }
    });

  }

  private class SearchBundle {
    int curPos;
    boolean found;
  }

  private SearchBundle findSelectedIndex(SearchBundle bundle,
      XulTreeChildren children, XulTreeItem selectedItem) {
    for (XulComponent c : children.getChildNodes()) {
      if (c == selectedItem) {
        bundle.found = true;
        return bundle;
      }
      bundle.curPos++;
      if (c.getChildNodes().size() > 1) {
        SearchBundle b = findSelectedIndex(bundle, (XulTreeChildren) c
            .getChildNodes().get(1), selectedItem);
        if (b.found) {
          return b;
        }
      }
    }
    return bundle;
  }


  private void setupTable() {
    table.setContentProvider(new XulTableContentProvider(this));

    table.setLabelProvider(new XulTableColumnLabelProvider(this));
    table.setCellModifier(new XulTableColumnModifier(this));
    Table baseTable = table.getTable();
    baseTable.setLayoutData(new GridData(GridData.FILL_BOTH));

    setupColumns();

    table.addSelectionChangedListener(new ISelectionChangedListener() {
      public void selectionChanged(SelectionChangedEvent event) {
        IStructuredSelection selection = (IStructuredSelection) event
            .getSelection();
        setSelectedIndex(getRootChildren().getChildNodes().indexOf(
            selection.getFirstElement()));
      }
    });

    // Turn on the header and the lines
    baseTable.setHeaderVisible(true);
    baseTable.setLinesVisible(true);
    table.setInput(this);

    final Composite parentComposite = ((Composite) parentComponent
        .getManagedObject());
    parentComposite.addControlListener(new ControlAdapter() {
      public void controlResized(ControlEvent e) {
        resizeColumns();

      }
    });

    table.getTable().setEnabled(! this.disabled);
    table.refresh();

  }

  private void resizeColumns(){
    final Composite parentComposite = ((Composite) parentComponent
      .getManagedObject());
    Rectangle area = parentComposite.getClientArea();
    Point preferredSize = table.getTable().computeSize(SWT.DEFAULT,
        SWT.DEFAULT);
    int width = area.width - 2 * table.getTable().getBorderWidth();
    if (preferredSize.y > area.height + table.getTable().getHeaderHeight()) {
      // Subtract the scrollbar width from the total column width
      // if a vertical scrollbar will be required
      Point vBarSize = table.getTable().getVerticalBar().getSize();
      width -= vBarSize.x;
    }
    width -= 20;
    double totalFlex = 0;
    for (XulComponent col : getColumns().getChildNodes()) {
      totalFlex += ((XulTreeCol) col).getFlex();
    }

    int colIdx = 0;
    for (XulComponent col : getColumns().getChildNodes()) {
      if(colIdx >= table.getTable().getColumnCount()){
        break;
      }
      TableColumn c = table.getTable().getColumn(colIdx);
      int colFlex = ((XulTreeCol) col).getFlex();
      if (totalFlex == 0) {
        c.setWidth(Math.round(width / getColumns().getColumnCount()));
      } else if (colFlex > 0) {
        c.setWidth(Integer.parseInt(""
            + Math.round(width * (colFlex / totalFlex))));
      }
      colIdx++;
    }
  }
  
  private void setSelectedIndex(int idx) {

    this.selectedIndex = idx;
    
    changeSupport.firePropertyChange("selectedRows", null, new int[] { idx });
    if (this.onSelect != null) {

      invoke(this.onSelect, new Object[] { new Integer(idx) });

    }
  }
  
  private void createColumnTypesSnapshot(){
    if(this.columns.getChildNodes().size() > 0){
      Object[] xulTreeColArray = this.columns.getChildNodes().toArray();
      
      currentColumnTypes = new ColumnType[xulTreeColArray.length];
      
      for(int i = 0; i < xulTreeColArray.length; i++){
        currentColumnTypes[i] = ColumnType.valueOf(((XulTreeCol)xulTreeColArray[i]).getType());
      }
    } else {
      // Create an empty array to indicate that it has been processed, but contains 0 columns
      currentColumnTypes = new ColumnType[0];
    }
  }
  
  private boolean columnsNeedUpdate(){
    
    // Differing number of columsn
    if(table.getTable().getColumnCount() != this.columns.getColumnCount()){
      return true;
    }
    
    // First run, always update
    if(currentColumnTypes == null){
      return true;
    }
    
    // Column Types have changed
    Object[] xulTreeColArray = this.columns.getChildNodes().toArray();
    for(int i = 0; i < xulTreeColArray.length; i++){
      if(!currentColumnTypes[i].toString().equalsIgnoreCase(((XulTreeCol)xulTreeColArray[i]).getType())){
        // A column has changed its type. Columns need updating
        return true;
      }
    }
    
    // Columns have not changed and do not need updating
    return false;    
  }

  private void setupColumns() {

    if(columnsNeedUpdate()){
      
      while(table.getTable().getColumnCount() > 0){
        table.getTable().getColumn(0).dispose();
      }
  
      // Add Columns
      for (XulComponent col : this.columns.getChildNodes()) {
        TableColumn tc = new TableColumn(table.getTable(), SWT.LEFT);
        String lbl = ((XulTreeCol) col).getLabel();
        tc.setText(lbl != null ? lbl : ""); //$NON-NLS-1$
      }
  
      // Pack the columns
      for (int i = 0; i < table.getTable().getColumnCount();  i++) {
        table.getTable().getColumn(i).pack();
      }
  

    }

    if (table.getCellEditors() != null) {
      for (int i = 0; i < table.getCellEditors().length; i++) {
        table.getCellEditors()[i].dispose();
      }
    }
    CellEditor[] editors = new CellEditor[this.columns.getChildNodes().size()];
    String[] names = new String[getColumns().getColumnCount()];
    int i = 0;
    for (XulComponent c : this.columns.getChildNodes()) {
      XulTreeCol col = (XulTreeCol) c;
      final int colIdx = i;

      CellEditor editor;
      ColumnType type = col.getColumnType();
      switch (type) {
      case CHECKBOX:
        editor = new CheckboxCellEditor(table.getTable());
        break;
      case COMBOBOX:
        editor = new ComboBoxCellEditor(table.getTable(), new String[] {},
            SWT.READ_ONLY);
        break;
      case EDITABLECOMBOBOX:
        editor = new ComboBoxCellEditor(table.getTable(), new String[] {});
        
        final CCombo editableControl = (CCombo) ((ComboBoxCellEditor) editor).getControl();
        editableControl.addKeyListener(new KeyAdapter(){

          @Override
          public void keyReleased(KeyEvent arg0) {
            super.keyReleased(arg0);

            XulTreeCell cell = getCell(colIdx);
            cell.setLabel(editableControl.getText());
          }
          
        });
        break;
      case TEXT:
      default:
        editor = new TextCellEditor(table.getTable());
        
        final Text textControl = (Text) ((TextCellEditor) editor).getControl();
        textControl.addKeyListener(new KeyAdapter(){

          @Override
          public void keyReleased(KeyEvent arg0) {
            super.keyReleased(arg0);

            XulTreeCell cell = getCell(colIdx);
            cell.setLabel(textControl.getText());
          }
          
        });
        break;
      }
      
      // Create selection listener for comboboxes.
      if(type == ColumnType.EDITABLECOMBOBOX || type == ColumnType.COMBOBOX){
        final CCombo editableControl = (CCombo) ((ComboBoxCellEditor) editor).getControl();
        editableControl.addSelectionListener(new SelectionAdapter(){
          @Override
          public void widgetDefaultSelected(SelectionEvent arg0) {
            // TODO Auto-generated method stub
            super.widgetDefaultSelected(arg0);
          }
  
          @Override
          public void widgetSelected(SelectionEvent arg0) {
            super.widgetSelected(arg0);
            
            XulTreeCell cell = getCell(colIdx);
            
            cell.setSelectedIndex(editableControl.getSelectionIndex());
          }
          
        });
      }
        
      
      editors[i] = editor;
      names[i] = "" + i; //$NON-NLS-1$
      i++;
    }
    table.setCellEditors(editors);

    table.setColumnProperties(names);
    resizeColumns();
    
    createColumnTypesSnapshot();


  }
  
  private XulTreeCell getCell(int colIdx){
    return ((XulTreeItem) (table.getTable().getSelection()[0]).getData()).getRow().getCell(colIdx);

  }

  public boolean isHierarchical() {
    return isHierarchical;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
    if (this.isHierarchical() == false && table != null) {
      table.getTable().setEnabled(!disabled);
    }
  }

  public int getRows() {
    return rowsToDisplay;
  }

  public void setRows(int rowsToDisplay) {
    this.rowsToDisplay = rowsToDisplay;

    if (table != null && (!table.getTable().isDisposed()) && (rowsToDisplay > 0)) {
      int ht = rowsToDisplay * table.getTable().getItemHeight();
      if (table.getTable().getLayoutData() != null) {
        // tree.setSize(tree.getSize().x,height);
        ((GridData) table.getTable().getLayoutData()).heightHint = ht;
        ((GridData) table.getTable().getLayoutData()).minimumHeight = ht;

        table.getTable().getParent().layout(true);
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

  }

  public void setColumns(XulTreeCols columns) {
    this.columns = columns;
  }

  public XulTreeCols getColumns() {
    return columns;
  }

  public XulTreeChildren getRootChildren() {
    if (rootChildren == null) {
      rootChildren = (XulTreeChildren) this.getChildNodes().get(1);
    }
    return rootChildren;
  }

  public void setRootChildren(XulTreeChildren rootChildren) {
    this.rootChildren = rootChildren;
  }

  public int[] getActiveCellCoordinates() {
    return new int[] { activeRow, activeColumn };
  }

  public void setActiveCellCoordinates(int row, int column) {
    activeRow = row;
    activeColumn = column;

  }

  public Object[][] getValues() {

    Object[][] data = new Object[getRootChildren().getChildNodes().size()][getColumns()
        .getColumnCount()];

    int y = 0;
    for (XulComponent item : getRootChildren().getChildNodes()) {
      int x = 0;
      for (XulComponent tempCell : ((XulTreeItem) item).getRow()
          .getChildNodes()) {
        XulTreeCell cell = (XulTreeCell) tempCell;
        switch (columns.getColumn(x).getColumnType()) {
        case CHECKBOX:
          Boolean flag = (Boolean) cell.getValue();
          if (flag == null) {
            flag = Boolean.FALSE;
          }
          data[y][x] = flag;
          break;
        case COMBOBOX:
          Vector values = (Vector) cell.getValue();
          int idx = cell.getSelectedIndex();
          data[y][x] = values.get(idx);
          break;
        default: // label
          data[y][x] = cell.getLabel();
          break;
        }
        x++;

      }

      y++;
    }

    return data;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public int[] getSelectedRows() {
    if (selectedIndex > -1) {
      return new int[] { selectedIndex };
    } else {
      return new int[] {};
    }
  }

  public void addTreeRow(XulTreeRow row) {
    this.addChild(row);
    XulTreeItem item = new SwtTreeItem(this.getRootChildren());
    row.setParentTreeItem(item);
    ((SwtTreeRow) row).layout();
  }

  public void removeTreeRows(int[] rows) {
    // TODO Auto-generated method stub

  }

  public void update() {
    if (this.isHierarchical) {
      this.tree.setInput(this);
      this.tree.refresh();
      if ("true".equals(getAttributeValue("expanded"))) {
        tree.expandAll();
      }
    } else {

      setupColumns();
      this.table.setInput(this);
      this.table.refresh();

    }
    this.selectedIndex = -1;

  }

  public void clearSelection() {
  }

  public void setSelectedRows(int[] rows) {
    if(this.isHierarchical){
      Object selected = getSelectedTreeItem(rows);
      int prevSelected = -1;
      if(selectedRows != null && selectedRows.length > 0){
        prevSelected = selectedRows[0]; // single selection only for now
      }
      changeSupport.firePropertyChange("selectedItem", prevSelected, selected);
    } else {
      table.getTable().setSelection(rows);
    }
    if(rows.length > 0){
      this.selectedIndex = rows[0]; // single selection only for now
    }
    changeSupport.firePropertyChange("selectedRows", this.selectedRows, rows);
    this.selectedRows = rows;

  }

  public String getOnedit() {
    return onedit;
  }

  public void setOnedit(String onedit) {
    this.onedit = onedit;
  }

  private Collection elements;
  private boolean suppressEvents = false;

  public <T> void setElements(Collection<T> elements) {
    this.elements = elements;
    this.getRootChildren().removeAll();

    if (elements == null) {
      update();
      changeSupport.firePropertyChange("selectedRows", null, getSelectedRows());
      return;
    }
    try {

      if (this.isHierarchical == false) {
        for (T o : elements) {
          XulTreeRow row = this.getRootChildren().addNewRow();

          for (int x = 0; x < this.getColumns().getChildNodes().size(); x++) {
            XulComponent col = this.getColumns().getColumn(x);
            final XulTreeCell cell = (XulTreeCell) getDocument().createElement(
                "treecell");
            XulTreeCol column = (XulTreeCol) col;

            for (InlineBindingExpression exp : ((XulTreeCol) col)
                .getBindingExpressions()) {
              logger.debug("applying binding expression [" + exp
                  + "] to xul tree cell [" + cell + "] and model [" + o + "]");

              String colType = column.getType();
              if (StringUtils.isEmpty(colType) == false
                  && colType.equals("dynamic")) {
                colType = extractDynamicColType(o, x);
              }
              Object bob = null;
              if ((colType.equalsIgnoreCase("combobox")
                  || colType.equalsIgnoreCase("editablecombobox"))
                  && column.getCombobinding() != null) {
                DefaultBinding binding = new DefaultBinding(o, column
                    .getCombobinding(), cell, "value");
                binding.setBindingType(Binding.Type.ONE_WAY);
                domContainer.addBinding(binding);
                binding.fireSourceChanged();

                binding = new DefaultBinding(o,
                    ((XulTreeCol) col).getBinding(), cell, "selectedIndex");
                binding.setConversion(new BindingConvertor<Object, Integer>() {

                  @Override
                  public Integer sourceToTarget(Object value) {
                    int index = ((Vector) cell.getValue()).indexOf(value);
                    return index > -1 ? index : 0;
                  }

                  @Override
                  public Object targetToSource(Integer value) {
                    return ((Vector) cell.getValue()).get(value);
                  }

                });
                domContainer.addBinding(binding);
                binding.fireSourceChanged();

                if (colType.equalsIgnoreCase("editablecombobox")) {
                  binding = new DefaultBinding(o, exp.getModelAttr(), cell, exp
                      .getXulCompAttr());
                  if (!this.editable) {
                    binding.setBindingType(Binding.Type.ONE_WAY);
                  } else {
                    binding.setBindingType(Binding.Type.BI_DIRECTIONAL);
                  }
                  domContainer.addBinding(binding);
                }

              } else if (colType.equalsIgnoreCase("checkbox")) {
                if (StringUtils.isNotEmpty(exp.getModelAttr())) {
                  DefaultBinding binding = new DefaultBinding(o, exp
                      .getModelAttr(), cell, "value");
                  if (!column.isEditable()) {
                    binding.setBindingType(Binding.Type.ONE_WAY);
                  }
                  domContainer.addBinding(binding);
                  binding.fireSourceChanged();
                }
              } else {

                if (StringUtils.isNotEmpty(exp.getModelAttr())) {
                  DefaultBinding binding = new DefaultBinding(o, exp
                      .getModelAttr(), cell, exp.getXulCompAttr());
                  if (!column.isEditable()) {
                    binding.setBindingType(Binding.Type.ONE_WAY);
                  }
                  domContainer.addBinding(binding);
                  binding.fireSourceChanged();
                }
              }

            }
            if (column.getDisabledbinding() != null) {
              String prop = column.getDisabledbinding();
              DefaultBinding bind = new DefaultBinding(o, column
                  .getDisabledbinding(), cell, "disabled");
              bind.setBindingType(Binding.Type.ONE_WAY);
              domContainer.addBinding(bind);
              bind.fireSourceChanged();
            }

            row.addCell(cell);
          }
        }
      } else {// tree

        for (T o : elements) {
          SwtTreeItem item = new SwtTreeItem(this.getRootChildren());
          item.setXulDomContainer(this.domContainer);
          
          SwtTreeRow newRow = new SwtTreeRow(item);
          item.setRow(newRow);
          this.getRootChildren().addChild(item);
          
          addTreeChild(o, newRow);
        }

      }

      update();
      suppressEvents = false;

      // treat as a selection change
      changeSupport.firePropertyChange("selectedRows", null, getSelectedRows());
    } catch (XulException e) {
      logger.error("error adding elements", e);
    } catch (Exception e) {
      logger.error("error adding elements", e);
    }
  }

  private <T> void addTreeChild(T element, XulTreeRow row) {
    try {
      XulTreeCell cell = (XulTreeCell) getDocument().createElement("treecell");

      for (InlineBindingExpression exp : ((XulTreeCol) this.getColumns()
          .getChildNodes().get(0)).getBindingExpressions()) {
        logger.debug("applying binding expression [" + exp
            + "] to xul tree cell [" + cell + "] and model [" + element + "]");

        // Tree Bindings are one-way for now as you cannot edit tree nodes
        DefaultBinding binding = new DefaultBinding(element,
            exp.getModelAttr(), cell, exp.getXulCompAttr());
        if(this.isEditable()){
          binding.setBindingType(Binding.Type.BI_DIRECTIONAL);
        } else {
          binding.setBindingType(Binding.Type.ONE_WAY);
        }
        domContainer.addBinding(binding);
        binding.fireSourceChanged();
      }

      row.addCell(cell);

      // find children
      String property = ((XulTreeCol) this.getColumns().getChildNodes().get(0)).getChildrenbinding();
      property = "get" + (property.substring(0, 1).toUpperCase() + property.substring(1));
      
      Method childrenMethod = element.getClass().getMethod(property,
          new Class[] {});
      Method imageMethod;
      String imageSrc = null;
      
      property = ((XulTreeCol) this.getColumns().getChildNodes().get(0)).getImagebinding();
      
      if(property != null){
        property = "get" + (property.substring(0, 1).toUpperCase() + property.substring(1));
        imageMethod = element.getClass().getMethod(property);
        imageSrc = (String) imageMethod.invoke(element);

        ((XulTreeItem) row.getParent()).setImage(imageSrc);
        
      }
      
      Collection<T> children = (Collection<T>) childrenMethod.invoke(element, new Object[] {});
      
      XulTreeChildren treeChildren = null;
      
      if (children != null && children.size() > 0) {
        treeChildren = (XulTreeChildren) getDocument().createElement(
            "treechildren");
        row.getParent().addChild(treeChildren);
      }

      for (T child : children) {

        SwtTreeItem item = new SwtTreeItem(treeChildren);
        item.setXulDomContainer(this.domContainer);
        
        SwtTreeRow newRow = new SwtTreeRow(item);
        item.setRow(newRow);
        treeChildren.addChild(item);
        
        addTreeChild(child, newRow);
      }
    } catch (Exception e) {
      logger.error("error adding elements", e);
    }
  }

  public <T> Collection<T> getElements() {
    return null;
  }

  private String extractDynamicColType(Object row, int columnPos) {
    try {
      Method method = row.getClass().getMethod(
          this.columns.getColumn(columnPos).getColumntypebinding());
      return (String) method.invoke(row, new Object[] {});
    } catch (Exception e) {
      logger.debug("Could not extract column type from binding");
    }
    return "text"; // default //$NON-NLS-1$
  }

  

  public Object getSelectedItem() {
    // TODO Auto-generated method stub
    return null;
  }

  private Object getSelectedTreeItem(int[] currentSelection) {
    if(this.isHierarchical && this.elements != null){

      int[] vals = currentSelection;
      if(vals == null || vals.length == 0 || elements == null || elements.size() == 0){
        return null;
      }

      String property = ((XulTreeCol) this.getColumns().getChildNodes().get(0)).getChildrenbinding();
      property = "get"+(property.substring(0,1).toUpperCase() + property.substring(1));
      
      int selectedIdx = vals[0];
      if (selectedIdx == -1) {
        return null;
      }
      FindSelectedItemTuple tuple = findSelectedItem(this.elements, property, new FindSelectedItemTuple(selectedIdx));
      return tuple != null ? tuple.selectedItem : null;
    } 
    return null;
  }
  
  private void fireSelectedItem(){
    this.changeSupport.firePropertyChange("selectedItem", null, getSelectedItem());
  }
  
  private static class FindSelectedItemTuple{
    Object selectedItem = null;
    int curpos = -1; //ignores first element (root)
    int selectedIndex;
    
    public FindSelectedItemTuple(int selectedIndex){
      this.selectedIndex = selectedIndex;
    }
  }
  
  private FindSelectedItemTuple findSelectedItem(Object parent, String childrenMethodProperty, FindSelectedItemTuple tuple){
    if(tuple.curpos == tuple.selectedIndex){
      tuple.selectedItem = parent;
      return tuple;
    }
    Collection children = null;
    Method childrenMethod = null;
    try{
      childrenMethod = parent.getClass().getMethod(childrenMethodProperty, new Class[]{});
    } catch(NoSuchMethodException e){
      if(parent instanceof Collection){
        children = (Collection) parent;
      }
    }
    try{
      if(childrenMethod != null){
        children = (Collection) childrenMethod.invoke(parent, new Object[]{});
      } 
    } catch(Exception e){
      logger.error(e);
      return null;
    }
    
    if(children == null || children.size() == 0){
      return null;
    }
    
    for(Object child : children){
      tuple.curpos++;
      findSelectedItem(child, childrenMethodProperty, tuple);
      if(tuple.selectedItem != null){
        return tuple;
      }
    }
    return null;
  }

  public void registerCellEditor(String key, TreeCellEditor editor) {
    // TODO Auto-generated method stub
    
  }

  public void registerCellRenderer(String key, TreeCellRenderer renderer) {
    // TODO Auto-generated method stub
    
  }
  
  
  
  
  

}
