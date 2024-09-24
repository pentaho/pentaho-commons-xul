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

package org.pentaho.ui.xul.gwt.service;

import com.google.gwt.user.client.rpc.RemoteService;

public interface XulLoaderService extends RemoteService {
  String getXulDocument( String url );

  String getXulDocument( String url, String resourceBundleUrl );

  Boolean setRootContext( String root );
}
