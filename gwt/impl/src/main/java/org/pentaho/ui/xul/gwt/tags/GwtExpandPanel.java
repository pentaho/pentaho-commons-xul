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

package org.pentaho.ui.xul.gwt.tags;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulExpandPanel;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.stereotype.Bindable;
import org.pentaho.ui.xul.util.Orient;

public class GwtExpandPanel extends AbstractGwtXulContainer implements XulExpandPanel {

  private DisclosurePanel disclosurePanel;
  private String headerText;
  private boolean expanded = false;
  private VerticalPanel contentPanel;
  private Label headerLabel;
  private Image expandedImage;
  private Image collapsedImage;
  private ExpandPanelHeader headerPanel;

  private static String[] SCROLL_CLASSES = new String[] { "label-scroll-panel" };

  public GwtExpandPanel() {
    super( ELEMENT_NAME );
    disclosurePanel = new DisclosurePanel();
    disclosurePanel.setStylePrimaryName( "expand-panel" );

    contentPanel = new VerticalPanel();
    headerLabel = new Label();
    headerLabel.setStylePrimaryName( "expand-panel-header-label" );
    expandedImage = new Image( GWT.getModuleBaseURL() + "images/arrow_open.png" );

    collapsedImage = new Image( GWT.getModuleBaseURL() + "images/arrow_closed.png" );

    headerPanel = new ExpandPanelHeader( collapsedImage, headerLabel );

    disclosurePanel.setContent( contentPanel );
    contentPanel.setStylePrimaryName( "expand-panel-content" );
    disclosurePanel.setHeader( headerPanel );

    disclosurePanel.addOpenHandler( new OpenHandler<DisclosurePanel>() {
      public void onOpen( OpenEvent<DisclosurePanel> disclosurePanelOpenEvent ) {
        disclosurePanel.setHeader( new ExpandPanelHeader( expandedImage, headerLabel ) );
      }
    } );

    disclosurePanel.addCloseHandler( new CloseHandler<DisclosurePanel>() {
      public void onClose( CloseEvent<DisclosurePanel> disclosurePanelCloseEvent ) {
        disclosurePanel.setHeader( new ExpandPanelHeader( collapsedImage, headerLabel ) );
      }
    } );

    orientation = Orient.VERTICAL;
    container = contentPanel;
    setManagedObject( disclosurePanel );
  }

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtExpandPanel();
      }
    } );
  }

  @Bindable
  public void setHeader( String caption ) {
    this.headerText = caption;
    headerLabel.setText( caption );
  }

  public void setExpanded( boolean isExpanded ) {
    expanded = isExpanded;
    disclosurePanel.setOpen( expanded );

  }

  @Bindable
  public void setExpanded( String isExpanded ) {
    setExpanded( Boolean.valueOf( isExpanded ) );
  }

  public boolean isExpanded() {
    return expanded;
  }

  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    super.init( srcEle, container );
    setHeader( srcEle.getAttribute( "header" ) );
    setExpanded( srcEle.getAttribute( "expanded" ) );
  }

  @Bindable
  public void setVisible( boolean visible ) {
    this.visible = visible;

    disclosurePanel.getElement().getStyle().setProperty( "display", ( this.visible ) ? "" : "none" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
  }

}

class ExpandPanelHeader extends HorizontalPanel {
  public ExpandPanelHeader( Image image, Label header ) {
    add( image );
    add( header );
    setStylePrimaryName( "expand-panel-header" );
  }
}
