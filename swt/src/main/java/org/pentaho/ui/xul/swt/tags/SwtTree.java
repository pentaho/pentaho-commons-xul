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

package org.pentaho.ui.xul.swt.tags;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.TreeViewerEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
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
import org.pentaho.ui.xul.dnd.DropEffectType;
import org.pentaho.ui.xul.dnd.DropEvent;
import org.pentaho.ui.xul.dnd.DropPosition;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.impl.XulEventHandler;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.swt.TableSelection;
import org.pentaho.ui.xul.swt.tags.treeutil.TableColumnSorter;
import org.pentaho.ui.xul.swt.tags.treeutil.TreeColumnSorter;
import org.pentaho.ui.xul.swt.tags.treeutil.XulSortProperties;
import org.pentaho.ui.xul.swt.tags.treeutil.XulTableColumnLabelProvider;
import org.pentaho.ui.xul.swt.tags.treeutil.XulTableColumnModifier;
import org.pentaho.ui.xul.swt.tags.treeutil.XulTableContentProvider;
import org.pentaho.ui.xul.swt.tags.treeutil.XulTreeCellLabelProvider;
import org.pentaho.ui.xul.swt.tags.treeutil.XulTreeColumnModifier;
import org.pentaho.ui.xul.swt.tags.treeutil.XulTreeContentProvider;
import org.pentaho.ui.xul.swt.tags.treeutil.XulTreeTextCellEditor;
import org.pentaho.ui.xul.util.ColumnType;
import org.pentaho.ui.xul.util.SortDirection;
import org.pentaho.ui.xul.util.SwtDragManager;
import org.pentaho.ui.xul.util.SwtXulUtil;
import org.pentaho.ui.xul.util.TreeCellEditor;
import org.pentaho.ui.xul.util.TreeCellRenderer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

public class SwtTree extends AbstractSwtXulContainer implements XulTree {

  // Tables and trees
  // share so much of the same API, I wrapped their common methods
  // into an interface (TabularWidget) and set the component to two
  // separate member variables here so that I don't have to reference
  // them separately.

  private static final Log logger = LogFactory.getLog( SwtTree.class );

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

  private boolean hiddenRoot = true;

  private String command;

  private String toggle;

  private boolean preserveExpandedState;

  private boolean linesVisible = true;

  private XulSortProperties sortProperties = new XulSortProperties();

  private List<Binding> elementBindings = new ArrayList<Binding>();

  private String newItemBinding;

  private boolean autoCreateNewRows;

  private boolean preserveSelection;

  private Collection currentSelectedItems = null;

  private Method dropVetoerMethod;

  private Object dropVetoerController;

  private PropertyChangeListener cellChangeListener = new PropertyChangeListener() {
    public void propertyChange( PropertyChangeEvent arg0 ) {
      SwtTree.this.update();
    }
  };

  public SwtTree( Element self, XulComponent parent, XulDomContainer container, String tagName ) {
    super( tagName );
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

    XulComponent primaryColumn = this.getElementByXPath( "treecols/treecol[@primary='true']" );
    XulComponent isaContainer = this.getElementByXPath( "treechildren/treeitem[@container='true']" );

    isHierarchical = ( primaryColumn != null ) || ( isaContainer != null );

    if ( isHierarchical ) {
      int style = ( this.selType == TableSelection.MULTIPLE ) ? SWT.MULTI : SWT.None;
      style |= SWT.BORDER;

      if ( SwtXulUtil.isRunningOnWebspoonMode() ) {
        tree = new TreeViewer( (Composite) parentComponent.getManagedObject(), style );
      } else {
        tree = new PentahoTreeViewer( (Composite) parentComponent.getManagedObject(), style );
      }
      setManagedObject( tree );

    } else {
      table =
          new TableViewer( (Composite) parentComponent.getManagedObject(), SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL
              | SWT.FULL_SELECTION | SWT.BORDER );
      setManagedObject( table );
    }
    if ( isHierarchical ) {
      setupTree();
    } else {
      setupTable();
    }

    if ( getOndrag() != null ) {
      DropEffectType effect = DropEffectType.COPY;
      if ( getDrageffect() != null ) {
        effect = DropEffectType.valueOfIgnoreCase( getDrageffect() );
      }
      super.enableDrag( effect );
    }
    if ( getOndrop() != null ) {
      super.enableDrop();
    }

    this.initialized = true;

  }

  private void resizeTreeColumn() {
    final Composite parentComposite = ( (Composite) parentComponent.getManagedObject() );
    Rectangle area = parentComposite.getClientArea();
    Point preferredSize = tree.getTree().computeSize( SWT.DEFAULT, SWT.DEFAULT );
    int width = area.width - 2 * tree.getTree().getBorderWidth();
    if ( preferredSize.y > area.height + tree.getTree().getHeaderHeight() ) {
      // Subtract the scrollbar width from the total column width
      // if a vertical scrollbar will be required
      Point vBarSize = tree.getTree().getVerticalBar().getSize();
      width -= vBarSize.x;
    }
    width -= 20;

    tree.getTree().getColumn( 0 ).setWidth( width );

  }

  private void setupTree() {

    final Composite parentComposite = ( (Composite) parentComponent.getManagedObject() );
    parentComposite.addControlListener( new ControlAdapter() {
      public void controlResized( ControlEvent e ) {
        resizeTreeColumn();

      }
    } );

    TreeViewerColumn treeCol = new TreeViewerColumn( tree, SWT.LEFT );

    treeCol.getColumn().setMoveable( true );
    treeCol.getColumn().setText( "Column 3" );
    treeCol.setLabelProvider( new XulTreeCellLabelProvider( this, this.domContainer ) );
    ColumnViewerToolTipSupport.enableFor( tree );

    tree.setCellEditors( new CellEditor[] { new XulTreeTextCellEditor( tree.getTree() ) } );
    tree.setCellModifier( new XulTreeColumnModifier( this ) );

    // tree.setLabelProvider(new XulTreeLabelProvider(this, this.domContainer));
    tree.setContentProvider( new XulTreeContentProvider( this ) );

    tree.setInput( this );
    tree.getTree().setLayoutData( new GridData( GridData.FILL_BOTH ) );
    tree.setColumnProperties( new String[] { "0" } );

    TreeViewerEditor.create( tree, new ColumnViewerEditorActivationStrategy( tree ) {

      @Override
      protected boolean isEditorActivationEvent( ColumnViewerEditorActivationEvent event ) {
        return super.isEditorActivationEvent( event );
      }

    }, ColumnViewerEditor.DEFAULT );

    tree.getTree().addKeyListener( new KeyAdapter() {
      public void keyPressed( KeyEvent event ) {
        switch ( event.keyCode ) {
          case SWT.CTRL:
            SwtTree.this.controlDown = true;
            break;
          case SWT.ESC:
            // End editing session
            tree.getTree().deselectAll();
            setSelectedRows( new int[] {} );
            break;
        }
      }

      @Override
      public void keyReleased( KeyEvent event ) {
        switch ( event.keyCode ) {
          case SWT.CTRL:
            SwtTree.this.controlDown = false;
            break;
        }
      }

    } );

    // Add a focus listener to clear the contol down selector
    tree.getTree().addFocusListener( new FocusListener() {

      public void focusGained( FocusEvent arg0 ) {
      }

      public void focusLost( FocusEvent arg0 ) {
        if ( tree.getCellEditors()[0].isActivated() == false ) {
          SwtTree.this.controlDown = false;
        }
      }

    } );

    tree.addTreeListener( new ITreeViewerListener() {
      public void treeCollapsed( TreeExpansionEvent arg0 ) {
        if ( arg0.getElement() instanceof XulTreeItem ) {
          XulTreeItem t = (XulTreeItem) arg0.getElement();
          t.setExpanded( false );
          if ( toggle != null ) {
            SearchBundle b = findSelectedIndex( new SearchBundle(), getRootChildren(), t );
            Object[] toggled = new Object[] { b.selectedItem };
            invoke( toggle, new Object[] { toggled, false } );
          }
        }
      }

      public void treeExpanded( TreeExpansionEvent arg0 ) {
        if ( arg0.getElement() instanceof XulTreeItem ) {
          XulTreeItem t = (XulTreeItem) arg0.getElement();
          t.setExpanded( true );
          if ( toggle != null ) {
            SearchBundle b = findSelectedIndex( new SearchBundle(), getRootChildren(), t );
            Object[] toggled = new Object[] { b.selectedItem };
            invoke( toggle, new Object[] { toggled, true } );
          }
        }
      }
    } );

    tree.addDoubleClickListener( new IDoubleClickListener() {

      public void doubleClick( DoubleClickEvent arg0 ) {
        if ( command != null ) {
          if ( elements != null ) {
            // Invoke with selected elements as parameter
            invoke( command, new Object[] { getSelectedItems().toArray() } );
          } else {
            // Invoke with selected indexes as parameter
            invoke( command, new Object[] { getSelectedRows() } );
          }
        }
      }

    } );

    tree.addSelectionChangedListener( new ISelectionChangedListener() {
      public void selectionChanged( SelectionChangedEvent event ) {
        // if the selection is empty clear the label
        if ( event.getSelection().isEmpty() ) {
          SwtTree.this.setSelectedIndex( -1 );
          return;
        }
        if ( event.getSelection() instanceof IStructuredSelection ) {
          IStructuredSelection selection = (IStructuredSelection) event.getSelection();

          int[] selected = new int[selection.size()];
          List selectedItems = new ArrayList();

          int i = 0;
          for ( Object o : selection.toArray() ) {
            XulTreeItem selectedItem = (XulTreeItem) o;
            SearchBundle b = findSelectedIndex( new SearchBundle(), getRootChildren(), selectedItem );
            selected[i++] = b.curPos;
            selectedItems.add( b.selectedItem );
          }

          if ( selected.length == 0 ) {
            setSelectedIndex( -1 );
          } else {
            setSelectedIndex( selected[0] );
          }

          int[] selectedRows = null;
          if ( SwtTree.this.controlDown && Arrays.equals( selected, selectedRows )
              && tree.getCellEditors()[0].isActivated() == false ) {
            tree.getTree().deselectAll();
            selectedRows = new int[] {};
          } else {
            selectedRows = selected;
          }

          changeSupport.firePropertyChange( "selectedRows", null, selectedRows );
          changeSupport.firePropertyChange( "absoluteSelectedRows", null, selectedRows );
          changeSupport.firePropertyChange( "selectedItems", null, selectedItems );
          currentSelectedItems = selectedItems;

          // Single selection binding.
          Object selectedItem = ( selectedItems.size() > 0 ) ? selectedItems.get( 0 ) : null;
          changeSupport.firePropertyChange( "selectedItem", null, selectedItem );

        }
      }
    } );

    sortProperties.setSortColumn( null );
    sortProperties.setSortable( false );

    for ( XulComponent col : this.columns.getChildNodes() ) {
      XulTreeCol xulCol = (XulTreeCol) col;

      // Only process the first column that is deemed sortActive,
      // since only one column is allowed sortActive at a time
      if ( xulCol.isSortActive() && sortProperties.getSortColumn() == null ) {
        sortProperties.setSortColumn( treeCol.getColumn() );
        sortProperties.setSortDirection( SortDirection.valueOf( xulCol.getSortDirection() ) );
      }
      sortProperties.setSortMethod( treeCol.getColumn(), toGetter( xulCol.getComparatorbinding() ) );
    }

    TreeColumnSorter sorter = new TreeColumnSorter( tree, sortProperties );
  }

