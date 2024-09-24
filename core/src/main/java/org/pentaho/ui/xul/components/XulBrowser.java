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

public interface XulBrowser extends XulComponent {
  void setSrc( String src );

  String getSrc();

  String getData();

  void execute( String data );

  void forward();

  void back();

  void home();

  void reload();

  void stop();

  void setShowtoolbar( boolean flag );

  boolean getShowtoolbar();
}
