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

package org.pentaho.ui.xul.gwt.util;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.RootPanel;
import org.pentaho.gwt.widgets.client.dialogs.GlassPane;

import java.util.HashSet;
import java.util.Set;

/**
 * User: RFellows Date: 7/31/13
 */
public class FrameCover {

  private Set<ClickHandler> clickHandlers = new HashSet<ClickHandler>();

  private static FocusPanel frameLid;

  public boolean isCovered() {
    return frameLid != null && frameLid.isVisible();
  }

  public void addClickHandler( ClickHandler handler ) {
    clickHandlers.add( handler );
  }

  public void cover() {
    if ( frameLid == null ) {
      frameLid = new FocusPanel() {
        public void onBrowserEvent( Event event ) {
          int type = event.getTypeInt();
          switch ( type ) {
            case Event.ONKEYDOWN: {
              if ( (char) event.getKeyCode() == KeyCodes.KEY_ESCAPE ) {
                event.stopPropagation();
                fireHandlers();
              }
              return;
            }
          }
          super.onBrowserEvent( event );
        };
      };
      frameLid.addClickHandler( new ClickHandler() {
        public void onClick( ClickEvent event ) {
          fireHandlers();
          frameLid.setVisible( false );
          frameLid.getElement().getStyle().setDisplay( Style.Display.NONE );
        }
      } );

      RootPanel.get().add( frameLid, 0, 0 );
      frameLid.setVisible( true );

      // Used for debugging if frame is covered or not.
      frameLid.addStyleName( "pen-frame-cover" );

      setFrameSize();
      Window.addResizeHandler( event -> setFrameSize() );
    }
    frameLid.getElement().getStyle().setDisplay( Style.Display.BLOCK );
    GlassPane.getInstance().show();
  }

  public void remove() {
    frameLid.getElement().getStyle().setDisplay( Style.Display.NONE );
    GlassPane.getInstance().hide();
  }

  private void fireHandlers() {
    for ( ClickHandler handler : clickHandlers ) {
      handler.onClick( null );
    }
  }

  private void setFrameSize() {
    // get all iFrames on the document
    NodeList<Element> iframes = Document.get().getElementsByTagName( "iframe" );

    int top = Integer.MAX_VALUE;
    int left = Integer.MAX_VALUE;
    int width = 0;
    int height = 0;

    // determine the MAX bounds they encompass
    for ( int i = 0; i < iframes.getLength(); i++ ) {
      Element iframe = iframes.getItem( i );
      if ( iframe.getOffsetWidth() > 0 && iframe.getOffsetHeight() > 0 ) {
        top = Math.min( top, iframe.getAbsoluteTop() );
        left = Math.min( left, iframe.getAbsoluteLeft() );
        width = Math.max( width, iframe.getAbsoluteRight() );
        height = Math.max( height, iframe.getAbsoluteBottom() );
      }
    }

    // set the size/position of the frame cover to that max
    frameLid.getElement().getStyle().setLeft( left, Style.Unit.PX );
    frameLid.getElement().getStyle().setTop( top, Style.Unit.PX );
    frameLid.getElement().getStyle().setWidth( width, Style.Unit.PX );
    frameLid.getElement().getStyle().setHeight( height, Style.Unit.PX );

  }

}
