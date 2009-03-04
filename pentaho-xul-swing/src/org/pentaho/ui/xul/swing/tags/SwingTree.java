package org.pentaho.ui.xul.swing.tags;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.CellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
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
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
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
import org.pentaho.ui.xul.swing.AbstractSwingContainer;

public class SwingTree extends AbstractSwingContainer implements XulTree {

  private JTable table;

  private JTree tree;

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

  public enum SELECTION_MODE {
    SINGLE, CELL, MULTIPLE
  };

  private SELECTION_MODE selType = SELECTION_MODE.SINGLE;

  private XulDomContainer domContainer;

  public SwingTree(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("tree");

    this.domContainer = domContainer;

  }

  public JTable getTable() {
    return table;
  }

  public JTree getTree() {
    return tree;
  }

  public int[] getActiveCellCoordinates() {
    return new int[] {  table.getSelectedRow(), table.getSelectedColumn() };

  }

  public XulTreeCols getColumns() {
    return this.columns;
  }

  public String getOnselect() {
    return this.onselect;
  }

  public XulTreeChildren getRootChildren() {
    if(rootChildren == null){
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
          default: //label
            data[y][x] = cell.getLabel();
            break;
        }
        x++;

      }

      y++;
    }

    //    for(int row=0; row<this.rootChildren.getRowCount(); row++){
    //      for(int col=0; col<model.getColumnCount(); col++){
    //        data[row][col] = model.getValueAt(row,col);
    //      }
    //    }
    return data;
  }

  public int getWidth() {
    return scrollpane.getWidth();
  }

  public void setWidth(int width) {
    if(tree == null || table == null){
      return;
    }
    int height = (table != null)? 
                    table.getHeight()
                    :(tree != null) ? 
                        tree.getHeight() 
                        :scrollpane.getHeight();
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
    if(table != null){
      table.setEnabled(!this.disabled);
    }
  }

  public void setEditable(boolean edit) {
    this.editable = edit;
  }

  public void setEnableColumnDrag(boolean drag) {
    this.columnDrag = drag;
    if(table != null){
      table.getTableHeader().setReorderingAllowed(drag);
    }
  }

  public void setOnselect(final String select) {
    if(table != null){
    table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

      public void valueChanged(ListSelectionEvent event) {
        if (event.getValueIsAdjusting() == true) {
          return;
        }
        invoke(select, new Object[] { new Integer(table.getSelectedRow()) });
      }
    });
    }
  }

  private XulTreeCols columns;

  public void setColumns(XulTreeCols columns) {
    this.columns = columns;
  }

  public void setRootChildren(final XulTreeChildren rootChildren) {
    if(this.rootChildren == null){
      this.rootChildren = rootChildren;
    }
    //    for (XulComponent row : rootChildren.getChildNodes()) {
    //
    //      XulTreeRow xrow = (XulTreeRow) row.getChildNodes().get(0);
    //      addTreeRow(xrow);
    //
    //    }

  }

  public void addTreeRow(XulTreeRow row) {

    getRootChildren().addItem(new SwingTreeItem(row));

    table.updateUI();
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

    table.updateUI();
    //treat as selection change.
    changeSupport.firePropertyChange("selectedRows", null, getSelectedRows());
  }

  private int rows = -1;

  public void setRows(int rows) {
    this.rows = rows;
  }

  public void setSeltype(String type) {
    if(table == null){
      return;
    }
    this.selType = SELECTION_MODE.valueOf(type.toUpperCase());
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
    } else {
      table = new JTable();
    }
    
    JComponent comp = (table != null ? table : tree);
    ToolTipManager.sharedInstance().unregisterComponent(comp);
    if(table != null){
      ToolTipManager.sharedInstance().unregisterComponent(table.getTableHeader());
    
    
      table.setRowHeight(18);
      scrollpane = new JScrollPane(table);
      setupTable();
      
    } else {
      setupTree();
      scrollpane = new JScrollPane(tree);
    }
    
    this.managedObject = scrollpane.getViewport();
  }

  int numOfListeners = 0;
  
  private void setupTable(){

    //generate table object based on TableModel and TableColumnModel

    //    if(tableModel == null){
    tableModel = new XulTableModel(this);
    //    }
    table.setModel(this.tableModel);

    TableColumnModel columnModel = table.getColumnModel();

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

      //      List<XulComponent> cells = child.getChildNodes();
      //      for(int z=0; z<cells.size(); z++){
      //        XulComponent cell = cells.get(z);
      //      }
      initialized = true;
    }

    table.addComponentListener(new ComponentListener() {
      boolean loaded = false;

      public void componentHidden(ComponentEvent arg0) {
      }

      public void componentMoved(ComponentEvent e) {
      }

      public void componentShown(ComponentEvent e) {
      }

      public void componentResized(ComponentEvent e) {
        if (loaded) {
          return;
        }
        Rectangle size = table.getBounds();
        int newWidth = size.width;
        if (SwingTree.this.rows > -1) {
          int minHeight = table.getRowHeight() * rows;
          scrollpane.getViewport().setMinimumSize(new Dimension(scrollpane.getWidth(), minHeight - 100));
        }
        int newHeight = size.height;

        for (int i = 0; i < table.getColumnCount(); i++) {
          int flex = SwingTree.this.columns.getColumn(table.getColumnModel().getColumn(i).getModelIndex()).getFlex();
          int newColWidth = (int) (newWidth * ((double) flex / totalFlex));

          int headerWidth = table.getColumnModel().getColumn(i).getPreferredWidth();
          int setWidth = newColWidth; //(headerWidth > newColWidth) ? headerWidth : newColWidth;

          table.getColumnModel().getColumn(i).setWidth(setWidth);
          table.getColumnModel().getColumn(i).setPreferredWidth(setWidth);
        }
        loaded = true;

      }

    });
    
    //Property change on selections
    table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

      public void valueChanged(ListSelectionEvent event) {
        if (event.getValueIsAdjusting() == true) {
          return;
        }
        SwingTree.this.changeSupport.firePropertyChange("selectedRows", null, SwingTree.this.getSelectedRows());
      }
    });
    
    table.getTableHeader().setReorderingAllowed(this.isEnableColumnDrag());
    
    this.setDisabled(this.isDisabled());
  }
  private TreeModel treeModel;
  
  private void setupTree(){
    DefaultMutableTreeNode topNode = new DefaultMutableTreeNode("placeholder");
    for (XulComponent c : getRootChildren().getChildNodes()){
      XulTreeItem item = (XulTreeItem) c;
      DefaultMutableTreeNode node = createNode(item);
      topNode.add(node);
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
    
    tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener(){

      public void valueChanged(TreeSelectionEvent e) {
        SwingTree.this.changeSupport.firePropertyChange("selectedRows", null, SwingTree.this.getSelectedRows());
      }
      
    });
    
  }
  
  private DefaultMutableTreeNode createNode(XulTreeItem item){
    DefaultMutableTreeNode node = new DefaultMutableTreeNode(item.getRow().getCell(0).getLabel());
    if(item.getChildNodes().size() > 1){
      //has children
      //TODO: make this more defensive
      XulTreeChildren children = (XulTreeChildren) item.getChildNodes().get(1);
      for(XulComponent c : children.getChildNodes()){

        DefaultMutableTreeNode childNode = createNode((XulTreeItem) c);
        node.add(childNode);
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
    //FIXME: A selection followed by removals with no new UI interaction returns the previous
    //selection even if the row count is now less.

    if(table != null){
      int[] tempSelection = table.getSelectedRows();
      List<Integer> clensedSelection = new ArrayList<Integer>();
      for (int row : tempSelection) {
        if (row < table.getModel().getRowCount()) {
          clensedSelection.add(row);
        }
      }
  
      //no automatic unboxing :(
      int[] returnArray = new int[clensedSelection.size()];
  
      int idx = 0;
      for (int row : clensedSelection) {
        returnArray[idx] = row;
        idx++;
      }
  
      return returnArray;
    } else {
      int[] vals = tree.getSelectionRows();
      if(vals == null){
        return new int[]{};
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
    if(table != null){
      table.updateUI();
    } else {
      tree.updateUI();
    }
  }

  private TableCellRenderer getCellRenderer(final SwingTreeCol col) {

    return new DefaultTableCellRenderer() {

      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
          int row, int column) {

        switch (col.getColumnType()) {
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
            return checkbox;
          case COMBOBOX:
            final SwingTreeCell cell = (SwingTreeCell) getRootChildren().getItem(row).getRow().getCell(
                SwingTree.this.columns.getChildNodes().indexOf(col));

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
                comboBox.setSelectedIndex(cell.getSelectedIndex());
              }
            }

            if (isSelected) {
              comboBox.setBackground(Color.LIGHT_GRAY);
            }
            return comboBox;
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
    return new DefaultCellEditor(new JComboBox()) {

      JComponent control;

      @Override
      public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, final int row,
          final int column) {
        Component comp;
        switch (col.getColumnType()) {
          case CHECKBOX:
            final JCheckBox checkbox = new JCheckBox();
            final JTable tbl = table;
            checkbox.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent event) {
                SwingTree.this.table.setValueAt(checkbox.isSelected(), row, column);
                tbl.getCellEditor().stopCellEditing();
              }
            });

            control = checkbox;
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
            comp = checkbox;
            break;
          case COMBOBOX:
            Vector val = (value != null) ? (Vector) value : new Vector();
            final JComboBox comboBox = new JComboBox(val);

            comboBox.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent event) {

                SwingTree.logger.debug("Setting ComboBox value from editor: " + comboBox.getSelectedItem() + ", " + row
                    + ", " + column);

                SwingTree.this.table.setValueAt(comboBox.getSelectedIndex(), row, column);
              }
            });
            if (isSelected) {
              comboBox.setBackground(Color.LIGHT_GRAY);
            }

            control = comboBox;
            comp = comboBox;
            break;
          default:
            final JTextField label = new JTextField((String) value);

            label.getDocument().addDocumentListener(new DocumentListener() {

              public void changedUpdate(DocumentEvent arg0) {
                SwingTree.this.table.setValueAt(label.getText(), row, column);
              }

              public void insertUpdate(DocumentEvent arg0) {
                SwingTree.this.table.setValueAt(label.getText(), row, column);
              }

              public void removeUpdate(DocumentEvent arg0) {
                SwingTree.this.table.setValueAt(label.getText(), row, column);
              }

            });
            if (isSelected) {
              label.setOpaque(true);
              label.setBackground(Color.LIGHT_GRAY);
            }

            control = label;
            comp = label;
            break;
        }

        return comp;
      }

      @Override
      public Object getCellEditorValue() {
        if (control instanceof JCheckBox) {
          return ((JCheckBox) control).isSelected();
        } else if (control instanceof JComboBox) {
          return ((JComboBox) control).getSelectedIndex();
        } else {
          return ((JTextField) control).getText();
        }
      }

    };

  }
  
  private class XulTreeModel extends DefaultTreeModel {
    public XulTreeModel(TreeNode root){
      super(root);
    }
  }

  private class XulTableModel extends AbstractTableModel {

    SwingTree tree = null;

    public XulTableModel(SwingTree tree) {
      this.tree = tree;
      Vector<String> columnNames = new Vector<String>();
      for (XulComponent col : tree.getColumns().getChildNodes()) {
        columnNames.add(((XulTreeCol) col).getLabel());
      }

    }

    public int getColumnCount() {
      return this.tree.getColumns().getColumnCount();
    }

    public int getRowCount() {
      if (this.tree != null) {
        XulTreeChildren children = this.tree.getRootChildren();
        return (children != null)? children.getItemCount() : 0;
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
      try {
        switch (col.getColumnType()) {
          case CHECKBOX:
            if (cell.getValue() != null) {
              return cell.getValue();
            }
          case COMBOBOX:
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

      switch (tree.getColumns().getColumn(columnIndex).getColumnType()) {
        case CHECKBOX:
          cell.setValue((Boolean) value);
          break;
        case COMBOBOX:
          if (value instanceof String) {
            logger.error("Combobox being set to String! (" + value + ")");
          } else {
            cell.setSelectedIndex((Integer) value);
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
    for (int row : rows) {
      table.changeSelection(row, -1, false, false);
    }

  }

  public String getOnedit() {
    return onedit;
  }

  public void setOnedit(String onedit) {
    this.onedit = onedit;
  }

  private boolean suppressEvents = false;
  public <T> void setElements(Collection<T> elements) {
    suppressEvents = true;
    this.getRootChildren().removeAll();
    
    //active editor needs updating, but won't if still active
    if(table != null){
      CellEditor ce = table.getCellEditor();
      if (ce != null) {
        ce.stopCellEditing();
      }
    }
    
    if(elements == null){
      if(table != null){
        table.updateUI();
      } else {
        tree.updateUI();
      }
      changeSupport.firePropertyChange("selectedRows", null, getSelectedRows());
      return;
    }
    try {
        
        if(table != null){
          for (T o : elements) {
            XulTreeRow row = this.getRootChildren().addNewRow();
    
            for (XulComponent col : this.getColumns().getChildNodes()) {
              final XulTreeCell cell = (XulTreeCell) getDocument().createElement("treecell");
              XulTreeCol column = (XulTreeCol) col;
              for (InlineBindingExpression exp : ((XulTreeCol) col).getBindingExpressions()) {
                logger.debug("applying binding expression [" + exp + "] to xul tree cell [" + cell + "] and model [" + o
                    + "]");
    
                if(((XulTreeCol) col).getType().equalsIgnoreCase("combobox")){
                  DefaultBinding binding = new DefaultBinding(o, ((XulTreeCol) col).getCombobinding(), cell, "value");
                  binding.setBindingType(Binding.Type.ONE_WAY);
                  domContainer.addBinding(binding);
                  binding.fireSourceChanged();
                  
                  binding = new DefaultBinding(o, ((XulTreeCol) col).getBinding(), cell, "selectedIndex");
                  binding.setConversion(new BindingConvertor<Object, Integer>(){
  
                    @Override
                    public Integer sourceToTarget(Object value) {
                      int index = ((Vector) cell.getValue()).indexOf(value);
                      return index > -1 ? index : 0;
                    }
  
                    @Override
                    public Object targetToSource(Integer value) {
                      return ((Vector)cell.getValue()).get(value);
                    }
                    
                  });
                  domContainer.addBinding(binding);
                  binding.fireSourceChanged();
                  
                } else {
                  DefaultBinding binding = new DefaultBinding(o, exp.getModelAttr(), cell, exp.getXulCompAttr());
                  if (!this.editable) {
                    binding.setBindingType(Binding.Type.ONE_WAY);
                  }
                  domContainer.addBinding(binding);
                  binding.fireSourceChanged();
                }
              
              }
              
              row.addCell(cell);
            }
          }
        } else {//tree
          
          for (T o : elements) {
            XulTreeRow row = this.getRootChildren().addNewRow();
            addTreeChild(o, row);
          }
          
        }

      
        if(table != null){
          table.updateUI();
        } else {
          setupTree();
          tree.updateUI();
        }
      suppressEvents = false;
      
      //treat as a selection change
      changeSupport.firePropertyChange("selectedRows", null, getSelectedRows());
    } catch (XulException e) {
      logger.error("error adding elements", e);
    } catch (Exception e) {
      logger.error("error adding elements", e);
    }
  }
  
  private <T> void addTreeChild(T element, XulTreeRow row){
    try{
      XulTreeCell cell = (XulTreeCell) getDocument().createElement("treecell");
      
      for (InlineBindingExpression exp : ((XulTreeCol) this.getColumns().getChildNodes().get(0)).getBindingExpressions()) {
        logger.debug("applying binding expression [" + exp + "] to xul tree cell [" + cell + "] and model [" + element
            + "]");
  
        // Tree Bindings are one-way for now as you cannot edit tree nodes
        DefaultBinding binding = new DefaultBinding(element, exp.getModelAttr(), cell, exp.getXulCompAttr());
        binding.setBindingType(Binding.Type.ONE_WAY);
        domContainer.addBinding(binding);
        binding.fireSourceChanged();
      }
  
      row.addCell(cell);
      
      //find children
      String property = ((XulTreeCol) this.getColumns().getChildNodes().get(0)).getChildrenbinding();
      property = "get"+(property.substring(0,1).toUpperCase() + property.substring(1));
      Method childrenMethod = element.getClass().getMethod(property, new Class[]{});
      
      Collection<T> children = (Collection<T>) childrenMethod.invoke(element, new Object[]{});
      XulTreeChildren treeChildren = null;
      
      if(children != null && children.size() > 0){
        treeChildren = (XulTreeChildren) getDocument().createElement("treechildren");
        row.getParent().addChild(treeChildren);
      }
      for(T child : children){
        row = treeChildren.addNewRow();
        addTreeChild(child, row);
      }
    } catch (Exception e) {
      logger.error("error adding elements", e);
    }
  }

  public <T> Collection<T> getElements() {
    return null;
  }

  
}
