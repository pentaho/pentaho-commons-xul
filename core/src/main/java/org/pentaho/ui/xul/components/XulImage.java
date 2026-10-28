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



package org.pentaho.ui.xul.components;

import org.pentaho.ui.xul.XulComponent;

public interface XulImage extends XulComponent {
  public void setSrc( String src );

  public void setSrc( Object img );

  public String getSrc();

  public void refresh();
}
