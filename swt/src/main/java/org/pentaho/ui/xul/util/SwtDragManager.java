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


package org.pentaho.ui.xul.util;

import org.pentaho.ui.xul.dnd.DropEvent;

/**
 * User: nbaker Date: Aug 24, 2010
 */
public class SwtDragManager {

  private static SwtDragManager instance = new SwtDragManager();
  private DropEvent currentDropEvent;

  private SwtDragManager() {

  }

  public static SwtDragManager getInstance() {
    return instance;
  }

  public DropEvent getCurrentDropEvent() {
    return currentDropEvent;
  }

  public void setCurrentDropEvent( DropEvent currentDropEvent ) {
    this.currentDropEvent = currentDropEvent;
  }
}
