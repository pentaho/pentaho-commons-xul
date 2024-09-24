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
 * An interface for a XUL spacer widget. A spacer is an element that takes up space but does not display anything.
 * It is usually used to place spacing within a container. If you don't specify that the spacer has a size or is
 * flexible, the spacer does not occupy any space.
 * 
 * @author nbaker
 * 
 */
public interface XulSpacer extends XulComponent {

  /**
   * Set the height that this space should occupy.
   * 
   * @param size
   *          The hieght.
   */
  public void setHeight( int size );

  /**
   * Set the width that this space should occupy.
   * 
   * @param size
   *          The width.
   */
  public void setWidth( int size );

}
