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
import org.pentaho.ui.xul.components.XulTreeCol;

public interface XulTreeCols extends XulContainer {

  public boolean isHierarchical();

  public XulTree getTree();

  public void addColumn( XulTreeCol column );

  public XulTreeCol getColumn( int index );

  public int getColumnCount();

}
