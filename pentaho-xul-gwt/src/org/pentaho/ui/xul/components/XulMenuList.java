/**
 * 
 */
package org.pentaho.ui.xul.components;

import java.util.Collection;

import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulDomException;

/**
 * @author aphillips
 *
 */
public interface XulMenuList<T> extends XulContainer {

  public void replaceAllItems(Collection<T> tees) throws XulDomException;

  public T getSelectedItem();
  
  public void setSelectedItem(T t);

  public void setSelectedIndex(int idx);

  public int getSelectedIndex();
  
  public void setOncommand(String command);

  public void setElements(Collection<T> elements);

  public Collection<T> getElements();

  public void setBinding(String binding);

  public String getBinding();
  
}