  private class SearchBundle {
    int curPos;

    boolean found;

    Object selectedItem;
  }

  private SearchBundle findSelectedIndex( SearchBundle bundle, XulTreeChildren children, XulTreeItem selectedItem ) {
    for ( XulComponent c : children.getChildNodes() ) {
      if ( c == selectedItem ) {
        bundle.found = true;
        if ( elements != null ) {
          bundle.selectedItem = findBoundTreeItem( bundle.curPos );
        }
        return bundle;
      }
      bundle.curPos++;
      if ( c.getChildNodes().size() > 1 ) {
        SearchBundle b = findSelectedIndex( bundle, (XulTreeChildren) c.getChildNodes().get( 1 ), selectedItem );
        if ( b.found ) {
          return b;
        }
      }
    }
    return bundle;
  }

  private Object findBoundTreeItem( int pos ) {

    if ( this.isHierarchical && this.elements != null ) {

      if ( elements == null || ( this.hiddenRoot && elements.size() == 0 ) ) {
        return null;
      }

      String method = toGetter( ( (XulTreeCol) this.getColumns().getChildNodes().get( 0 ) ).getChildrenbinding() );

      if ( pos == -1 ) {
        return null;
      }
      FindSelectedItemTuple tuple =
          findSelectedItem( this.elements, method, new FindSelectedItemTuple( pos, this.isHiddenrootnode() ) );
      return tuple != null ? tuple.selectedItem : null;
    }
    return null;

  }

  private void setupTable() {
    table.setContentProvider( new XulTableContentProvider( this ) );

    table.setLabelProvider( new XulTableColumnLabelProvider( this, domContainer ) );
    table.setCellModifier( new XulTableColumnModifier( this ) );
    Table baseTable = table.getTable();
    baseTable.setLayoutData( new GridData( GridData.FILL_BOTH ) );

    setupColumns();

    table.addSelectionChangedListener( new ISelectionChangedListener() {
      public void selectionChanged( SelectionChangedEvent event ) {

        IStructuredSelection selection = (IStructuredSelection) event.getSelection();

        setSelectedIndex( getRootChildren().getChildNodes().indexOf( selection.getFirstElement() ) );

        int[] selectedRows = new int[selection.size()];

        int i = 0;
        for ( Iterator it = selection.iterator(); it.hasNext(); ) {
          Object sel = it.next();
          selectedRows[i] = getRootChildren().getChildNodes().indexOf( sel );
          i++;
        }

        changeSupport.firePropertyChange( "selectedRows", null, selectedRows );
        changeSupport.firePropertyChange( "absoluteSelectedRows", null, selectedRows );
        Collection selectedItems = findSelectedTableRows( selectedRows );
        changeSupport.firePropertyChange( "selectedItems", null, selectedItems );

        // Single selection binding.
        Object selectedItem = ( selectedItems.size() > 0 ) ? selectedItems.toArray()[0] : null;
        changeSupport.firePropertyChange( "selectedItem", null, selectedItem );

      }
    } );

    MouseAdapter lsMouseT = new MouseAdapter() {
      public void mouseDown( MouseEvent event ) {
        if ( event.button == 1 ) {
          boolean shift = ( event.stateMask & SWT.SHIFT ) != 0;
          boolean control = ( event.stateMask & SWT.CONTROL ) != 0;
          if ( !shift && !control ) {
            Rectangle clientArea = table.getTable().getClientArea();
            Point pt = new Point( event.x, event.y );
            int index = table.getTable().getTopIndex();
            while ( index < table.getTable().getItemCount() ) {
              boolean visible = false;
              final TableItem item = table.getTable().getItem( index );
              for ( int i = 0; i < table.getTable().getColumnCount(); i++ ) {
                Rectangle rect = item.getBounds( i );
                if ( !rect.contains( pt ) ) {
                  if ( i == table.getTable().getColumnCount() - 1 && // last column
                      pt.x > rect.x + rect.width && // to the right
                      pt.y >= rect.y && pt.y <= rect.y + rect.height // same height as this visible item
                  ) {
                    return; // don't do anything when clicking to the right of the grid.
                  }
                } else {
                  return;
                }

                if ( !visible && rect.intersects( clientArea ) ) {
                  visible = true;
                }
              }
              if ( !visible ) {
                return;
              }
              index++;
            }
            insertRowAtLast();
          }
        }
      }
    };

    table.getTable().addMouseListener( lsMouseT );

    table.getTable().addTraverseListener( new TraverseListener() {

      public void keyTraversed( TraverseEvent arg0 ) {
        if ( arg0.keyCode == SWT.ARROW_DOWN ) {
          int[] rows = getSelectedRows();
          if ( rows != null && rows.length > 0 && rows[0] == table.getTable().getItemCount() - 1 ) {
            insertRowAtLast();
          }
        }
      }

    } );

    table.addDoubleClickListener( new IDoubleClickListener() {

      public void doubleClick( DoubleClickEvent arg0 ) {
        if ( command != null ) {
          if ( elements != null ) {
            // Invoke with selected elements as parameter
            invoke( command, new Object[] { getSelectedItems().toArray() } );
          } else {
            // Invoke with selected indexes as parameter
            invoke( command, new Object[] { getSelectedRows() } );
          }
        }
      }

    } );

    // Turn on the header and the lines
    baseTable.setHeaderVisible( true );
    baseTable.setLinesVisible( isTreeLines() );
    table.setInput( this );

    final Composite parentComposite = ( (Composite) parentComponent.getManagedObject() );
    parentComposite.addControlListener( new ControlAdapter() {
      public void controlResized( ControlEvent e ) {
        resizeColumns();

      }
    } );
    TableColumnSorter sorter = new TableColumnSorter( table, sortProperties );
    table.getTable().setEnabled( !this.disabled );
    table.refresh();

  }

  private void insertRowAtLast() {
    if ( this.elements != null && newItemBinding != null ) { // Bound table.
      invoke( newItemBinding );
    } else if ( autoCreateNewRows ) {
      getRootChildren().addNewRow();
      update();
    }
  }

  private Collection findSelectedTableRows( int[] selectedRows ) {
    if ( elements == null ) {
      return Collections.emptyList();
    }
    List selectedItems = new ArrayList();
    for ( int i = 0; i < selectedRows.length; i++ ) {
      if ( selectedRows[i] >= 0 && selectedRows[i] < elements.size() ) {
        selectedItems.add( elements.toArray()[selectedRows[i]] );
      }
    }
    return selectedItems;
  }

