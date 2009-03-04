package org.pentaho.ui.xul.gwt.tags;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.Vector;

import org.pentaho.gwt.widgets.client.table.BaseTable;
import org.pentaho.gwt.widgets.client.table.ColumnComparators.BaseColumnComparator;
import org.pentaho.gwt.widgets.client.utils.StringUtils;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulEventSource;
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
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.gwt.binding.GwtBinding;
import org.pentaho.ui.xul.gwt.binding.GwtBindingContext;
import org.pentaho.ui.xul.gwt.binding.GwtBindingMethod;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.TreeListener;
import com.google.gwt.user.client.ui.Widget;
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
  private boolean suppressEvents = false;
  private boolean editable = false;
  
  public GwtTree() {
    super("tree");
  }
  
  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    setOnselect(srcEle.getAttribute("onselect"));
    setOnedit(srcEle.getAttribute("onedit"));
    setSeltype(srcEle.getAttribute("seltype"));
    this.setEditable("true".equals(srcEle.getAttribute("editable")));
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
  private boolean firstLayout = true;
  // need to handle layouting
  public void layout() {
    if(firstLayout){

      XulTreeItem item = (XulTreeItem) this.getRootChildren().getFirstChild();
      
      if(item != null && item.getAttributeValue("container") != null && item.getAttributeValue("container").equals("true")){
        isHierarchical = true;
      }
      firstLayout = false;
    }

    if(isHierarchical()){
      setupTree();
    } else {
      setupTable();
    }
  }
  private ScrollPanel sp;
  private void setupTree(){
    if(tree == null){
      tree = new Tree();
      sp = new ScrollPanel(tree);
      SimplePanel div = new SimplePanel();
      div.add(sp);
      managedObject = div;
    }
    if(getWidth() > 0){
      sp.setWidth(getWidth()+"px");
      sp.setHeight(getHeight()+"px");
      sp.getElement().getStyle().setProperty("backgroundColor", "white");
    }
    
    tree.addTreeListener(new TreeListener(){

      public void onTreeItemSelected(TreeItem arg0) {
        int pos = -1;
        int curPos = 0;
        for(int i=0; i < tree.getItemCount(); i++){
          TreeItem tItem = tree.getItem(i);
          TreeCursor cursor = GwtTree.this.findPosition(tItem, arg0, curPos);
          pos = cursor.foundPosition;
          curPos = cursor.curPos+1;
          if(pos > -1){
            break;
          }
        }
          
        if(pos > -1 && GwtTree.this.suppressEvents == false){
          GwtTree.this.changeSupport.firePropertyChange("selectedRows",null,new int[]{pos});
        }
        

      }

      public void onTreeItemStateChanged(TreeItem arg0) {
          
      }
      
    });
    updateUI();
  }
  
  private class TreeCursor{
    int foundPosition = -1;
    int curPos;
    TreeItem itemToFind;
    public TreeCursor(int curPos, TreeItem itemToFind, int foundPosition){
      this.foundPosition = foundPosition;
      this.curPos = curPos;
      this.itemToFind = itemToFind;
    }
  }
  
  private TreeCursor findPosition(TreeItem curItem, TreeItem itemToFind, int curPos){
    if(curItem == itemToFind){
      TreeCursor p = new TreeCursor(curPos, itemToFind, curPos);
      return p;
    } else if(curItem.getChildIndex(itemToFind) > -1) {
      curPos = curPos+1;
      return new TreeCursor(curPos, itemToFind, curItem.getChildIndex(itemToFind)+curPos);
    } else {
      for(int i=0; i<curItem.getChildCount(); i++){
        TreeCursor p;
        if((p = findPosition(curItem.getChild(i), itemToFind, curPos +i)).foundPosition > -1){
          return p;
        }
      }
        curPos += curItem.getChildCount() ;
      return new TreeCursor(curPos, itemToFind, -1);
    }
    
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
    
    Object data[][] = new Object[getRootChildren().getItemCount()][getColumns().getColumnCount()];
    
    for (int i = 0; i < getRootChildren().getItemCount(); i++) {
      for (int j = 0; j < getColumns().getColumnCount(); j++) {
        
//        String label = getRootChildren().getItem(i).getRow().getCell(j).getLabel();
//        if(label == null || label.equals("")){
//          label = "&nbsp;";
//        }
        
        data[i][j] = getColumnEditor(j,i);
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
  
  private Widget getColumnEditor(final int x, final int y){
    
    
    String colType = this.columns.getColumn(x).getType();
    String val = getRootChildren().getItem(y).getRow().getCell(x).getLabel();
    if(StringUtils.isEmpty(colType)){
      return new HTML(val);
    } else if(colType.equals("text")){
      final TextBox b = new TextBox();
      b.addKeyboardListener(new KeyboardListener(){


        public void onKeyDown(Widget arg0, char arg1, int arg2) {}

        public void onKeyPress(Widget arg0, char arg1, int arg2) {}

        public void onKeyUp(Widget arg0, char arg1, int arg2) {
          getRootChildren().getItem(y).getRow().getCell(x).setLabel(b.getText());
        }
        
      });
      b.setText(val);
      return b;
    } else if(colType.equals("combobox")){
      final ListBox lb = new ListBox();
      final XulTreeCell cell = getRootChildren().getItem(y).getRow().getCell(x); 
      lb.addChangeListener(new ChangeListener(){

        public void onChange(Widget arg0) {
          cell.setSelectedIndex(lb.getSelectedIndex());
        }
        
      });
      
      Vector vals = (Vector) cell.getValue();
      for(Object label : vals){
        lb.addItem(label.toString());
      }
      int idx = cell.getSelectedIndex();
      if(idx < 0){
        idx = 0;
      }
      lb.setSelectedIndex(idx);
      return lb;
      
    } else { return new HTML(val); }
    
    
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

    table.addTableListener(new TableListener(){

      public void onCellClicked(SourcesTableEvents arg0, int y, int x) {
      }
      
    });
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
    };
    if(this.suppressEvents == false){
      changeSupport.firePropertyChange("selectedRows", null, getSelectedRows());
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
    if(this.isHierarchical()){
      TreeItem item = tree.getSelectedItem();
      for(int i=0; i <tree.getItemCount(); i++){
        if(tree.getItem(i) == item){
          return new int[]{i};
        }
      }
      
      return new int[]{};
      
    } else {
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
  }
  
  public void setSelectedRows(int[] rows) {
    if (table == null) {
      // this only works after the table has been materialized
      return;
    }
    for (int r : rows) {
      table.selectRow(r);
    }
    if(this.suppressEvents == false){
      this.changeSupport.firePropertyChange("selectedRows",null,rows);
    }
  }

  public String getSeltype() {
    return getAttributeValue("seltype");
  }

  public Object[][] getValues() {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean isEditable() {
    return editable;
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
    this.editable = edit;
  }

  public <T> void setElements(Collection<T> elements) {
    suppressEvents = true;
    this.getRootChildren().removeAll();
    
    
    if(elements == null || elements.size() == 0){
      updateUI();
      return;
    }
    try {
      if(table != null){
        for (T o : elements) {
          XulTreeRow row = this.getRootChildren().addNewRow();
  
          for (XulComponent col : this.getColumns().getChildNodes()) {

            XulTreeCol column = ((XulTreeCol) col);
            final XulTreeCell cell = (XulTreeCell) getDocument().createElement("treecell");
            for (InlineBindingExpression exp : column.getBindingExpressions()) {
          
              
              if(column.getType() != null && column.getType().equals("combobox")){
                GwtBinding binding = new GwtBinding(o, column.getCombobinding(), cell, "value");
                binding.setBindingType(Binding.Type.ONE_WAY);
                domContainer.addBinding(binding);
                binding.fireSourceChanged();
                
                binding = new GwtBinding(o, ((XulTreeCol) col).getBinding(), cell, "selectedIndex");
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
                
              } else if(o instanceof XulEventSource){

                GwtBinding binding = new GwtBinding(o, exp.getModelAttr(), cell, exp.getXulCompAttr());
                if(GwtTree.this.isEditable() == false){
                  binding.setBindingType(Binding.Type.ONE_WAY);
                }
                domContainer.addBinding(binding);
                binding.fireSourceChanged();
              } else {
                cell.setLabel(o.toString());
              }
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
      //treat as a selection change
    } catch (XulException e) {
      Window.alert("error adding elements "+e);
      System.out.println(e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      Window.alert("error adding elements "+e);
      System.out.println(e.getMessage());
      e.printStackTrace();
    }

    suppressEvents = false;
    updateUI();
  }
  
  private <T> void addTreeChild(T element, XulTreeRow row){
    try{
      XulTreeCell cell = (XulTreeCell) getDocument().createElement("treecell");
      
      for (InlineBindingExpression exp : ((XulTreeCol) this.getColumns().getChildNodes().get(0)).getBindingExpressions()) {
        GwtBinding binding = new GwtBinding(element, exp.getModelAttr(), cell, exp.getXulCompAttr());
        binding.setBindingType(Binding.Type.ONE_WAY);
        domContainer.addBinding(binding);
        binding.fireSourceChanged();
      }
  
      row.addCell(cell);
      
      //find children
      String property = ((XulTreeCol) this.getColumns().getChildNodes().get(0)).getChildrenbinding();
      
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
      Window.alert("error adding elements "+e.getMessage());
      e.printStackTrace();
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
    updateUI();
  }

  public void adoptAttributes(XulComponent component) {
    // TODO Auto-generated method stub
    
  }


}
