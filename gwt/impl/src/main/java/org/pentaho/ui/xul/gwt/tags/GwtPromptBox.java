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
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import org.pentaho.gwt.widgets.client.panel.HorizontalFlexPanel;
import org.pentaho.gwt.widgets.client.panel.VerticalFlexPanel;
import org.pentaho.ui.xul.components.XulPromptBox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.util.XulDialogCallback;

import java.util.ArrayList;
import java.util.List;

public class GwtPromptBox extends GwtMessageBox implements XulPromptBox {

  private TextBox textbox = new TextBox();

  private List<XulDialogCallback<String>> callbacks = new ArrayList<XulDialogCallback<String>>();

  private String acceptLabel = "OK";

  private String cancelLabel = "Cancel";

  private Button acceptBtn = new Button();
  private Button cancelBtn = new Button();

  static final String ELEMENT_NAME = "promptbox"; //$NON-NLS-1$

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtPromptBox();
      }
    } );
  }

  public GwtPromptBox() {
    super( ELEMENT_NAME );

    // ARIA
    // Revert back role to "dialog".
    setAriaRole( Roles.getDialogRole().getName() );

    textbox.setWidth( "90%" );
    textbox.addKeyDownHandler( new KeyDownHandler() {
      public void onKeyDown( KeyDownEvent event ) {
        final int keyCode = event.getNativeKeyCode();

        switch ( keyCode ) {
          case KeyCodes.KEY_ENTER:
            notifyListenersOnAccept();
            break;
          case KeyCodes.KEY_ESCAPE:
            notifyListenersOnCancel();
            break;
        }

      }
    } );

    // setup default width and height in case user does not specify
    setHeight( 150 );
    setWidth( 275 );

    acceptBtn.addClickListener( new ClickListener() {

      public void onClick( Widget sender ) {
        notifyListenersOnAccept();
      }
    } );
    acceptBtn.setStylePrimaryName( "pentaho-button" );

    cancelBtn.addClickListener( new ClickListener() {

      public void onClick( Widget sender ) {
        notifyListenersOnCancel();
      }
    } );
    cancelBtn.setStylePrimaryName( "pentaho-button" );
  }

  private void notifyListenersOnAccept() {
    hide();
    for ( XulDialogCallback<String> callback : callbacks ) {
      callback.onClose( GwtPromptBox.this, XulDialogCallback.Status.ACCEPT, textbox.getText() );
    }
  }

  private void notifyListenersOnCancel() {
    hide();
    for ( XulDialogCallback<String> callback : callbacks ) {
      callback.onClose( GwtPromptBox.this, XulDialogCallback.Status.CANCEL, null );
    }
  }

  @Override
  public Panel getButtonPanel() {
    acceptBtn.setText( acceptLabel );
    cancelBtn.setText( cancelLabel );

    HorizontalPanel hp = new HorizontalFlexPanel();
    hp.add( acceptBtn );
    hp.setCellWidth( acceptBtn, "100%" );
    hp.setCellHorizontalAlignment( acceptBtn, hp.ALIGN_RIGHT );
    hp.add( cancelBtn );
    return hp;
  }

  @Override
  public Panel getDialogContents() {

    VerticalPanel vp = new VerticalFlexPanel();
    Label lbl = new Label( getMessage() );
    lbl.addStyleName( "promptBoxLabel" );
    vp.add( lbl );
    vp.add( textbox );
    vp.setCellHorizontalAlignment( textbox, vp.ALIGN_CENTER );
    vp.setCellVerticalAlignment( lbl, vp.ALIGN_BOTTOM );
    return vp;
  }

  public void setAcceptLabel( String lbl ) {
    this.acceptLabel = lbl;
  }

  public void setCancelLabel( String lbl ) {
    this.cancelLabel = lbl;
  }

  public void addDialogCallback( XulDialogCallback callback ) {
    this.callbacks.add( callback );
  }

  public void removeDialogCallback( XulDialogCallback callback ) {
    this.callbacks.remove( callback );
  }

  public String getValue() {
    return this.textbox.getValue();
  }

  public void setValue( String value ) {
    this.textbox.setValue( value );
  }

  @Override
  public int open() {
    super.show();
    textbox.setFocus( true );
    return 0;
  }

}
