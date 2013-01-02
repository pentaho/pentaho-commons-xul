package org.pentaho.ui.xul.swing.tags;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.CellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.CellEditorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.*;
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
import org.pentaho.ui.xul.swing.AbstractSwingContainer;
import org.pentaho.ui.xul.util.ColumnType;
import org.pentaho.ui.xul.util.TreeCellEditorCallback;
import org.pentaho.ui.xul.util.TreeCellRenderer;

public class SwingTree extends AbstractSwingContainer implements XulTree {

  private JTable table;

  private JTree tree;

  private ListSelectionListener selectionListener;
  private TreeSelectionListener treeSelectionListener;

  private JScrollPane scrollpane;

  private TableColumnModel columnModel;

  private TableModel tableModel;

  private XulTreeChildren rootChildren;

  private boolean columnDrag = true;

  private boolean disabled = false;

  private boolean editable = false;

  private String onselect;

  private String onedit = null;

  private static final Log logger = LogFactory.getLog(SwingTree.class);

  private Vector<String> columnNames = new Vector<String>();

  private boolean isHierarchical = false;

  private Map<String, org.pentaho.ui.xul.util.TreeCellEditor> customEditors = new HashMap<String, org.pentaho.ui.xul.util.TreeCellEditor>();

  // TODO: migrate to XulTree interface
  public enum SELECTION_MODE {
    SINGLE, CELL, MULTIPLE, NONE
  };

  private SELECTION_MODE selType = SELECTION_MODE.SINGLE;

  private XulDomContainer domContainer;

  private String command;

  private String newItemBinding;

  private boolean autoCreateNewRows;


  public SwingTree(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("tree");

    this.domContainer = domContainer;

    this.registerCellEditor("custom-editor", new CustomTreeCellEditor());

  }

  public JTable getTable() {
    return table;
  }

  public JTree getTree() {
    return tree;
  }

  public int[] getActiveCellCoordinates() {
    return new int[] { table.getSelectedRow(), table.getSelectedColumn() };

  }

  public XulTreeCols getColumns() {
    return this.columns;
  }

  public String getOnselect() {
    return this.onselect;
  }

  public XulTreeChildren getRootChildren() {
    if (rootChildren == null) {
      rootChildren = (XulTreeChildren) this.getChildNodes().get(1);
    }
    return rootChildren;
  }

  public int getRows() {
    return getRootChildren().getItemCount();
  }

  public String getSeltype() {
    return selType.toString();
  }

  public Object[][] getValues() {

    TableModel model = table.getModel();
    Object[][] data = new Object[getRootChildren().getChildNodes().size()][model.getColumnCount()];

    int y = 0;
    for (XulComponent item : getRootChildren().getChildNodes()) {
      int x = 0;
      for (XulComponent tempCell : ((XulTreeItem) item).getRow().getChildNodes()) {
        SwingTreeCell cell = (SwingTreeCell) tempCell;
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

    // for(int row=0; row<this.rootChildren.getRowCount(); row++){
    // for(int col=0; col<model.getColumnCount(); col++){
    // data[row][col] = model.getValueAt(row,col);
    // }
    // }
    return data;
  }

  public int getWidth() {
    return scrollpane.getWidth();
  }

  public void setWidth(int width) {
    if (tree == null || table == null) {
      return;
    }
    int height = (table != null) ? table.getHeight() : (tree != null) ? tree.getHeight() : scrollpane.getHeight();
    Dimension dim = new Dimension(width, height);
    scrollpane.setPreferredSize(dim);
    scrollpane.setMaximumSize(dim);
    scrollpane.setSize(dim);
  }

  public boolean isDisabled() {
    return disabled;
  }

  public boolean isEditable() {
    return editable;
  }

  public boolean isEnableColumnDrag() {
    return this.columnDrag;
  }

  public boolean isHierarchical() {
    // TODO Auto-generated method stub
    return false;
  }

  public void setActiveCellCoordinates(int row, int column) {
    table.changeSelection(row, column, false, false);
  }

  public void setDisabled(boolean dis) {
    this.disabled = dis;
    if (table != null) {
      table.setEnabled(!this.disabled);
    }
  }

  public void setEditable(boolean edit) {
    this.editable = edit;
  }

  public void setEnableColumnDrag(boolean drag) {
    this.columnDrag = drag;
    if (table != null) {
      table.getTableHeader().setReorderingAllowed(drag);
    }
  }

  public void setOnselect(final String select) {
    selectionListener = new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent event) {
        if (event.getValueIsAdjusting() == true) {
          return;
        }
        invoke(select, new Object[] { new Integer(table.getSelectedRow()) });
      }
    };

    if (table != null) {
      table.getSelectionModel().addListSelectionListener(selectionListener);
    }

    treeSelectionListener = new TreeSelectionListener() {
      public void valueChanged(TreeSelectionEvent e) {
        if (tree != null) {
          invoke(select, new Object[] { tree.getSelectionRows()[0]});
        }
      }
    };

    if (tree != null) {
      tree.getSelectionModel().addTreeSelectionListener(treeSelectionListener);
    }
  }

  private XulTreeCols columns;

  public void setColumns(XulTreeCols columns) {
    this.columns = columns;
  }

  public void setRootChildren(final XulTreeChildren rootChildren) {
    if (this.rootChildren == null) {
      this.rootChildren = rootChildren;
    }
    // for (XulComponent row : rootChildren.getChildNodes()) {
    //
    // XulTreeRow xrow = (XulTreeRow) row.getChildNodes().get(0);
    // addTreeRow(xrow);
    //
    // }

  }

  public void addTreeRow(XulTreeRow row) {

    getRootChildren().addItem(new SwingTreeItem(row));

//    table.updateUI();
  }

  public void removeTreeRows(int[] rows) {

    // sort the rows high to low
    ArrayList<Integer> rowArray = new ArrayList<Integer>();
    for (int i = 0; i < rows.length; i++) {
      rowArray.add(rows[i]);
    }
    Collections.sort(rowArray, Collections.reverseOrder());

    // remove the items in that order
    for (int i = 0; i < rowArray.size(); i++) {
      int item = rowArray.get(i);
      if (item >= 0 && item < getRootChildren().getItemCount()) {
        getRootChildren().removeItem(item);
      }
    }

//    table.updateUI();
    // treat as selection change.
    firePropertyChange("selectedRows", null, getSelectedRows());
    firePropertyChange("absoluteSelectedRows", null, getAbsoluteSelectedRows());
  }

