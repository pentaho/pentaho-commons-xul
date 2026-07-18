/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 - 2026 by Pentaho Canada Inc. : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2030-06-15
 ******************************************************************************/



package org.pentaho.ui.xul.gwt.service;

import com.google.gwt.user.client.rpc.RemoteService;

public interface XulLoaderService extends RemoteService {
  String getXulDocument( String url );

  String getXulDocument( String url, String resourceBundleUrl );

  Boolean setRootContext( String root );
}
