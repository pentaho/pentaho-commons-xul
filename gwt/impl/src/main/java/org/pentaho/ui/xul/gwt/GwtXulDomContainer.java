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

package org.pentaho.ui.xul.gwt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pentaho.gwt.widgets.client.utils.i18n.ResourceBundle;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulEventSource;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.XulPerspective;
import org.pentaho.ui.xul.XulSettingsManager;
import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.components.XulMessageBox;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.gwt.binding.GwtBindingContext;
import org.pentaho.ui.xul.gwt.binding.GwtBindingMethod;
import org.pentaho.ui.xul.gwt.overlay.OverlayProfile;
import org.pentaho.ui.xul.gwt.util.EventHandlerWrapper;
import org.pentaho.ui.xul.impl.XulEventHandler;

public class GwtXulDomContainer implements XulDomContainer {

  Document document;
  Map<String, XulEventHandler> handlers = new HashMap<String, XulEventHandler>();
  Map<XulEventHandler, EventHandlerWrapper> handlerWrapers = new HashMap<XulEventHandler, EventHandlerWrapper>();
  GwtXulLoader loader;
  private XulSettingsManager settings;
  private List<Object> resourceBundles = new ArrayList<Object>();
  private List<String> overlayToBeApplied = new ArrayList<String>();

  private boolean initialized;

  protected GwtBindingContext bindings;

  public GwtXulDomContainer() {
    bindings = new GwtBindingContext();
  }

  public void addDocument( Document document ) {
    this.document = document;
  }

  public Document getDocumentRoot() {
    return document;
  }

  public XulDomContainer loadFragment( String xulLocation ) throws XulException {
    // TODO Auto-generated method stub
    return null;
  }

  Map<String, XulEventHandler> eventHandlers = new HashMap<String, XulEventHandler>();

  public void addEventHandler( XulEventHandler handler ) {
    handler.setXulDomContainer( this );
    this.handlers.put( handler.getName(), handler );
  }

  /**
   * 
   * @deprecated Use {@link #addEventHandler(XulEventHandler)}. This work-around is no longer needed.
   * @param wrapper
   */
  @Deprecated
  public void addEventHandler( EventHandlerWrapper wrapper ) {

    XulEventHandler handler = wrapper.getHandler();
    this.handlerWrapers.put( handler, wrapper );
    handler.setXulDomContainer( this );
    this.handlers.put( handler.getName(), handler );

  }

  public void addEventHandler( String id, String eventClassName ) {
    throw new UnsupportedOperationException( "use addEventHandler(XulEventHandler handler)" );
  }

  public XulEventHandler getEventHandler( String key ) throws XulException {
    return handlers.get( key );
  }

  public XulMessageBox createMessageBox( String message ) {
    // TODO Auto-generated method stub
    return null;
  }

  public void initialize() {
    this.initialized = true;
  }

  public boolean isInitialized() {
    return initialized;
  }

  public void close() {
    // TODO Auto-generated method stub

  }

  public Document getDocument( int idx ) {

    return document;

  }

  public Map<String, XulEventHandler> getEventHandlers() {
    return eventHandlers;
  }

  public Object getOuterContext() {
    return "";
  }

  private Object[] getArgs( String methodCall ) {
    if ( methodCall.endsWith( "()" ) ) {
      return null;
    }
    String argsList = methodCall.substring( methodCall.indexOf( "(" ) + 1, methodCall.lastIndexOf( ")" ) );
    String[] stringArgs = argsList.split( "," );
    Object[] args = new Object[stringArgs.length];
    int i = -1;
    for ( String obj : stringArgs ) {
      i++;
      obj = obj.trim();
      try {
        Integer num = Integer.valueOf( obj );
        args[i] = num;
        continue;
      } catch ( NumberFormatException e ) {
        try {
          Double num = Double.valueOf( obj );
          args[i] = num;
          continue;
        } catch ( NumberFormatException e2 ) {
          try {
            if ( obj.indexOf( '\'' ) == -1 && obj.indexOf( '\"' ) == -1 ) {
              throw new IllegalArgumentException( "Not a string" );
            }
            String str = obj.replaceAll( "'", "" );
            str = str.replaceAll( "\"", "" );
            args[i] = str;
            continue;
          } catch ( IllegalArgumentException e4 ) {
            try {
              Boolean flag = Boolean.parseBoolean( obj );
              args[i] = flag;
              continue;
            } catch ( NumberFormatException e3 ) {
              continue;
            }
          }
        }
      }
    }
    return args;

  }

