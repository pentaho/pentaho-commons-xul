/**
 * 
 */
package org.pentaho.ui.xul;

import java.util.Map;

/**
 * @author OEM
 *
 */
public interface XulServiceCall {

  public String getBaseUrl();
  public String getMethod();
  public String getAction();
  public Map getParams();
  
}
