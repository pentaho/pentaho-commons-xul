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



/**
 *
 */

package org.pentaho.ui.xul.components;

import org.pentaho.ui.xul.XulComponent;

/**
 * The XulCaption widget is the title that gets applied to XulGroupBox container.
 * 
 * @author nbaker
 * 
 */
public interface XulCaption extends XulComponent {

  /**
   * 
   * @return the title that this caption represents.
   */
  public String getLabel();

  /**
   * Applies the parameter label to the groupbox widget.
   * 
   * @param label
   *          The title to set on the groupbox.
   */
  public void setLabel( String label );
}