  private void resizeColumns() {
    final Composite parentComposite = ( (Composite) parentComponent.getManagedObject() );
    Rectangle area = parentComposite.getClientArea();
    Point preferredSize = table.getTable().computeSize( SWT.DEFAULT, SWT.DEFAULT );
    int width = area.width - 2 * table.getTable().getBorderWidth();
    if ( preferredSize.y > area.height + table.getTable().getHeaderHeight() ) {
      // Subtract the scrollbar width from the total column width
      // if a vertical scrollbar will be required
      Point vBarSize = table.getTable().getVerticalBar().getSize();
      width -= vBarSize.x;
    }
    width -= 20;
    double totalFlex = 0;
    for ( XulComponent col : getColumns().getChildNodes() ) {
      totalFlex += ( (XulTreeCol) col ).getFlex();
    }

    int colIdx = 0;
    for ( XulComponent col : getColumns().getChildNodes() ) {
      if ( colIdx >= table.getTable().getColumnCount() ) {
        break;
      }
      TableColumn c = table.getTable().getColumn( colIdx );
      int colFlex = ( (XulTreeCol) col ).getFlex();
      int colWidth = ( (XulTreeCol) col ).getWidth();
      if ( totalFlex == 0 ) {
        if ( colWidth > 0 ) {
          c.setWidth( colWidth );
        } else {
          c.setWidth( Math.round( width / getColumns().getColumnCount() ) );
        }
      } else if ( colFlex > 0 ) {
        if ( colWidth > 0 ) {
          c.setWidth( colWidth );
        } else {
          c.setWidth( Integer.parseInt( "" + Math.round( width * ( colFlex / totalFlex ) ) ) );
        }
      }
      colIdx++;
    }
  }

  private void setSelectedIndex( int idx ) {

    this.selectedIndex = idx;

    changeSupport.firePropertyChange( "selectedRows", null, new int[] { idx } );
    changeSupport.firePropertyChange( "absoluteSelectedRows", null, new int[] { idx } );

    if ( this.onSelect != null ) {
      invoke( this.onSelect, new Object[] { new Integer( idx ) } );
    }
  }

  private void createColumnTypesSnapshot() {
    if ( this.columns.getChildNodes().size() > 0 ) {
      Object[] xulTreeColArray = this.columns.getChildNodes().toArray();

      currentColumnTypes = new ColumnType[xulTreeColArray.length];

      for ( int i = 0; i < xulTreeColArray.length; i++ ) {
        currentColumnTypes[i] = ColumnType.valueOf( ( (XulTreeCol) xulTreeColArray[i] ).getType() );
      }
    } else {
      // Create an empty array to indicate that it has been processed, but contains 0 columns
      currentColumnTypes = new ColumnType[0];
    }
  }

  private boolean columnsNeedUpdate() {

    // Differing number of columsn
    if ( table.getTable().getColumnCount() != this.columns.getColumnCount() ) {
      return true;
    }

    // First run, always update
    if ( currentColumnTypes == null ) {
      return true;
    }

    // Column Types have changed
    Object[] xulTreeColArray = this.columns.getChildNodes().toArray();
    for ( int i = 0; i < xulTreeColArray.length; i++ ) {
      XulTreeCol xulTreeCol = (XulTreeCol) xulTreeColArray[i];
      if ( !currentColumnTypes[i].toString().equalsIgnoreCase( ( (XulTreeCol) xulTreeColArray[i] ).getType() ) ) {
        // A column has changed its type. Columns need updating
        return true;
      }
      if ( ( table.getTable().getColumn( i ).getWidth() > 0 && !xulTreeCol.isVisible() )
          || table.getTable().getColumn( i ).getWidth() == 0 && xulTreeCol.isVisible() ) {
        // A column has changed its visible status
        return true;
      }
    }

    // Columns have not changed and do not need updating
    return false;
  }

  private void setupColumns() {

    if ( columnsNeedUpdate() ) {

      sortProperties.setSortColumn( null );

      while ( table.getTable().getColumnCount() > 0 ) {
        table.getTable().getColumn( 0 ).dispose();
      }

      // Add Columns
      for ( XulComponent col : this.columns.getChildNodes() ) {
        TableColumn tc = new TableColumn( table.getTable(), SWT.LEFT );
        XulTreeCol column = (XulTreeCol) col;
        String lbl = column.getLabel();
        tc.setText( lbl != null ? lbl : "" ); //$NON-NLS-1$

        // Only process the first column that is deemed sortActive,
        // since only one column is allowed sortActive at a time
        if ( column.isSortActive() && sortProperties.getSortColumn() == null ) {
          sortProperties.setSortColumn( tc );
          sortProperties.setSortDirection( SortDirection.valueOf( column.getSortDirection() ) );
        }
        sortProperties.setSortMethod( tc, toGetter( column.getComparatorbinding() ) );
      }

      // Pack the columns
      for ( int i = 0; i < table.getTable().getColumnCount(); i++ ) {
        if ( this.columns.getColumn( i ).isVisible() ) {
          table.getTable().getColumn( i ).pack();
        }
      }
    }

    if ( table.getCellEditors() != null ) {
      for ( int i = 0; i < table.getCellEditors().length; i++ ) {
        table.getCellEditors()[i].dispose();
      }
    }
    CellEditor[] editors = new CellEditor[this.columns.getChildNodes().size()];
    String[] names = new String[getColumns().getColumnCount()];
    int i = 0;
    for ( XulComponent c : this.columns.getChildNodes() ) {
      XulTreeCol col = (XulTreeCol) c;
      final int colIdx = i;

      CellEditor editor;
      ColumnType type = col.getColumnType();
      switch ( type ) {
        case CHECKBOX:
          editor = new CheckboxCellEditor( table.getTable() );
          break;
        case COMBOBOX:
          editor = new ComboBoxCellEditor( table.getTable(), new String[] {}, SWT.READ_ONLY );
          break;
        case EDITABLECOMBOBOX:
          editor = new ComboBoxCellEditor( table.getTable(), new String[] {} );

          final CCombo editableControl = (CCombo) ( (ComboBoxCellEditor) editor ).getControl();
          editableControl.addModifyListener( new ModifyListener() {
            public void modifyText( ModifyEvent modifyEvent ) {
              domContainer.invokeLater( new Runnable() {
                @Override public void run() {
                  XulTreeCell cell = getCell( colIdx );
                  cell.setLabel( editableControl.getText() );
                }
              } );
            }
          } );

          break;
        case PASSWORD:
          editor = new TextCellEditor( table.getTable() );
          ( (Text) editor.getControl() ).setEchoChar( '*' );
          break;
        case TEXT:
        default:
          editor = new TextCellEditor( table.getTable() );

          final Text textControl = (Text) ( (TextCellEditor) editor ).getControl();
          textControl.addModifyListener( new ModifyListener() {
            public void modifyText( ModifyEvent modifyEvent ) {
              XulTreeCell cell = getCell( colIdx );
              cell.setLabel( textControl.getText() );
            }
          } );
          break;
      }

      // Create selection listener for comboboxes.
      if ( type == ColumnType.EDITABLECOMBOBOX || type == ColumnType.COMBOBOX ) {
        final CCombo editableControl = (CCombo) ( (ComboBoxCellEditor) editor ).getControl();
        editableControl.addSelectionListener( new SelectionAdapter() {
          @Override
          public void widgetDefaultSelected( SelectionEvent arg0 ) {
            // TODO Auto-generated method stub
            super.widgetDefaultSelected( arg0 );
          }

          @Override
          public void widgetSelected( SelectionEvent arg0 ) {
            super.widgetSelected( arg0 );

            XulTreeCell cell = getCell( colIdx );

            cell.setSelectedIndex( editableControl.getSelectionIndex() );
          }

        } );
      }

      editors[i] = editor;
      names[i] = "" + i; //$NON-NLS-1$
      i++;
    }
    table.setCellEditors( editors );

    table.setColumnProperties( names );
    resizeColumns();

    createColumnTypesSnapshot();

  }

  private XulTreeCell getCell( int colIdx ) {
    return ( (XulTreeItem) ( table.getTable().getSelection()[0] ).getData() ).getRow().getCell( colIdx );

  }

