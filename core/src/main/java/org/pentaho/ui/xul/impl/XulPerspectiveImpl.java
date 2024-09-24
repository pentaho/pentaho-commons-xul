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
