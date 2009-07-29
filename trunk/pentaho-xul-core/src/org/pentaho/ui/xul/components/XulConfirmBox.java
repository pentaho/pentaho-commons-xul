package org.pentaho.ui.xul.components;

import org.pentaho.ui.xul.util.XulDialogCallback;

public interface XulConfirmBox extends XulMessageBox{
  
  void addDialogCallback(XulDialogCallback callback);
  void removeDialogCallback(XulDialogCallback callback);
  void setCancelLabel(String label);
}
