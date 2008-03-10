/**
 * 
 */
package org.pentaho.ui.xul;

/**
 * @author OEM
 *
 */
public class XulException extends Exception {

  public XulException() {
    super();
  }

  public XulException(String message, Throwable e) {
    super(message, e);
  }

  public XulException(Throwable e) {
    super(e);
  }

  public XulException(String str){
    super(str);
  }
}
