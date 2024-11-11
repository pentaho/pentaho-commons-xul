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


package org.pentaho.ui.xul.swt;

import org.eclipse.jface.dialogs.IDialogConstants;

public enum DialogButton {
  ACCEPT( IDialogConstants.OK_ID, IDialogConstants.OK_LABEL ), CANCEL( IDialogConstants.CANCEL_ID,
      IDialogConstants.CANCEL_LABEL ), HELP( IDialogConstants.HELP_ID, IDialogConstants.HELP_LABEL ), DISCLOSURE(
      IDialogConstants.DETAILS_ID, IDialogConstants.SHOW_DETAILS_LABEL ), EXTRA1( IDialogConstants.CLIENT_ID + 1,
      "Extra1" ), EXTRA2( IDialogConstants.CLIENT_ID + 2, "Extra2" );

  private int id;
  private String defaultLabel = null;
  private String label = null;

  private DialogButton( int id, String defaultLabel ) {
    this.id = id;
    this.defaultLabel = defaultLabel;
  }

  private DialogButton( DialogButton base, String defaultLabel ) {
    this.id = base.id;
    this.defaultLabel = defaultLabel;
  }

  public String getLabel() {
    return label == null ? defaultLabel : label;
  }

  public void setLabel( String label ) {
    this.label = label;
  }

  public int getId() {
    return id;
  }

}
