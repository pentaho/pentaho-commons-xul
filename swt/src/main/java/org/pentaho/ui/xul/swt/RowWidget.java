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



package org.pentaho.ui.xul.swt;

import org.eclipse.swt.widgets.Item;

public interface RowWidget {

  public static final int EVENT_SELECT = 123;

  public Item getItem();

  public void setText( int index, String text );

  public void makeCellEditable( int index );

  public void remove();

}
