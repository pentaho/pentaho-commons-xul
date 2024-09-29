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

package org.pentaho.ui.xul.components;

import org.pentaho.ui.xul.XulComponent;

/**
 * @author OEM
 * 
 */
public interface XulMenuitem extends XulComponent {

  public String getAcceltext();

  public void setAcceltext( String accel );

  public String getLabel();

  public void setLabel( String label );

  public String getAccesskey();

  public void setAccesskey( String accessKey );

  public boolean isDisabled();

  public void setDisabled( boolean disabled );

  public String getImage();

  public void setImage( String image );

  public boolean isSelected();

  public void setSelected( boolean selected );

  public void setCommand( String command );

  public String getCommand();

}
