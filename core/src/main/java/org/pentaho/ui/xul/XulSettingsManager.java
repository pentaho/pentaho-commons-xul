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

import java.io.IOException;

public interface XulSettingsManager {
  void storeSetting( String prop, String val );

  String getSetting( String prop );

  void save() throws IOException;
}
