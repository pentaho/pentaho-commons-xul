package org.pentaho.ui.xul.gwt.binding;

public interface TypeController {
  public GwtBindingMethod findGetMethod(Object obj, String propertyName);
  public GwtBindingMethod findSetMethod(Object obj, String propertyName);
}

  