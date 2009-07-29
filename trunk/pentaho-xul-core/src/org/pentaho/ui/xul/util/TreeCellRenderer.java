package org.pentaho.ui.xul.util;

public interface TreeCellRenderer {
  String getText(Object value);
  boolean supportsNativeComponent();
  Object getNativeComponent();
}
