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



package org.pentaho.ui.xul.gwt.util;

import org.pentaho.ui.xul.gwt.GwtXulRunner;

public interface IXulLoaderCallback {
  public void xulLoaded( GwtXulRunner runner );

  public void overlayLoaded();

  public void overlayRemoved();
}
