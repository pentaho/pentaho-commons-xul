package org.pentaho.ui.xul;

public class XulDomException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3676902604274491856L;

	public XulDomException() {
    super();
  }

  public XulDomException(String message, Throwable e) {
    super(message, e);
  }

  public XulDomException(Throwable e) {
    super(e);
  }

  public XulDomException(String str){
    super(str);
  }

}
