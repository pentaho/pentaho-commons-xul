package org.pentaho.ui.xul.swt;

import org.eclipse.swt.SWT;

/**
 * Mapping between XUL align attribute values and 
 * SWT align property values. 
 * 
 * @author gmoran
 *
 */
public enum SwtAlign {
  
  START(SWT.BEGINNING), 
  CENTER(SWT.CENTER), 
  END(SWT.END), 
  BASELINE(SWT.BEGINNING), 
  STRETCH(SWT.FILL);
  
  private final int equivalent;
  
  private SwtAlign(int swtEquivalent){
    this.equivalent = swtEquivalent;
  }
  
  public int getSwtAlign(){
    return equivalent;
  }

}
