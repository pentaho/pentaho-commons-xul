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


package org.pentaho.ui.xul.util;

public interface TreeCellEditor {

  public void setValue( Object val );

  public Object getValue();

  public void show( int row, int col, Object boundObj, String columnBinding, TreeCellEditorCallback callback );

  public void hide();
}
