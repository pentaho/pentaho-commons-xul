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

package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulOverlay;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

public class GwtOverlay extends AbstractGwtXulContainer implements XulOverlay {

  public static void register() {
    GwtXulParser.registerHandler( "overlay", new GwtXulHandler() {
      public Element newInstance() {
        return new GwtOverlay();
      }
    } );
  }

  public GwtOverlay() {
    super( "overlay" );
  }

  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    super.init( srcEle, container );
  }

  public String getSrc() {
    return "";
  }

  public void setSrc( String src ) {

  }

  public boolean isDisabled() {
    return false;
  }

  public void setDisabled( boolean disabled ) {
  }

  @Override
  public void layout() {
    // No layout required
  }

  public String getOverlayUri() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getResourceBundleUri() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getOverlayXml() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getSource() {
    // TODO Auto-generated method stub
    return null;
  }

  public int getPriority() {
    // TODO Auto-generated method stub
    return 9999;
  }

}
