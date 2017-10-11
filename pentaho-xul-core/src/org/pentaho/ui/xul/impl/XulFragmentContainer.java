/*!
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
 */

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
