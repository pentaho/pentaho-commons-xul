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

package org.pentaho.ui.xul.swt;

import java.awt.Image;

import org.eclipse.swt.widgets.Item;

public interface ColumnWidget {

  public boolean getMoveable();

  public void setMovable( boolean movable );

  public boolean getResizable();

  public void setResizable( boolean resizable );

  public int getWidth();

  public void setWidth( int width );

  public String getText();

  public void setText( String string );

  public void setImage( Image image );

  public Item getItem();

  public void autoSize();

}
