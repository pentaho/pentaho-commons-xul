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

package org.pentaho.ui.xul.gwt.util;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.pentaho.gwt.widgets.client.dialogs.GlassPane;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;

public abstract class GenericDialog extends AbstractGwtXulContainer {

  private SimplePanel glasspane = new SimplePanel();
  protected DialogBox dialog;
  private VerticalPanel contents = new VerticalPanel();
  private String title = "";

  public static final int CANCEL = 0;
  public static final int ACCEPT = 1;
  public static final int EXTRA1 = 2;
  public static final int EXTRA2 = 3;
  private static int dialogPos = 1100;

  // requested height is adjusted by this value.
  private static final int HEADER_HEIGHT = 32;

  public GenericDialog( String tagName ) {
    super( tagName );

    glasspane.setStyleName( "glasspane" );
    Style glassPaneStyle = glasspane.getElement().getStyle();
    glassPaneStyle.setProperty( "width", "100%" );
    glassPaneStyle.setProperty( "height", "100%" );
    glassPaneStyle.setProperty( "display", "block" );

  }

  private void createDialog() {
    dialog = new DialogBox() {
      @Override
      public void hide() {
        // User may press the "ESC" key, invoking this code
        super.hide();
        RootPanel.get().remove( glasspane );
        GlassPane.getInstance().hide();
      }
    };
    dialog.add( contents );
    dialog.setStylePrimaryName( "pentaho-dialog" );

  }

  public void hide() {
    if ( dialog != null ) {
      dialog.hide();
    }
  }

  public void show() {
    // Instantiation if delayed to prevent errors with the underlying GWT's not being able to calculate available
    // size
    // in the case that the GWT app has been loaded into an iframe that's not visible.
    if ( dialog == null ) {
      createDialog();
    }
    dialog.setText( title );

    contents.clear();

    // implement the buttons
    VerticalPanel panel = new VerticalPanel();

    Panel p = getDialogContents();
    p.setSize( "100%", "100%" );

    p.setStyleName( "dialog-content" ); //$NON-NLS-1$

    panel.add( p );
    panel.setCellHeight( p, "100%" );
    panel.setStyleName( "dialog" ); //$NON-NLS-1$
    panel.setWidth( "100%" ); //$NON-NLS-1$
    panel.setSpacing( 0 );
    panel.setHeight( "100%" ); //$NON-NLS-1$
    contents.add( panel );
    contents.setCellHeight( panel, "100%" );

    if ( getBgcolor() != null ) {
      p.getElement().getStyle().setProperty( "backgroundColor", getBgcolor() );
    }

    p = this.getButtonPanel();
    p.setWidth( "100%" );
    HorizontalPanel buttonPanelWrapper = new HorizontalPanel();
    buttonPanelWrapper.setStyleName( "button-panel" ); //$NON-NLS-1$
    buttonPanelWrapper.add( p );
    buttonPanelWrapper.setWidth( "100%" ); //$NON-NLS-1$
    buttonPanelWrapper.setCellWidth( p, "100%" );
    contents.add( buttonPanelWrapper );

    contents.setWidth( "100%" ); //$NON-NLS-1$
    contents.setHeight( "100%" ); //$NON-NLS-1$

    if ( getWidth() > 0 ) {
      contents.setWidth( getWidth() + "px" ); //$NON-NLS-1$
    }
    if ( getHeight() > 0 ) {
      int offsetHeight = getHeight() - HEADER_HEIGHT;
      contents.setHeight( offsetHeight + "px" ); //$NON-NLS-1$
    }
    dialog.center();
    dialog.show();

    // Show glasspane element
    RootPanel.get().add( glasspane );

    // Notify GlassPane listeners
    GlassPane.getInstance().show();

    glasspane.getElement().getStyle().setProperty( "zIndex", "" + ( GenericDialog.dialogPos ) ); //$NON-NLS-1$
    dialog.getElement().getStyle().setProperty( "zIndex", "" + ( ++GenericDialog.dialogPos ) ); //$NON-NLS-1$

  }

  public Panel getDialogContents() {
    return null;
  }

  public Panel getButtonPanel() {
    return null;
  }

  public void setTitle( final String title ) {
    this.title = title;
  }

  public boolean isHidden() {
    return dialog == null || !dialog.isVisible();
  }

  public boolean isVisible() {
    return !isHidden();
  }

}
