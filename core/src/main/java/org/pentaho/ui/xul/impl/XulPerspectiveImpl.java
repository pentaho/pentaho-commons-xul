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

package org.pentaho.ui.xul.impl;

import org.pentaho.ui.xul.XulOverlay;
import org.pentaho.ui.xul.XulPerspective;
import org.pentaho.ui.xul.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class XulPerspectiveImpl implements XulPerspective {

  private List<XulOverlay> overlays = new ArrayList<XulOverlay>();

  private List<XulEventHandler> eventHandlers = new ArrayList<XulEventHandler>();
  private String name, id;

  public String getDisplayName() {
    return name;
  }

  public void setDisplayName( String name ) {
    this.name = name;
  }

  public List<XulEventHandler> getEventHandlers() {
    return eventHandlers;
  }

  public String getID() {
    return id;
  }

  public void setID( String id ) {
    this.id = id;
  }

  public List<XulOverlay> getOverlays() {
    return overlays;
  }

  public void addOverlay( XulOverlay overlay ) {
    this.overlays.add( overlay );
  }

  public void addEventHandler( XulEventHandler handler ) {
    this.eventHandlers.add( handler );
  }

  public void onLoad( Document doc ) {

  }

  public void onUnload() {

  }

}
