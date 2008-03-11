package org.pentaho.ui.xul.swt;

import org.eclipse.swt.SWT;

public enum Align {
  
  START(SWT.BEGINNING), 
  CENTER(SWT.CENTER), 
  END(SWT.END), 
  BASELINE(SWT.BEGINNING), 
  STRETCH(SWT.FILL);
  
  private final int equivalent;
  
  private Align(int swtEquivalent){
    this.equivalent = swtEquivalent;
  }
  
  public int getSwtAlign(){
    return equivalent;
  }

}
