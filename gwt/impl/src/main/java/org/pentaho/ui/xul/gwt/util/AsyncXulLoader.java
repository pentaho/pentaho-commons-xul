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


package org.pentaho.ui.xul.gwt.util;

import java.util.HashMap;
import java.util.Map;

import org.pentaho.gwt.widgets.client.utils.i18n.IResourceBundleLoadCallback;
import org.pentaho.gwt.widgets.client.utils.i18n.ResourceBundle;
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
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.XMLParser;

public class AsyncXulLoader implements IResourceBundleLoadCallback {

  private static Map<String, String> overlayCache = new HashMap<String, String>();

  private ResourceBundle messageBundle;

  private String xulSrc;

  private String bundle;

  private IXulLoaderCallback callback;

  private boolean loadingOverlay = false;
  private boolean removingOverlay = false;
  private boolean fromSource = false;
  private boolean caching = false;

  private GwtXulDomContainer container;
  public static final String PROPERTIES_EXTENSION = ".properties"; //$NON-NLS-1$
  public static final String SEPARATOR = "/"; //$NON-NLS-1$

  public static final String NS_KEY = "there.is.only.xul";

  public static void loadXulFromUrl( String location, String bundle, IXulLoaderCallback callback ) {
    AsyncXulLoader loader = new AsyncXulLoader( location, bundle, callback );
    loader.init();

  }

  public static void loadXulFromSource( String source, String bundle, IXulLoaderCallback callback ) {
    AsyncXulLoader loader = new AsyncXulLoader( source, bundle, callback, true );
    loader.init();
  }

  public static void loadOverlayFromUrl( String location, String bundle, GwtXulDomContainer container,
      IXulLoaderCallback callback ) {
    AsyncXulLoader loader = new AsyncXulLoader( location, bundle, container, callback, true, false );
    loader.init();
  }

  public static void loadOverlayFromUrl( String location, String bundle, GwtXulDomContainer container,
      IXulLoaderCallback callback, boolean cache ) {
    if ( cache ) {
      if ( overlayCache.containsKey( location ) ) {
        AsyncXulLoader loader =
            new AsyncXulLoader( overlayCache.get( location ), bundle, container, callback, true, true );
        loader.init();
        return;
      }
    }
    // if not cached, load as normal
    AsyncXulLoader loader = new AsyncXulLoader( location, bundle, container, callback, true, false, cache );
    loader.init();

  }

  public static void removeOverlayFromUrl( String location, String bundle, GwtXulDomContainer container,
      IXulLoaderCallback callback ) {
    AsyncXulLoader loader = new AsyncXulLoader( location, bundle, container, callback, false, false );
    loader.init();
  }

  public static void loadOverlayFromSource( String location, String bundle, GwtXulDomContainer container,
      IXulLoaderCallback callback ) {
    AsyncXulLoader loader = new AsyncXulLoader( location, bundle, container, callback, true, true );
    loader.init();
  }

  public static void removeOverlayFromSource( String location, String bundle, GwtXulDomContainer container,
      IXulLoaderCallback callback ) {
    AsyncXulLoader loader = new AsyncXulLoader( location, bundle, container, callback, false, true );
    loader.init();
  }

  private AsyncXulLoader( String source, String bundle, IXulLoaderCallback callback ) {
    xulSrc = source;
    this.callback = callback;
    this.bundle = bundle;
  }

  private AsyncXulLoader( String source, String bundle, IXulLoaderCallback callback, boolean fromSource ) {
    this( source, bundle, callback );
    this.fromSource = fromSource;
  }

  private AsyncXulLoader( String source, String bundle, GwtXulDomContainer container, IXulLoaderCallback callback,
      boolean loadOverlay, boolean fromSource ) {
    this( source, bundle, callback, fromSource );
    this.container = container;
    if ( loadOverlay ) {
      this.loadingOverlay = true;
    } else {
      this.removingOverlay = true;
    }
  }

  private AsyncXulLoader( String source, String bundle, GwtXulDomContainer container, IXulLoaderCallback callback,
      boolean loadOverlay, boolean fromSource, boolean caching ) {
    this( source, bundle, callback, fromSource );
    this.container = container;
    if ( loadOverlay ) {
      this.loadingOverlay = true;
    } else {
      this.removingOverlay = true;
    }
    this.caching = true;
  }

  private void init() {
    String folder = ""; //$NON-NLS-1$
    String baseName = bundle;

    // we have to separate the folder from the base name
    if ( bundle.indexOf( SEPARATOR ) > -1 ) {
      folder = bundle.substring( 0, bundle.lastIndexOf( SEPARATOR ) + 1 );
      baseName = bundle.substring( bundle.lastIndexOf( SEPARATOR ) + 1 );
    }

    // some may put the .properties on incorrectly
    if ( baseName.contains( PROPERTIES_EXTENSION ) ) {
      baseName = baseName.substring( 0, baseName.indexOf( PROPERTIES_EXTENSION ) );
    }
    // some may put the .properties on incorrectly
    if ( baseName.contains( ".properties" ) ) {
      baseName = baseName.substring( 0, baseName.indexOf( ".properties" ) );
    }

    try {
      messageBundle = new ResourceBundle( folder, baseName, true, this );
    } catch ( Exception e ) {
      Window.alert( "Error loading message bundle: " + e.getMessage() ); //$NON-NLS-1$
      e.printStackTrace();
    }
  }

