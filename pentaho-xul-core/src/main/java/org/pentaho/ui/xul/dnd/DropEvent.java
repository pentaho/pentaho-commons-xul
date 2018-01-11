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

package org.pentaho.ui.xul.dnd;

public class DropEvent {

  private DataTransfer dataTransfer;
  private Object nativeEvent;
  private Object dropParent;
  private int dropIndex;
  private boolean accepted;
  private DropPosition dropPosition;

  public DropPosition getDropPosition() {
    return dropPosition;
  }

  public void setDropPosition( DropPosition dropPosition ) {
    this.dropPosition = dropPosition;
  }

  public void setDataTransfer( DataTransfer dataTransfer ) {
    this.dataTransfer = dataTransfer;
  }

  public DataTransfer getDataTransfer() {
    return dataTransfer;
  }

  public void setAccepted( boolean accepted ) {
    this.accepted = accepted;
  }

  public boolean isAccepted() {
    return accepted;
  }

  public void setNativeEvent( Object nativeEvent ) {
    this.nativeEvent = nativeEvent;
  }

  public Object getNativeEvent() {
    return nativeEvent;
  }

  public void setDropParent( Object dropParent ) {
    this.dropParent = dropParent;
  }

  /**
   * For the case of trees, this is the node in the tree where the item was dropped, only for bound trees. For all
   * other elements, this is null.
   * 
   * @return parent node
   */
  public Object getDropParent() {
    return dropParent;
  }

  public void setDropIndex( int dropIndex ) {
    this.dropIndex = dropIndex;
  }

  /**
   * For elements that have ordered lists in them (trees and lists) this is the index of the drop.
   * 
   * @return index
   */
  public int getDropIndex() {
    return dropIndex;
  }

}
