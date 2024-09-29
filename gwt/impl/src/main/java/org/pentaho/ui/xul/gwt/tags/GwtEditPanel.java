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


package org.pentaho.ui.xul.gwt.tags;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.pentaho.gwt.widgets.client.panel.VerticalFlexPanel;
import org.pentaho.ui.xul.components.XulCaption;
import org.pentaho.ui.xul.containers.XulEditpanel;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.util.Orient;

public class GwtEditPanel extends AbstractGwtXulContainer implements XulEditpanel {
  static final String ELEMENT_NAME = "editpanel";
  private String type;

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, GwtEditPanel::new );
  }

  private Label title = new Label();

  public GwtEditPanel() {
    super( ELEMENT_NAME );
    this.orientation = Orient.VERTICAL;

    FlowPanel outerPanel = new FlowPanel();
    outerPanel.setStylePrimaryName( "xul-editPanel" );

    setManagedObject( outerPanel );

    SimplePanel titlePanel = new SimplePanel();
    titlePanel.setStylePrimaryName( "xul-editPanel-title" );
    outerPanel.add( titlePanel );

    title.setStylePrimaryName( "xul-editPanel-title-label" );
    titlePanel.add( title );

    VerticalPanel contentPanel = new VerticalFlexPanel();
    contentPanel.addStyleName( "xul-editPanel-content" );
    contentPanel.addStyleName( "vbox" );

    contentPanel.setHeight( "100%" );
    contentPanel.setSpacing( 2 );
    contentPanel.setWidth( "100%" );

    container = contentPanel;
    outerPanel.add( contentPanel );
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

  public void setType( String type ) {
    this.type = type;
  }

  public void open() {
    // not implemented
  }
}
