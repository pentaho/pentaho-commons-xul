package org.pentaho.ui.xul.swt.tags;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulCheckbox;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Element;

public class SwtCheckbox extends SwtButton implements XulCheckbox {
	
	private static final Log logger = LogFactory.getLog(SwtCheckbox.class);
	
  public SwtCheckbox(XulComponent parent, XulDomContainer container, String tagName) {
    super(parent, container, tagName);
  }

  @Override
  protected Button createNewButton(Composite parent) {
    return new Button(parent, SWT.CHECK);
  }
  public boolean isChecked() {
    return button.getSelection();
  }

  public void setChecked(boolean checked) {
    if ((!button.isDisposed()) && (button != null)) button.setSelection(checked);
  }
  
  public void setCommand(final String method) {
    button.addSelectionListener(new SelectionAdapter(){
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent arg0){
        Element rootElement = getDocument().getRootElement();
        XulWindow window = (XulWindow) rootElement;
        try{
        	window.invoke(method, new Object[]{});
        } catch(XulException e){
        	logger.error("error invoking command",e);
        }
      }
    });
  }
  
  public void setOnclick(final String method) {
    // TODO Log unsupported attribute message... 
  }



}
