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
 * Copyright (c) 2002-2023 Hitachi Vantara.  All rights reserved.
 */

package org.pentaho.ui.xul.gwt.tags;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import org.pentaho.gwt.widgets.client.panel.HorizontalFlexPanel;
import org.pentaho.gwt.widgets.client.panel.VerticalFlexPanel;
import org.pentaho.ui.xul.components.XulConfirmBox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.util.XulDialogCallback;

import java.util.ArrayList;
import java.util.List;

public class GwtConfirmBox extends GwtMessageBox implements XulConfirmBox {

  private List<XulDialogCallback<String>> callbacks = new ArrayList<XulDialogCallback<String>>();

  private String acceptLabel = "Yes";

  private String cancelLabel = "No";

  private Button acceptBtn = new Button();
  private Button cancelBtn = new Button();

  static final String ELEMENT_NAME = "confirmbox"; //$NON-NLS-1$

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtConfirmBox();
      }
    } );
  }

  public GwtConfirmBox() {
    super( ELEMENT_NAME );
    // setup default width and height in case user does not specify
    setHeight( 150 );
    setWidth( 275 );

    acceptBtn.addClickListener( new ClickListener() {

      public void onClick( Widget sender ) {
        hide();
        for ( XulDialogCallback<String> callback : callbacks ) {
          callback.onClose( GwtConfirmBox.this, XulDialogCallback.Status.ACCEPT, null );
        }
      }
    } );
    acceptBtn.setStylePrimaryName( "pentaho-button" );

    cancelBtn.addClickListener( new ClickListener() {

      public void onClick( Widget sender ) {
        hide();
        for ( XulDialogCallback<String> callback : callbacks ) {
          callback.onClose( GwtConfirmBox.this, XulDialogCallback.Status.CANCEL, null );
        }
      }
    } );
    cancelBtn.setStylePrimaryName( "pentaho-button" );
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
    vp.add( lbl );
    vp.setCellHorizontalAlignment( lbl, vp.ALIGN_CENTER );
    vp.setCellVerticalAlignment( lbl, vp.ALIGN_MIDDLE );

    return vp;
  }

  public Object[] getButtons() {
    return null;
  }

  public Object getIcon() {
    return null;
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

}
