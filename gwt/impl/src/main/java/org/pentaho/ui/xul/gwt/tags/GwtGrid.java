/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/

package org.pentaho.ui.xul.gwt.tags;

import java.util.List;

import org.pentaho.gwt.widgets.client.panel.ScrollFlexPanel;
import org.pentaho.gwt.widgets.client.utils.ElementUtils;
import org.pentaho.gwt.widgets.client.utils.string.StringUtils;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulColumns;
import org.pentaho.ui.xul.containers.XulGrid;
import org.pentaho.ui.xul.containers.XulRows;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class GwtGrid extends AbstractGwtXulContainer implements XulGrid {
  private Grid grid;
  private ScrollPanel sp;
  private boolean firstLayout = true;

  public static void register() {
    GwtXulParser.registerHandler( "grid",
        new GwtXulHandler() {
          public Element newInstance() {
            return new GwtGrid();
          }
        } );
  }

  public GwtGrid() {
    super( "grid" );
  }

  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    super.init( srcEle, container );
    setAlign( srcEle.getAttribute( "align" ) );
  }

  public void update() {
    layout();

  }

  public void layout() {
    if ( firstLayout ) {
      firstLayout = false;
    }
    setupGrid();
  }

  private void setupGrid() {

    if ( grid == null ) {
      grid = new Grid();
      grid.addStyleName( "gwt-grid" );

      sp = new ScrollFlexPanel( grid );

      SimplePanel div = new SimplePanel();
      div.addStyleName( "gwt-grid-panel" );
      div.addStyleName( "flex-column" );

      div.add( sp );
      container = div;
      setManagedObject( container );
    }

    if ( getFlex() > 0 ) {
      grid.setHeight( "100%" );
      grid.setWidth( "100%" );
      sp.setHeight( "100%" );
      sp.setWidth( "100%" );
    } else if ( getWidth() > 0 ) {
      sp.setWidth( getWidth() + "px" );
      sp.setHeight( getHeight() + "px" );
    }
    grid.setCellSpacing( 1 );
    if ( getPadding() > 0 ) {
      grid.setCellPadding( getPadding() );
    }
    updateUI();
  }

  public void updateUI() {
    XulRows rows = getRows();
    XulColumns columns = getColumns();

    int colFlexTotal = 0;
    StringBuilder cssGridTemplateColumns = new StringBuilder();

    for ( XulComponent col : columns.getChildNodes() ) {
      int flex = col.getFlex();
      if ( flex > 0 ) {
        colFlexTotal += flex;
        cssGridTemplateColumns.append( flex ).append( "fr " );
      } else {
        cssGridTemplateColumns.append( "auto " );
      }
    }

    ElementUtils.setStyleProperty( grid.getElement(), "--grid-template-columns", cssGridTemplateColumns.toString().trim() );

    // Resizing the Grid
    grid.resize( getRowCount(), getColumnCount() );

    // Adding rows to the grid
    List<XulComponent> rowComponents = rows.getChildNodes();
    for ( int rowIndex = 0; rowIndex < getRowCount(); rowIndex++ ) {

      XulComponent rowComponent = rowComponents.get( rowIndex );

      updateUIRow( columns, colFlexTotal, rowIndex, rowComponent );
    }

    this.initialized = true;
  }

  private void updateUIRow( XulColumns columns, int colFlexTotal, int rowIndex, XulComponent rowComponent ) {
    List<XulComponent> cellList = rowComponent.getChildNodes();
    List<XulComponent> colList = columns.getChildNodes();

    for ( int colIndex = 0; colIndex < getColumnCount(); colIndex++ ) {
      XulComponent cellComponent = cellList.get( colIndex );
      Widget cellWidget = (Widget) cellComponent.getManagedObject();

      XulComponent colComponent = colList.get( colIndex );

      updateUICell( colFlexTotal, rowIndex, colIndex, rowComponent, colComponent, cellWidget );
    }
  }

  private void updateUICell( int colFlexTotal, int rowIndex, int colIndex, XulComponent rowComponent,
                             XulComponent colComponent, Widget cellWidget ) {

    grid.setWidget( rowIndex, colIndex, cellWidget );

    // CSS Grid auto-placement would result out-of-sync. for items which are display:none.
    ElementUtils.setStyleProperty( cellWidget.getElement(), "--grid-row", String.valueOf( rowIndex + 1 ) );
    ElementUtils.setStyleProperty( cellWidget.getElement(), "--grid-column", String.valueOf( colIndex + 1 ) );

    if ( rowIndex == 0 ) {
      grid.getRowFormatter().setStyleName( rowIndex, "rowHeaderFormat" );
    } else {
      grid.getRowFormatter().setStyleName( rowIndex, "cellFormat" );
    }

    if ( colFlexTotal == 0 ) {
      grid.getCellFormatter().setWidth( rowIndex, colIndex, rowComponent.getWidth() + "%" );
    } else {
      int colFlex = colComponent.getFlex();
      if ( colFlex > 0 ) {
        int pct = colFlex * 100 / colFlexTotal;
        grid.getCellFormatter().setWidth( rowIndex, colIndex, pct + "%" );
      }
    }
  }

  public XulRows getRows() {
    List<XulComponent> components = this.getChildNodes();
    for ( XulComponent component : components ) {
      if ( !StringUtils.isEmpty( component.getName() ) && component.getName().equals( "rows" ) ) {
        return (XulRows) component;
      }
    }
    return null;
  }

  public XulColumns getColumns() {
    List<XulComponent> components = this.getChildNodes();
    for ( XulComponent component : components ) {
      if ( !StringUtils.isEmpty( component.getName() ) && component.getName().equals( "columns" ) ) {
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
