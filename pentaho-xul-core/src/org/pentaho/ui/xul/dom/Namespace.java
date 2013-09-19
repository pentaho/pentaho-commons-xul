/**
 * 
 */
package org.pentaho.ui.xul.dom;

/**
 * @author OEM
 *
 */
public class Namespace {
  private String uri;
  private String prefix;
  
  public Namespace(String uri, String prefix){
    this.uri = uri;
    this.prefix = prefix;
  }
  
  public String getURI(){
    return uri;
  }
  
  public String getPrefix(){
    return prefix;
  }
}
