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
  public void setText(String str);
  public void setOnClick(String method);
}
