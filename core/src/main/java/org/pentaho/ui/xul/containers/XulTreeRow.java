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
