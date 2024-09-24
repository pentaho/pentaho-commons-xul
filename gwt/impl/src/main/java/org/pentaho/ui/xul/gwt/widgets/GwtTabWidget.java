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

package org.pentaho.ui.xul.gwt.widgets;

import org.pentaho.gwt.widgets.client.utils.ElementUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class GwtTabWidget extends HorizontalPanel {
  private TabPanel tabPanel;
  private Widget tabContent;
  private Label textLabel = new Label();
  private HorizontalPanel panel = new HorizontalPanel();
  private String fullText;

  public GwtTabWidget( String text, String tooltip, final TabPanel tabPanel, final Widget tabContent ) {
    this.tabPanel = tabPanel;
    this.tabContent = tabContent;
    this.fullText = text;
    setVerticalAlignment( HasVerticalAlignment.ALIGN_MIDDLE );
    panel.setStyleName( "pentaho-tabWidget" ); //$NON-NLS-1$
    Image leftCapImage = new Image( GWT.getModuleBaseURL() + "images/spacer.gif" );
    leftCapImage.setStylePrimaryName( "tab-spacer" );

    setLabelText( text );
    setLabelTooltip( tooltip );
    textLabel.setStyleName( "pentaho-tabWidgetLabel" );
    textLabel.setWordWrap( false );
    tabPanel.addSelectionHandler( new SelectionHandler<Integer>() {

      public void onSelection( SelectionEvent<Integer> event ) {
        int tabIndex = event.getSelectedItem();
        ElementUtils.blur( getElement().getParentElement() );
        if ( tabIndex == tabPanel.getWidgetIndex( tabContent ) ) {
          panel.addStyleName( "pentaho-tabWidget-selected" ); //$NON-NLS-1$
        } else {
          panel.removeStyleName( "pentaho-tabWidget-selected" ); //$NON-NLS-1$
        }
      }
    } );

    panel.setVerticalAlignment( HasVerticalAlignment.ALIGN_MIDDLE );
    panel.add( textLabel );
    add( leftCapImage );
    leftCapImage.getElement().getParentElement().setAttribute( "class", "tab-spacer-wrapper" );
    add( panel );
    sinkEvents( Event.ONDBLCLICK | Event.ONMOUSEUP );
  }

  public String getText() {
    return fullText;
  }

  public void setLabelText( String text ) {
    String trimmedText = text.substring( 0, Math.min( 18, text.length() ) );
    if ( !trimmedText.equals( text ) ) {
      trimmedText += "\u2026"; // Ellipsis
    }
    textLabel.setText( trimmedText );
  }

  public void setLabelTooltip( String tooltip ) {
    textLabel.setTitle( tooltip );
  }
}
