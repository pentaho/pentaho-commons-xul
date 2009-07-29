package org.pentaho.ui.xul.util;

import org.pentaho.ui.xul.XulComponent;

public interface XulDialogCallback<T> {
 enum Status{
   ACCEPT,
   CANCEL,
   ONEXTRA1,
   ONEXTRA2
 }
  void onClose(XulComponent sender, Status returnCode, T retVal);
  void onError(XulComponent sender, Throwable t);
}