  private int rows = -1;

  public void setRows(int rows) {
    this.rows = rows;
  }

  public void setSeltype(String type) {
    this.selType = SELECTION_MODE.valueOf(type.toUpperCase());
    if (table == null) {
      return;
    }
    switch (this.selType) {
    case CELL:
      table.setCellSelectionEnabled(true);
      break;
    case MULTIPLE:
      table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
      break;
    case SINGLE:
      table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      break;

    }
  }

  public void setTableModel(TableModel model) {
    this.tableModel = model;

  }

  private int totalFlex = 0;

  @Override
  public void layout() {

    XulComponent primaryColumn = this.getElementByXPath("//treecol[@primary='true']");
    XulComponent isaContainer = this.getElementByXPath("treechildren/treeitem[@container='true']");

    isHierarchical = (primaryColumn != null) || (isaContainer != null);

    if (isHierarchical) {
      tree = new JTree();
      if (treeSelectionListener != null) {
        tree.getSelectionModel().addTreeSelectionListener(treeSelectionListener);
      }
      tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
        public void valueChanged(TreeSelectionEvent e) {
          SwingTree.this.firePropertyChange("selectedRows", null, SwingTree.this.getSelectedRows());
          SwingTree.this.firePropertyChange("absoluteSelectedRows", null, SwingTree.this.getAbsoluteSelectedRows());
          SwingTree.this.fireSelectedItem();
        }
      });
    } else {
      table = new JTable();
      if (selectionListener != null) {
        table.getSelectionModel().addListSelectionListener(selectionListener);
      }
    }

    JComponent comp = (table != null ? table : tree);
    ToolTipManager.sharedInstance().unregisterComponent(comp);
    if (table != null) {
      ToolTipManager.sharedInstance().unregisterComponent(table.getTableHeader());

      table.setRowHeight(18);
      scrollpane = new JScrollPane(table);
      setupTable();

    } else {
      setupTree();
      scrollpane = new JScrollPane(tree);
    }

    setManagedObject(scrollpane.getViewport());
  }

  int numOfListeners = 0;

  private void updateColumnModel() {
    TableColumnModel columnModel = table.getColumnModel();
    totalFlex = 0;

    for (int i = 0; i < columns.getChildNodes().size(); i++) {
      if (i >= columnModel.getColumnCount()) {
        break;
      }

      SwingTreeCol child = (SwingTreeCol) columns.getChildNodes().get(i);
      TableColumn col = columnModel.getColumn(i);

      totalFlex += child.getFlex();

      col.setHeaderValue(child.getLabel());

      col.setCellEditor(getCellEditor(child));

      col.setCellRenderer(getCellRenderer(child));

      // List<XulComponent> cells = child.getChildNodes();
      // for(int z=0; z<cells.size(); z++){
      // XulComponent cell = cells.get(z);
      // }
    }
  }

  private void setupTable() {

    // generate table object based on TableModel and TableColumnModel

    // if(tableModel == null){
    tableModel = new XulTableModel(this);
    // }
    table.setModel(this.tableModel);
    this.setSeltype(getSeltype());

    updateColumnModel();

    initialized = true;

    table.addComponentListener(new ComponentListener() {
      public void componentHidden(ComponentEvent arg0) {
      }

      public void componentMoved(ComponentEvent e) {
      }

      public void componentShown(ComponentEvent e) {
      }

      public void componentResized(ComponentEvent e) {
        calcColumnWidths();
      }
    });

    // Property change on selections
    table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

      public void valueChanged(ListSelectionEvent event) {
        if (event.getValueIsAdjusting() == true) {
          return;
        }
        SwingTree.this.firePropertyChange("selectedRows", null, SwingTree.this.getSelectedRows());
        SwingTree.this.firePropertyChange("absoluteSelectedRows", null, SwingTree.this.getAbsoluteSelectedRows());
      }
    });

    table.getTableHeader().setReorderingAllowed(this.isEnableColumnDrag());

    this.setDisabled(this.isDisabled());
  }

  private void calcColumnWidths() {
    Rectangle size = table.getBounds();
    int newWidth = size.width;
    if (SwingTree.this.rows > -1) {
      int minHeight = table.getRowHeight() * rows;
      scrollpane.getViewport().setMinimumSize(new Dimension(scrollpane.getWidth(), minHeight - 100));
    }
    for (int i = 0; i < table.getColumnCount(); i++) {
      int newColWidth = 50; // reasonable default size
      if (totalFlex > 0) {
        int flex = SwingTree.this.columns.getColumn(table.getColumnModel().getColumn(i).getModelIndex()).getFlex();
        if (flex != 0) {
          newColWidth = (int) (newWidth * ((double) flex / totalFlex));
        }
      } else {
        newColWidth = (int) (newWidth * ((double) 1 / table.getColumnCount()));

      }

      table.getColumnModel().getColumn(i).setWidth(newColWidth);
      table.getColumnModel().getColumn(i).setPreferredWidth(newColWidth);

      table.getColumnModel().getColumn(i).setMinWidth(newColWidth);
    }
  }

  private TreeModel treeModel;

  private class XulTreeNode extends DefaultMutableTreeNode {
    XulTreeItem item = null;

    public XulTreeNode(Object obj, XulTreeItem item) {
      super(obj);
      this.item = item;
    }
  }

  private void setupTree() {
    XulTreeNode topNode = new XulTreeNode("placeholder", null);
    for (XulComponent c : getRootChildren().getChildNodes()) {
      XulTreeItem item = (XulTreeItem) c;
      XulTreeNode node = createNode(item);
      if (node != null) {
        topNode.add(node);
      }
    }
    treeModel = new XulTreeModel(topNode);
    tree.setModel(treeModel);
    tree.setRootVisible(false);
    DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
    renderer.setLeafIcon(null);
    renderer.setClosedIcon(null);
    renderer.setOpenIcon(null);
    tree.setCellRenderer(renderer);
    tree.setShowsRootHandles(true);
    this.setExpanded(this.getExpanded());

  }

  private XulTreeNode createNode(XulTreeItem item) {
    if (item.getRow().getCell(0) == null) {
      return null;
    }
    XulTreeNode node = new XulTreeNode(item.getRow().getCell(0).getLabel(), item);
    if (item.getChildNodes().size() > 1) {
      // has children
      // TODO: make this more defensive
      XulTreeChildren children = (XulTreeChildren) item.getChildNodes().get(1);
      for (XulComponent c : children.getChildNodes()) {

        XulTreeNode childNode = createNode((XulTreeItem) c);
        if (childNode != null) {
          node.add(childNode);
        }
      }
    }
    return node;
  }

  @Override
  public void replaceChild(XulComponent oldElement, XulComponent newElement) throws XulDomException {
    // TODO Auto-generated method stub
    super.replaceChild(oldElement, newElement);
  }

  @Override
  public Object getManagedObject() {
    return scrollpane;
  }

  public int[] getSelectedRows() {
    // FIXME: A selection followed by removals with no new UI interaction returns the previous
    // selection even if the row count is now less.

    if (table != null) {
      int[] tempSelection = table.getSelectedRows();
      List<Integer> clensedSelection = new ArrayList<Integer>();
      for (int row : tempSelection) {
        if (row < table.getModel().getRowCount()) {
          clensedSelection.add(row);
        }
      }

      // no automatic unboxing :(
      int[] returnArray = new int[clensedSelection.size()];

      int idx = 0;
      for (int row : clensedSelection) {
        returnArray[idx] = row;
        idx++;
      }

      return returnArray;
    } else {
      int[] vals = tree.getSelectionRows();
      if (vals == null) {
        return new int[] {};
      }
      return vals;
    }

  }

  public int[] getAbsoluteSelectedRows() {
    // FIXME: A selection followed by removals with no new UI interaction returns the previous
    // selection even if the row count is now less.
    if (table != null) {
      return getSelectedRows();
    } else {
      ArrayList<Boolean> expandedState = new ArrayList<Boolean>();
      for (int i = 0; i < tree.getRowCount(); i++) {
        expandedState.add(tree.isExpanded(i));
      }
      for (int i = tree.getRowCount() - 1; i >= 0; i--) {
        tree.expandRow(i);
      }
      int[] vals = tree.getSelectionRows();
      for (int i = 0; i < expandedState.size(); i++) {
        if (expandedState.get(i)) {
          tree.expandRow(i);
        } else {
          tree.collapseRow(i);
        }
      }
      if (vals == null) {
        return new int[] {};
      }
      return vals;
    }
  }

  public void addRow(XulTreeRow row) {
    SwingTreeItem item = new SwingTreeItem(row);

    getRootChildren().addItem(item);

  }

  public Object getData() {
    return null;
  }

  public void setData(Object data) {

  }

  public void update() {
    if (!suppressEvents) {
      if (table != null) {
        table.setModel(new XulTableModel(this));
        updateColumnModel();
        calcColumnWidths();
//        table.updateUI();
      } else {
        setupTree();
//        tree.updateUI();
      }
    }
  }

  private String extractDynamicColType(Object row, int columnPos) {
    try {
      String rawMethod = this.columns.getColumn(columnPos).getColumntypebinding();

      StringBuilder methodName = new StringBuilder();

      methodName.append("get");
      methodName.append(rawMethod.substring(0, 1).toUpperCase());
      methodName.append(rawMethod.substring(1));

      Method method = row.getClass().getMethod(methodName.toString());
      return ((String) method.invoke(row, new Object[] {})).toUpperCase();
    } catch (Exception e) {
      logger.warn("Could not extract column type from binding");
    }
    return "text"; // default //$NON-NLS-1$
  }

  private TableCellRenderer getCellRenderer(final SwingTreeCol col) {

    return new DefaultTableCellRenderer() {

      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        ColumnType colType = col.getColumnType();
        if (colType == ColumnType.DYNAMIC) {
          colType = ColumnType.valueOf(extractDynamicColType(elements.toArray()[row], column));
        }

        final XulTreeCell cell = getRootChildren().getItem(row).getRow().getCell(column);
        switch (colType) {
        case CHECKBOX:
          JCheckBox checkbox = new JCheckBox();
          if (value instanceof String) {
            checkbox.setSelected(((String) value).equalsIgnoreCase("true"));
          } else if (value instanceof Boolean) {
            checkbox.setSelected((Boolean) value);
          } else if (value == null) {
            checkbox.setSelected(false);
          }
          if (isSelected) {
            checkbox.setBackground(Color.LIGHT_GRAY);
          }
          checkbox.setEnabled(!cell.isDisabled());
          return checkbox;
        case COMBOBOX:
        case EDITABLECOMBOBOX:
          Vector data;

          final JComboBox comboBox = new JComboBox();
          if (cell == null) {
          } else {
            data = (cell.getValue() != null) ? (Vector) cell.getValue() : new Vector();

            if (data == null) {
              logger.debug("SwingTreeCell combobox data is null, passed in value: " + value);
              if (value instanceof Vector) {
                data = (Vector) value;
              }
            }
            if (data != null) {
              comboBox.setModel(new DefaultComboBoxModel(data));
              try {
                comboBox.setSelectedIndex(cell.getSelectedIndex());
              } catch (Exception e) {
                logger.error("error setting selected index on the combobox editor");
              }
            }
          }

          if (colType == ColumnType.EDITABLECOMBOBOX) {
            comboBox.setEditable(true);
            ((JTextComponent) comboBox.getEditor().getEditorComponent()).setText(cell.getLabel());
          }

          if (isSelected) {
            comboBox.setBackground(Color.LIGHT_GRAY);
          }
          comboBox.setEnabled(!cell.isDisabled());
          return comboBox;
        case CUSTOM:
          return new CustomCellEditorWrapper(cell, customEditors.get(col.getType()));
        default:
          JLabel label = new JLabel((String) value);

          if (isSelected) {
            label.setOpaque(true);
            label.setBackground(Color.LIGHT_GRAY);
          }
          return label;
        }

      }
    };

  }

  private TableCellEditor getCellEditor(final SwingTreeCol col) {
    return new SwingTreeCellEditor(col);
  }

  private class XulTreeModel extends DefaultTreeModel {
    public XulTreeModel(TreeNode root) {
      super(root);
    }
  }

  private class XulTableModel extends AbstractTableModel {

    SwingTree tree = null;

    public XulTableModel(SwingTree tree) {
      this.tree = tree;
    }

    public void update() {

    }

    public int getColumnCount() {
      return this.tree.getColumns().getColumnCount();
    }

    public int getRowCount() {
      if (this.tree != null) {
        XulTreeChildren children = this.tree.getRootChildren();
        return (children != null) ? children.getItemCount() : 0;
      } else {
        return 0;
      }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {

      XulTreeCol col = tree.getColumns().getColumn(columnIndex);
      XulTreeCell cell = this.tree.getRootChildren().getItem(rowIndex).getRow().getCell(columnIndex);
      if (cell == null) {
        return null;
      }

      ColumnType colType = col.getColumnType();
      if (colType == ColumnType.DYNAMIC) {
        colType = ColumnType.valueOf(extractDynamicColType(elements.toArray()[rowIndex], columnIndex));
      }

      try {
        switch (colType) {
        case CHECKBOX:
          if (cell.getValue() != null) {
            return cell.getValue();
          }
        case COMBOBOX:
        case EDITABLECOMBOBOX:
          if (cell.getValue() != null) {
            return cell.getValue();
          }
        default:
          return cell.getLabel();
        }
      } catch (Exception e) {
        logger.error("Error getting value of cell at row:" + rowIndex + " column:" + columnIndex, e);

      }
      return null;
    }

    @Override
    public int findColumn(String columnName) {
      for (int i = 0; i < tree.getColumns().getColumnCount(); i++) {
        if (tree.getColumns().getColumn(i).getName().toUpperCase().equals(columnName.toUpperCase())) {
          return i;
        }
      }
      return -1;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
      switch (tree.getColumns().getColumn(columnIndex).getColumnType()) {
      case CHECKBOX:
        return Boolean.class;
      case COMBOBOX:
      case EDITABLECOMBOBOX:
        return Vector.class;
      default:
        return String.class;

      }
    }

    @Override
    public String getColumnName(int column) {
      return tree.getColumns().getColumn(column).getLabel();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
      return tree.getColumns().getColumn(columnIndex).isEditable();
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
      if (onedit != null && !suppressEvents) {
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            invoke(onedit, new Object[] { new Integer(table.getSelectedRow()) });
          }
        });
      }
      XulTreeItem row = this.tree.getRootChildren().getItem(rowIndex);
      if (row == null) {
        logger.info("Row removed, setVal returning");
        return;
      }
      XulTreeCell cell = row.getRow().getCell(columnIndex);

      ColumnType colType = tree.getColumns().getColumn(columnIndex).getColumnType();

      if (colType == ColumnType.DYNAMIC) {
        colType = ColumnType.valueOf(extractDynamicColType(elements.toArray()[rowIndex], columnIndex));
      }

      switch (colType) {
      case CHECKBOX:
        cell.setValue((Boolean) value);
        break;
      case COMBOBOX:
      case EDITABLECOMBOBOX:
        if (value instanceof String) {
          cell.setLabel((String) value);
        } else if (((Integer) value) > -1) {
          cell.setSelectedIndex((Integer) value);
          cell.setLabel(((Vector) cell.getValue()).get((Integer) value).toString());
        }
        break;
      default:
        cell.setLabel((String) value);

      }
    }

  }

  public void clearSelection() {
    table.getSelectionModel().clearSelection();
    CellEditor ce = table.getCellEditor();
    if (ce != null) {
      ce.stopCellEditing();
    }
  }

  public void setSelectedRows(int[] rows) {
    if (isHierarchical) {
      tree.setSelectionRows(rows);
    } else {
      table.clearSelection();
      for (int row : rows) {
        table.changeSelection(row, -1, false, true);
      }
    }
  }

  public String getOnedit() {
    return onedit;
  }

  public void setOnedit(String onedit) {
    this.onedit = onedit;
  }

  private boolean suppressEvents = false;
  private Collection elements;

  private boolean expanded;

  public <T> void setElements(Collection<T> elements) {
    suppressEvents = true;
    this.elements = elements;
    this.getRootChildren().removeAll();

    // active editor needs updating, but won't if still active
    if (table != null) {
      CellEditor ce = table.getCellEditor();
      if (ce != null) {
        ce.stopCellEditing();
      }
    }

    if (elements == null) {
      if (table != null) {
//        table.updateUI();
      } else {
//        tree.updateUI();
      }
      firePropertyChange("selectedRows", null, getSelectedRows());
      firePropertyChange("absoluteSelectedRows", null, getAbsoluteSelectedRows());
      return;
    }
    try {

      if (table != null) {
        for (T o : elements) {
          XulTreeRow row = this.getRootChildren().addNewRow();

          for (int x = 0; x < this.getColumns().getChildNodes().size(); x++) {
            XulComponent col = this.getColumns().getColumn(x);
            final XulTreeCell cell = (XulTreeCell) getDocument().createElement("treecell");
            XulTreeCol column = (XulTreeCol) col;

            for (InlineBindingExpression exp : ((XulTreeCol) col).getBindingExpressions()) {
              logger.debug("applying binding expression [" + exp + "] to xul tree cell [" + cell + "] and model [" + o + "]");

              String colType = column.getType();
              if (StringUtils.isEmpty(colType) == false && colType.equalsIgnoreCase("dynamic")) {
                colType = extractDynamicColType(o, x);
              }

              if (colType == null) {
                if (StringUtils.isNotEmpty(exp.getModelAttr())) {
                  Binding binding = createBinding((XulEventSource) o, exp.getModelAttr(), cell, exp.getXulCompAttr());
                  if (!this.editable) {
                    binding.setBindingType(Binding.Type.ONE_WAY);
                  }
                  domContainer.addBinding(binding);
                  binding.fireSourceChanged();
                }
              } else if ((colType.equalsIgnoreCase("combobox") || colType.equalsIgnoreCase("editablecombobox")) && column.getCombobinding() != null) {
                Binding binding = createBinding((XulEventSource) o, column.getCombobinding(), cell, "value");
                binding.setBindingType(Binding.Type.ONE_WAY);
                domContainer.addBinding(binding);
                binding.fireSourceChanged();

                binding = createBinding((XulEventSource) o, ((XulTreeCol) col).getBinding(), cell, "selectedIndex");
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

                  binding = createBinding((XulEventSource) o, exp.getModelAttr(), cell, exp.getXulCompAttr());
                  if (!this.editable) {
                    binding.setBindingType(Binding.Type.ONE_WAY);
                  } else {
                    binding.setBindingType(Binding.Type.BI_DIRECTIONAL);
                  }
                  domContainer.addBinding(binding);
                }

              } else if (colType.equalsIgnoreCase("checkbox")) {

                if (StringUtils.isNotEmpty(exp.getModelAttr())) {
                  Binding binding = createBinding((XulEventSource) o, exp.getModelAttr(), cell, "value");

                  if (!this.editable) {
                    binding.setBindingType(Binding.Type.ONE_WAY);
                  }
                  domContainer.addBinding(binding);
                  binding.fireSourceChanged();
                }

              } else if (colType != null && this.customEditors.containsKey(colType)) {

                Binding binding = createBinding((XulEventSource) o, exp.getModelAttr(), cell, "value");
                binding.setBindingType(Binding.Type.BI_DIRECTIONAL);
                domContainer.addBinding(binding);
                binding.fireSourceChanged();

              } else {
                if (StringUtils.isNotEmpty(exp.getModelAttr())) {
                  Binding binding = createBinding((XulEventSource) o, exp.getModelAttr(), cell, exp.getXulCompAttr());
                  if (!this.editable) {
                    binding.setBindingType(Binding.Type.ONE_WAY);
                  }
                  domContainer.addBinding(binding);
                  binding.fireSourceChanged();
                }
              }

            }
            if (column.getDisabledbinding() != null) {
              String prop = column.getDisabledbinding();
              Binding bind = createBinding((XulEventSource) o, column.getDisabledbinding(), cell, "disabled");
              bind.setBindingType(Binding.Type.ONE_WAY);
              domContainer.addBinding(bind);
              bind.fireSourceChanged();
            }

            row.addCell(cell);
          }
        }
      } else {// tree

        for (T o : elements) {
          XulTreeRow row = this.getRootChildren().addNewRow();
          addTreeChild(o, row);
        }

      }

      if (table != null) {
//        table.updateUI();
      } else {
        setupTree();
//        tree.updateUI();
      }
      suppressEvents = false;

      // treat as a selection change
      firePropertyChange("selectedRows", null, getSelectedRows());
      firePropertyChange("absoluteSelectedRows", null, getAbsoluteSelectedRows());
    } catch (XulException e) {
      logger.error("error adding elements", e);
    } catch (Exception e) {
      logger.error("error adding elements", e);
    }
  }

  private <T> void addTreeChild(T element, XulTreeRow row) {
    try {
      XulTreeCell cell = (XulTreeCell) getDocument().createElement("treecell");

      for (InlineBindingExpression exp : ((XulTreeCol) this.getColumns().getChildNodes().get(0)).getBindingExpressions()) {
        logger.debug("applying binding expression [" + exp + "] to xul tree cell [" + cell + "] and model [" + element + "]");

        // Tree Bindings are one-way for now as you cannot edit tree nodes
        Binding binding = createBinding((XulEventSource) element, exp.getModelAttr(), cell, exp.getXulCompAttr());
        binding.setBindingType(Binding.Type.ONE_WAY);
        domContainer.addBinding(binding);
        binding.fireSourceChanged();
      }

      row.addCell(cell);

      // find children
      String property = ((XulTreeCol) this.getColumns().getChildNodes().get(0)).getChildrenbinding();
      property = "get" + (property.substring(0, 1).toUpperCase() + property.substring(1));

      Method childrenMethod = null;

      try{
        childrenMethod = element.getClass().getMethod(property, new Class[] {});
      } catch(NoSuchMethodException e){
        logger.debug("Could not find children binding method for object: "+element.getClass().getSimpleName());
      }


      Collection<T> children = null;
      if(childrenMethod != null){
        children = (Collection<T>) childrenMethod.invoke(element, new Object[] {});
      } else if(element instanceof Collection ){
        children = (Collection<T>) element;
      }

      XulTreeChildren treeChildren = null;

      if (children != null && children.size() > 0) {
        treeChildren = (XulTreeChildren) getDocument().createElement("treechildren");
        row.getParent().addChild(treeChildren);
      }
      for (T child : children) {
        row = treeChildren.addNewRow();
        addTreeChild(child, row);
      }
    } catch (Exception e) {
      logger.error("error adding elements", e);
    }
  }

  public <T> Collection<T> getElements() {
    return this.elements;
  }

  private void fireSelectedItem() {
    this.firePropertyChange("selectedItem", null, getSelectedItem());
  }

  public Object getSelectedItem() {
    if (this.isHierarchical && this.elements != null) {

      int[] vals = tree.getSelectionRows();
      if (vals == null || vals.length == 0) {
        return null;
      }

      TreePath path = tree.getSelectionPath();
      if (path.getLastPathComponent() instanceof XulTreeNode) {
        XulTreeNode node = (XulTreeNode) path.getLastPathComponent();
        // now link node.item to object via bindings
        SearchBundle b = findSelectedIndex(new SearchBundle(), getRootChildren(), node.item);
        vals[0] = b.curPos;
      }

      String property = ((XulTreeCol) this.getColumns().getChildNodes().get(0)).getChildrenbinding();
      property = "get" + (property.substring(0, 1).toUpperCase() + property.substring(1));
//      Method childrenMethod = null;
//      try {
//        childrenMethod = elements.getClass().getMethod(property, new Class[] {});
//      } catch (NoSuchMethodException e) {
//        // Since this tree is built recursively, when at a leaf it will throw this exception.
//        logger.debug(e);
//        return null;
//      }

      FindSelectedItemTuple tuple = findSelectedItem(this.elements, property, new FindSelectedItemTuple(vals[0]));
      return tuple != null ? tuple.selectedItem : null;
    }
    return null;
  }

  private class SearchBundle {
    int curPos;

    boolean found;

    Object selectedItem;
  }

  private SearchBundle findSelectedIndex(SearchBundle bundle, XulTreeChildren children, XulTreeItem selectedItem) {
    for (XulComponent c : children.getChildNodes()) {
      if (c == selectedItem) {
        bundle.found = true;
        return bundle;
      }
      bundle.curPos++;
      if (c.getChildNodes().size() > 1) {
        SearchBundle b = findSelectedIndex(bundle, (XulTreeChildren) c.getChildNodes().get(1), selectedItem);
        if (b.found) {
          return b;
        }
      }
    }
    return bundle;
  }

  private static class FindSelectedItemTuple {
    Object selectedItem = null;
    int curpos = -1; // ignores first element (root)
    int selectedIndex;

    public FindSelectedItemTuple(int selectedIndex) {
      this.selectedIndex = selectedIndex;
    }
  }

