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

package org.pentaho.ui.xul.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.XulLoader;
import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.components.XulMessageBox;
import org.pentaho.ui.xul.dom.Document;

/**
 * @author OEM
 * 
 */
public class XulFragmentContainer extends AbstractXulDomContainer {
  private static final Log logger = LogFactory.getLog( XulFragmentContainer.class );
  private Document fragment;

  public XulFragmentContainer( XulLoader xulLoader ) {
    super();
    this.xulLoader = xulLoader;
  }

  public Document getDocumentRoot() {
    return fragment;
  }

  public void addDocument( Document document ) {
    this.fragment = document;
  }

  @Override
  public void close() {
  }

  public boolean isClosed() {
    return false;
  }

  @Override
  public XulFragmentContainer loadFragment( String xulLocation ) throws XulException {
    logger.error( "loadFragment not implemented in XulFragmentContainer" );
    throw new XulException( "loadFragment not implemented in XulFragmentContainer" );
  }

  public XulMessageBox createErrorMessageBox( String title, String message, Throwable throwable ) {
    return null;
  }

  public XulDomContainer loadFragment( String xulLocation, Object bundle ) throws XulException {
    logger.error( "loadFragment not implemented in XulFragmentContainer" );
    return null;
  }

  public Document getDocument( int idx ) {
    if ( idx > 0 ) {
      return null;
    } else {
      return fragment;
    }
  }

  @Deprecated
  public void addBinding( Binding binding ) {
    // no implementation needed
  }

  public void addInitializedBinding( Binding binding ) {
    // no implementation needed
  }

  public void loadOverlay( String src ) throws XulException {
    // no implementation needed
  }

  public void removeOverlay( String src ) throws XulException {
    // no implementation needed
  }

  public void removeBinding( Binding binding ) {
    // no implementation needed

  }

  public void loadFragment( String id, String src ) throws XulException {
    // no implementation needed
  }

  public void loadOverlay( String src, Object resourceBundle ) throws XulException {
    // TODO Auto-generated method stub

  }

}
