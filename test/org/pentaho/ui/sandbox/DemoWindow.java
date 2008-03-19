package org.pentaho.ui.sandbox;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class DemoWindow {
  
  
  public static void main (String args[]){
    Shell window = new Shell();
    
    List listBox = new List(window, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");
    listBox.add("New Item1");

    
    window.setLayout(new GridLayout());
    GridData data = new GridData();
    data.horizontalSpan = 1;
    data.verticalSpan = 1;
    data.grabExcessHorizontalSpace = true;
    data.grabExcessVerticalSpace = false;
    data.horizontalAlignment = SWT.FILL;
   // data.heightHint = 30;

    listBox.setLayoutData(data);
   
    
   // listBox.setSize(listBox.computeSize(50,50));

    
   /*
    listBox.setSize(listBox.computeSize(70, 50));
    listBox.getParent().layout(true);
    listBox.pack(true);
    listBox.update();
*/    
    window.open(); 
    //window.pack();

    while(!window.isDisposed()) {
      if(!window.getDisplay().readAndDispatch()) {
        window.getDisplay().sleep();
      }
   }
    
  }
}

