package org.pentaho.ui.xul.gwt.tags;

import java.util.List;

import org.pentaho.gwt.widgets.client.utils.StringUtils;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulColumns;
import org.pentaho.ui.xul.containers.XulGrid;
import org.pentaho.ui.xul.containers.XulRows;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.util.Orient;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class GwtGrid  extends AbstractGwtXulContainer implements XulGrid {
  private Grid grid;
  private ScrollPanel sp;
  private boolean firstLayout = true;
  
  public static void register() {
    GwtXulParser.registerHandler("grid",  //$NON-NLS-1$
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtGrid();
      }
    });
  }
  
  public GwtGrid() {
    super("grid"); //$NON-NLS-1$
  }

  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    setAlign(srcEle.getAttribute("align"));
  }

  public void update() {
    layout();
    
  }
  public void layout() {
    if(firstLayout){
      firstLayout = false;
    }
    setupGrid();
  }
  private void setupGrid() {
    
    if(grid == null){
      grid = new Grid();
      sp = new ScrollPanel(grid);
      SimplePanel div = new SimplePanel();
      div.add(sp);
      managedObject = container = div;
    }

    if(getFlex() > 0) {
      grid.setHeight("100%"); //$NON-NLS-1$
      grid.setWidth("100%");//$NON-NLS-1$
      sp.setHeight("100%");//$NON-NLS-1$
      sp.setWidth("100%");//$NON-NLS-1$
    } else if(getWidth() > 0){
      sp.setWidth(getWidth()+"px");//$NON-NLS-1$
      sp.setHeight(getHeight()+"px");//$NON-NLS-1$
    }
    grid.setCellSpacing(1);
    updateUI();
  }

  public void updateUI() {
    XulRows rows = getRows();
    XulColumns columns = getColumns();
    
    int colFlexTotal = 0;
    boolean columnFlexLayout = false;

    for(XulComponent col : columns.getChildNodes()){
      if(col.getFlex() > 0){
        columnFlexLayout = true;
        colFlexTotal += col.getFlex();
      }
    }
    
    // Resizing the Grid
    grid.resize(getRowCount(), getColumnCount());

        
    //Adding rows to the grid
    List<XulComponent> rowComponents = rows.getChildNodes();
    for(int rowCount=0;rowCount<getRowCount();rowCount++) {
      XulComponent component = rowComponents.get(rowCount);
      List<XulComponent> rowList = component.getChildNodes();
      List<XulComponent> colList = columns.getChildNodes();
      for(int colCount=0;colCount<getColumnCount();colCount++) {
        XulComponent rowComponent = rowList.get(colCount);
        Widget widget =  (Widget) rowComponent.getManagedObject();
        grid.setWidget(rowCount, colCount, widget);
        if(rowCount == 0) {
          grid.getRowFormatter().setStyleName(rowCount, "rowHeaderFormat");
        } else {
          grid.getRowFormatter().setStyleName(rowCount, "cellFormat");  
        }
        if(!columnFlexLayout) {
          grid.getCellFormatter().setWidth(rowCount, colCount, component.getWidth() + "%"); //$NON-NLS-1$
        } else {
          String percentage = Math.round((colList.get(colCount).getFlex()*100/colFlexTotal))+"%";//$NON-NLS-1$
          grid.getCellFormatter().setWidth(rowCount, colCount, percentage); 
        }
      }
    }
    this.initialized = true;
  }
  public XulRows getRows() {
    List<XulComponent> components = this.getChildNodes();
    for(XulComponent component:components) {
     if(!StringUtils.isEmpty(component.getName()) && component.getName().equals("rows")) { //$NON-NLS-1$
       return (XulRows) component;
     }
    }
    return null;
  }
  
  public XulColumns getColumns() {
    List<XulComponent> components = this.getChildNodes();
    for(XulComponent component:components) {
     if(!StringUtils.isEmpty(component.getName()) && component.getName().equals("columns")) { //$NON-NLS-1$
       return (XulColumns) component;
     }
    }
    return null;
  }
  
  private int getColumnCount() {
    return getColumns().getChildNodes().size();
  }

  private int getRowCount() {
    return getRows().getChildNodes().size();
  }

}
