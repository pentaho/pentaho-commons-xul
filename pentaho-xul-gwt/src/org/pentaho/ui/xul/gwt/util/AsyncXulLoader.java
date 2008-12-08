package org.pentaho.ui.xul.gwt.util;

import org.pentaho.gwt.widgets.client.utils.IMessageBundleLoadCallback;
import org.pentaho.gwt.widgets.client.utils.MessageBundle;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.gwt.GwtXulLoader;
import org.pentaho.ui.xul.gwt.GwtXulRunner;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.xml.client.XMLParser;

public class AsyncXulLoader implements IMessageBundleLoadCallback{

  private MessageBundle messageBundle;
  
  private String xulLocation;
  
  private IXulLoaderCallback callback;
  
  public static void loadXul(String location, String bundle, IXulLoaderCallback callback){
    new AsyncXulLoader(location, bundle, callback);
  }
  
  private AsyncXulLoader(String location, String bundle, IXulLoaderCallback callback){
    xulLocation = location;
    this.callback = callback;
    String folder = "";
    String baseName = bundle;

    //we have to separate the folder from the base name
    if(bundle.indexOf("/") > -1){
      folder = bundle.substring(0, folder.lastIndexOf("/")+1);
      baseName = bundle.substring(bundle.lastIndexOf("/")+1);
    }
    
    try {
      messageBundle = new MessageBundle(folder, baseName, this );    //$NON-NLS-1$   //$NON-NLS-2$
    } catch (Exception e) {
      Window.alert("Error loading message bundle: "+e.getMessage());    //$NON-NLS-1$
      e.printStackTrace();
    }
  }
  
  public void bundleLoaded(String bundleName) {
    try {
      RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, xulLocation);    //$NON-NLS-1$

      try {
        Request response = builder.sendRequest(null, new RequestCallback() {
          public void onError(Request request, Throwable exception) {
            Window.alert("Error loading XUL: "+exception.getMessage());   //$NON-NLS-1$
          }

          public void onResponseReceived(Request request, Response response) {
            generateXul(response.getText());
          }
        });
      } catch (RequestException e) {
        Window.alert("error loading bundle: "+e.getMessage());    //$NON-NLS-1$
      }
    } catch (Exception e) {
      Window.alert("error loading bundle: "+e.getMessage());    //$NON-NLS-1$
      e.printStackTrace();
    }
  }
   
  private void generateXul(String xulStr){
    try {
      
      GwtXulLoader loader = new GwtXulLoader();
      GwtXulRunner runner = new GwtXulRunner();

      com.google.gwt.xml.client.Document gwtDoc = XMLParser.parse(xulStr);
      XulDomContainer container;

      if(messageBundle != null){
        container = loader.loadXul(gwtDoc, messageBundle);
      } else {
        container = loader.loadXul(gwtDoc);
      }


      runner.addContainer(container);
      
      callback.xulLoaded(runner);
      
    } catch (Exception e) {
      Window.alert("Error generating XUL: "+e.getMessage());    //$NON-NLS-1$
      e.printStackTrace();
    }
  }
   
}

  
  