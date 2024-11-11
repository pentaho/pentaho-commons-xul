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


package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;

public interface XulToolbar extends XulContainer {

  public enum ToolbarMode {
    ICONS, TEXT, FULL
  }

  void setMode( String mode );

  String getMode();

  String getToolbarName();

  void setToolbarName( String name );

}
