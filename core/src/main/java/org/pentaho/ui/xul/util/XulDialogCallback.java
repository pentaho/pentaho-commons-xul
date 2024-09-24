/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/

package org.pentaho.ui.xul.util;

import org.pentaho.ui.xul.XulComponent;

public interface XulDialogCallback<T> {
  enum Status {
    ACCEPT, CANCEL, ONEXTRA1, ONEXTRA2
  }

  void onClose( XulComponent sender, Status returnCode, T retVal );

  void onError( XulComponent sender, Throwable t );
}
