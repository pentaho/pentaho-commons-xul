package org.pentaho.ui.xul.swt.custom;

import java.util.Iterator;

import org.apache.commons.collections.IterableMap;
import org.apache.commons.collections.map.HashedMap;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.pentaho.ui.xul.components.XulTreeCol;
import org.pentaho.ui.xul.containers.XulTree;
import org.pentaho.ui.xul.containers.XulTreeCols;
import org.pentaho.ui.xul.containers.XulTreeItem;
import org.pentaho.ui.xul.containers.XulTreeRow;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.RowWidget;
import org.pentaho.ui.xul.util.ColumnType;

public class TableItemWrapper implements RowWidget {

  
  TableItem item = null;
  Table parentTable = null;
  XulTree parentTree = null;

  TableEditor textEditor = null;
  IterableMap checkedEditors = new HashedMap();
  
  int firstEditableColumn = -1;
  
  public TableItemWrapper(XulTree parent){

    parentTable = (Table)parent.getManagedObject();
    item = new TableItem(parentTable, SWT.NONE);
    
    textEditor = new TableEditor(parentTable);
    
    parentTree = parent;
    
    XulTreeCols columns = parent.getColumns();
   
    for (int i = 0; i < columns.getColumnCount(); i++) {
      XulTreeCol column = columns.getColumn(i);
      if (column.isEditable()){
        firstEditableColumn = i;
        break;
      }
    }
    
    // Checkboxes do not come and go like text fields do...
    for (int i = 0; i < columns.getColumnCount(); i++) {
      
      XulTreeCol column = columns.getColumn(i);
      if (column.getColumnType().equals(ColumnType.CHECKBOX)){
        makeCellEditable(i);
      }
    }
    

  }
  
  public void makeCellEditable(int index){
    
    if (index == -1){
      if (firstEditableColumn == -1){
        return; 
      }
      index = firstEditableColumn;
    }
    
    XulTreeCol column = parentTree.getColumns().getColumn(index);
    
    if (!column.isEditable()){
      return;
    }
    
    switch (column.getColumnType()){
      case CHECKBOX:
        createEditableCheckbox(index);
        break;
      case TEXT:
        createEditableText(index);
        break;
      case PROGRESSMETER:
        // TODO Log not supported yet... 
        break;
    }
    
  }

  public Item getItem() {
    return item;
  }

  public void setText(int index, String text) {
    item.setText(index, text);

    Control control = textEditor.getEditor();
    
    if ((control != null) && (control instanceof Text)){
      ((Text)control).setText(item.getText(index));
    }
    
    if(checkedEditors.containsKey(new Integer(index))){
      control = ((TableEditor)checkedEditors.get(new Integer(index))).getEditor();
      if ((control != null) && (control instanceof Button)){
        boolean checked = item.getText(index).equalsIgnoreCase("true");
        ((Button)control).setSelection(checked);
      }
    }
  }
  
  private void createEditableText(final int index){

    Control oldEditor = textEditor.getEditor();
    if (oldEditor != null && !oldEditor.isDisposed()) {
      try {
        oldEditor.dispose();
      } catch (SWTException swte) {
        // intentionally swallow exception
      }
    }

    Text edit = new Text(parentTable, SWT.NONE);
    edit.setText(item.getText(index));
    edit.selectAll();
    edit.setFocus();
    edit.addKeyListener(new TextKeyAdapter());
    
    textEditor.setEditor(edit, item, index);
    textEditor.grabHorizontal=true;
    
    
    edit.addModifyListener(new ModifyListener(){
      public void modifyText(ModifyEvent event){
        item.setText(index, ((Text)event.widget).getText());
      }
    });
    
      
    
    Listener textListener = new Listener () {
      public void handleEvent (final Event e) {
        switch (e.type) {
          case SWT.FocusOut:
            item.setText (index, ((Text)e.widget).getText ());
            ((Text)e.widget).dispose ();
            break;
          case SWT.Traverse:
            switch (e.detail) {
              case SWT.TRAVERSE_RETURN:
                item.setText (index, ((Text)e.widget).getText ());
                //FALL THROUGH
              case SWT.TRAVERSE_ESCAPE:
               e.doit = false;
            }

            break;
        }
      }
    };
    
    edit.addListener (SWT.FocusOut, textListener);
    edit.addListener (SWT.Traverse, textListener);
    edit.addTraverseListener(new TraverseListener(){

      public void keyTraversed(TraverseEvent e) {
        e.doit=false;
      }
      
    });
    
    parentTable.showItem(item);
    parentTable.setSelection(new TableItem[] { item });
  }

