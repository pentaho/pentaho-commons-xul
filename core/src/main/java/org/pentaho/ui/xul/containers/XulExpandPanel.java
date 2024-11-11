/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.ui.xul.containers;

import org.pentaho.ui.xul.XulContainer;

/**
 * Expand panels are simple containers that have a text label and a content panel that expands or collapses when
 * the label is clicked
 */
public interface XulExpandPanel extends XulContainer {
  public static final String ELEMENT_NAME = "expandpanel";

  void setExpanded( boolean isExpanded );

  void setExpanded( String isExpanded );

  boolean isExpanded();

}
