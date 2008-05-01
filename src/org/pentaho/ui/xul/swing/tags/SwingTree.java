package org.pentaho.ui.xul.swing.tags;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.CellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.ToolTipManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TreeColumn;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulTreeCell;
import org.pentaho.ui.xul.components.XulTreeCol;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeChildren;
import org.pentaho.ui.xul.containers.XulTreeCols;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.swt.TableSelection;
import org.pentaho.ui.xul.swt.custom.TableWrapper;
import org.pentaho.ui.xul.swt.custom.TreeWrapper;
import org.pentaho.ui.xul.util.ColumnType;


public class SwingTree extends SwingElement implements XulTree{

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
	private static final Log logger = LogFactory.getLog(SwingTree.class);

	private boolean isHierarchical = false;
	  
	public enum SELECTION_MODE{
		SINGLE, CELL, MULTIPLE
	};
	private SELECTION_MODE selType = SELECTION_MODE.SINGLE;
	
	public SwingTree(XulComponent parent, XulDomContainer domContainer, String tagName) {
		super("tree");
		

	    XulComponent primaryColumn = getElementByXPath("//treecol[@primary='true']");
	    XulComponent isaContainer = getElementByXPath("//treeitem[@container='true']");

	    isHierarchical = (primaryColumn != null) && (isaContainer != null);

		if(isHierarchical){
			tree = new JTree();
		} else {
			table = new JTable();
		}
		ToolTipManager.sharedInstance().unregisterComponent(table);
		ToolTipManager.sharedInstance().unregisterComponent(table.getTableHeader());
		
		scrollpane = new JScrollPane(table);
		
		this.managedObject = scrollpane.getViewport();
		logger.info("Set Managed Object to ViewPort");
	}
	
	public JTable getTable(){
		return table;
	}

	public JTree getTree(){
		return tree;
	}
	
	public int[] getActiveCellCoordinates() {
		return new int[]{table.getSelectedColumn(), table.getSelectedRow()};
		
	}

	public XulTreeCols getColumns() {
		return this.columns;
	}

	public String getOnselect() {
		return this.onselect;
	}

	public XulTreeChildren getRootChildren() {
		return this.rootChildren;
	}

	public int getRows() {
		return this.tableData.size();
	}

	public String getSeltype() {
		return selType.toString();
	}

	public Object[][] getValues() {
		
		TableModel model = table.getModel();
		Object[][] data = new Object[model.getRowCount()][model.getColumnCount()];
		
		for(int row=0; row<model.getRowCount(); row++){
			for(int col=0; col<model.getColumnCount(); col++){
				data[row][col] = model.getValueAt(row,col);
			}
		}
		return data;
	}

	public int getWidth() {
		return table.getWidth();
	}


	public void setWidth(int width) {
		Dimension dim = new Dimension(width, table.getHeight());
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
		
	}


	public void setDisabled(boolean dis) {
		this.disabled = dis;
		table.setEnabled(!this.disabled);
	}

	public void setEditable(boolean edit) {
		this.editable = edit;
	}

	public void setEnableColumnDrag(boolean drag) {
		this.columnDrag = drag;
		table.getTableHeader().setReorderingAllowed(drag);

	}

