package org.pentaho.ui.xul.gwt.tags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.pentaho.gwt.widgets.client.dialogs.GlassPane;
import org.pentaho.gwt.widgets.client.dialogs.PromptDialogBox;
import org.pentaho.gwt.widgets.client.listbox.CustomListBox;
import org.pentaho.gwt.widgets.client.listbox.DefaultListItem;
import org.pentaho.gwt.widgets.client.table.BaseTable;
import org.pentaho.gwt.widgets.client.table.ColumnComparators.BaseColumnComparator;
import org.pentaho.gwt.widgets.client.utils.StringUtils;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulEventSource;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.binding.BindingConvertor;
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
import org.pentaho.ui.xul.util.TreeCellEditor;
import org.pentaho.ui.xul.util.TreeCellEditorListener;
import org.pentaho.ui.xul.util.TreeCellRenderer;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
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

  /**
   * Cached elements.
   */
  private Collection elements; 
  
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
  private boolean visible = true;
  
  private Map<String, TreeCellEditor> customEditors = new HashMap<String, TreeCellEditor>();
  private Map<String, TreeCellRenderer> customRenderers = new HashMap<String, TreeCellRenderer>();
  
  
  public boolean isVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
    if(simplePanel != null) {
      simplePanel.setVisible(visible);
    }
  }

  /**
   * Used when this widget is a tree. Not used when this widget is a table.
   */
  private ScrollPanel scrollPanel = new ScrollPanel();
  
  /**
   * The managed object. If this widget is a tree, then the tree is added to the scrollPanel, which is added to this
   * simplePanel. If this widget is a table, then the table is added directly to this simplePanel.
   */
  private SimplePanel simplePanel = new SimplePanel();
  
  /**
   * Clears the parent panel and adds the given widget.
   * @param widget tree or table to set in parent panel
   */
  protected void setWidgetInPanel(final Widget widget) {
    if (isHierarchical()) {
      scrollPanel.clear();
      simplePanel.add(scrollPanel);
      scrollPanel.add(widget);
    } else {
      simplePanel.clear();
      simplePanel.add(widget);
    }
  }
  
  public GwtTree() {
    super("tree");

    // managedObject is neither a native GWT tree nor a table since the entire native GWT object is thrown away each 
    // time we call setup{Tree|Table}; because the widget is thrown away, we need to reconnect the new widget to the
    // simplePanel, which is the managedObject
    managedObject = simplePanel;
    
    this.registerCellEditor("custom-editor", new CustomEditor());
    this.registerCellRenderer("custom-editor", new CustomRenderer());
    
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
    if(this.getRootChildren() == null){
      //most likely an overlay
      return;
    }
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
    setVisible(visible);
  }

  private int prevSelectionPos = -1;
  private void setupTree(){
    if(tree == null){
      tree = new Tree();
      setWidgetInPanel(tree);
    }
    scrollPanel.setWidth("100%"); //$NON-NLS-1$
    scrollPanel.setHeight("100%"); //$NON-NLS-1$
    scrollPanel.getElement().getStyle().setProperty("backgroundColor", "white");  //$NON-NLS-1$//$NON-NLS-2$
    if (getWidth() > 0){
      scrollPanel.setWidth(getWidth()+"px"); //$NON-NLS-1$
    }
    if (getHeight() > 0) {
      scrollPanel.setHeight(getHeight()+"px"); //$NON-NLS-1$
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
        
        if(pos > -1 && GwtTree.this.suppressEvents == false && prevSelectionPos != pos){
          GwtTree.this.changeSupport.firePropertyChange("selectedRows",null,new int[]{pos});
        }
        prevSelectionPos = pos;

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
    if (getWidth() != 0) {
      table.setTableWidth(getWidth() + "px");
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
  
  private String extractDynamicColType(Object row, int columnPos) {
    GwtBindingMethod method = GwtBindingContext.typeController.findGetMethod(row, this.columns.getColumn(columnPos).getColumntypebinding());
    try{
      return (String) method.invoke(row, new Object[]{});
    } catch (Exception e){
      System.out.println("Could not extract column type from binding");
    }
    return "text"; // default //$NON-NLS-1$
  }
  
  private Widget getColumnEditor(final int x, final int y){
    

    final XulTreeCol column = this.columns.getColumn(x);
    String colType = this.columns.getColumn(x).getType();
    String val = getRootChildren().getItem(y).getRow().getCell(x).getLabel();
    final GwtTreeCell cell = (GwtTreeCell) getRootChildren().getItem(y).getRow().getCell(x);

    if(StringUtils.isEmpty(colType) == false && colType.equalsIgnoreCase("dynamic")){
      Object row = elements.toArray()[y];
      colType = extractDynamicColType(row, x);
    }

    if(StringUtils.isEmpty(colType) || !column.isEditable()){
      return new HTML(val);
    } else if(colType.equalsIgnoreCase("text")){

      try{

        GwtTextbox b = (GwtTextbox) this.domContainer.getDocumentRoot().createElement("textbox");
        b.setDisabled(!column.isEditable());
        b.layout();
        b.setValue(val);
        GwtBinding bind = new GwtBinding(cell, "label", b, "value");
        bind.setBindingType(Binding.Type.BI_DIRECTIONAL);
        domContainer.addBinding(bind);
        bind.fireSourceChanged();

        bind = new GwtBinding(cell, "disabled", b, "disabled");
        bind.setBindingType(Binding.Type.BI_DIRECTIONAL);
        domContainer.addBinding(bind);
        bind.fireSourceChanged();

        return (Widget) b.getManagedObject();
      } catch(Exception e){
        System.out.println("error creating textbox, fallback");
        e.printStackTrace();
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
      }
    } else if(colType.equalsIgnoreCase("combobox") || colType.equalsIgnoreCase("editablecombobox")){
      final CustomListBox lb = new CustomListBox();

      lb.setWidth("100%");

      Vector vals = (Vector) cell.getValue();
      lb.setSuppressLayout(true);
      for(Object label : vals){
        lb.addItem(label.toString());
      }
      lb.setSuppressLayout(false);
      lb.addChangeListener(new ChangeListener(){

        public void onChange(Widget arg0) {
          if(column.getType().equalsIgnoreCase("editablecombobox")){
            cell.setLabel(lb.getValue());
          } else {
            cell.setSelectedIndex(lb.getSelectedIndex());
          }
        }

      });
      
      int idx = cell.getSelectedIndex();
      if(idx < 0){
        idx = 0;
      }
      if(idx < vals.size()){
        lb.setSelectedIndex(idx);
      }
      if(colType.equalsIgnoreCase("editablecombobox")){
        lb.setEditable(true);
      }

      return lb;
      
    } else if (colType != null && customEditors.containsKey(colType)){
      if(this.customRenderers.containsKey(colType)){
        return new CustomCellEditorWrapper(cell, customEditors.get(colType), customRenderers.get(colType));
      } else {
        return new CustomCellEditorWrapper(cell, customEditors.get(colType));
      }
      
    } else {
      if(val == null || val.equals("")){
        return new HTML("&nbsp;");
      }
      return new HTML(val);
    }
    
    
  }
  
  private void setupTable(){
    String cols[] = new String[getColumns().getColumnCount()];
    int len[] = new int[cols.length];
    // for each column
    
    
    int totalFlex = 0;
    for (int i = 0; i < cols.length; i++) {
      totalFlex += getColumns().getColumn(i).getFlex();
    }
    
    for (int i = 0; i < cols.length; i++) {
      cols[i] = getColumns().getColumn(i).getLabel();
      if(totalFlex > 0 && getWidth() > 0){
        len[i] = (int) (getWidth() * ((double) getColumns().getColumn(i).getFlex() / totalFlex))  - 15;
      } 
    }
    
    // use base table from pentaho widgets library for now
    
    SelectionPolicy policy = SelectionPolicy.DISABLED;
    if ("single".equals(getSeltype())) {
      policy = SelectionPolicy.ONE_ROW;
    } else if ("multiple".equals(getSeltype())) {
      policy = SelectionPolicy.MULTI_ROW;
    }
    
    int[] colWidths = (getWidth() > 0 && totalFlex > 0) ? len : null;
    table = new BaseTable(cols, colWidths, new BaseColumnComparator[cols.length], policy);

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

    setWidgetInPanel(table);
    
    table.setTableWidth("100%"); //$NON-NLS-1$
    table.setTableHeight("100%"); //$NON-NLS-1$
    if (getWidth() > 0){
      table.setTableWidth(getWidth()+"px"); //$NON-NLS-1$
    }
    if (getHeight() > 0) {
      table.setTableHeight(getHeight()+"px"); //$NON-NLS-1$
    }
    updateUI();
  }
  
  public void updateUI() {
    if(this.isHierarchical()){
      populateTree();
    } else {
      populateTable();
    };
//    if(this.suppressEvents == false){
//      changeSupport.firePropertyChange("selectedRows", null, getSelectedRows());
//    }
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
    return this.elements;
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
 
  private int[] selectedRows;
  public void setSelectedRows(int[] rows) {
    if (table == null) {
      // this only works after the table has been materialized
      return;
    }
 
    int[] prevSelected = selectedRows;
    selectedRows = rows;;
    
    for (int r : rows) {
      table.selectRow(r);
    }
    if(this.suppressEvents == false && Arrays.equals(selectedRows, prevSelected) == false){
      this.changeSupport.firePropertyChange("selectedRows", prevSelected, rows);
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

    try{
      this.elements = elements;
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

            for (int x=0; x< this.getColumns().getChildNodes().size(); x++) {
              XulComponent col = this.getColumns().getColumn(x);

              XulTreeCol column = ((XulTreeCol) col);
              final XulTreeCell cell = (XulTreeCell) getDocument().createElement("treecell");
              for (InlineBindingExpression exp : column.getBindingExpressions()) {

                String colType = column.getType();
                if(StringUtils.isEmpty(colType) == false && colType.equalsIgnoreCase("dynamic")){
                  colType = extractDynamicColType(o, x);
                }

                if(colType != null && (colType.equalsIgnoreCase("combobox") || colType.equalsIgnoreCase("editablecombobox"))){
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

                  if(colType.equalsIgnoreCase("editablecombobox")){
                    binding = new GwtBinding(o, exp.getModelAttr(), cell, exp.getXulCompAttr());
                    if (!this.editable) {
                      binding.setBindingType(Binding.Type.ONE_WAY);
                    } else {
                      binding.setBindingType(Binding.Type.BI_DIRECTIONAL);
                    }
                    domContainer.addBinding(binding);
                  }

                } else if(colType != null && this.customEditors.containsKey(colType)){
                  
                  GwtBinding binding = new GwtBinding(o, exp.getModelAttr(), cell, "value");
                  binding.setBindingType(Binding.Type.BI_DIRECTIONAL);
                  domContainer.addBinding(binding);
                  binding.fireSourceChanged();
                  
                } else if(o instanceof XulEventSource && StringUtils.isEmpty(exp.getModelAttr()) == false){
                
                  GwtBinding binding = new GwtBinding(o, exp.getModelAttr(), cell, exp.getXulCompAttr());
                  if(column.isEditable() == false){
                    binding.setBindingType(Binding.Type.ONE_WAY);
                  }
                  domContainer.addBinding(binding);
                  binding.fireSourceChanged();
                } else {
                  cell.setLabel(o.toString());
                }
              }

              if(StringUtils.isEmpty(column.getDisabledbinding()) == false){
                String prop = column.getDisabledbinding();
                GwtBinding bind = new GwtBinding(o, column.getDisabledbinding(), cell, "disabled");
                bind.setBindingType(Binding.Type.ONE_WAY);
                domContainer.addBinding(bind);
                bind.fireSourceChanged();
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
    } catch(Exception e){
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
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
    layout();
  }

  public void adoptAttributes(XulComponent component) {
    super.adoptAttributes(component);    
  }

  public Object getSelectedItem() {
    // TODO Auto-generated method stub
    return null;
  }

  public void registerCellEditor(String key, TreeCellEditor editor){
    customEditors.put(key, editor);
  }
  
  
  public void registerCellRenderer(String key, TreeCellRenderer renderer) {
    customRenderers.put(key, renderer);
    
  }

  public class CustomCellEditorWrapper extends SimplePanel implements TreeCellEditorListener{
    
    private TreeCellEditor editor;
    private TreeCellRenderer renderer;
    private Label label = new Label();
    private XulTreeCell cell;
    
    public CustomCellEditorWrapper(XulTreeCell cell, TreeCellEditor editor){
      super();
      this.sinkEvents(Event.MOUSEEVENTS);
      this.editor = editor;
      this.cell = cell;
      this.add( label );
      this.label.setText((cell.getValue() != null) ? cell.getValue().toString() : " ");
      
      editor.addTreeCellEditorListener(this);
    }
    
    public CustomCellEditorWrapper(XulTreeCell cell, TreeCellEditor editor, TreeCellRenderer renderer){
      this(cell, editor);
      this.renderer = renderer;
      
      if(this.renderer.supportsNativeComponent()){
        this.clear();
        this.add((Widget) this.renderer.getNativeComponent());
      } else {
        this.label.setText(this.renderer.getText(cell.getValue()));
      }
      
    }
    
    public void onCellEditorClosed(Object value) {
      cell.setValue(value);
      if(this.renderer == null){
        this.label.setText(value.toString());
      } else if(this.renderer.supportsNativeComponent()){
        this.clear();
        this.add((Widget) this.renderer.getNativeComponent());
      } else {
        this.label.setText(this.renderer.getText(value));
      }
      
    }

    @Override
    public void onBrowserEvent(Event event) {
      int code = event.getTypeInt();
      switch(code){
        case Event.ONMOUSEUP:
          editor.setValue(cell.getValue());
          
          int col = cell.getParent().getChildNodes().indexOf(cell);
          
          XulTreeItem item = (XulTreeItem) cell.getParent().getParent();
          int row = item.getParent().getChildNodes().indexOf(item);
          
          Object boundObj = (GwtTree.this.getElements() != null) ? GwtTree.this.getElements().toArray()[row] : null;
          String columnBinding = GwtTree.this.getColumns().getColumn(col).getBinding();
          
          editor.show(row, col, boundObj, columnBinding);
        default:
          break;
      }
      super.onBrowserEvent(event);
    }
    
    
  }
  
  private static class CustomRenderer implements TreeCellRenderer{

    public Object getNativeComponent() {
      return null;
    }

    public String getText(Object value) {
      return "custom: "+value;
    }

    public boolean supportsNativeComponent() {
      return false;
    }
  }
  
  private static class CustomEditor implements TreeCellEditor, PopupListener{
    
    PromptDialogBox dialog;
    
    TextBox txt = new TextBox();
    private TreeCellEditorListener listener;
    
    public CustomEditor(){
      dialog = new PromptDialogBox("Enter Value", "Ok", "Cancel", true, false);
      dialog.addPopupListener(this);
      
      HorizontalPanel panel = new HorizontalPanel();
      panel.add(new Label("Enter Your Name"));
      panel.add(txt);
      dialog.setContent(panel);
      
    }
    
    public Object getValue() {
      return txt.getText();
    }

    public void hide() {
      // TODO Auto-generated method stub
      
    }

    public void setValue(Object val) {
      txt.setText(val.toString());
      
    }

    public void show(int row, int col, Object boundObj, String columnBinding) {
      dialog.getElement().getStyle().setProperty("zIndex", "10000");
      dialog.center();
    }

    public void onPopupClosed(PopupPanel sender, boolean autoClosed) {
      this.listener.onCellEditorClosed(txt.getText());
    }
    
    public void addTreeCellEditorListener(TreeCellEditorListener listener){
      this.listener = listener;
    }
    
  }

}