  public boolean isHierarchical() {
    return isHierarchical;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled( boolean disabled ) {
    this.disabled = disabled;
    if ( this.isHierarchical() == false && table != null ) {
      table.getTable().setEnabled( !disabled );
    }
  }

  public int getRows() {
    return rowsToDisplay;
  }

  public void setRows( int rowsToDisplay ) {
    this.rowsToDisplay = rowsToDisplay;

    if ( table != null && ( !table.getTable().isDisposed() ) && ( rowsToDisplay > 0 ) ) {
      int ht = rowsToDisplay * table.getTable().getItemHeight();
      if ( table.getTable().getLayoutData() != null ) {
        // tree.setSize(tree.getSize().x,height);
        ( (GridData) table.getTable().getLayoutData() ).heightHint = ht;
        ( (GridData) table.getTable().getLayoutData() ).minimumHeight = ht;

        table.getTable().getParent().layout( true );
      }
    }
  }

  public enum SELECTION_MODE {
    SINGLE, CELL, MULTIPLE
  };

  public String getSeltype() {
    return selType.toString();
  }

  public void setSeltype( String selType ) {
    if ( selType.equalsIgnoreCase( getSeltype() ) ) {
      return; // nothing has changed
    }
    this.selType = TableSelection.valueOf( selType.toUpperCase() );

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

  public void setEditable( boolean edit ) {
    editable = edit;
  }

  public void setEnableColumnDrag( boolean drag ) {
    enableColumnDrag = drag;
  }

  public String getOnselect() {
    return onSelect;
  }

  public void setOnselect( final String method ) {
    if ( method == null ) {
      return;
    }
    onSelect = method;

  }

  public void setColumns( XulTreeCols columns ) {
    this.columns = columns;
  }

  public XulTreeCols getColumns() {
    return columns;
  }

  public XulTreeChildren getRootChildren() {
    if ( rootChildren == null ) {
      rootChildren = (XulTreeChildren) this.getChildNodes().get( 1 );
    }
    return rootChildren;
  }

  public void setRootChildren( XulTreeChildren rootChildren ) {
    this.rootChildren = rootChildren;
  }

  public int[] getActiveCellCoordinates() {
    return new int[] { activeRow, activeColumn };
  }

  public void setActiveCellCoordinates( int row, int column ) {
    activeRow = row;
    activeColumn = column;

  }

  public Object[][] getValues() {

    Object[][] data = new Object[getRootChildren().getChildNodes().size()][getColumns().getColumnCount()];

    int y = 0;
    for ( XulComponent item : getRootChildren().getChildNodes() ) {
      int x = 0;
      for ( XulComponent tempCell : ( (XulTreeItem) item ).getRow().getChildNodes() ) {
        XulTreeCell cell = (XulTreeCell) tempCell;
        switch ( columns.getColumn( x ).getColumnType() ) {
          case CHECKBOX:
            Boolean flag = (Boolean) cell.getValue();
            if ( flag == null ) {
              flag = Boolean.FALSE;
            }
            data[y][x] = flag;
            break;
          case COMBOBOX:
            Vector values = (Vector) cell.getValue();
            int idx = cell.getSelectedIndex();
            data[y][x] = values.get( idx );
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

  public void setData( Object data ) {
    this.data = data;
  }

  public int[] getSelectedRows() {
    if ( selectedIndex > -1 ) {
      return new int[] { selectedIndex };
    } else {
      return new int[] {};
    }
  }

  public int[] getAbsoluteSelectedRows() {
    return getSelectedRows();
  }

  public Collection getSelectedItems() {
    if ( elements == null ) {
      return null;
    }

    if ( isHierarchical() ) {

      List selectedItems = new ArrayList();
      IStructuredSelection selection = (IStructuredSelection) tree.getSelection();

      int i = 0;
      for ( Object o : selection.toArray() ) {
        XulTreeItem selectedItem = (XulTreeItem) o;
        SearchBundle b = findSelectedIndex( new SearchBundle(), getRootChildren(), selectedItem );
        selectedItems.add( b.selectedItem );
      }
      return selectedItems;
    } else {

      IStructuredSelection selection = (IStructuredSelection) table.getSelection();

      setSelectedIndex( getRootChildren().getChildNodes().indexOf( selection.getFirstElement() ) );

      int[] selectedRows = new int[selection.size()];

      int i = 0;
      for ( Iterator it = selection.iterator(); it.hasNext(); ) {
        Object sel = it.next();
        selectedRows[i] = getRootChildren().getChildNodes().indexOf( sel );
        i++;
      }

      return findSelectedTableRows( selectedRows );
    }

  }

  public void addTreeRow( XulTreeRow row ) {
    this.addChild( row );
    XulTreeItem item = new SwtTreeItem( this.getRootChildren() );
    row.setParentTreeItem( item );
    ( (SwtTreeRow) row ).layout();
  }

  public void removeTreeRows( int[] rows ) {
    // TODO Auto-generated method stub

  }

  public void update() {
    if ( settingElements.get() ) {
      return;
    }
    if ( this.isHierarchical ) {
      this.tree.refresh();

      if ( "true".equals( getAttributeValue( "expanded" ) ) ) {
        tree.expandAll();
      } else if ( expandBindings.size() > 0 && this.suppressEvents == false ) {
        for ( Binding expBind : expandBindings ) {
          try {
            expBind.fireSourceChanged();
          } catch ( Exception e ) {
            logger.error( e );
          }
        }
        expandBindings.clear();
      }
      resizeTreeColumn();
    } else {

      setupColumns();
      this.table.setInput( this );
      this.table.refresh();

    }
    this.selectedIndex = -1;

  }

  public void clearSelection() {
  }

  public void setSelectedRows( int[] rows ) {
    if ( this.isHierarchical ) {
      Object selected = getSelectedTreeItem( rows );
      int prevSelected = -1;
      if ( selectedRows != null && selectedRows.length > 0 ) {
        prevSelected = selectedRows[0]; // single selection only for now
      }

      // tree.setSelection(new StructuredSelection(getSelectedTreeItems(rows)));
      changeSupport.firePropertyChange( "selectedItem", prevSelected, selected );
    } else {
      table.getTable().setSelection( rows );
    }
    if ( rows.length > 0 ) {
      this.selectedIndex = rows[0]; // single selection only for now
    }
    changeSupport.firePropertyChange( "selectedRows", this.selectedRows, rows );
    changeSupport.firePropertyChange( "absoluteSelectedRows", this.selectedRows, rows );
    this.selectedRows = rows;

  }

  public String getOnedit() {
    return onedit;
  }

  public void setOnedit( String onedit ) {
    this.onedit = onedit;
  }

  private Collection elements;

  private boolean suppressEvents = false;

  private final AtomicBoolean settingElements = new AtomicBoolean( false );

  private String childrenBinding;

  private String getChildrenBinding() {
    if ( childrenBinding == null ) {
      childrenBinding = ( (XulTreeCol) this.getColumns().getChildNodes().get( 0 ) ).getChildrenbinding();
    }
    return childrenBinding;
  }

  int[] expandCache;

  private void cacheExpandedState() {
    Object[] expandedTreeItems = tree.getExpandedElements();
    expandCache = new int[expandedTreeItems.length];
    for ( int i = 0; i < expandedTreeItems.length; i++ ) {
      XulTreeItem item = (XulTreeItem) expandedTreeItems[i];

      SearchBundle b = findSelectedIndex( new SearchBundle(), getRootChildren(), item );

      expandCache[i] = b.curPos;
    }
  }

  private void restoreExpandedState() {
    for ( int i = 0; i < expandCache.length; i++ ) {
      XulTreeItem item = findTreeItemForPos( expandCache[i] );
      if ( item != null ) {
        tree.setExpandedState( item, true );
      }
    }
  }

  private XulTreeItem findTreeItemForPos( int pos ) {
    if ( !isHierarchical() ) {
      return (XulTreeItem) getTreeChildren( this ).getChildNodes().get( pos );
    } else {

      FindTreeItemForPosTuple tuple = new FindTreeItemForPosTuple( pos, this );
      findTreeItemAtPosFunc( tuple );
      return tuple.treeItem;
    }
  }

  private void findTreeItemAtPosFunc( FindTreeItemForPosTuple bundle ) {
    if ( bundle.curPos == bundle.pos ) {
      bundle.found = true;
      bundle.treeItem = (XulTreeItem) bundle.curComponent;
      return;
    }

    XulTreeChildren children = getTreeChildren( bundle.curComponent );
    if ( children == null ) {
      return;
    }
    for ( XulComponent c : getTreeChildren( bundle.curComponent ).getChildNodes() ) {
      bundle.curPos++;
      bundle.curComponent = c;
      findTreeItemAtPosFunc( bundle );
      if ( bundle.found ) {
        return;
      }
    }
  }

  private static class FindTreeItemForPosTuple {
    int pos;
    int curPos = -1;
    XulComponent curComponent;
    XulTreeItem treeItem;
    boolean found;

    public FindTreeItemForPosTuple( int pos, XulComponent curComponent ) {
      this.pos = pos;
      this.curComponent = curComponent;
    }
  }

  private void destroyPreviousBindings() {

    for ( Binding bind : elementBindings ) {
      bind.destroyBindings();
    }
    elementBindings.clear();

  }

  public <T> void setElements( Collection<T> elements ) {

    settingElements.set( true );
    try {
      // If preserveselection is set to true in the xul file. We will honor that and save the selection before
      // setting the elements. After the setElements is done we will set the current selected selection
      int scrollPos = -1;
      if ( this.isHierarchical ) {
        if ( isPreserveexpandedstate() ) {
          cacheExpandedState();
        }
        scrollPos = tree.getTree().getVerticalBar().getSelection();
      }

      destroyPreviousBindings();

      this.elements = elements;
      this.getRootChildren().removeAll();

      if ( elements == null ) {
        update();
        changeSupport.firePropertyChange( "selectedRows", null, getSelectedRows() );
        changeSupport.firePropertyChange( "absoluteSelectedRows", null, getAbsoluteSelectedRows() );
        return;
      }

      try {

        if ( this.isHierarchical == false ) {
          for ( T o : elements ) {
            XulTreeRow row = this.getRootChildren().addNewRow();

            // Give the Xul Element a reference back to the domain object
            ( (XulTreeItem) row.getParent() ).setBoundObject( o );

            for ( int x = 0; x < this.getColumns().getChildNodes().size(); x++ ) {
              XulComponent col = this.getColumns().getColumn( x );
              final XulTreeCell cell = (XulTreeCell) getDocument().createElement( "treecell" );
              XulTreeCol column = (XulTreeCol) col;

              for ( InlineBindingExpression exp : ( (XulTreeCol) col ).getBindingExpressions() ) {
                if ( logger.isDebugEnabled() ) {
                  logger
                    .debug( "applying binding expression [" + exp + "] to xul tree cell [" + cell + "] and model [" + o
                      + "]" );
                }

                String colType = column.getType();
                if ( StringUtils.isEmpty( colType ) == false && colType.equals( "dynamic" ) ) {
                  colType = extractDynamicColType( o, x );
                }

                if ( ( colType.equalsIgnoreCase( "combobox" ) || colType.equalsIgnoreCase( "editablecombobox" ) )
                  && column.getCombobinding() != null ) {

                  Binding binding = createBinding( (XulEventSource) o, column.getCombobinding(), cell, "value" );
                  elementBindings.add( binding );
                  binding.setBindingType( Binding.Type.ONE_WAY );
                  domContainer.addBinding( binding );
                  binding.fireSourceChanged();

                  binding =
                    createBinding( (XulEventSource) o, ( (XulTreeCol) col ).getBinding(), cell, "selectedIndex" );
                  elementBindings.add( binding );
                  binding.setConversion( new BindingConvertor<Object, Integer>() {

                    @Override
                    public Integer sourceToTarget( Object value ) {
                      int index = ( (Vector) cell.getValue() ).indexOf( value );
                      return index > -1 ? index : 0;
                    }

                    @Override
                    public Object targetToSource( Integer value ) {
                      return ( (Vector) cell.getValue() ).get( value );
                    }

                  } );
                  domContainer.addBinding( binding );
                  binding.fireSourceChanged();

                  if ( colType.equalsIgnoreCase( "editablecombobox" ) ) {
                    binding = createBinding( (XulEventSource) o, exp.getModelAttr(), cell, exp.getXulCompAttr() );
                    elementBindings.add( binding );
                    if ( !this.editable ) {
                      binding.setBindingType( Binding.Type.ONE_WAY );
                    } else {
                      binding.setBindingType( Binding.Type.BI_DIRECTIONAL );
                    }
                    domContainer.addBinding( binding );
                  }

                } else if ( colType.equalsIgnoreCase( "checkbox" ) ) {
                  if ( StringUtils.isNotEmpty( exp.getModelAttr() ) ) {
                    Binding binding = createBinding( (XulEventSource) o, exp.getModelAttr(), cell, "value" );
                    elementBindings.add( binding );
                    if ( !column.isEditable() ) {
                      binding.setBindingType( Binding.Type.ONE_WAY );
                    }
                    domContainer.addBinding( binding );
                    binding.fireSourceChanged();
                  }
                } else {

                  if ( StringUtils.isNotEmpty( exp.getModelAttr() ) ) {
                    Binding binding =
                      createBinding( (XulEventSource) o, exp.getModelAttr(), cell, exp.getXulCompAttr() );
                    elementBindings.add( binding );
                    if ( !column.isEditable() ) {
                      binding.setBindingType( Binding.Type.ONE_WAY );
                    }
                    domContainer.addBinding( binding );
                    binding.fireSourceChanged();
                  } else {
                    cell.setLabel( o.toString() );
                  }
                }

              }
              if ( column.getDisabledbinding() != null ) {
                String prop = column.getDisabledbinding();
                Binding bind = createBinding( (XulEventSource) o, column.getDisabledbinding(), cell, "disabled" );
                elementBindings.add( bind );
                bind.setBindingType( Binding.Type.ONE_WAY );
                domContainer.addBinding( bind );
                bind.fireSourceChanged();
              }

              Method imageMethod;
              String imageSrc = null;

              String method = toGetter( ( (XulTreeCol) this.getColumns().getChildNodes().get( x ) ).getImagebinding() );

              if ( method != null ) {
                imageMethod = o.getClass().getMethod( method );
                imageSrc = (String) imageMethod.invoke( o );
                SwtTreeItem item = (SwtTreeItem) row.getParent();
                item.setXulDomContainer( this.domContainer );
                ( (XulTreeItem) row.getParent() ).setImage( imageSrc );
              }

              row.addCell( cell );
            }
          }
        } else {
          // tree
          suppressEvents = true;
          if ( isHiddenrootnode() == false ) {
            SwtTreeItem item = new SwtTreeItem( this.getRootChildren() );
            item.setXulDomContainer( this.domContainer );

            item.setBoundObject( elements );

            SwtTreeRow newRow = new SwtTreeRow( item );
            item.setRow( newRow );
            this.getRootChildren().addChild( item );

            addTreeChild( elements, newRow );

          } else {
            for ( T o : elements ) {
              SwtTreeItem item = new SwtTreeItem( this.getRootChildren() );
              item.setXulDomContainer( this.domContainer );
              item.setBoundObject( o );

              SwtTreeRow newRow = new SwtTreeRow( item );
              item.setRow( newRow );
              this.getRootChildren().addChild( item );

              addTreeChild( o, newRow );
            }
          }
          suppressEvents = false;

        }

        update();
        if ( this.isHierarchical ) {
          if ( isPreserveexpandedstate() ) {
            restoreExpandedState();
          }
          final int fScrollPos = scrollPos;
        }
        // Now since we are done with setting the elements, we will now see if preserveselection was set to be true
        // then we will set the selected items to the currently saved one
        if ( isPreserveselection() && currentSelectedItems != null && currentSelectedItems.size() > 0 ) {
          setSelectedItems( currentSelectedItems );
          suppressEvents = false;
        } else {
          // treat as a selection change
          suppressEvents = false;
          changeSupport.firePropertyChange( "selectedRows", null, getSelectedRows() );
          changeSupport.firePropertyChange( "absoluteSelectedRows", null, getAbsoluteSelectedRows() );
          changeSupport.firePropertyChange( "selectedItems", null, Collections.EMPTY_LIST );
          changeSupport.firePropertyChange( "selectedItem", "", null );
        }

      } catch ( XulException e ) {
        logger.error( "error adding elements", e );
      } catch ( Exception e ) {
        logger.error( "error adding elements", e );
      }
    } finally {
      settingElements.set( false );
      update();
    }
  }

  private List<Binding> expandBindings = new ArrayList<Binding>();

  private TreeLabelBindingConvertor treeLabelConvertor = new TreeLabelBindingConvertor( this );

  private <T> void addTreeChild( T element, XulTreeRow row ) {
    try {
      SwtTreeCell cell = (SwtTreeCell) getDocument().createElement( "treecell" );

      for ( InlineBindingExpression exp : ( (XulTreeCol) this.getColumns().getChildNodes().get( 0 ) )
          .getBindingExpressions() ) {
        if ( logger.isDebugEnabled() ) {
          logger
            .debug( "applying binding expression [" + exp + "] to xul tree cell [" + cell + "] and model [" + element
              + "]" );
        }

        // Tree Bindings are one-way for now as you cannot edit tree nodes
        Binding binding = createBinding( (XulEventSource) element, exp.getModelAttr(), cell, exp.getXulCompAttr() );
        elementBindings.add( binding );
        binding.setConversion( treeLabelConvertor );
        if ( this.isEditable() ) {
          binding.setBindingType( Binding.Type.BI_DIRECTIONAL );
        } else {
          binding.setBindingType( Binding.Type.ONE_WAY );
        }
        domContainer.addBinding( binding );
        binding.fireSourceChanged();
      }

      // TODO: migrate this into the XulComponent
      cell.addPropertyChangeListener( "label", cellChangeListener );

      XulTreeCol column = (XulTreeCol) this.getColumns().getChildNodes().get( 0 );
      String expBind = column.getExpandedbinding();
      if ( expBind != null ) {
        Binding binding = createBinding( (XulEventSource) element, expBind, row.getParent(), "expanded" );
        elementBindings.add( binding );
        binding.setBindingType( Binding.Type.BI_DIRECTIONAL );
        domContainer.addBinding( binding );
        expandBindings.add( binding );
      }

      if ( column.getDisabledbinding() != null ) {
        String prop = column.getDisabledbinding();
        Binding bind =
            createBinding( (XulEventSource) element, column.getDisabledbinding(), row.getParent(), "disabled" );
        elementBindings.add( bind );
        bind.setBindingType( Binding.Type.ONE_WAY );
        domContainer.addBinding( bind );
        bind.fireSourceChanged();
      }

      if ( column.getTooltipbinding() != null ) {
        String prop = column.getTooltipbinding();
        Binding bind =
            createBinding( (XulEventSource) element, column.getTooltipbinding(), row.getParent(), "tooltiptext" );
        elementBindings.add( bind );
        bind.setBindingType( Binding.Type.ONE_WAY );
        domContainer.addBinding( bind );
        bind.fireSourceChanged();
      }

      row.addCell( cell );

      // find children
      String method = toGetter( ( (XulTreeCol) this.getColumns().getChildNodes().get( 0 ) ).getChildrenbinding() );
      Method childrenMethod = null;

      try {
        childrenMethod = element.getClass().getMethod( method, new Class[] {} );
      } catch ( NoSuchMethodException e ) {
        logger.debug( "Could not find children binding method for object: " + element.getClass().getSimpleName() );
      }

      method = ( (XulTreeCol) this.getColumns().getChildNodes().get( 0 ) ).getImagebinding();
      if ( method != null ) {
        Binding binding = createBinding( (XulEventSource) element, method, row.getParent(), "image" );
        elementBindings.add( binding );
        binding.setBindingType( Binding.Type.ONE_WAY );
        domContainer.addBinding( binding );
        binding.fireSourceChanged();
      }

      Collection<T> children = null;
      if ( childrenMethod != null ) {
        children = (Collection<T>) childrenMethod.invoke( element, new Object[] {} );
      } else if ( element instanceof Collection ) {
        children = (Collection<T>) element;
      }

      XulTreeChildren treeChildren = null;

      if ( children != null && children.size() > 0 ) {
        treeChildren = (XulTreeChildren) getDocument().createElement( "treechildren" );
        row.getParent().addChild( treeChildren );
      }
      if ( children == null ) {
        return;
      }
      for ( T child : children ) {

        SwtTreeItem item = new SwtTreeItem( treeChildren );
        item.setXulDomContainer( this.domContainer );

        SwtTreeRow newRow = new SwtTreeRow( item );
        item.setRow( newRow );
        item.setBoundObject( child );
        treeChildren.addChild( item );

        addTreeChild( child, newRow );
      }
    } catch ( Exception e ) {
      logger.error( "error adding elements", e );
    }
  }

  public <T> Collection<T> getElements() {
    return null;
  }

  private String extractDynamicColType( Object row, int columnPos ) {
    try {
      Method method = row.getClass().getMethod( this.columns.getColumn( columnPos ).getColumntypebinding() );
      return (String) method.invoke( row, new Object[] {} );
    } catch ( Exception e ) {
      logger.debug( "Could not extract column type from binding" );
    }
    return "text"; // default //$NON-NLS-1$
  }

  private static String toGetter( String property ) {
    if ( property == null ) {
      return null;
    }
    return "get" + ( property.substring( 0, 1 ).toUpperCase() + property.substring( 1 ) );
  }

  public Object getSelectedItem() {
    Collection c = getSelectedItems();
    if ( c != null && c.size() > 0 ) {
      return c.toArray()[0];
    }
    return null;

  }

  // private List<Object> getSelectedTreeItems(int[] currentSelection) {
  // if (this.isHierarchical && this.elements != null) {
  //
  // int[] vals = currentSelection;
  // if (vals == null || vals.length == 0 || elements == null || elements.size() == 0) {
  // return null;
  // }
  //
  // String property = toGetter(((XulTreeCol) this.getColumns().getChildNodes().get(0)).getChildrenbinding());
  //
  // List<Object> selection = new ArrayList<Object>();
  // for(int pos : currentSelection){
  // FindBoundItemTuple tuple = new FindBoundItemTuple(pos);
  // findBoundItem(this.elements, this, property, tuple);
  //
  // selection.add(tuple.treeItem);
  // }
  // return selection;
  // }
  // return null;
  // }

  private Object getSelectedTreeItem( int[] currentSelection ) {
    if ( this.isHierarchical && this.elements != null ) {

      int[] vals = currentSelection;
      if ( vals == null || vals.length == 0 || elements == null || elements.size() == 0 ) {
        return null;
      }

      String property = toGetter( ( (XulTreeCol) this.getColumns().getChildNodes().get( 0 ) ).getChildrenbinding() );

      int selectedIdx = vals[0];
      if ( selectedIdx == -1 ) {
        return null;
      }
      FindSelectedItemTuple tuple =
          findSelectedItem( this.elements, property, new FindSelectedItemTuple( selectedIdx, this.isHiddenrootnode() ) );
      return tuple != null ? tuple.selectedItem : null;
    }
    return null;
  }

  private void fireSelectedItem() {
    this.changeSupport.firePropertyChange( "selectedItem", null, getSelectedItem() );
  }

  private static class FindSelectedItemTuple {
    Object selectedItem = null;

    int curpos = -1; // ignores first element (root)

    int selectedIndex;

    public FindSelectedItemTuple( int selectedIndex, boolean rootHidden ) {
      this.selectedIndex = selectedIndex;
      if ( rootHidden == false ) {
        curpos = 0;
      }
    }
  }

  private void removeItemFromElements( Object item, DropEvent event ) {
    String method = toGetter( ( (XulTreeCol) this.getColumns().getChildNodes().get( 0 ) ).getChildrenbinding() );
    removeItem( elements, method, item, event );
  }

  private void removeItem( Object parent, String childrenMethodProperty, Object toRemove, DropEvent event ) {
    Collection children = getChildCollection( parent, childrenMethodProperty );
    if ( children == null ) {
      return;
    }
    Iterator iter = children.iterator();
    int pos = 0;
    while ( iter.hasNext() ) {
      Object next = iter.next();
      if ( next == toRemove ) {
        // check to see if DnD is same origin, if so index needs to be adjusted
        if ( event.getDropParent() == children && event.getDropIndex() > pos ) {
          event.setDropIndex( event.getDropIndex() - 1 );
        }
        children.remove( toRemove );
        return;
      }
      removeItem( next, childrenMethodProperty, toRemove, event );
      pos++;
    }
  }

  private FindSelectedItemTuple findSelectedItem( Object parent, String childrenMethodProperty,
      FindSelectedItemTuple tuple ) {
    if ( tuple.curpos == tuple.selectedIndex ) {
      tuple.selectedItem = parent;
      return tuple;
    }
    Collection children = getChildCollection( parent, childrenMethodProperty );

    if ( children == null || children.size() == 0 ) {
      return null;
    }

    for ( Object child : children ) {
      tuple.curpos++;
      findSelectedItem( child, childrenMethodProperty, tuple );
      if ( tuple.selectedItem != null ) {
        return tuple;
      }
    }
    return null;
  }

  public void registerCellEditor( String key, TreeCellEditor editor ) {
    // TODO Auto-generated method stub

  }

  public void registerCellRenderer( String key, TreeCellRenderer renderer ) {
    // TODO Auto-generated method stub

  }

  public void collapseAll() {
    if ( this.isHierarchical ) {
      tree.collapseAll();
    }
  }

  public void expandAll() {
    if ( this.isHierarchical ) {
      tree.expandAll();
    }
  }

  private static class FindBoundItemTuple {
    Object item = null;
    XulComponent treeItem;

    public FindBoundItemTuple( Object item ) {
      this.item = item;
    }
  }

  private static Collection getChildCollection( Object obj, String childrenMethodProperty ) {
    Collection children = null;
    Method childrenMethod = null;
    try {
      childrenMethod = obj.getClass().getMethod( childrenMethodProperty, new Class[] {} );
    } catch ( NoSuchMethodException e ) {
      if ( obj instanceof Collection ) {
        children = (Collection) obj;
      }
    }
    try {
      if ( childrenMethod != null ) {
        children = (Collection) childrenMethod.invoke( obj, new Object[] {} );
      }
    } catch ( Exception e ) {
      logger.error( e );
      return null;
    }

    return children;
  }

  private XulTreeChildren getTreeChildren( XulComponent parent ) {
    // Top node is an exception when showing root
    if ( parent == this && this.isHiddenrootnode() == false ) {
      List<XulComponent> childNodes = this.getRootChildren().getChildNodes();
      if ( childNodes.size() > 0 ) {
        parent = childNodes.get( 0 );
      } else {
        return null;
      }
    }

    for ( XulComponent c : parent.getChildNodes() ) {
      if ( c instanceof XulTreeChildren ) {
        return (XulTreeChildren) c;
      }
    }
    return null;
  }

  private FindBoundItemTuple findBoundItem( Object obj, XulComponent parent, String childrenMethodProperty,
      FindBoundItemTuple tuple ) {
    if ( obj.equals( tuple.item ) ) {
      tuple.treeItem = parent;
      return tuple;
    }
    Collection children = getChildCollection( obj, childrenMethodProperty );
    if ( children == null || children.size() == 0 ) {
      return null;
    }
    XulTreeChildren xulChildren = getTreeChildren( parent );
    Object[] childrenArry = children.toArray();
    for ( int i = 0; i < children.size(); i++ ) {
      findBoundItem( childrenArry[i], xulChildren.getChildNodes().get( i ), childrenMethodProperty, tuple );
      if ( tuple.treeItem != null ) {
        return tuple;
      }
    }
    return null;
  }

  public void setBoundObjectExpanded( Object o, boolean expanded ) {
    FindBoundItemTuple tuple = new FindBoundItemTuple( o );
    String property = toGetter( ( (XulTreeCol) this.getColumns().getChildNodes().get( 0 ) ).getChildrenbinding() );
    findBoundItem( this.elements, this, property, tuple );
    if ( tuple.treeItem != null ) {
      setTreeItemExpanded( (XulTreeItem) tuple.treeItem, expanded );
    }
  }

  public void setTreeItemExpanded( XulTreeItem item, boolean expanded ) {
    if ( this.isHierarchical ) {
      tree.setExpandedState( item, expanded );
    }
  }

  @Override
  public void setPopup( final IMenuManager menuMgr ) {
    final Control control;
    if ( isHierarchical() ) {
      control = tree.getControl();
    } else {
      control = table.getControl();
    }

    Menu menu = ( (MenuManager) menuMgr ).createContextMenu( control );
    control.setMenu( menu );

    control.addListener( SWT.MenuDetect, new Listener() {

      public void handleEvent( Event evt ) {
        Point pt = control.getDisplay().map( control, null, new Point( evt.x, evt.y ) );
        Menu menu = control.getMenu();
        menu.setLocation( evt.x, evt.y );
        menu.setVisible( true );
      }
    } );

  }

  protected Control getDndObject() {
    Control control;
    if ( isHierarchical() ) {
      control = (Control) tree.getControl();
    } else {
      control = (Control) table.getControl();
    }
    return control;
  }

  protected List<Object> cachedDndItems;

  @Override
  protected List<Object> getSwtDragData() {

    // if bound, return a list of bound objects, otherwise return strings.
    // note, all of these elements must be serializable.
    if ( elements != null ) {
      cachedDndItems = (List<Object>) getSelectedItems();
    } else {

      IStructuredSelection selection;
      if ( !isHierarchical ) {
        selection = (IStructuredSelection) tree.getSelection();
      } else {
        selection = (IStructuredSelection) table.getSelection();
      }
      List<Object> list = new ArrayList<Object>();
      int i = 0;
      for ( Object o : selection.toArray() ) {
        list.add( ( (XulTreeItem) o ).getRow().getCell( 0 ).getLabel() );
      }
      cachedDndItems = list;
    }
    return cachedDndItems;
  }

  @Override
  protected void resolveDndParentAndIndex( DropEvent xulEvent ) {
    Object parentObj = null;
    int index = -1;

    if ( !isHierarchical ) {
      DropTargetEvent event = (DropTargetEvent) xulEvent.getNativeEvent();
      if ( event.item != null ) {
        TableItem item = (TableItem) event.item;

        if ( item != null ) {
          if ( elements != null ) {
            // swt -> xul -> element
            if ( item.getData() instanceof SwtTreeItem ) {
              SwtTreeItem treeItem = (SwtTreeItem) item.getData();
              parentObj = treeItem.getBoundObject();
            }
          } else {
            parentObj = item.getText();
          }
        }

        index = 0;
      }
    } else {
      TreeItem parent = null;

      DropTargetEvent event = (DropTargetEvent) xulEvent.getNativeEvent();
      if ( event.item != null ) {
        TreeItem item = (TreeItem) event.item;
        Point pt = tree.getControl().getDisplay().map( null, tree.getControl(), event.x, event.y );
        Rectangle bounds = item.getBounds();
        parent = item.getParentItem();

        if ( parent != null ) {
          TreeItem[] items = parent.getItems();
          index = 0;
          for ( int i = 0; i < items.length; i++ ) {
            if ( items[i] == item ) {
              index = i;
              break;
            }
          }
          if ( pt.y > bounds.y + 2 * bounds.height / 3 ) {
            // HANDLE parent, index + 1
            index++;
          } else {
            parent = item;
            index = 0;
          }

        } else {
          TreeItem[] items = tree.getTree().getItems();
          index = 0;
          for ( int i = 0; i < items.length; i++ ) {
            if ( items[i] == item ) {
              index = i;
              break;
            }
          }
          if ( pt.y > bounds.y + 2 * bounds.height / 3 ) {
            index++;
          } else {
            // item is parent
            parent = item;
            index = 0;
          }
        }
      }

      if ( parent != null ) {
        if ( elements != null ) {
          // swt -> xul -> element
          SearchBundle b = findSelectedIndex( new SearchBundle(), getRootChildren(), (XulTreeItem) parent.getData() );
          parentObj = b.selectedItem;
        } else {
          parentObj = parent.getText();
        }
      }
    }

    xulEvent.setDropParent( parentObj );
    xulEvent.setDropIndex( index );

  }

  @Override
  protected void onSwtDragDropAccepted( DropEvent xulEvent ) {
    List results = xulEvent.getDataTransfer().getData();
    if ( elements != null ) {
      // place the new elements in the new location
      Collection insertInto = elements;
      if ( xulEvent.getDropParent() != null ) {
        String method = toGetter( ( (XulTreeCol) this.getColumns().getChildNodes().get( 0 ) ).getChildrenbinding() );
        insertInto = getChildCollection( xulEvent.getDropParent(), method );
      }
      if ( insertInto instanceof List ) {
        List list = (List) insertInto;
        if ( xulEvent.getDropIndex() == -1 ) {
          for ( Object o : results ) {
            list.add( o );
          }
        } else {
          for ( int i = results.size() - 1; i >= 0; i-- ) {
            list.add( xulEvent.getDropIndex(), results.get( i ) );
          }
        }
      }
      // todo, can i trigger this through another mechanism?
      setElements( elements );
    }
  }

  @Override
  protected void onSwtDragFinished( DropEffectType effect, DropEvent event ) {
    if ( effect == DropEffectType.MOVE ) {
      // ISelection sel = tree.getSelection();
      if ( elements != null ) {
        // remove cachedDndItems from the tree.. traverse
        for ( Object item : cachedDndItems ) {
          removeItemFromElements( item, event );
        }
        cachedDndItems = null;
        setElements( elements );
      } else {
        if ( isHierarchical() ) {
          tree.remove( tree.getSelection() );
        } else {
          table.remove( table.getSelection() );
        }
      }
    }
  }

  @Override
  public void setDropvetoer( String dropVetoMethod ) {
    if ( StringUtils.isEmpty( dropVetoMethod ) ) {
      return;
    }
    super.setDropvetoer( dropVetoMethod );
  }

  private void resolveDropVetoerMethod() {
    if ( dropVetoerMethod == null && getDropvetoer() != null ) {

      String id = getDropvetoer().substring( 0, getDropvetoer().indexOf( "." ) );
      try {
        XulEventHandler controller = this.domContainer.getEventHandler( id );
        this.dropVetoerMethod =
            controller.getClass().getMethod(
                getDropvetoer().substring( getDropvetoer().indexOf( "." ) + 1, getDropvetoer().indexOf( "(" ) ),
                new Class[] { DropEvent.class } );
        this.dropVetoerController = controller;
      } catch ( XulException e ) {
        e.printStackTrace();
      } catch ( NoSuchMethodException e ) {
        e.printStackTrace();
      }
    }

  }

  private DropPosition curPos;

  protected void onSwtDragOver( DropTargetEvent dropEvent ) {

    dropEvent.feedback = DND.FEEDBACK_EXPAND | DND.FEEDBACK_SCROLL;
    if ( dropEvent.item != null ) {
      Rectangle bounds = null;
      Point pt = null;
      if ( isHierarchical() ) {
        TreeItem item = (TreeItem) dropEvent.item;
        pt = tree.getControl().getDisplay().map( null, tree.getControl(), dropEvent.x, dropEvent.y );
        bounds = item.getBounds();
      } else {
        TableItem item = (TableItem) dropEvent.item;
        pt = table.getControl().getDisplay().map( null, table.getControl(), dropEvent.x, dropEvent.y );
        bounds = item.getBounds();
      }

      if ( pt.y < bounds.y + bounds.height / 3 ) {
        curPos = DropPosition.ABOVE;
      } else if ( pt.y > bounds.y + 2 * ( bounds.height / 3 ) ) {
        curPos = DropPosition.BELOW;
      } else {
        curPos = DropPosition.MIDDLE;
      }

      resolveDropVetoerMethod();

      DropEvent event = SwtDragManager.getInstance().getCurrentDropEvent();
      if ( dropVetoerMethod != null ) {

        XulTreeItem xulItem = (XulTreeItem) dropEvent.item.getData();

        if ( curPos == DropPosition.MIDDLE ) {
          event.setDropParent( xulItem.getBoundObject() );
        } else {

          XulComponent parent = xulItem.getParent().getParent();
          Object parentObj;
          if ( parent instanceof SwtTree ) {
            parentObj = SwtTree.this.elements;
          } else {
            parentObj = ( (XulTreeItem) parent ).getBoundObject();
          }
          event.setDropParent( parentObj );
        }

        event.setNativeEvent( dropEvent );
        event.setAccepted( true );

        resolveDndParentAndIndex( event );

        event.setDropPosition( curPos );

        // if(curPos == DropPosition.MIDDLE){
        // event.setDropParent(xulItem.getBoundObject());
        // } else {
        //
        // XulComponent parent = xulItem.getParent().getParent();
        // Object parentObj;
        // if(parent instanceof GwtTree){
        // parentObj = GwtTree.this.elements;
        // } else {
        // parentObj = ((XulTreeItem) parent).getBoundObject();
        // }
        // event.setDropParent(parentObj);
        // }

        try {
          // Consult Vetoer method to see if this is a valid drop operation
          dropVetoerMethod.invoke( dropVetoerController, new Object[] { event } );

        } catch ( InvocationTargetException e ) {
          e.printStackTrace();
        } catch ( IllegalAccessException e ) {
          e.printStackTrace();
        }
      }

      if ( event.isAccepted() == false ) {
        dropEvent.feedback = DND.FEEDBACK_NONE;
      } else if ( pt.y < bounds.y + bounds.height / 3 ) {
        dropEvent.feedback |= DND.FEEDBACK_INSERT_BEFORE;
      } else if ( pt.y > bounds.y + 2 * ( bounds.height / 3 ) ) {
        dropEvent.feedback |= DND.FEEDBACK_INSERT_AFTER;
      } else {
        dropEvent.feedback |= DND.FEEDBACK_SELECT;
      }

    }

  }

  public <T> void setSelectedItems( Collection<T> items ) {
    int[] selIndexes = new int[items.size()];

    if ( this.isHierarchical && this.elements != null ) {
      List<Object> selection = new ArrayList<Object>();

      String property = toGetter( ( (XulTreeCol) this.getColumns().getChildNodes().get( 0 ) ).getChildrenbinding() );
      for ( T t : items ) {
        FindBoundItemTuple tuple = new FindBoundItemTuple( t );
        findBoundItem( this.elements, this, property, tuple );

        XulComponent bItem = tuple.treeItem;
        if ( tuple.treeItem == this ) { // Selecting top-level handled specially.
          bItem = this.getRootChildren().getChildNodes().get( 0 );
        }
        if ( bItem != null ) {
          selection.add( bItem );
        }
      }
      tree.setSelection( new StructuredSelection( selection ) );

    } else {
      int pos = 0;
      for ( T t : items ) {
        selIndexes[pos++] = findIndexOfItem( t );
      }
      this.setSelectedRows( selIndexes );
    }
  }

  public int findIndexOfItem( Object o ) {

    String property = ( (XulTreeCol) this.getColumns().getChildNodes().get( 0 ) ).getChildrenbinding();
    property = "get" + ( property.substring( 0, 1 ).toUpperCase() + property.substring( 1 ) );
    Method childrenMethod = null;
    try {
      childrenMethod = elements.getClass().getMethod( property, new Class[] {} );
    } catch ( NoSuchMethodException e ) {
      // Since this tree is built recursively, when at a leaf it will throw this exception.
      logger.debug( e );
    }

    FindSelectedIndexTuple tuple = findSelectedItem( this.elements, childrenMethod, new FindSelectedIndexTuple( o ) );
    return tuple.selectedIndex;
  }

  private static class FindSelectedIndexTuple {
    Object selectedItem = null;
    Object currentItem = null; // ignores first element (root)
    int curpos = -1; // ignores first element (root)
    int selectedIndex = -1;

    public FindSelectedIndexTuple( Object selectedItem ) {
      this.selectedItem = selectedItem;
    }
  }

  private FindSelectedIndexTuple findSelectedItem( Object parent, Method childrenMethod, FindSelectedIndexTuple tuple ) {
    if ( tuple.selectedItem == parent ) {
      tuple.selectedIndex = tuple.curpos;
      return tuple;
    }
    Collection children = null;
    if ( childrenMethod != null ) {
      try {
        children = (Collection) childrenMethod.invoke( parent, new Object[] {} );
      } catch ( Exception e ) {
        logger.error( e );
        return tuple;
      }
    } else if ( parent instanceof List ) {
      children = (List) parent;
    }

    if ( children == null || children.size() == 0 ) {
      return null;
    }

    for ( Object child : children ) {
      tuple.curpos++;
      findSelectedItem( child, childrenMethod, tuple );
      if ( tuple.selectedIndex > -1 ) {
        return tuple;
      }
    }
    return tuple;
  }

  public boolean isHiddenrootnode() {
    return hiddenRoot;
  }

  public void setHiddenrootnode( boolean hidden ) {
    this.hiddenRoot = hidden;
  }

  public String getCommand() {
    return command;
  }

  public void setCommand( String command ) {
    this.command = command;
  }

  public String getToggle() {
    return toggle;
  }

  public void setToggle( String toggle ) {
    this.toggle = toggle;
  }

  public boolean isPreserveexpandedstate() {
    return preserveExpandedState;
  }

  public void setPreserveexpandedstate( boolean preserve ) {
    this.preserveExpandedState = preserve;
  }

  private static class TreeLabelBindingConvertor extends BindingConvertor<String, String> {

    private SwtTree tree;

    public TreeLabelBindingConvertor( SwtTree tree ) {
      this.tree = tree;
    }

    @Override
    public String sourceToTarget( String value ) {
      return value;
    }

    @Override
    public String targetToSource( String value ) {
      tree.update();
      return value;
    }

  }

  public boolean isSortable() {
    return sortProperties.isSortable();
  }

  public void setSortable( boolean sortable ) {
    sortProperties.setSortable( sortable );
  }

  public boolean isTreeLines() {
    return linesVisible;
  }

  public void setTreeLines( boolean visible ) {
    linesVisible = visible;
  }

  public void setNewitembinding( String binding ) {
    newItemBinding = binding;
  }

  public String getNewitembinding() {
    return newItemBinding;
  }

  public void setAutocreatenewrows( boolean auto ) {
    this.autoCreateNewRows = auto;
  }

  public boolean getAutocreatenewrows() {
    return autoCreateNewRows;
  }

  public boolean isPreserveselection() {
    return preserveSelection;
  }

  public void setPreserveselection( boolean preserve ) {
    preserveSelection = preserve;

  }

  private Binding createBinding( XulEventSource source, String prop1, XulEventSource target, String prop2 ) {
    if ( bindingProvider != null ) {
      return bindingProvider.getBinding( source, prop1, target, prop2 );
    }
    return new DefaultBinding( source, prop1, target, prop2 );
  }

  /**
   * This class should not be necessary if we use JFace correctly.
   * What we really need to do is implement lazy-loading properly by making the XulTreeContentProvider an
   * ILazyTreeContentProvider and work within the expectations of the JFace API.  This subclass
   * restores behavior from an older version of the JFace library to work around a bug.
   */
  @SuppressWarnings( "squid:S110" )
  private class PentahoTreeViewer extends TreeViewer {

    private ListenerList<ITreeViewerListener> duplicateTreeListenerList = new ListenerList<>();

    public PentahoTreeViewer( Composite parent, int style ) {
      super( parent, style );
    }

    @Override
    public void addTreeListener( ITreeViewerListener listener ) {
      // maintain a duplicate of the list in the parent class so we can call the listeners as needed
      duplicateTreeListenerList.add( listener );
      super.addTreeListener( listener );
    }

    @Override
    public void removeTreeListener( ITreeViewerListener listener ) {
      // maintain a duplicate of the list in the parent class so we can call the listeners as needed
      duplicateTreeListenerList.remove( listener );
      super.removeTreeListener( listener );
    }

    @Override
    protected void fireTreeExpanded( final TreeExpansionEvent event ) {
      // same behavior as parent class without using the checkBusy method to avoid reentrant updates.
      // restores the behavior of the older version of the library we were using

      for ( ITreeViewerListener l : duplicateTreeListenerList ) {
        SafeRunnable.run( new SafeRunnable() {
          @Override
          public void run() {
            l.treeExpanded( event );
          }
        } );
      }
    }

    @Override
    protected void fireTreeCollapsed( final TreeExpansionEvent event ) {
      // same behavior as parent class without using the checkBusy method to avoid reentrant updates.
      // restores the behavior of the older version of the library we were using

      for ( ITreeViewerListener l : duplicateTreeListenerList ) {
        SafeRunnable.run( new SafeRunnable() {
          @Override
          public void run() {
            l.treeCollapsed( event );
          }
        } );
      }
    }

  }
}
