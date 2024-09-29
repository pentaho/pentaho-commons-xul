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

import com.google.gwt.aria.client.Roles;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import org.pentaho.gwt.widgets.client.dialogs.DialogBox;
import org.pentaho.gwt.widgets.client.panel.HorizontalFlexPanel;
import org.pentaho.gwt.widgets.client.panel.VerticalFlexPanel;
import org.pentaho.ui.xul.components.XulMessageBox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.gwt.util.GenericDialog;

public class GwtMessageBox extends GenericDialog implements XulMessageBox {

  private static final String OK = "OK"; //$NON-NLS-1$

  private String message;

  private Object[] defaultButtons = new Object[] { OK };

  private Object[] buttons = defaultButtons;

  static final String ELEMENT_NAME = "messagebox"; //$NON-NLS-1$

  private String acceptLabel = "OK";

  private Button acceptBtn = new Button();

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtMessageBox();
      }
    } );
  }

  public GwtMessageBox() {
    super( ELEMENT_NAME );
    // setup default width in case user does not specify
    setWidth( 250 );

    acceptBtn.addClickListener( new ClickListener() {
      public void onClick( Widget sender ) {
        hide();
      }
    } );

    acceptBtn.setStylePrimaryName( "pentaho-button" );

    // ARIA
    // Override role from "dialog" to "alertdialog".
    setAriaRole( Roles.getAlertdialogRole().getName() );
  }

  protected GwtMessageBox( String elementName ) {
    super( elementName );

    // ARIA
    // Override role from "dialog" to "alertdialog".
    setAriaRole( Roles.getAlertdialogRole().getName() );
  }

  @Override
  protected DialogBox createManagedDialog() {
    DialogBox dialog = super.createManagedDialog();
    dialog.setResponsive( true );
    dialog.setWidthCategory( DialogBox.DialogWidthCategory.TEXT );
    dialog.setMinimumHeightCategory( DialogBox.DialogMinimumHeightCategory.CONTENT );

    return dialog;
  }

  @Override
  public Panel getButtonPanel() {
    HorizontalPanel hp = new HorizontalFlexPanel();
    acceptBtn.setText( this.acceptLabel );
    hp.add( acceptBtn );
    hp.setCellWidth( acceptBtn, "100%" );
    hp.setCellHorizontalAlignment( acceptBtn, hp.ALIGN_RIGHT );
    return hp;
  }

  @Override
  public Panel getDialogContents() {

    VerticalPanel vp = new VerticalFlexPanel();
    String[] lines = message.split( "\n", -1 );
    StringBuffer sb = new StringBuffer();
    for ( String line : lines ) {
      sb.append( line + "<br/>" );
    }
    HTML lbl = new HTML( sb.toString() );
    lbl.addStyleName( "gwt-Label" );
    vp.add( lbl );
    vp.setCellHorizontalAlignment( lbl, vp.ALIGN_CENTER );
    vp.setCellVerticalAlignment( lbl, vp.ALIGN_MIDDLE );
    vp.setSize( "200px", "125px" );

    return vp;
  }

  public Object[] getButtons() {
    return buttons;
  }

  public Object getIcon() {
    return null;
  }

  public String getMessage() {
    return message;
  }

  public int open() {
    show();
    return 0;
  }

  public void setButtons( Object[] buttons ) {
    // Can't have null buttons - accept default
    this.buttons = ( buttons == null ) ? defaultButtons : buttons;
  }

  public void setIcon( Object icon ) {
    // not implemented
  }

  public void setMessage( final String message ) {
    this.message = message;
  }

  public void setModalParent( final Object parent ) {
    // not implemented
  }

  public void setScrollable( final boolean scroll ) {
    // not implemented
  }

  public void setAcceptLabel( String lbl ) {
    this.acceptLabel = lbl;
  }

}
