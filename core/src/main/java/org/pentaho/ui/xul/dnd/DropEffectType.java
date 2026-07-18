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



package org.pentaho.ui.xul.dnd;

public enum DropEffectType {
  COPY, MOVE, LINK, NONE;

  public static DropEffectType valueOfIgnoreCase( String value ) {
    return valueOf( value.toUpperCase() );
  }
}
