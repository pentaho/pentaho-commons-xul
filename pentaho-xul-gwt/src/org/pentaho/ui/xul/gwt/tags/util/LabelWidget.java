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
 * Copyright (c) 2002-2013 Pentaho Corporation..  All rights reserved.
 */

package org.pentaho.ui.xul.gwt.tags.util;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.WidgetCollection;

import java.util.Iterator;

/**
 */
public class LabelWidget extends Widget implements HasWidgets {
  final WidgetCollection children = new WidgetCollection( this );

  public LabelWidget() {
    setElement( DOM.createSpan() );
    sinkEvents( Event.ONCLICK );
    addHandler( new ClickHandler() {
      @Override
      public void onClick( ClickEvent event ) {
        clickElement( DOM.getElementById( getFor() ) );
        event.stopPropagation();
      }
    }, ClickEvent.getType() );
  }

  public LabelWidget( String forElementId ) {
    this();
    setFor( forElementId );
  }

  public void setFor( String forElementId ) {
    getElement().setAttribute( "for", forElementId );
  }

  public String getFor() {
    return getElement().getAttribute( "for" );
  }

  @Override
  protected void doAttachChildren() {
    super.doAttachChildren();

    final Iterator<Widget> iterator = iterator();
    while ( iterator.hasNext() ) {
      DOM.appendChild( getElement(), iterator.next().getElement() );
    }
  }

  @Override
  public void add( Widget w ) {
    children.add( w );
  }

  @Override
  public void clear() {
    while ( children.size() > 0 ) {
      children.remove( 0 );
    }
  }

  @Override
  public Iterator<Widget> iterator() {
    return children.iterator();
  }

  @Override
  public boolean remove( Widget w ) {
    if ( children.contains( w ) ) {
      children.remove( w );
      return true;
    }
    return false;
  }

  private static native void clickElement( Element elem ) /*-{
    elem.click();
  }-*/;
}
