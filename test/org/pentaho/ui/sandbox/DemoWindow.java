package org.pentaho.ui.sandbox;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class DemoWindow {
  
  
  public static void main (String args[]){
    Shell window = new Shell();
    Group group = new Group(window, SWT.NONE);
    Label label1 = new Label(group, SWT.NONE);
    Label label2 = new Label(group, SWT.NONE);
    Label label3 = new Label(group, SWT.NONE);
    label1.setText("First Label");
    label2.setText("Second Label");
    label3.setText("Third Label");
    
    Group group2 = new Group(window, SWT.NONE);
    Label label4 = new Label(group2, SWT.NONE);
    Label label5 = new Label(group2, SWT.NONE);
    Label label6 = new Label(group2, SWT.NONE);
    label4.setText("First Label");
    label5.setText("Second Label");
    label6.setText("Third Label");

    window.setLayout(new GridLayout(7, true));
    group.setLayout(new GridLayout());
    group2.setLayout(new GridLayout());
    
    GridData data = new GridData();
    data.horizontalSpan = 5;
    data.verticalSpan = 1;
    data.grabExcessHorizontalSpace=true;
    data.grabExcessVerticalSpace=true;
    data.horizontalAlignment=SWT.FILL;
    data.verticalAlignment=SWT.END;
    
    group.setLayoutData(data);


    GridData data2 = new GridData();
    data2.horizontalSpan = 2;
    data2.verticalSpan = 1;
    data2.grabExcessHorizontalSpace=true;
    data2.grabExcessVerticalSpace=true;
    data2.horizontalAlignment=SWT.FILL;
    data2.verticalAlignment=SWT.END;
    
    group2.setLayoutData(data2);

    window.open(); 
    //window.pack();

    while(!window.isDisposed()) {
      if(!window.getDisplay().readAndDispatch()) {
        window.getDisplay().sleep();
      }
   }
    
  }


}
