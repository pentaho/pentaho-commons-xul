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
package org.pentaho.ui.xul.gwt.tags.util;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.WidgetCollection;

import java.util.Iterator;

/**
 */
public class LabelWidget extends Widget implements HasWidgets {
  final WidgetCollection children = new WidgetCollection( this );

  public LabelWidget() {
    setElement( DOM.createLabel() );
  }

  public LabelWidget( String forElementId ) {
    this();
    setFor( forElementId );
  }

  public void setFor( String forElementId ) {
    getElement().setAttribute( "for", forElementId );
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
}
