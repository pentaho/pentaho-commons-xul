package org.pentaho.ui.xul.service;

import com.google.gwt.user.client.rpc.RemoteService;

public interface XulLoaderService  extends RemoteService{
  String getXulDocument(String url);
  String getXulDocument(String url, String resourceBundleUrl);
  Boolean setRootContext(String root);
}

  