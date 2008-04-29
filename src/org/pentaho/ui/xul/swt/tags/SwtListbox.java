package org.pentaho.ui.xul.swt.tags;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.containers.XulListbox;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.impl.XulWindowContainer;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtListbox extends SwtElement implements XulListbox{
  private static final long serialVersionUID = 3064125049914932493L;
  private static final Log logger = LogFactory.getLog(SwtListbox.class);

  private List listBox;
  private boolean disabled = false;
  private String selType;
  private int rowsToDisplay = 0;
  String onSelect = null;
  
  public SwtListbox(XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    listBox = new List((Composite)parent.getManagedObject(), SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
    managedObject = listBox;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
    if (!listBox.isDisposed()) listBox.setEnabled( !disabled );
  }

  public int getRows() {
    return rowsToDisplay;
  }

  public void setRows(int rowsToDisplay) {
    this.rowsToDisplay = rowsToDisplay;
    if ((!listBox.isDisposed()) && (rowsToDisplay > 0)){
      int height = rowsToDisplay * listBox.getItemHeight();

      //listBox.setSize(listBox.getSize().x,height);
      if (listBox.getLayoutData() != null){
        ((GridData)listBox.getLayoutData()).heightHint = height;
        ((GridData)listBox.getLayoutData()).minimumHeight = height;
      }
    }
  }

  
  public String getSeltype() {
    return selType;
  }

  /**
   * TODO: PARTIAL IMPL: Because this is needed on construction, 
   * we need to rework this class a bit to allow setting of 
   * multiple selection. 
   */
  public void setSeltype(String selType) {
    this.selType = selType;
    
  }

  public void addItem(Object item) {
    
    // SWT limitation - these can only be strings ...
    
    // We could still attempt to load the object using 
    // its toString() method, but then what you recieved
    // back from getItem() or getSelection() would 
    // be inconsistent with what you put into the list... 
    
    // TODO: Could possibly simulate a model 
    // by holding onto real objects and syncing them
    // with the listbox. 
    
    if (!(item instanceof String)){
      // log error... only strings supported...
    }
    listBox.add((String)item);
    
  }
  
  public void removeItems(){
    listBox.removeAll();
  }

  public String getOnselect() {
    return onSelect;
  }

  public void setOnselect(final String method) {
    onSelect = method;
    listBox.addSelectionListener(new SelectionAdapter(){
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent arg0){
        Element rootElement = getDocument().getRootElement();
        XulWindow window = (XulWindow) rootElement;
        try{
        	window.invoke(method, new Object[]{});
        } catch(XulException e){
        	logger.error("Error invoking onselect command",e);
        }
      }
    });
  }

  public Object getSelectedItem() {
    if (listBox.getSelection()==null || 
        listBox.getSelectionCount()<=0){
      return null;
    }
    return listBox.getSelection()[0];
  }

  public Object[] getSelectedItems() {
    return listBox.getSelection();
  }
  
  public int getSelectedIndex(){
    return listBox.getSelectionIndex();
  }
  
  public int[] getSelectedIndices(){
    return listBox.getSelectionIndices();
  }

  public void setSelectedItem(Object item) {
    setSelectedItems(new String[] {(String)item});
  }

  public void setSelectedItems(Object[] items) {
    if (listBox.isDisposed()){
      // TODO log error .. 
    }
    for (Object object : items) {
      if (!(object instanceof String)){
        // TODO  log error... only strings supported...
      }
    }
    listBox.setSelection((String[])items);
    // SWT doesn't seem to fire this event when the selection
    // is made via code, only with a mouse or keyboard action.
    listBox.notifyListeners(SWT.Selection, new Event());
  }

  public int getRowCount() {
    return (!listBox.isDisposed()) ? listBox.getItemCount() : 0;
  }

  public void setSelectedIndex(int index) {
    if (listBox.isDisposed()){
      // TODO log error .. 
    }
    listBox.setSelection(index);
  }

}
