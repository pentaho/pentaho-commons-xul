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

package org.pentaho.ui.xul.gwt;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;

public class GwtDomDocument extends GwtDomElement implements Document {

  Element rootElement;
  XulDomContainer container;

  public GwtDomDocument() {
    super( "DOCUMENT" );
  }

  public XulComponent getRootElement() {
    // TODO Auto-generated method stub
    return (XulComponent) getFirstChild();
  }

  public void addOverlay( String src ) throws XulException {

    // TODO Auto-generated method stub

  }

  public XulComponent createElement( String elementName ) throws XulException {
    return this.container.getXulLoader().createElement( elementName );
  }

  public void invokeLater( Runnable runnable ) {

    // TODO Auto-generated method stub

  }

  public boolean isRegistered( String elementName ) {
    return false;
  }

  public void loadFragment( String id, String src ) throws XulException {

    // TODO Auto-generated method stub

  }

  public void removeOverlay( String src ) throws XulException {

    // TODO Auto-generated method stub

  }

  public void setXulDomContainer( XulDomContainer container ) {
    this.container = container;
  }

  public void addBinding( Binding bind ) {
    ( (GwtXulDomContainer) this.container ).addBinding( bind );

  }

  public void addInitializedBinding( Binding b ) {
    ( (GwtXulDomContainer) this.container ).addInitializedBinding( b );

  }

  public void loadPerspective( String id ) {

  }

}
