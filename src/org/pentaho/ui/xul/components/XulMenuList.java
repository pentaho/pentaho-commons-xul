/**
 * 
 */
package org.pentaho.ui.xul.components;

import java.util.List;

import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomException;

/**
 * @author aphillips
 *
 */
public interface XulMenuList extends XulContainer {
  
  public void replaceAllItems(List<String> objects) throws XulDomException;
  
  public String getSelectedItem();
  
  public void setOncommand(String command);
}
