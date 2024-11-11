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