  public void bundleLoaded( String bundleName ) {

    if ( fromSource ) { // already given Xul source
      if ( loadingOverlay ) {
        loadOverlay( xulSrc );
      } else if ( removingOverlay ) {
        removeOverlay( xulSrc );
      } else {
        generateXulContainer( xulSrc );
      }
      return;
    }

    // Load XUL source from server
    try {
      RequestBuilder builder = new RequestBuilder( RequestBuilder.GET, xulSrc );

      try {
        Request response = builder.sendRequest( null, new RequestCallback() {
          public void onError( Request request, Throwable exception ) {
            Window.alert( "Error loading XUL: " + exception.getMessage() ); //$NON-NLS-1$
          }

          public void onResponseReceived( Request request, Response response ) {
            if ( caching ) {
              overlayCache.put( xulSrc, response.getText() );
            }
            if ( AsyncXulLoader.this.loadingOverlay ) {
              loadOverlay( response.getText() );
            } else if ( AsyncXulLoader.this.removingOverlay ) {
              removeOverlay( response.getText() );
            } else {
              generateXulContainer( response.getText() );
            }
          }
        } );
      } catch ( RequestException e ) {
        Window.alert( "error loading bundle: " + e.getMessage() ); //$NON-NLS-1$
      }
    } catch ( Exception e ) {
      Window.alert( "error loading bundle: " + e.getMessage() ); //$NON-NLS-1$
      e.printStackTrace();
    }
  }

  private void removeOverlay( String xulStr ) {
    com.google.gwt.xml.client.Document gwtDoc = XMLParser.parse( xulStr );
    try {
      this.container.removeOverlay( gwtDoc );
      callback.overlayRemoved();
    } catch ( XulException e ) {
      Window.alert( "Error loading XUL Overlay: " + e.getMessage() ); //$NON-NLS-1$
      e.printStackTrace();
    }

  }

  private void loadOverlay( String xulStr ) {

    com.google.gwt.xml.client.Document gwtDoc = XMLParser.parse( xulStr );
    try {

      if ( messageBundle != null ) {
        this.container.loadOverlay( gwtDoc, messageBundle );
      } else {
        this.container.loadOverlay( gwtDoc );
      }

      callback.overlayLoaded();
    } catch ( XulException e ) {
      Window.alert( "Error loading XUL Overlay: " + e.getMessage() ); //$NON-NLS-1$
      e.printStackTrace();
    }
  }

  private void loadXulContainer( com.google.gwt.xml.client.Document doc ) {

    try {

      GwtXulLoader loader = new GwtXulLoader();
      GwtXulRunner runner = new GwtXulRunner();

      XulDomContainer container;

      if ( messageBundle != null ) {
        container = loader.loadXul( doc, messageBundle );
      } else {
        container = loader.loadXul( doc );
      }

      runner.addContainer( container );

      callback.xulLoaded( runner );

    } catch ( Exception e ) {
      Window.alert( "Error generating XUL: " + e.getMessage() ); //$NON-NLS-1$
      e.printStackTrace();
    }
  }

  private void generateXulContainer( String xulStr ) {
    if ( xulStr == null || xulStr.indexOf( NS_KEY ) < 0 ) {
      Window.alert( "Error loading XUL Application. Your session may have timed out." );
      return;
    }
    try {
      final com.google.gwt.xml.client.Document gwtDoc = XMLParser.parse( xulStr );

      processIncludes( gwtDoc );

    } catch ( Exception e ) {
      Window.alert( "Error generating XUL: " + e.getMessage() ); //$NON-NLS-1$
      e.printStackTrace();
    }
  }

  public void processIncludes( final com.google.gwt.xml.client.Document gwtDoc ) {

    for ( int y = 0; y < gwtDoc.getChildNodes().getLength(); y++ ) {
      final com.google.gwt.xml.client.Node window = gwtDoc.getChildNodes().item( y );
      try {
        if ( window.getNodeName().equals( "window" ) == false ) {
          continue;
        }
      } catch ( Exception e ) {
        continue;
      }

      for ( int i = 0; i < window.getChildNodes().getLength(); i++ ) {
        final com.google.gwt.xml.client.Node ele = window.getChildNodes().item( i );
        if ( ele.getNodeName() != null && ele.getNodeName().equals( "pen:include" ) ) {
          try {
            RequestBuilder builder =
                new RequestBuilder( RequestBuilder.GET, ele.getAttributes().getNamedItem( "src" ).getNodeValue() );
            builder.sendRequest( null, new RequestCallback() {
              public void onError( Request request, Throwable exception ) {
                Window.alert( "Error loading XUL: " + exception.getMessage() ); //$NON-NLS-1$
              }

              public void onResponseReceived( Request request, Response response ) {
                com.google.gwt.xml.client.Document doc = XMLParser.parse( response.getText() );
                Node parent = ele.getParentNode();
                parent.removeChild( ele );

                for ( int z = 0; z < doc.getChildNodes().getLength(); z++ ) {
                  if ( doc.getChildNodes().item( z ).getChildNodes().getLength() > 0 ) {
                    parent.appendChild( doc.getChildNodes().item( z ) );
                    break;
                  }
                }
                processIncludes( gwtDoc );
                return;
              }
            } );
          } catch ( RequestException e ) {
            Window.alert( "error loading bundle: " + e.getMessage() ); //$NON-NLS-1$
          }

        }
      }
    }

    loadXulContainer( gwtDoc );
  }

}
