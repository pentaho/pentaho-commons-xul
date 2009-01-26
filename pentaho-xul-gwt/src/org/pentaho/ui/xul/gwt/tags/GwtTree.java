package org.pentaho.ui.xul.gwt.tags;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.pentaho.gwt.widgets.client.table.BaseTable;
import org.pentaho.gwt.widgets.client.table.ColumnComparators.BaseColumnComparator;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.binding.InlineBindingExpression;
import org.pentaho.ui.xul.components.XulTreeCell;
import org.pentaho.ui.xul.components.XulTreeCol;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeChildren;
import org.pentaho.ui.xul.containers.XulTreeCols;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.gwt.binding.GwtBinding;
import org.pentaho.ui.xul.gwt.binding.GwtBindingContext;
import org.pentaho.ui.xul.gwt.binding.GwtBindingMethod;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.widgetideas.client.ResizableWidgetCollection;
import com.google.gwt.widgetideas.table.client.SourceTableSelectionEvents;
import com.google.gwt.widgetideas.table.client.TableSelectionListener;
import com.google.gwt.widgetideas.table.client.SelectionGrid.SelectionPolicy;

public class GwtTree extends AbstractGwtXulContainer implements XulTree {

  public static void register() {
    GwtXulParser.registerHandler("tree", 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTree();
      }
    });
  }

  XulTreeCols columns = null;
  XulTreeChildren rootChildren = null;
  private XulDomContainer domContainer;
  private Tree tree;
  
  public GwtTree() {
    super("tree");
  }
  
  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    setOnselect(srcEle.getAttribute("onselect"));
    setOnedit(srcEle.getAttribute("onedit"));
    setSeltype(srcEle.getAttribute("seltype"));
    this.domContainer = container;
  }
  
  public void addChild(Element element) {
    super.addChild(element);
    if (element.getName().equals("treecols")) {
      columns = (XulTreeCols)element;
    } else if (element.getName().equals("treechildren")) {
      rootChildren = (XulTreeChildren)element;
    }
  }
  
  public void addTreeRow(XulTreeRow row) {
      GwtTreeItem item = new GwtTreeItem();
      item.setRow(row);
      this.rootChildren.addItem(item);
      
      // update UI
      updateUI();
  }
  private BaseTable table;
  private boolean isHierarchical = false;
  
  // need to handle layouting
  public void layout() {

    //XulComponent primaryColumn = this.getElementByXPath("//treecol[@primary='true']");
    //XulComponent isaContainer = this.getElementByXPath("treechildren/treeitem[@container='true']");

    //isHierarchical = (primaryColumn != null) || (isaContainer != null);
    
    XulTreeItem item = (XulTreeItem) this.getRootChildren().getFirstChild();
    if(item.getAttributeValue("container") != null && item.getAttributeValue("container").equals("true")){
      isHierarchical = true;
    }
    if(isHierarchical()){
      setupTree();
    } else {
      setupTable();
    }
  }
  private void setupTree(){
    tree = new Tree();
    managedObject = tree;
    updateUI();
  }
  
  private void populateTree(){
    tree.removeItems();
    TreeItem topNode = new TreeItem("placeholder");
    if(this.rootChildren == null){
      this.rootChildren = (XulTreeChildren) this.getChildNodes().get(1);
    }
    for (XulComponent c : this.rootChildren.getChildNodes()){
      XulTreeItem item = (XulTreeItem) c;
      TreeItem node = createNode(item);
      tree.addItem(node);
    }
    
  }
  
  private void populateTable(){
    BaseTable table = (BaseTable)managedObject;
    
    Object data[][] = new Object[getRootChildren().getItemCount()][getColumns().getColumnCount()];
    
    for (int i = 0; i < getRootChildren().getItemCount(); i++) {
      for (int j = 0; j < getColumns().getColumnCount(); j++) {
        String label = getRootChildren().getItem(i).getRow().getCell(j).getLabel();
        if(label == null || label.equals("")){
          label = "&nbsp;";
        }
        data[i][j] = label;
      }
    }
    if (getHeight() != 0) {
      table.setTableHeight(getHeight() + "px");
    }
    table.populateTable(data);
    ResizableWidgetCollection.get().setResizeCheckingEnabled(false);
  }
  
  private TreeItem createNode(XulTreeItem item){
    TreeItem node = new TreeItem(item.getRow().getCell(0).getLabel());
    if(item.getChildNodes().size() > 1){
      //has children
      //TODO: make this more defensive
      XulTreeChildren children = (XulTreeChildren) item.getChildNodes().get(1);
      for(XulComponent c : children.getChildNodes()){

        TreeItem childNode = createNode((XulTreeItem) c);
        node.addItem(childNode);
      }
    }
    return node;
  }
  
  private void setupTable(){
    String cols[] = new String[getColumns().getColumnCount()];
    int len[] = new int[cols.length];
    // for each column
    for (int i = 0; i < cols.length; i++) {
      cols[i] = getColumns().getColumn(i).getLabel();
      len[i] = 100;
    }
    
    // use base table from pentaho widgets library for now
    
    SelectionPolicy policy = SelectionPolicy.DISABLED;
    if ("single".equals(getSeltype())) {
      policy = SelectionPolicy.ONE_ROW;
    } else if ("multiple".equals(getSeltype())) {
      policy = SelectionPolicy.MULTI_ROW;
    }
    
    table = new BaseTable(cols, null, new BaseColumnComparator[cols.length], policy);

    table.addTableSelectionListener(new TableSelectionListener() {
      public void onAllRowsDeselected(SourceTableSelectionEvents sender) {
      }
      public void onCellHover(SourceTableSelectionEvents sender, int row, int cell) {
      }
      public void onCellUnhover(SourceTableSelectionEvents sender, int row, int cell) {
      }
      public void onRowDeselected(SourceTableSelectionEvents sender, int row) {
      }
      public void onRowHover(SourceTableSelectionEvents sender, int row) {
      }
      public void onRowUnhover(SourceTableSelectionEvents sender, int row) {
      }
      public void onRowsSelected(SourceTableSelectionEvents sender, int firstRow, int numRows) {
        try {
          if (getOnselect() != null && getOnselect().trim().length() > 0) {
            getXulDomContainer().invoke(getOnselect(), new Object[]{});
          }
          Integer[] selectedRows = table.getSelectedRows().toArray(new Integer[table.getSelectedRows().size()]);
          //set.toArray(new Integer[]) doesn't unwrap ><
          int[] rows = new int[selectedRows.length];
          for(int i=0; i<selectedRows.length; i++){
            rows[i] = selectedRows[i];
          }
          GwtTree.this.setSelectedRows(rows);
        } catch (XulException e) {
          e.printStackTrace();
        }
      }
    });
    managedObject = table;
    updateUI();
  }
  
  public void updateUI() {
    if(this.isHierarchical()){
      populateTree();
    } else {
      populateTable();
    }
  }
  
  public void afterLayout() {
    updateUI();
  }

  public void clearSelection() {
    // TODO Auto-generated method stub
    
  }

  public int[] getActiveCellCoordinates() {
    // TODO Auto-generated method stub
    return null;
  }

  public XulTreeCols getColumns() {
    return columns;
  }

  public Object getData() {
    // TODO Auto-generated method stub
    return null;
  }

  public <T> Collection<T> getElements() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getOnedit() {
    return getAttributeValue("onedit");
  }

  public String getOnselect() {
    return getAttributeValue("onselect");
  }

  public XulTreeChildren getRootChildren() {
    return rootChildren;
  }

  public int getRows() {
    if (rootChildren == null) {
      return 0;
    } else {
      return rootChildren.getItemCount();
    }
  }

  public int[] getSelectedRows() {
    BaseTable table = (BaseTable)managedObject;
    if (table == null) {
      return new int[0];
    }
    Set<Integer> rows = table.getSelectedRows();
    int rarr[] = new int[rows.size()];
    int i = 0;
    for (Integer v : rows) {
      rarr[i++] = v;
    }
    return rarr;
  }
  
  public void setSelectedRows(int[] rows) {
    BaseTable table = (BaseTable)managedObject;
    if (table == null) {
      // this only works after the table has been materialized
      return;
    }
    for (int r : rows) {
      table.selectRow(r);
    }
    this.changeSupport.firePropertyChange("selectedRows",null,rows);
  }

  public String getSeltype() {
    return getAttributeValue("seltype");
  }

  public Object[][] getValues() {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean isEditable() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isEnableColumnDrag() {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isHierarchical() {
    return isHierarchical;
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
      if (item >= 0 && item < rootChildren.getItemCount()) {
        this.rootChildren.removeItem(item);
      }
    }
    updateUI();
  }

  public void setActiveCellCoordinates(int row, int column) {
    // TODO Auto-generated method stub
    
  }

  public void setColumns(XulTreeCols columns) {
    if (getColumns() != null) {
      this.removeChild(getColumns());
    }
    addChild(columns);
  }

  public void setData(Object data) {
    // TODO Auto-generated method stub
    
  }

  public void setEditable(boolean edit) {
    // TODO Auto-generated method stub
    
  }

  public <T> void setElements(Collection<T> elements) {
    this.getRootChildren().removeAll();
    
    
    if(elements == null){
      updateUI();
      changeSupport.firePropertyChange("selectedRows", null, getSelectedRows());
      return;
    }
    try {
      if(table != null){
        for (T o : elements) {
          XulTreeRow row = this.getRootChildren().addNewRow();
  
          for (XulComponent col : this.getColumns().getChildNodes()) {
            XulTreeCell cell = (XulTreeCell) getDocument().createElement("treecell");
            for (InlineBindingExpression exp : ((XulTreeCol) col).getBindingExpressions()) {
          
              GwtBinding binding = new GwtBinding(o, exp.getModelAttr(), cell, exp.getXulCompAttr());
              domContainer.addBinding(binding);
              binding.fireSourceChanged();
            }
  
            row.addCell(cell);
          }
  
        }
      } else { //tree

        for (T o : elements) {
          XulTreeRow row = this.getRootChildren().addNewRow();
          addTreeChild(o, row);
        }
        
      }
      updateUI();
      
      //treat as a selection change
      changeSupport.firePropertyChange("  ", null, getSelectedRows());
    } catch (XulException e) {
      Window.alert("error adding elements"+e);
    } catch (Exception e) {
      Window.alert("error adding elements"+e);
    }
    
  }
  
  private <T> void addTreeChild(T element, XulTreeRow row){
    try{
      XulTreeCell cell = (XulTreeCell) getDocument().createElement("treecell");
      
      for (InlineBindingExpression exp : ((XulTreeCol) this.getColumns().getChildNodes().get(0)).getBindingExpressions()) {
        GwtBinding binding = new GwtBinding(element, exp.getModelAttr(), cell, exp.getXulCompAttr());
        domContainer.addBinding(binding);
        binding.fireSourceChanged();
      }
  
      row.addCell(cell);
      
      //find children
      String property = ((XulTreeCol) this.getColumns().getChildNodes().get(0)).getChildrenbinding();
      property = "get"+(property.substring(0,1).toUpperCase() + property.substring(1));
      
      GwtBindingMethod childrenMethod = GwtBindingContext.typeController.findGetMethod(element, property);
      
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
      Window.alert("error adding elements"+e.getMessage());
    }
  }

  public void setEnableColumnDrag(boolean drag) {
    // TODO Auto-generated method stub
    
  }

  public void setOnedit(String onedit) {
    this.setAttribute("onedit", onedit);
  }

  public void setOnselect(String select) {
    this.setAttribute("onselect", select);
  }

  public void setRootChildren(XulTreeChildren rootChildren) {
    if (getRootChildren() != null) {
      this.removeChild(getRootChildren());
    }
    addChild(rootChildren);
  }

  public void setRows(int rows) {
    // TODO Auto-generated method stub
    
  }



  public void setSeltype(String type) {
    // TODO Auto-generated method stub
    // SINGLE, CELL, MULTIPLE, NONE
    this.setAttribute("seltype", type);
  }

  public void update() {
    // TODO Auto-generated method stub
    
  }

  public void adoptAttributes(XulComponent component) {
    // TODO Auto-generated method stub
    
  }


}