//  private FindSelectedItemTuple findSelectedItem(Object parent, Method childrenMethod, FindSelectedItemTuple tuple) {
//    if (tuple.curpos == tuple.selectedIndex) {
//      tuple.selectedItem = parent;
//      return tuple;
//    }
//    Collection children = null;
//    try {
//      children = (Collection) childrenMethod.invoke(parent, new Object[] {});
//    } catch (Exception e) {
//      logger.error(e);
//      return null;
//    }
//
//    if (children == null || children.size() == 0) {
//      return null;
//    }
//
//    for (Object child : children) {
//      tuple.curpos++;
//      findSelectedItem(child, childrenMethod, tuple);
//      if (tuple.selectedItem != null) {
//        return tuple;
//      }
//    }
//    return null;
//  }

  private FindSelectedItemTuple findSelectedItem(Object parent, String childrenMethodProperty,
      FindSelectedItemTuple tuple) {
    if (tuple.curpos == tuple.selectedIndex) {
      tuple.selectedItem = parent;
      return tuple;
    }
    Collection children = getChildCollection(parent, childrenMethodProperty);

    if (children == null || children.size() == 0) {
      return null;
    }

    for (Object child : children) {
      tuple.curpos++;
      findSelectedItem(child, childrenMethodProperty, tuple);
      if (tuple.selectedItem != null) {
        return tuple;
      }
    }
    return null;
  }


  private static Collection getChildCollection(Object obj, String childrenMethodProperty) {
    Collection children = null;
    Method childrenMethod = null;
    try {
      childrenMethod = obj.getClass().getMethod(childrenMethodProperty, new Class[] {});
    } catch (NoSuchMethodException e) {
      if (obj instanceof Collection) {
        children = (Collection) obj;
      }
    }
    try {
      if (childrenMethod != null) {
        children = (Collection) childrenMethod.invoke(obj, new Object[] {});
      }
    } catch (Exception e) {
      logger.error(e);
      return null;
    }

    return children;
  }


  public void setExpanded(boolean expanded) {
    this.expanded = expanded;
    if (this.tree != null) {
      int rowCount = 0;
      int newRowCount = 0;
      while ((newRowCount = tree.getRowCount()) > 0 && newRowCount > rowCount) {
        rowCount = newRowCount;
        for (int i = 0; i < rowCount; i++) {
          tree.expandRow(i);
        }
      }
    }
  }

  public boolean getExpanded() {
    return this.expanded;
  }

  public void registerCellEditor(String key, org.pentaho.ui.xul.util.TreeCellEditor editor) {
    this.customEditors.put(key, editor);

  }

  private class CustomCellEditorWrapper extends JLabel implements TreeCellEditorCallback {

    private org.pentaho.ui.xul.util.TreeCellEditor editor;
    private XulTreeCell cell;

    public CustomCellEditorWrapper(XulTreeCell cell, org.pentaho.ui.xul.util.TreeCellEditor editor) {
      super();
      this.editor = editor;
      this.cell = cell;
      this.setText(this.cell.getValue().toString());

      final int col = cell.getParent().getChildNodes().indexOf(cell);
      XulTreeItem item = (XulTreeItem) cell.getParent().getParent();
      final int row = item.getParent().getChildNodes().indexOf(item);
      final Object boundObj = (SwingTree.this.getElements() != null) ? SwingTree.this.getElements().toArray()[row] : null;
      final String columnBinding = SwingTree.this.getColumns().getColumn(col).getBinding();

      this.addMouseListener(new MouseAdapter() {

        @Override
        public void mousePressed(MouseEvent arg0) {
          CustomCellEditorWrapper.this.editor.show(row, col, boundObj, columnBinding, CustomCellEditorWrapper.this);
        }

      });
    }

    public void onCellEditorClosed(Object value) {
      this.setText(value.toString());
      cell.setValue(value);
    }

    public Object getCellEditorValue()
    {
      return getText();
    }

  }

  private static class CustomTreeCellEditor implements org.pentaho.ui.xul.util.TreeCellEditor {
    private TreeCellEditorCallback callback;

    public Object getValue() {
      // TODO Auto-generated method stub
      return null;
    }

    public void hide() {
      // TODO Auto-generated method stub

    }

    public void setValue(Object val) {
      // TODO Auto-generated method stub

    }

    public void show(int row, int col, Object boundObj, String columnBinding, TreeCellEditorCallback callback) {
      this.callback = callback;
      String returnVal = JOptionPane.showInputDialog("Enter a Value");
      this.callback.onCellEditorClosed(returnVal);
    }

  }

  public void registerCellRenderer(String key, TreeCellRenderer renderer) {

  }

  public void setBoundObjectExpanded(Object o, boolean expanded) {

    throw new UnsupportedOperationException("not implemented");
  }

  public void setTreeItemExpanded(XulTreeItem item, boolean expanded) {

    throw new UnsupportedOperationException("not implemented");
  }


  public void collapseAll() {
    if (this.isHierarchical) {
      // TODO: Not yet implemented
    }

  }

  public void expandAll() {
    if (this.isHierarchical) {
      this.setExpanded(true);
    }

  }

  public <T> Collection<T> getSelectedItems() {
    // TODO Auto-generated method stub
    return null;
  }

  public <T> void setSelectedItems(Collection<T> items) {
    int[] selIndexes= new int[items.size()];

    if (this.isHierarchical && this.elements != null) {
      int pos = 0;
      for(T t : items){
        selIndexes[pos++] = findIndexOfItem(t);
      }
    }
    this.setSelectedRows(selIndexes);
  }

  public int findIndexOfItem(Object o){

    String property = ((XulTreeCol) this.getColumns().getChildNodes().get(0)).getChildrenbinding();
    property = "get" + (property.substring(0, 1).toUpperCase() + property.substring(1));
    Method childrenMethod = null;
    try {
      childrenMethod = elements.getClass().getMethod(property, new Class[] {});
    } catch (NoSuchMethodException e) {
      // Since this tree is built recursively, when at a leaf it will throw this exception.
      logger.debug(e);
    }

    FindSelectedIndexTuple tuple = findSelectedItem(this.elements, childrenMethod, new FindSelectedIndexTuple(o));
    return tuple.selectedIndex;
  }


  private static class FindSelectedIndexTuple {
    Object selectedItem = null;
    Object currentItem = null; // ignores first element (root)
    int curpos = -1; // ignores first element (root)
    int selectedIndex = -1;

    public FindSelectedIndexTuple(Object selectedItem) {
      this.selectedItem = selectedItem;
    }
  }

  private FindSelectedIndexTuple findSelectedItem(Object parent, Method childrenMethod, FindSelectedIndexTuple tuple) {
    if (tuple.selectedItem == tuple.currentItem) {
      tuple.selectedItem = parent;
      return tuple;
    }
    Collection children = null;
    try {
      children = (Collection) childrenMethod.invoke(parent, new Object[] {});
    } catch (Exception e) {
      logger.error(e);
      return null;
    }

    if (children == null || children.size() == 0) {
      return null;
    }

    for (Object child : children) {
      tuple.curpos++;
      findSelectedItem(child, childrenMethod, tuple);
      if (tuple.selectedIndex > -1) {
        return tuple;
      }
    }
    return null;
  }

  public boolean isHiddenrootnode() {
    // TODO Auto-generated method stub
    return false;
  }

  public void setHiddenrootnode(boolean hidden) {
    // TODO Auto-generated method stub

  }

  public String getCommand() {
    return command;
  }

  public void setCommand(String command) {
    this.command = command;
  }

  public boolean isPreserveexpandedstate() {
    return false;
  }

  public void setPreserveexpandedstate(boolean preserve) {

  }

  public boolean isSortable() {
    // TODO Auto-generated method stub
    return false;
  }

  public void setSortable(boolean sort) {
    // TODO Auto-generated method stub

  }

  public boolean isTreeLines() {
    // TODO Auto-generated method stub
    return false;
  }

  public void setTreeLines(boolean visible) {
    // TODO Auto-generated method stub

  }

  public void setNewitembinding(String binding){
    newItemBinding = binding;
  }

  public String getNewitembinding(){
    return newItemBinding;
  }

  public void setAutocreatenewrows(boolean auto){
    this.autoCreateNewRows = auto;
  }

  public boolean getAutocreatenewrows(){
    return autoCreateNewRows;
  }

  public boolean isPreserveselection() {
    // TODO This method is not fully implemented. We need to completely implement this in this class
    return false;
  }

  public void setPreserveselection(boolean preserve) {
    // TODO This method is not fully implemented. We need to completely implement this in this class

  }


  private Binding createBinding(XulEventSource source, String prop1, XulEventSource target, String prop2){
    if(bindingProvider != null){
      return bindingProvider.getBinding(source, prop1, target, prop2);
    }
    return new DefaultBinding(source, prop1, target, prop2);
  }

  private class SwingTreeCellEditor extends DefaultCellEditor
  {
    private final SwingTreeCol col;
    private CheckBoxCellEditor checkBoxCellEditor;
    private ComboBoxCellEditor comboBoxCellEditor;
    private LabelCellEditor labelCellEditor;
    private DefaultCellEditor activeCellEditor;
    private TextFieldCellEditor textFieldCellEditor;
    private CustomCellEditorWrapper wrapper;

    public SwingTreeCellEditor(final SwingTreeCol col)
    {
      super(new JTextField());
      this.col = col;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, final int row, final int column) {
      ColumnType colType = col.getColumnType();
      if (colType == ColumnType.DYNAMIC) {
        colType = ColumnType.valueOf(extractDynamicColType(elements.toArray()[row], column));
      }

      switch (colType) {
      case CHECKBOX:
      {
        if (checkBoxCellEditor == null)
          checkBoxCellEditor = new CheckBoxCellEditor();

        activeCellEditor = checkBoxCellEditor;
        return checkBoxCellEditor.getTableCellEditorComponent(table, value, isSelected, row, column);
      }
      case EDITABLECOMBOBOX:
      case COMBOBOX:
      {
        if (comboBoxCellEditor == null)
          comboBoxCellEditor = new ComboBoxCellEditor(col);

        activeCellEditor = comboBoxCellEditor;
        return comboBoxCellEditor.getTableCellEditorComponent(table, value, isSelected, row, column);
      }
      case LABEL:
      {
        if (labelCellEditor == null)
          labelCellEditor = new LabelCellEditor();

        final XulTreeCell cell = getRootChildren().getItem(row).getRow().getCell(column);
        labelCellEditor.setValue(cell.getLabel());
        activeCellEditor = labelCellEditor;
        return labelCellEditor.getTableCellEditorComponent(table, value, isSelected, row, column);
      }
      case CUSTOM:
      {
        activeCellEditor = null;
        final XulTreeCell cell = getRootChildren().getItem(row).getRow().getCell(column);
        wrapper = new CustomCellEditorWrapper(cell, customEditors.get(col.getType()));
        return wrapper;
      }
      default:
        if (textFieldCellEditor == null)
          textFieldCellEditor = new TextFieldCellEditor();
        return textFieldCellEditor.getTableCellEditorComponent(table, value, isSelected, row, column);
      }
    }

    @Override
    public Object getCellEditorValue() {
      if (activeCellEditor != null)
        return activeCellEditor.getCellEditorValue();
      if (wrapper != null)
        wrapper.getCellEditorValue();
      return null;
    }

    public boolean isCellEditable(final EventObject anEvent)
    {

      return false;
    }

    public boolean shouldSelectCell(final EventObject anEvent)
    {
      return false;
    }

    public boolean stopCellEditing()
    {
      return false;
    }

    public void cancelCellEditing()
    {

    }

    public void addCellEditorListener(final CellEditorListener l)
    {

    }

    public void removeCellEditorListener(final CellEditorListener l)
    {

    }
  }

  private class TextFieldCellEditor extends DefaultCellEditor implements DocumentListener
  {
    private JTable table;
    private int row;
    private int column;
    private boolean skipEvents;

    private TextFieldCellEditor()
    {
      super(new JTextField());

      final JTextField label = new JTextField();
      editorComponent = label;
      label.getDocument().addDocumentListener(this);
    }

    public void changedUpdate(DocumentEvent arg0) {
      if (skipEvents) return;
      final JTextField label = (JTextField) editorComponent;
      this.table.setValueAt(label.getText(), row, column);
    }

    public void insertUpdate(DocumentEvent arg0) {
      if (skipEvents) return;
      final JTextField label = (JTextField) editorComponent;
      table.setValueAt(label.getText(), row, column);
    }

    public void removeUpdate(DocumentEvent arg0) {
      if (skipEvents) return;
      final JTextField label = (JTextField) editorComponent;
      table.setValueAt(label.getText(), row, column);
    }

    public Component getTableCellEditorComponent(final JTable table,
                                                 final Object value,
                                                 final boolean isSelected,
                                                 final int row,
                                                 final int column)
    {
      skipEvents = true;
      try
      {
        this.table = table;
        this.row = row;
        this.column = column;
        final XulTreeCell cell = getRootChildren().getItem(row).getRow().getCell(column);
        JTextField component = (JTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);
        component.setEnabled(!cell.isDisabled());
        component.setDisabledTextColor(Color.DARK_GRAY);

        return component;
      }
      finally
      {
        skipEvents = false;
      }
    }

    public Object getCellEditorValue()
    {
      final JTextField label = (JTextField) editorComponent;
      return label.getText();
    }
  }

  private class LabelCellEditor extends DefaultCellEditor
  {
    private LabelCellEditor()
    {
      super(new JTextField());
      editorComponent = new JLabel();
    }

    public void setValue(final String value)
    {
      JLabel label = (JLabel) editorComponent;
      label.setText(value);
    }

    public Object getCellEditorValue()
    {
      JLabel label = (JLabel) editorComponent;
      return label.getText();
    }
  }

  private class ComboBoxCellEditor extends DefaultCellEditor
  {
    private SwingTreeCol col;

    private ComboBoxCellEditor(SwingTreeCol col)
    {
      super(new JComboBox());
      this.col = col;
    }

    public Component getTableCellEditorComponent(final JTable table,
                                                 final Object value,
                                                 final boolean isSelected,
                                                 final int row,
                                                 final int column)
    {
      Vector val = (value != null && value instanceof Vector) ? (Vector) value : new Vector();
      final JComboBox comboBox = new JComboBox(val);
      this.editorComponent = comboBox;

      if (isSelected) {
        comboBox.setBackground(Color.LIGHT_GRAY);
      }
      ColumnType colType = col.getColumnType();
      if (colType == ColumnType.DYNAMIC) {
        colType = ColumnType.valueOf(extractDynamicColType(elements.toArray()[row], column));
      }

      if (colType == ColumnType.EDITABLECOMBOBOX) {

        comboBox.setEditable(true);
        final JTextComponent textComp = (JTextComponent) comboBox.getEditor().getEditorComponent();

        textComp.addKeyListener(new KeyListener() {
          private String oldValue = "";

          public void keyPressed(KeyEvent e) {
            oldValue = textComp.getText();
          }

          public void keyReleased(KeyEvent e) {
            if (oldValue != null && !oldValue.equals(textComp.getText())) {
              SwingTree.this.table.setValueAt(textComp.getText(), row, column);

              oldValue = textComp.getText();
            } else if (oldValue == null) {
              // AWT error where sometimes the keyReleased is fired before keyPressed.
              oldValue = textComp.getText();
            } else {
              logger.debug("Special key pressed, ignoring");
            }
          }

          public void keyTyped(KeyEvent e) {
          }
        });

        comboBox.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent event) {
            // if(textComp.hasFocus() == false && comboBox.hasFocus()){
            SwingTree.logger.debug("Setting ComboBox value from editor: " + comboBox.getSelectedItem() + ", " + row + ", " + column);

            SwingTree.this.table.setValueAt(comboBox.getSelectedIndex(), row, column);
            // }
          }
        });
      } else {
        comboBox.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent event) {

            SwingTree.logger.debug("Setting ComboBox value from editor: " + comboBox.getSelectedItem() + ", " + row + ", " + column);

            SwingTree.this.table.setValueAt(comboBox.getSelectedIndex(), row, column);
          }
        });
      }

      final XulTreeCell cell = getRootChildren().getItem(row).getRow().getCell(column);
      comboBox.setEnabled(!cell.isDisabled());
      return comboBox;
    }

    public Object getCellEditorValue()
    {
      JComboBox box = (JComboBox) editorComponent;
      if (box.isEditable()) {
        JTextComponent editorComponent1 = (JTextComponent) box.getEditor().getEditorComponent();
        return editorComponent1.getText();
      } else {
        return box.getSelectedIndex();
      }
    }

  }
  private class CheckBoxCellEditor extends DefaultCellEditor
  {
    private CheckBoxCellEditor()
    {
      super(new JCheckBox());
    }

    public Component getTableCellEditorComponent(final JTable table,
                                                 final Object value,
                                                 final boolean isSelected,
                                                 final int row,
                                                 final int column)
    {
      final Boolean bvalue;
      if (value instanceof String) {
        bvalue = (((String) value).equalsIgnoreCase("true"));
      } else if (value instanceof Boolean) {
        bvalue = ((Boolean) value);
      } else if (value != null) {
        bvalue = (false);
      }
      else bvalue = null;

      Component component = super.getTableCellEditorComponent(table, bvalue, isSelected, row, column);

      final XulTreeCell cell = getRootChildren().getItem(row).getRow().getCell(column);
      component.setEnabled(!cell.isDisabled());
      if (isSelected) {
        component.setBackground(Color.LIGHT_GRAY);
      }
      return component;
    }

    public Object getCellEditorValue()
    {
      final JCheckBox c = (JCheckBox) editorComponent;
      return c.isSelected();
    }
  }
}
