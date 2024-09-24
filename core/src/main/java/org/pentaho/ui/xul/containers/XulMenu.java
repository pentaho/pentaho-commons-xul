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

/**
 * 
 */

package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;

/**
 * @author OEM
 * 
 */
public interface XulMenu extends XulContainer {
  public String getAcceltext();

  public void setAcceltext( String accel );

  public String getLabel();

  public void setLabel( String label );

  public String getAccesskey();

  public void setAccesskey( String accessKey );

  public boolean isDisabled();

  public void setDisabled( boolean disabled );

}
