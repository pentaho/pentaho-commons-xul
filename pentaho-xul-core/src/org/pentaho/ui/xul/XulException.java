/**
 * 
 */
package org.pentaho.ui.xul;

/**
 * @author nbaker
 *
 */
public class XulException extends Exception {
  private static final long serialVersionUID = -4430086632572793141L;

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
