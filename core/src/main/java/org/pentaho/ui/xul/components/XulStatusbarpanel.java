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
 * A special panel placed inside Statusbar elements that can display an image or text, but not both.
 * 
 * @author nbaker
 */

public interface XulStatusbarpanel extends XulComponent {
  public void setLabel( String label );

  public String getLabel();

  public void setImage( String image );

  public String getImage();
}
