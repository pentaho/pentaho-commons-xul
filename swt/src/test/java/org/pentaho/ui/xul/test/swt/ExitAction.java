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