  private Class unBoxPrimative( Class clazz ) {
    return clazz;
  }

  private Map<String, GwtBindingMethod> methodMap = new HashMap<String, GwtBindingMethod>();

  private GwtBindingMethod getMethod( Object handler, String method ) {
    GwtBindingMethod m = methodMap.get( method );
    if ( m != null ) {
      return m;
    }

    String methodName = method.substring( method.indexOf( "." ) + 1, method.indexOf( "(" ) );

    m = GwtBindingContext.typeController.findMethod( handler, methodName );
    methodMap.put( method, m );
    return m;
  }

  public Object invoke( String method, Object[] args ) throws XulException {
    try {
      if ( method == null || method.indexOf( '.' ) == -1 ) {
        throw new XulException( "method call does not follow the pattern [EventHandlerID].methodName()" );
      }

      if ( args == null || args.length == 0 ) {
        String methodName = method.substring( method.indexOf( "." ) + 1 );
        Object[] arguments = getArgs( methodName );
        if ( arguments != null ) {
          return invoke( method.substring( 0, method.indexOf( "(" ) ) + "()", arguments );
        }
      }

      String eventID = method.substring( 0, method.indexOf( "." ) );
      Object handler = this.handlers.get( eventID );
      GwtBindingMethod m = getMethod( handler, method );

      if ( args.length > 0 ) {
        try {
          return m.invoke( handler, args );
        } catch ( Exception e ) {
          throw new XulException( "Error invoking method: " + method, e );
        }
      } else {
        try {
          return m.invoke( handler, new Object[] {} );
        } catch ( Exception e ) {
          throw new XulException( "Error invoking method: " + method, e );
        }
      }
    } catch ( Exception e ) {
      throw new XulException( "Error invoking method: " + method, e );
    }
  }

  public void invokeLater( Runnable runnable ) {

    // TODO Auto-generated method stub

  }

  public boolean isClosed() {
    return false;
  }

  public boolean isRegistered( String widgetHandlerName ) {
    return this.loader.isRegistered( widgetHandlerName );
  }

  public void loadFragment( String id, String src ) throws XulException {
  }

  public void mergeContainer( XulDomContainer container ) {

    // TODO Auto-generated method stub

  }

  private Map<String, OverlayProfile> overlays = new HashMap<String, OverlayProfile>();

  private void applyOverlay( Document doc, boolean apply ) {
    this.document = getDocumentRoot();

    // components come in referencing a different dom container. reset it now
    setXulDomContainer( doc.getRootElement() );
    OverlayProfile overlayProfile = new OverlayProfile( doc.getRootElement(), document.getRootElement() );
    String id = doc.getRootElement().getAttributeValue( "id" );
    overlays.put( id, overlayProfile );
    // Check if the if the overlay id exists in the overlay to be loaded cache
    // boolean exists = existsInOverlayCache(id);
    boolean exists = overlayToBeApplied.contains( id );
    if ( !exists ) {
      // attribute on the overlay can veto the loading (set to false). It can't force one with true though
      if ( !apply || "false".equals( doc.getRootElement().getAttributeValue( "loadatstart" ) ) ) {
        return;
      }
    } else {
      // remove the overlay id from the cache and apply the overlay
      // removeFromOverlayCache(id);
      overlayToBeApplied.remove( id );
    }
    overlayProfile.perform();
  }

  private void setXulDomContainer( XulComponent ele ) {
    ( (AbstractGwtXulComponent) ele ).setXulDomContainer( this );
    for ( XulComponent child : ele.getChildNodes() ) {
      setXulDomContainer( child );
    }
  }

  public void loadOverlay( com.google.gwt.xml.client.Document overlayDoc, ResourceBundle bundle ) throws XulException {
    loadOverlay( overlayDoc, bundle, true );
  }

