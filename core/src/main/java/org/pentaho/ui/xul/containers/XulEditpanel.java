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
 * Edit panels are similar to the docked views in Eclipse. They have a title via a caption child, can be
 * collapsed/expanded and contain a toolbar.
 * <p>
 * Edit Panels are intended to have only one panel child
 * 
 * @author nbaker
 * 
 */

public interface XulEditpanel extends XulContainer, XulCaptionedPanel {
  enum TYPE {
    COLLAPSIBLE, CLOSABLE
  };

  void setType( String type );

  String getType();

  void open();
}
