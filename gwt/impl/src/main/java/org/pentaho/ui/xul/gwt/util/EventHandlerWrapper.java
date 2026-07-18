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

public interface EventHandlerWrapper {
  void execute( String methodName, Object[] args );

  void setHandler( XulEventHandler handler );

  XulEventHandler getHandler();
}
