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

import org.pentaho.ui.xul.impl.XulEventHandler;

public abstract class AbstractEventHandlerWrapper implements EventHandlerWrapper {
  protected XulEventHandler handler;

  public void setHandler( XulEventHandler handler ) {
    this.handler = handler;
  }

}
