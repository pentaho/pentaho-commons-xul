package org.pentaho.ui.xul.swt.custom;

import org.eclipse.swt.SWT;

public enum DialogConstant {
  OK(SWT.OK), CANCEL(SWT.CANCEL), YES(SWT.YES), NO(SWT.NO);
  
  private int value; 
  private DialogConstant(int val){
    this.value = val;
  }
  
  public int getValue(){
    return value;
  }
}
