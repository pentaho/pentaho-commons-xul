package org.pentaho.ui.xul.components;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.util.XulDialogCallback;

public interface XulPromptBox extends XulMessageBox{
  
  void addDialogCallback(XulDialogCallback callback);
  void removeDialogCallback(XulDialogCallback callback);
  String getValue();
  void setValue(String value);
  void setCancelLabel(String label);
}
