package org.pentaho.ui.xul.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface XulLoaderServiceAsync {

  void getXulDocument(String url, AsyncCallback<String> callback);
  void getXulDocument(String url, String resourceBundleUrl, AsyncCallback<String> callback);
  void setRootContext(String root, AsyncCallback<Boolean> callback);
}

  