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

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.pentaho.ui.xul.components.XulCaption;
import org.pentaho.ui.xul.containers.XulEditpanel;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.util.Orient;

/**
 * User: nbaker Date: 5/3/11
 */
public class GwtEditPanel extends AbstractGwtXulContainer implements XulEditpanel {

  static final String ELEMENT_NAME = "editpanel"; //$NON-NLS-1$
  private String type;

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtEditPanel();
      }
    } );
  }

  private FlowPanel outterPanel = new FlowPanel();
  private SimplePanel titlePanel = new SimplePanel();
  private Label title = new Label();
  private VerticalPanel contentPanel = new VerticalPanel();

  public GwtEditPanel() {
    super( ELEMENT_NAME );
    this.orientation = Orient.VERTICAL;
    setManagedObject( outterPanel );
    outterPanel.setStylePrimaryName( "xul-editPanel" );
    titlePanel.setStylePrimaryName( "xul-editPanel-title" );
    contentPanel.setStylePrimaryName( "xul-editPanel-content" );
    title.setStylePrimaryName( "xul-editPanel-title-label" );

    contentPanel.setHeight( "100%" );
    contentPanel.setSpacing( 2 );
    container = contentPanel;
    container.setWidth( "100%" );
    contentPanel.addStyleName( "vbox" );

    outterPanel.add( titlePanel );
    titlePanel.add( title );
    outterPanel.add( contentPanel );
  }

  @Override
  public void addChild( Element element ) {
    if ( element instanceof XulCaption ) {
      setCaption( ( (XulCaption) element ).getLabel() );
    }
    super.addChild( element );
  }

  public void setCaption( String caption ) {
    title.setText( caption );
  }

  public void resetContainer() {

    container.clear();
  }

  public String getType() {
    return type;
  }

  public void setType( String s ) {
    this.type = s;
  }

  public void open() {
    // not implemented
  }
}
