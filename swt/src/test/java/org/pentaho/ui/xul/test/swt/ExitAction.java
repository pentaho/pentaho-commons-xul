/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 - 2026 by Pentaho Canada Inc. : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2030-06-15
 ******************************************************************************/



package org.pentaho.ui.xul.test.swt;

import org.eclipse.jface.action.Action;

public class ExitAction extends Action {
  JFaceExample parent;

  public ExitAction( JFaceExample parent ) {
    this.parent = parent;
    setText( "E&xit@Ctrl+W" );
    setToolTipText( "Exit the application" );
  }

  public void run() {
    parent.stop( true );
  }
}
