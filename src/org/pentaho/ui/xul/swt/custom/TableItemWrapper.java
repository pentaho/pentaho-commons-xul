package org.pentaho.ui.xul.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
import org.pentaho.ui.xul.swt.RowWidget;

public class TableItemWrapper implements RowWidget {

  
  TableItem item = null;
  Table parentTable = null;
  XulTree parentTree = null;

  TableEditor editor = null;
  
  public TableItemWrapper(XulTree parent){

    parentTable = (Table)parent.getManagedObject();
    item = new TableItem(parentTable, SWT.NONE);
    editor = new TableEditor(parentTable);
    
    parentTree = parent;
    

  }
  
  public void makeCellEditable(int index){
    
    XulTreeCol column = parentTree.getColumns().getColumn(index);
    
    Control oldEditor = editor.getEditor();
    if (oldEditor != null && !oldEditor.isDisposed()) {
      try {
        oldEditor.dispose();
      } catch (SWTException swte) {
        // intentionally swallow exception
      }
    }

    switch (column.getColumnType()){
      case CHECKBOX:
        Button check = new Button(parentTable, SWT.CHECK);
        editor.setEditor(check, item, index);
        editor.grabHorizontal=true;
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

    Control control = editor.getEditor();
    
    if ((control != null) && (control instanceof Text)){
      ((Text)control).setText(item.getText(index));
    }
  }
  
  private void createEditableText(final int index){

    final Text edit = new Text(parentTable, SWT.NONE);
    edit.setText(item.getText(index));
    edit.selectAll();
    edit.setFocus();
    
    editor.setEditor(edit, item, index);
    editor.grabHorizontal=true;
    
    
    edit.addModifyListener(new ModifyListener(){
      public void modifyText(ModifyEvent event){
        item.setText(index, edit.getText());
      }
    });
    
    Listener textListener = new Listener () {
      public void handleEvent (final Event e) {
        switch (e.type) {
          case SWT.FocusOut:
            item.setText (index, edit.getText ());
            edit.dispose ();
            break;
          case SWT.Traverse:
            switch (e.detail) {
              case SWT.TRAVERSE_RETURN:
                item.setText (index, edit.getText ());
                //FALL THROUGH
              case SWT.TRAVERSE_ESCAPE:
                edit.dispose ();
                e.doit = false;
            }
            break;
        }
      }
    };
    
    edit.addListener (SWT.FocusOut, textListener);
    edit.addListener (SWT.Traverse, textListener);
    
  }


}


















