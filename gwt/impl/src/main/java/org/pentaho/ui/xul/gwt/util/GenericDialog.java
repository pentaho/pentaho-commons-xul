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
 * Copyright (c) 2002-2023 Hitachi Vantara. All rights reserved.
 */

package org.pentaho.ui.xul.gwt.util;

import com.google.gwt.aria.client.Roles;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.pentaho.gwt.widgets.client.dialogs.DialogBox;
import org.pentaho.gwt.widgets.client.panel.HorizontalFlexPanel;
import org.pentaho.gwt.widgets.client.panel.VerticalFlexPanel;
import org.pentaho.gwt.widgets.client.utils.ElementUtils;
import org.pentaho.gwt.widgets.client.utils.string.StringUtils;
import org.pentaho.ui.xul.gwt.AbstractGwtXulContainer;
import org.pentaho.ui.xul.gwt.tags.GwtVbox;

public abstract class GenericDialog extends AbstractGwtXulContainer {
  protected DialogBox dialog;
  private VerticalPanel contents = new VerticalFlexPanel();
  private String title = "";

  public static final int CANCEL = 0;
  public static final int ACCEPT = 1;
  public static final int EXTRA1 = 2;
  public static final int EXTRA2 = 3;

  // requested height is adjusted by this value.
  private static final int HEADER_HEIGHT = 32;

  private static final String ATTRIBUTE_ARIA_DESCRIBEDBY = "pen:aria-describedby";

  public GenericDialog( String tagName ) {
    super( tagName );

    // Default ARIA role.
    setAriaRole( Roles.getDialogRole().getName() );
  }

  protected DialogBox createManagedDialog() {
    return new DialogBox();
  }

  protected void prepareManagedDialog() {
    dialog.setText( title );
    dialog.setAriaRole( getAriaRole() );

    contents.clear();

    // implement the buttons
    VerticalPanel panel = GwtVbox.createManagedPanel();

    Panel dialogContents = getDialogContents();
    dialogContents.setSize( "100%", "100%" );

    dialogContents.addStyleName( "dialog-content" ); //$NON-NLS-1$

    panel.add( dialogContents );
    panel.setCellHeight( dialogContents, "100%" );
    panel.addStyleName( "dialog" );
    panel.setWidth( "100%" ); //$NON-NLS-1$
    panel.setSpacing( 0 );
    panel.setHeight( "100%" ); //$NON-NLS-1$
    contents.add( panel );
    contents.setCellHeight( panel, "100%" );

    if ( getBgcolor() != null ) {
      dialogContents.getElement().getStyle().setProperty( "backgroundColor", getBgcolor() );
    }

    // ARIA describedBy attribute
    String describedBy = getAriaDescribedBy();
    if ( isAriaRoleAlertDialog() && StringUtils.isEmpty( describedBy ) ) {
      describedBy = ElementUtils.ensureId( dialogContents );
    }

    dialog.setAriaDescribedBy( describedBy );

    Panel buttonPanel = this.getButtonPanel();
    buttonPanel.addStyleName( "inner-button-wrapper" );
    buttonPanel.setWidth( "100%" );

    HorizontalPanel buttonPanelWrapper = new HorizontalFlexPanel();
    buttonPanelWrapper.addStyleName( "button-panel" ); //$NON-NLS-1$
    buttonPanelWrapper.add( buttonPanel );
    buttonPanelWrapper.setWidth( "100%" ); //$NON-NLS-1$
    buttonPanelWrapper.setCellWidth( buttonPanel, "100%" );

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
  }

  public void hide() {
    if ( dialog != null ) {
      dialog.hide();
    }
  }

  public void show() {
    // Instantiation is delayed to prevent errors with the underlying GWT's not being able to calculate available
    // size, in the case that the GWT app has been loaded into an iframe that's not visible.
    if ( dialog == null ) {
      dialog = createManagedDialog();
      dialog.addStyleName( "pentaho-xul-gwt" );
      dialog.addStyleName( "pentaho-xul-" + getName() );
      dialog.setWidget( contents );
    }

    prepareManagedDialog();

    dialog.center();
  }

  public Panel getDialogContents() {
    return null;
  }

  public Panel getButtonPanel() {
    return null;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle( final String title ) {
    this.title = title;
  }

  public boolean isHidden() {
    return dialog == null || !dialog.isShowing();
  }

  public boolean isVisible() {
    return !isHidden();
  }

  protected boolean isAriaRoleAlertDialog() {
    return Roles.getAlertdialogRole().getName().equals( getAriaRole() );
  }

  // region ariaDescribedBy attribute

  /**
   * Gets the identifier of the ARIA description element.
   */
  public String getAriaDescribedBy() {
    return getAttributeValue( ATTRIBUTE_ARIA_DESCRIBEDBY );
  }

  /**
   * Sets the identifier of the ARIA description element.
   *
   * @param describedById The description element identifier.
   */
  public void setAriaDescribedBy( String describedById ) {
    setAttribute( ATTRIBUTE_ARIA_DESCRIBEDBY, describedById );
  }
  // endregion
}
