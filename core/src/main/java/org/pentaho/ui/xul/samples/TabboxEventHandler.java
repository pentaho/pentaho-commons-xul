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

package org.pentaho.ui.xul.samples;

import org.pentaho.ui.xul.components.XulTab;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

public class TabboxEventHandler extends AbstractXulEventHandler {

  public void setDisabled( Boolean flag, Integer pos ) {
    ( (XulTab) document.getElementById( "tab" + ( pos + 1 ) ) ).setDisabled( flag );
  }

  public Object getData() {
    return null;
  }

  public void setData( Object data ) {

  }

}
