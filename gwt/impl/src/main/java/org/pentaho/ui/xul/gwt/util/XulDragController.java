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


package org.pentaho.ui.xul.gwt.util;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import org.pentaho.gwt.widgets.client.ui.Draggable;

/**
 * User: nbaker Date: Aug 5, 2010
 */
public class XulDragController extends PickupDragController {

  private Widget proxy;
  private static XulDragController instance = new XulDragController();

  public Widget getProxy() {
    return proxy;
  }

  public void setProxy( Widget proxy ) {
    this.proxy = proxy;
  }

  private XulDragController() {
    super( RootPanel.get(), false );
    setBehaviorDragProxy( true );
    setBehaviorDragStartSensitivity( 5 );
  }

  public static XulDragController getInstance() {
    return instance;
  }

  @Override
  protected void restoreSelectedWidgetsStyle() {
  }

  @Override
  protected void saveSelectedWidgetsLocationAndStyle() {
  }

  @Override
  protected void restoreSelectedWidgetsLocation() {
  }

  @Override
  public void dragEnd() {
    proxy.removeFromParent();
    proxy = null;
    super.dragEnd();
  }

  @Override
  protected Widget newDragProxy( DragContext context ) {
    proxy = ( (Draggable) context.draggable ).makeProxy( context.draggable );
    return proxy;
  }

  @Override
  public void previewDragEnd() throws VetoDragException {
    super.previewDragEnd();

  }

  public Object getDragObject() {
    return ( (Draggable) context.draggable ).getDragObject();
  }

}
