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

/**
 * The XulLoader will build the DOM model and corresponding managed UI implementation from the XUL document.
 * 
 * @author nbaker
 * 
 */
public interface XulLoader {

  /**
   * Loads a XUL document into a container, building the necessary model, impl and event handling.
   * 
   * @param xulDocument
   *          An XML document initialized from XUL XML.
   * @return The container holding the modeled UI.
   * @throws IllegalArgumentException
   *           Exception thrown if any problems are encountered instantiating the XUL library component from the
   *           XUL XML.
   * @throws XulException
   *           Exception thrown if errors encountered getting an instance of the parser.
   */
  // TODO: use a generic Document interface instead of Object
  public XulDomContainer loadXul( Object xulDocument ) throws IllegalArgumentException, XulException;

  /**
   * Loads a XUL document fragment into a container, building the necessary model, impl and event handling.
   * 
   * @param xulDocument
   *          An XML document initialized from XUL XML.
   * @return The container holding the modeled fragment.
   * @throws IllegalArgumentException
   *           Exception thrown if any problems are encountered instantiating the XUL library component from the
   *           XUL XML.
   * @throws XulException
   *           Exception thrown if errors encountered getting an instance of the parser.
   */
  // TODO: use a generic Document interface instead of Object
  public XulDomContainer loadXulFragment( Object xulDocument ) throws IllegalArgumentException, XulException;

  /**
   * Loads a XUL document into a container, building the necessary model, impl and event handling.
   * 
   * This method will look for a ResourceBundle Property file based on the resource name with a .properties
   * extension. If a Resource Bundle is found the Xul file is processed for any externalized strings
   * 
   * @param resource
   *          The location of the Xul document to load.
   * @return The container holding the modeled UI.
   * @throws IllegalArgumentException
   *           Exception thrown if any problems are encountered instantiating the XUL library component from the
   *           XUL XML.
   * @throws XulException
   *           Exception thrown if errors encountered getting an instance of the parser.
   */
  public XulDomContainer loadXul( String resource ) throws IllegalArgumentException, XulException;

  /**
   * Loads a XUL document into a container, building the necessary model, impl and event handling.
   * 
   * Processes the Xul Document for any externalized strings contained in the Resource Bundle.
   * 
   * @param resource
   *          The location of the Xul document to load.
   * @param bundle
   *          The Message ResourceBundle used to build the document.
   * @return The container holding the modeled UI.
   * @throws IllegalArgumentException
   *           Exception thrown if any problems are encountered instantiating the XUL library component from the
   *           XUL XML.
   * @throws XulException
   *           Exception thrown if errors encountered getting an instance of the parser.
   */
  public XulDomContainer loadXul( String resource, Object bundle ) throws XulException;

  /**
   * Loads a XUL document fragment into a container, building the necessary model, impl and event handling.
   * 
   * This method will look for a ResourceBundle Property file based on the resource name with a .properties
   * extension. If a Resource Bundle is found the Xul file is processed for any externalized strings
   * 
   * @param resource
   *          The location of the Xul document to load.
   * @return The container holding the modeled fragment.
   * @throws IllegalArgumentException
   *           Exception thrown if any problems are encountered instantiating the XUL library component from the
   *           XUL XML.
   * @throws XulException
   *           Exception thrown if errors encountered getting an instance of the parser.
   */
  public XulDomContainer loadXulFragment( String resource ) throws IllegalArgumentException, XulException;

  /**
   * Loads a XUL document fragment into a container, building the necessary model, impl and event handling.
   * 
   * Processes the Xul Document for any externalized strings contained in the Resource Bundle.
   * 
   * @param resource
   *          The location of the Xul document to load.
   * @param bundle
   *          The Message ResourceBundle used to build the document.
   * @return The container holding the modeled UI.
   * @throws IllegalArgumentException
   *           Exception thrown if any problems are encountered instantiating the XUL library component from the
   *           XUL XML.
   * @throws XulException
   *           Exception thrown if errors encountered getting an instance of the parser.
   */
  public XulDomContainer loadXulFragment( String resource, Object bundle ) throws XulException;

  /**
   * Support methed for creating XulComponents programatically at runtime (event handlers)
   * 
   * @param elementName
   *          The tag name of the element to create.
   * @return A XulComponent
   * @throws XulException
   *           Exception thrown if parser cannot handle tag
   */
  public XulComponent createElement( String elementName ) throws XulException;

  /**
   * 
   * Support method for determining if an element has been registered for creation.
   * 
   * @param elementName
   *          name of element to check
   * @return boolean value indicating element has been registered
   */
  public boolean isRegistered( String elementName );

  public void register( String tagName, String className );

  /**
   * Returns the location of the root Xul file.
   * 
   * @return Directory location as String
   */
  public String getRootDir();

  /**
   * Sets the root directory from which the loader will attempt to locate includes / overlays.
   * 
   * @param str
   *          root classpath directory
   */
  public void setRootDir( String str );

  /**
   * Provides a running application the ability to get a new XulLoader instance of their runtime flavor
   * 
   * @return XulLoader instance
   */
  public XulLoader getNewInstance() throws XulException;

  /**
   * Accommodates those objects that require a parenting on construction. Set the root parent before parsing.
   * 
   * @param context
   *          root context
   */
  public void setOuterContext( Object context );

  public void processOverlay( String overlaySrc, org.pentaho.ui.xul.dom.Document targetDocument,
      XulDomContainer container ) throws XulException;

  public void processOverlay( String overlaySrc, org.pentaho.ui.xul.dom.Document targetDocument,
      XulDomContainer container, Object resourceBundle ) throws XulException;

  public void removeOverlay( String overlaySrc, org.pentaho.ui.xul.dom.Document targetDocument,
      XulDomContainer container ) throws XulException;

  // TODO: create wrapper.
  void registerClassLoader( Object loader );

  void deRegisterClassLoader( Object loader );

  Object getResourceAsStream( String name );

  void setSettingsManager( XulSettingsManager settings );

  XulSettingsManager getSettingsManager();

}