  public void loadOverlay( com.google.gwt.xml.client.Document overlayDoc, ResourceBundle bundle, boolean applyAtStart )
    throws XulException {
    XulDomContainer overlayContainer = this.loader.loadXul( overlayDoc, bundle );
    applyOverlay( overlayContainer.getDocumentRoot(), applyAtStart );
  }

  public void loadOverlay( com.google.gwt.xml.client.Document overlayDoc ) throws XulException {
    loadOverlay( overlayDoc, true );
  }

  public void loadOverlay( com.google.gwt.xml.client.Document overlayDoc, boolean applyAtStart ) throws XulException {
    XulDomContainer overlayContainer = this.loader.loadXul( overlayDoc );
    applyOverlay( overlayContainer.getDocumentRoot(), applyAtStart );
  }

  // public void removeOverlay(com.google.gwt.xml.client.Document overlayDoc) throws XulException {
  // XulDomContainer overlayContainer = this.loader.loadXul(overlayDoc);
  //
  // for (XulComponent child : overlayContainer.getDocumentRoot().getChildNodes()) {
  // String id = child.getId();
  // Window.alert("processing remove: " + id);
  // if (id == null | id.equals("")) {
  // continue;
  // }
  // XulComponent insertedNode = document.getElementById(id);
  // if (insertedNode != null) {
  // Window.alert("found and removing node: " + id);
  // insertedNode.getParent().removeChild(insertedNode);
  // } else {
  // Window.alert("no such node: " + id);
  // }
  // }
  // }

  public void removeOverlay( com.google.gwt.xml.client.Document overlayDoc ) throws XulException {
    OverlayProfile profile = overlays.get( overlayDoc.getAttributes().getNamedItem( "id" ) );
    if ( profile != null ) {
      profile.remove();
    }

  }

  public void setOuterContext( Object context ) {

    // TODO Auto-generated method stub

  }

  public void setLoader( GwtXulLoader loader ) {
    this.loader = loader;
  }

  public XulLoader getXulLoader() {
    return loader;
  }

  public void addBinding( Binding binding ) {

    bindings.add( binding );

  }

  public void addInitializedBinding( Binding b ) {
    // TODO Auto-generated method stub

  }

  public Binding createBinding( XulEventSource source, String sourceAttr, String targetId, String targetAttr ) {
    // TODO Auto-generated method stub
    return null;
  }

  public Binding createBinding( String source, String sourceAttr, String targetId, String targetAttr ) {
    // TODO Auto-generated method stub
    return null;
  }

  public XulDomContainer loadFragment( String xulLocation, Object bundle ) throws XulException {
    // TODO Auto-generated method stub
    return null;
  }

  public void loadOverlay( String src ) throws XulException {

    OverlayProfile profile = overlays.get( src );
    if ( profile != null ) {
      profile.perform();
    } else {
      // Overlay is not yet registered by the plugin. So cache this id and apply it at the time of
      // registration
      overlayToBeApplied.add( src );
    }

  }

  public void loadOverlay( String src, Object resourceBundle ) throws XulException {
    throw new RuntimeException( "not yet implemented" );
  }

  public void removeBinding( Binding binding ) {
    throw new RuntimeException( "not yet implemented" );

  }

  public void removeOverlay( String src ) throws XulException {
    OverlayProfile profile = overlays.get( src );
    if ( profile != null ) {
      profile.remove();
    }

  }

  public void setResourceBundles( final List<Object> resourceBundles ) {
    if ( resourceBundles != null ) {
      this.resourceBundles = resourceBundles;
    } else {
      this.resourceBundles = new ArrayList<Object>();
    }
  }

  public List<Object> getResourceBundles() {
    return resourceBundles;
  }

  public void addPerspective( XulPerspective perspective ) {
    // TODO Auto-generated method stub

  }

  public void loadPerspective( String id ) {
    // TODO Auto-generated method stub

  }

  public void registerClassLoader( Object loader ) {
    // TODO Auto-generated method stub

  }

  public void deRegisterClassLoader(Object loader) {

  }

  public XulSettingsManager getSettingsManager() {
    return settings;
  }

  public void setSettingsManager( XulSettingsManager settings ) {
    this.settings = settings;
  }
}