	public void setOnselect(final String select) {
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){

			public void valueChanged(ListSelectionEvent arg0) {
	
	      Document doc = SwingTree.this.getDocument();
	      Element rootElement = doc.getRootElement();
	      XulWindow window = (XulWindow) rootElement;
	    	try{
	    		window.invoke(select, new Object[] {new Integer(table.getSelectedRow())});
	    	} catch(XulException e){
	    		logger.error("Error invoking onselect event",e);
	    	}
	   
		
			}
			
		});
		
	}

	private XulTreeCols columns;
	public void setColumns(XulTreeCols columns) {
		this.columns = columns;
	}

	private Vector<Vector> tableData = new Vector<Vector>();
	private Vector<String> columnNames = new Vector<String>();
	
	public void setRootChildren(final XulTreeChildren rootChildren) {
		this.rootChildren = rootChildren;
		
		for(XulComponent row : rootChildren.getChildNodes()){
			
			XulTreeRow xrow = (XulTreeRow) row.getChildNodes().get(0);
			addTreeRow(xrow);
			
		}
		createColumnModel();
		
		tableModel = new DefaultTableModel(tableData,columnNames){
			@Override
			public void setValueAt(Object val, int row, int col) {

				super.setValueAt(val, row, col);
				
				logger.info("set value at ("+row+", "+col+")");
				
		    XulTreeCell cell = rootChildren.getItem(row).getRow().getCell(col);
		    switch(columns.getColumn(col).getColumnType()){
			    case CHECKBOX:
						cell.setValue(val);
						break;
					case COMBOBOX:
						cell.setValue(val);
						((SwingTreeCell) cell).setSelectedIndex(((Vector)cell.getValue()).indexOf(val));
						break;
					default: //label
						cell.setLabel((String) val);
						break;
		    }
				tableData.get(row).set(col, val);
				fireTableCellUpdated(row, col);
		        
			}
			
			public Class getColumnClass(int c) {
				logger.info("Class: "+getValueAt(0, c).getClass());
	        return getValueAt(0, c).getClass();
	    }

			public boolean isCellEditable(int row, int column)
		    {
		        return SwingTree.this.getColumns().getColumn(column).isEditable();
		    }
		};
		
	}

	private void createColumnModel(){
		for(XulComponent col : this.columns.getChildNodes()){
			columnNames.add(((XulTreeCol) col).getLabel());
		}
	}
	
	public void addTreeRow(XulTreeRow row){

		Vector<Object> newRow = new Vector<Object>();
		
		List<XulComponent> cells = row.getChildNodes();
		
		for(int i=0; i<cells.size(); i++){
			XulTreeCell xcell = (XulTreeCell) cells.get(i);

			if(this.getColumns().getColumn(i).getType().equalsIgnoreCase("checkbox")){
				newRow.add((Boolean) xcell.getValue());
			} else if (this.getColumns().getColumn(i).getType().equalsIgnoreCase("combobox")){
				newRow.add(xcell.getValue());
			} else {
				newRow.add(xcell.getLabel());
			}
		}
		
		this.rootChildren.addItem(new SwingTreeItem(row));
		
		tableData.add(newRow);
		table.updateUI();
	}

	public void removeTreeRows(int[] rows){
		for(int i=0; i<rows.length; i++){
			this.tableData.remove(rows[i]);
				
		}
		table.updateUI();
	}
	
	private int rows = -1;
	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setSeltype(String type) {
	    this.selType = SELECTION_MODE.valueOf(type.toUpperCase());
	    switch(this.selType){
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
	
	public void setTableModel(TableModel model){
		this.tableModel = model;
		
	}
	
	
	private TableCellRenderer getCellRenderer(final SwingTreeCol col){
		
		return new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				
				switch(col.getColumnType()){
					case CHECKBOX:
						JCheckBox checkbox = new JCheckBox();
						if(value instanceof String){
							checkbox.setSelected(((String) value).equalsIgnoreCase("true"));
						} else {
							checkbox.setSelected((Boolean) value);
						}
						return checkbox;
					case COMBOBOX:
						JComboBox comboBox= new JComboBox((Vector) value);

				    SwingTreeCell cell = (SwingTreeCell) rootChildren.getItem(row).getRow().getCell(column);
						comboBox.setSelectedIndex(cell.getSelectedIndex());
						return comboBox;
					default:
						JLabel label = new JLabel((String) value);
						return label;
				}
				
			}
		};

	}
	
	private TableCellEditor getCellEditor(final SwingTreeCol col){
		return new DefaultCellEditor(new JComboBox()){

			JComponent control;
			@Override
			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, final int row, final int column) {
				switch(col.getColumnType()){
					case CHECKBOX:
						final JCheckBox checkbox = new JCheckBox();
						
						checkbox.addActionListener(new ActionListener() {
			        public void actionPerformed(ActionEvent event) {
			        	SwingTree.this.table.setValueAt(checkbox.isSelected(), row, column);
			        } 
			      });
						
						control = checkbox;
						if(value instanceof String){
							checkbox.setSelected(((String) value).equalsIgnoreCase("true"));
						} else {
							checkbox.setSelected((Boolean) value);
						}
						return checkbox;
					case COMBOBOX:
						final JComboBox comboBox= new JComboBox((Vector) value);
						

						comboBox.addActionListener(new ActionListener() {
			        public void actionPerformed(ActionEvent event) {
			        	
			        	SwingTree.logger.info("Setting ComboBox value from editor: "+comboBox.getSelectedItem()+", "+row+", "+column);
			        	
			        	SwingTree.this.table.setValueAt(comboBox.getSelectedItem(), row, column);
			        } 
			      });
						
						control = comboBox;
						return comboBox;
					default:
						final JTextField label = new JTextField((String) value);
			
						label.getDocument().addDocumentListener(new DocumentListener(){

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
					
						control = label;
						return label;
				}
			}

			@Override
			public Object getCellEditorValue() {
				if(control instanceof JCheckBox){
					return ((JCheckBox) control).isSelected();
				} else if(control instanceof JComboBox){
					return ((JComboBox) control).getSelectedItem();
				} else {
					return ((JTextField) control).getText();
				}
			}
			
		};

	}
	


	private int totalFlex = 0;
	@Override
	public void layout() {
		//generate table object based on TableModel and TableColumnModel
			
		if(tableModel == null){
			tableModel = new DefaultTableModel();
		}
		table.setModel(this.tableModel);
		
		TableColumnModel columnModel = table.getColumnModel();

		for(int i=0; i<columns.getChildNodes().size(); i++){
			if(i >= columnModel.getColumnCount()){
				break;
			}
			
			SwingTreeCol child = (SwingTreeCol) columns.getChildNodes().get(i);
			TableColumn col = columnModel.getColumn(i);
			
			totalFlex += child.getFlex();
			
			col.setHeaderValue( child.getLabel());
			
			col.setCellEditor(getCellEditor(child));
			
			col.setCellRenderer(getCellRenderer(child));
			
//			List<XulComponent> cells = child.getChildNodes();
//			for(int z=0; z<cells.size(); z++){
//				XulComponent cell = cells.get(z);
//			}
			
		}
		
		table.addComponentListener(new ComponentListener(){
			boolean loaded = false;

			public void componentHidden(ComponentEvent arg0) {}
			public void componentMoved(ComponentEvent e) {}
			public void componentShown(ComponentEvent e) {}
			public void componentResized(ComponentEvent e) {
				if(loaded){
					return;
				}
				Rectangle size = table.getBounds();
				int newWidth = size.width;
				if(SwingTree.this.rows > -1){
					int minHeight = table.getRowHeight() * rows;
					scrollpane.getViewport().setMinimumSize(new Dimension(scrollpane.getWidth(), minHeight-100));
				}
				int newHeight = size.height;
				
				for(int i=0; i<table.getColumnCount(); i++){
					int flex = SwingTree.this.columns.getColumn(table.getColumnModel().getColumn(i).getModelIndex()).getFlex();
					int newColWidth = (int)( newWidth * ((double) flex / totalFlex));
					

					int headerWidth = table.getColumnModel().getColumn(i).getPreferredWidth();
					int setWidth = (headerWidth > newColWidth)? headerWidth : newColWidth;
					
					table.getColumnModel().getColumn(i).setWidth(setWidth);	
					table.getColumnModel().getColumn(i).setPreferredWidth(setWidth);	
				}
				loaded = true;
				
			}
			
		});
		
		
	}

	@Override
	public void replaceChild(XulComponent oldElement, XulComponent newElement) throws XulDomException{
		// TODO Auto-generated method stub
		super.replaceChild(oldElement, newElement);
	}

	@Override
	public Object getManagedObject() {
		return scrollpane;
	}

	public int[] getSelectedRows() {
		//TODO: add tree selection code
		return (this.isHierarchical)? null : table.getSelectedRows();
		
	}

	public void addRow(XulTreeRow row) {
		SwingTreeItem item = new SwingTreeItem(row);
		
		this.rootChildren.addItem(item);
		
	}

  public Object getData() {
    // TODO Auto-generated method stub
    return null;
  }

  public void setData(Object data) {
    // TODO Auto-generated method stub
    
  }

}
