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

package org.pentaho.ui.xul.gwt.overlay;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;

/**
 * User: nbaker Date: 10/12/11
 */
public class AddElementOverlayAction implements IOverlayAction {

  private Element element;
  private Element parent;
  private Element relativeElement;
  private Type insertType;

  public enum Type {
    BEFORE, AFTER, FIRST, LAST, POSITION
  }

  private int position;

  public AddElementOverlayAction( Element element, Element parent, Type insertType ) {
    this.element = element;
    this.parent = parent;
    this.insertType = insertType;
  }

  public AddElementOverlayAction( Element element, Element parent, Element relativeElement, Type insertType ) {
    this.element = element;
    this.parent = parent;
    this.relativeElement = relativeElement;
    this.insertType = insertType;
  }

  public AddElementOverlayAction( Element element, Element parent, Type insertType, int position ) {
    this.element = element;
    this.parent = parent;
    this.insertType = insertType;
    this.position = position;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition( int position ) {
    this.position = position;
  }

  public Element getParent() {
    return parent;
  }

  public void setParent( Element parent ) {
    this.parent = parent;
  }

  public Type getInsertType() {
    return insertType;
  }

  public void setInsertType( Type insertType ) {
    this.insertType = insertType;
  }

  public Element getRelativeElement() {
    return relativeElement;
  }

  public void setRelativeElement( Element relativeElement ) {
    this.relativeElement = relativeElement;
  }

  public Element getElement() {
    return element;
  }

  public void setElement( Element element ) {
    this.element = element;
  }

  public void perform() {

    int size = parent.getChildNodes().size();
    boolean existingSubMenu = false;
    for ( int i = 0; i < size; i++ ) {
      XulComponent comp = parent.getChildNodes().get( i );
      if ( comp.getId() == element.getAttributeValue( "id" ) ) {
        existingSubMenu = true;
        for ( XulComponent node : element.getChildNodes() ) {
          Element nodeElement = node;
          comp.addChild( nodeElement );
        }
        ( (AbstractGwtXulComponent) comp ).layout();
      }
    }

    if ( !existingSubMenu ) {
      switch ( insertType ) {
        case BEFORE:
          parent.addChildAt( element, parent.getChildNodes().indexOf( relativeElement ) );
          break;
        case AFTER:
          parent.addChildAt( element, parent.getChildNodes().indexOf( relativeElement ) + 1 );
          break;
        case FIRST:
          parent.addChildAt( element, 0 );
          break;
        case POSITION:
          parent.addChildAt( element, position );
          break;
        case LAST:
        default:
          parent.addChild( element );
          break;
      }
    }

    ( (AbstractGwtXulComponent) parent ).layout();
  }

  public void remove() {
    element.getParent().removeChild( element );
    ( (AbstractGwtXulComponent) parent ).layout();
  }
}
