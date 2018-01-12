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
