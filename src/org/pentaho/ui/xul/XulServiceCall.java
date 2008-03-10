/**
 * 
 */
package org.pentaho.ui.xul;

import java.util.Map;

/**
 * @author OEM
 *
 */
public class XulServiceCall {

  private String baseUrl;
  private String method;
  private String action;
  private Map params;
  
  public XulServiceCall(){
    
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public Map getParams() {
    return params;
  }

  public void setParams(Map params) {
    this.params = params;
  }


  
}
