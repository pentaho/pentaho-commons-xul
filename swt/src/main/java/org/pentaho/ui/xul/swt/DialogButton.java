/*!
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
 */

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
