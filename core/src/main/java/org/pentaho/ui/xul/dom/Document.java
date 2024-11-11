/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


/**
 * 
 */

package org.pentaho.ui.xul.dom;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.binding.Binding;

/**
 * @author NBaker
 * 
 */
public interface Document extends Element {
  public XulComponent getRootElement();

  public XulComponent createElement( String elementName ) throws XulException;

  public boolean isRegistered( String elementName );

  public void setXulDomContainer( XulDomContainer container );

  public void addOverlay( String src ) throws XulException;

  public void removeOverlay( String src ) throws XulException;

  public void loadFragment( String id, String src ) throws XulException;

  @Deprecated
  public void addBinding( Binding bind );

  public void addInitializedBinding( Binding b );

  public void invokeLater( Runnable runnable );

  void loadPerspective( String id );
}
