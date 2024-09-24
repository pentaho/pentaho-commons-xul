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

package org.pentaho.ui.xul.components;

import org.pentaho.ui.xul.XulComponent;

public interface XulScale extends XulComponent {

  public void setOrient( String orient );

  public String getOrient();

  public void setMin( int min );

  public int getMin();

  public void setMax( int max );

  public int getMax();

  public void setPageincrement( int increment );

  public int getPageincrement();

  public void setInc( int increment );

  public int getInc();

  public void setValue( int value );

  public int getValue();

  public void setDir( String direction );

  public String getDir();
}
