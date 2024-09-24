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

package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulColumns;
import org.pentaho.ui.xul.containers.XulRows;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;

/**
 * User: nbaker Date: Apr 14, 2009
 */
public class SwingRows extends AbstractSwingContainer implements XulColumns, XulRows {

  public SwingRows( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "rows" );

    setManagedObject( "empty" );
  }

  @Override
  public void layout() {

  }

}
