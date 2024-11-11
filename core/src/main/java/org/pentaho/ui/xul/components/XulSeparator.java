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

/**
 * Please See http://developer.mozilla.org/en/docs/XUL:separator for more information.
 * 
 * todo: support class, orient attribute
 */
public interface XulSeparator extends XulComponent {
  public void setOrient( String orient );

  public String getOrient();
}
