/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.ui.xul.components;

import org.pentaho.ui.xul.util.XulDialogCallback;

public interface XulPromptBox extends XulMessageBox {

  void addDialogCallback( XulDialogCallback callback );

  void removeDialogCallback( XulDialogCallback callback );

  String getValue();

  void setValue( String value );

  void setCancelLabel( String label );
}
