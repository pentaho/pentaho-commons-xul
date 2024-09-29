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


package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;

/**
 * An interface for a deck container. This container will stack its children one on top of the other, with only the
 * selected child showing. The selected index determines which child is displayed. The first child is selected by
 * default.
 * 
 * @author gmoran
 */

public interface XulDeck extends XulContainer {

  /**
   * The selected index is a zero based index referring to the child control of this deck that should be displayed.
   * 
   * @param index
   *          The index of the child to display.
   */
  public void setSelectedIndex( int index );

  /**
   * 
   * @return the index of the selected or displayed child control.
   */
  public int getSelectedIndex();

}
