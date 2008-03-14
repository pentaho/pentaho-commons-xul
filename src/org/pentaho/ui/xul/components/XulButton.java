/**
 * 
 */
package org.pentaho.ui.xul.components;

/**
 * @author OEM
 *
 */
public interface XulButton {
  public void click();
  public void dblClick();
  public void setLabel(String label);
  public void setOnClick(String method);
}
