package org.pentaho.ui.xul.gwt.util;

import org.pentaho.gwt.widgets.client.utils.IMessageBundleLoadCallback;
import org.pentaho.gwt.widgets.client.utils.MessageBundle;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.gwt.GwtXulDomContainer;
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
  
  private String xulSrc;
  
  private String bundle;
  
  private IXulLoaderCallback callback;
  
  private boolean loadingOverlay = false;
  
  private boolean fromSource = false;
  
  private GwtXulDomContainer container;
  
  public static void loadXulFromUrl(String location, String bundle, IXulLoaderCallback callback){
    AsyncXulLoader loader = new AsyncXulLoader(location, bundle, callback);
    loader.init();
    
  }
  
  public static void loadOverlayFromUrl(String location, String bundle, GwtXulDomContainer container, IXulLoaderCallback callback){
    AsyncXulLoader loader = new AsyncXulLoader(location, bundle, container, callback);
    loader.init();
  }

  public static void loadXulFromSource(String source, String bundle, IXulLoaderCallback callback){
    AsyncXulLoader loader = new AsyncXulLoader(source, bundle, callback, true);
    loader.init();
  }
  
  public static void loadOverlayFromSource(String source, String bundle, GwtXulDomContainer container, IXulLoaderCallback callback){
    AsyncXulLoader loader = new AsyncXulLoader(source, bundle, container, callback, true);
    loader.init();
  }
  
  private AsyncXulLoader(String location, String bundle, IXulLoaderCallback callback){
    xulSrc = location;
    this.callback = callback;
    this.bundle = bundle;
    
  }
  
  private AsyncXulLoader(String location, String bundle, GwtXulDomContainer container, IXulLoaderCallback callback){
    this(location, bundle, callback);
    this.container = container;
    this.loadingOverlay = true;
  }
  
  private AsyncXulLoader(String source, String bundle, IXulLoaderCallback callback, boolean fromSource){
    this(source, bundle, callback);
    this.fromSource = true;    
  }
  
  private AsyncXulLoader(String source, String bundle, GwtXulDomContainer container, IXulLoaderCallback callback, boolean fromSource){
    this(source, bundle, container, callback);
    this.fromSource = true;    
  }
  
  private void init(){
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
    if(fromSource){   //already given Xul source
      if(loadingOverlay){
        loadOverlay(xulSrc);
      } else {
        generateXulContainer(xulSrc);
      }
      return;
    }
    
    //Load XUL source from server
    try {
      RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, xulSrc);    //$NON-NLS-1$

      try {
        Request response = builder.sendRequest(null, new RequestCallback() {
          public void onError(Request request, Throwable exception) {
            Window.alert("Error loading XUL: "+exception.getMessage());   //$NON-NLS-1$
          }

          public void onResponseReceived(Request request, Response response) {
            if(AsyncXulLoader.this.loadingOverlay){
              loadOverlay(response.getText());
            } else {
              generateXulContainer(response.getText());
            }
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
  
  private void loadOverlay(String xulStr){

    com.google.gwt.xml.client.Document gwtDoc = XMLParser.parse(xulStr);
    try{
      

      if(messageBundle != null){
        this.container.loadOverlay(gwtDoc, messageBundle);
      } else {
        this.container.loadOverlay(gwtDoc);
      }


      callback.overlayLoaded();
    } catch(XulException e){
      Window.alert("Error loading XUL Overlay: "+e.getMessage());    //$NON-NLS-1$
      e.printStackTrace();
    }
  }
   
  private void generateXulContainer(String xulStr){
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

  
  