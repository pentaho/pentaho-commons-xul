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


package org.pentaho.ui.xul;

import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.impl.XulEventHandler;

import java.util.List;

public interface XulPerspective {
  List<XulOverlay> getOverlays();

  List<XulEventHandler> getEventHandlers();

  String getID();

  String getDisplayName();

  void onLoad( Document doc );

  void onUnload();

}
