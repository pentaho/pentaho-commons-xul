/**
 * 
 */
package org.pentaho.ui.xul;

import org.pentaho.ui.xul.swt.Orient;

/**
 * @author OEM
 *
 */
public interface XulContainer {
  public void addComponent(XulComponent component);
  public Orient getOrientation();
}