  private void createEditableCheckbox(final int index){

    TableEditor checkEditor = null;
    Button check = null;
    
    if (checkedEditors.containsKey(new Integer(index))){
      checkEditor = (TableEditor)checkedEditors.get(new Integer(index));
      check = (Button) checkEditor.getEditor();
      check.setSelection(Boolean.valueOf(item.getText(index)));
      check.setFocus();
      return;
    }

    check = new Button(parentTable, SWT.CHECK);
    checkEditor = new TableEditor(parentTable);
    checkEditor.setEditor(check, item, index);
    checkEditor.grabHorizontal=true;

    checkedEditors.put(new Integer(index), checkEditor);
    
    check.setSelection(Boolean.valueOf(item.getText(index)));
    check.setFocus();
    
    check.addSelectionListener(new SelectionAdapter(){
      public void widgetSelected(SelectionEvent e){
        item.setText(index, String.valueOf(((Button)e.widget).getSelection()));

        // send all selection events back through to the parent table with the selection index... 
        Element rootElement = parentTree.getDocument().getRootElement();
        XulWindow window = (XulWindow) rootElement;
        window.invoke(parentTree.getOnselect(), new Object[] {new Integer(parentTable.indexOf(item))});
      
        }
    });
    
    Listener textListener = new Listener () {
      public void handleEvent ( Event e) {
        switch (e.type) {
          case SWT.FocusOut:
            item.setText(index, String.valueOf(((Button)e.widget).getSelection()));
            break;
          case SWT.Traverse:
            switch (e.detail) {
              case SWT.TRAVERSE_RETURN:
                item.setText(index, String.valueOf(((Button)e.widget).getSelection()));
                //FALL THROUGH
              case SWT.TRAVERSE_ESCAPE:
                e.doit = false;
            }
            break;
        }
      }
    };
    
    check.addListener (SWT.FocusOut, textListener);
    check.addListener (SWT.Traverse, textListener);
    check.addTraverseListener(new TraverseListener(){

      public void keyTraversed(TraverseEvent e) {
        e.doit=false;
      }
      
    });
  }

  public void remove(){

    Control oldEditor = textEditor.getEditor();
    if (oldEditor != null && !oldEditor.isDisposed()) {
      try {
        oldEditor.dispose();
      } catch (SWTException swte) {
        // intentionally swallow exception
      }
    }

    Iterator mapIt = checkedEditors.mapIterator();
    while (mapIt.hasNext()) {
      Button check = (Button)mapIt.next();
      if (check != null && !check.isDisposed()) {
        try {
          check.dispose();
        } catch (SWTException swte) {
          // intentionally swallow exception
        }
      }
    }
    
    int myIndex = parentTable.indexOf(item);
    if (myIndex > -1){
      parentTable.remove(myIndex);
      parentTable.layout();
    }
    
  }
  
  
  /* =================================================================================== */

  private class TextKeyAdapter extends KeyAdapter {
    public void keyPressed(KeyEvent e) {
      
      boolean right = false;
      boolean left = false;

      int activeRow = parentTree.getActiveCellCoordinates()[0];
      int activeCol = parentTree.getActiveCellCoordinates()[1];

      // "ENTER": close the text editor and copy the data over 
      // We edit the data after moving to another cell, only if editNextCell = true;
      
      if (e.character == SWT.CR || e.keyCode == SWT.ARROW_DOWN || e.keyCode == SWT.ARROW_UP || e.keyCode == SWT.TAB
          || left || right) {
        
        if ((activeRow <0) || (activeCol < 0))
          return;

        if (e.character == SWT.CR){
          parentTable.forceFocus();
        }
        
        int maxcols = parentTable.getColumnCount();
        int maxrows =  parentTable.getItemCount();

        boolean editNextCell = false;
        if (e.keyCode == SWT.ARROW_DOWN && activeRow < maxrows - 1) {
          activeRow++;
          editNextCell = true;
        }
        if (e.keyCode == SWT.ARROW_UP && activeRow > 0) {
          activeRow--;
          editNextCell = true;
        }
        // TAB
        if ((e.keyCode == SWT.TAB && ((e.stateMask & SWT.SHIFT) == 0)) || right) {
          activeCol++;
          editNextCell = true;
        }
        // Shift Tab
        if ((e.keyCode == SWT.TAB && ((e.stateMask & SWT.SHIFT) != 0)) || left) {
          activeCol--;
          editNextCell = true;
        }
        if (activeCol < 0) // from SHIFT-TAB
        {
          activeCol = maxcols - 1;
          if (activeRow > 0)
            activeRow--;
        }
        if (activeCol >= maxcols) // from TAB
        {
          activeCol = 0;
          activeRow++;

        }
        // Tab beyond last line: add a line to table!
        if (activeRow >= maxrows) {
          if (parentTree.isEditable()){
            XulTreeRow row = parentTree.getRootChildren().addNewRow();
          }
        }

        if (editNextCell) {
          XulTreeItem rowItem = parentTree.getRootChildren().getItem(activeRow);
          rowItem.getRow().makeCellEditable(activeCol);
        } else {
          if (e.keyCode == SWT.ARROW_DOWN && activeRow == maxrows - 1) {
            if (parentTree.isEditable()){
              XulTreeRow treeRow = parentTree.getRootChildren().addNewRow();
              //XulTreeItem rowItem = parentTree.getRootChildren().getItem(activeRow);
              treeRow.makeCellEditable(-1);
              activeRow++;
            }
          }
        }

      } else if (e.keyCode == SWT.ESC) {
        //text.dispose();
      }
      parentTree.setActiveCellCoordinates(activeRow, activeCol);
    }
  }

}


















