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

package org.pentaho.ui.xul.gwt.util;

import org.pentaho.ui.xul.impl.XulEventHandler;

public abstract class AbstractEventHandlerWrapper implements EventHandlerWrapper {
  protected XulEventHandler handler;

  public void setHandler( XulEventHandler handler ) {
    this.handler = handler;
  }

}
