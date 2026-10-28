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



package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.components.XulTreeCell;

public interface XulTreeRow extends XulContainer {

  public void addCell( XulTreeCell cell );

  public void addCellText( int index, String text );

  public void makeCellEditable( int index );

  public void remove();

  public XulTreeCell getCell( int index );

  public int getSelectedColumnIndex();

  public void setParentTreeItem( XulTreeItem item );

}
