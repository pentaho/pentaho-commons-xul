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

/**
 * 
 */

package org.pentaho.ui.xul;

import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.impl.XulEventHandler;

import java.util.List;
import java.util.Map;

/**
 * @author OEM
 * 
 */
public interface XulDomContainer {

  public void addDocument( Document document );

  public Document getDocumentRoot();

  public Document getDocument( int idx );

  public XulDomContainer loadFragment( String xulLocation ) throws XulException;

  /**
   * @deprecated We are getting away from using xul-instantiated event handlers. It is prefered that the
   *             application set the event handler via {@link XulDomContainer#addEventHandler(XulEventHandler)}
   */
  public void addEventHandler( String id, String eventClassName ) throws XulException;

  public XulDomContainer loadFragment( String xulLocation, Object bundle ) throws XulException;

  public void loadFragment( String id, String src ) throws XulException;

  public XulEventHandler getEventHandler( String key ) throws XulException;

  /**
   * Registers an event handler with elements of this container. Attributes of command-type elements within a xul
   * file can refer to an event handler by name. See {@link XulEventHandler#getName()}. Adding an event handler by
   * a name that is already registered will completely mask out and replace the original event handler.
   * 
   * @param handler
   *          - a XulEventHandler
   */
  public void addEventHandler( XulEventHandler handler );

  @Deprecated
  public void addBinding( Binding binding );

  public void addInitializedBinding( Binding b );

  public void removeBinding( Binding binding );

  public void initialize();

  public boolean isInitialized();

  public void close();

  public boolean isClosed();

  public XulLoader getXulLoader();

  public void mergeContainer( XulDomContainer container );

  public Map<String, XulEventHandler> getEventHandlers();

  /**
   * Execute the method passed, with any args as parameters. This invokation is used for plumbing event handlers to
   * the event methods.
   * 
   * @param method
   *          The method to execute
   * @param args
   *          Any parameters needed for the method.
   * 
   * @return the invoked method's return object
   */
  public Object invoke( String method, Object[] args ) throws XulException;

  /**
   * Accommodates those objects that require a parenting on construction. Set the root parent before parsing.
   * 
   * @param context
   *          root context
   */
  public void setOuterContext( Object context );

  public Object getOuterContext();

  @Deprecated
  public Binding createBinding( XulEventSource source, String sourceAttr, String targetId, String targetAttr );

  @Deprecated
  public Binding createBinding( String source, String sourceAttr, String targetId, String targetAttr );

  public void loadOverlay( String src ) throws XulException;

  public void loadOverlay( String src, Object resourceBundle ) throws XulException;

  public void removeOverlay( String src ) throws XulException;

  public void invokeLater( Runnable runnable );

  public boolean isRegistered( String widgetHandlerName );

  public void setResourceBundles( List<Object> resourceBundles );

  public List<Object> getResourceBundles();

  void loadPerspective( String id );

  void addPerspective( XulPerspective perspective );

  // TODO: create wrapper.
  void registerClassLoader( Object loader );

  void deRegisterClassLoader( Object loader );

  void setSettingsManager( XulSettingsManager settings );

  XulSettingsManager getSettingsManager();

}
