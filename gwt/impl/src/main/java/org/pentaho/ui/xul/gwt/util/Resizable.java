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

/**
 * Tags should implement this interface if they contain elements that need to be notified of resize and visibility
 * changes.
 * 
 * @author nbaker
 * 
 */
public interface Resizable {

  /**
   * Called when a parent has resized or changed visibility.
   */
  public void onResize();
}
