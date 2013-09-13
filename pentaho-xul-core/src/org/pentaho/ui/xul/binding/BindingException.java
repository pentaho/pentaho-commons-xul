package org.pentaho.ui.xul.binding;

public class BindingException extends RuntimeException {
  private static final long serialVersionUID = 2465634550238284760L;

  public BindingException(Throwable t) {
    super(t);
  }

  public BindingException(String message, Throwable cause) {
    super(message, cause);
  }

  public BindingException(String message) {
    super(message);
  }
}
