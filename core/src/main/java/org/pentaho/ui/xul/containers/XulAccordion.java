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

public interface XulAccordion extends XulContainer {
  public void setMode( String mode );

  public String getMode();

}
