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

import org.pentaho.gwt.widgets.client.menuitem.PentahoMenuItem;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.containers.XulMenupopup;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.stereotype.Bindable;

import com.google.gwt.user.client.Command;

public class GwtMenuitem extends AbstractGwtXulComponent implements XulMenuitem {

  static final String ELEMENT_NAME = "menuitem"; //$NON-NLS-1$

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtMenuitem();
      }
    } );
  }

  private String image;
  private String jscommand;
  private String command;
  private PentahoMenuItem menuitem;
  private boolean isSelected;

  public GwtMenuitem() {
    super( ELEMENT_NAME );
    menuitem = new PentahoMenuItem( "blank", (Command) null );
    setManagedObject( menuitem );
  }

  public GwtMenuitem( XulMenupopup popup ) {
    this();

  }

  @Override
  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    super.init( srcEle, container );
    String type = srcEle.getAttribute( "type" );
    if ( "checkbox".equalsIgnoreCase( type ) ) {
      boolean checked = "true".equals( srcEle.getAttribute( "checked" ) );
      menuitem.setUseCheckUI( true );
      menuitem.setChecked( checked );
    }
    this.setLabel( srcEle.getAttribute( "label" ) );
    this.setCommand( srcEle.getAttribute( "command" ) );
    this.setJscommand( srcEle.getAttribute( "js-command" ) );
    this.setDisabled( "true".equals( srcEle.getAttribute( "disabled" ) ) );
  }

  public String getAcceltext() {
    return null;
  }

  public String getAccesskey() {
    return null;
  }

  public boolean isDisabled() {
    return !menuitem.isEnabled();
  }

  @Bindable
  public boolean isChecked() {
    return menuitem.isChecked();
  }

  @Bindable
  public void setVisible( boolean visible ) {
    this.visible = visible;
    menuitem.getElement().getStyle().setProperty( "display", ( this.visible ) ? "" : "none" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
  }

  public String getLabel() {
    return menuitem.getText();
  }

  public void setAcceltext( String accel ) {
    throw new RuntimeException( "Not implemented" );
  }

  public void setAccesskey( String accessKey ) {
    throw new RuntimeException( "Not implemented" );
  }

  public void setDisabled( boolean disabled ) {
    menuitem.setEnabled( !disabled );
  }

  public void setDisabled( String disabled ) {
    menuitem.setEnabled( "false".equals( disabled ) );
  }

  @Bindable
  public void setChecked( boolean checked ) {
    menuitem.setUseCheckUI( true );
    menuitem.setChecked( checked );
  }

  public void setChecked( String checked ) {
    menuitem.setUseCheckUI( true );
    menuitem.setChecked( "true".equals( checked ) );
  }

  public void setLabel( String label ) {
    menuitem.setText( label );
  }

  public String getImage() {
    return image;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected( boolean selected ) {
    this.isSelected = selected;
  }

  public void setImage( String image ) {
    this.image = image;
  }

  public String getCommand() {
    return this.command;
  }

  public void setCommand( final String command ) {
    this.command = command;
    if ( command != null ) {
      menuitem.setCommand( new Command() {
        public void execute() {
          invoke( command );
        }
      } );
    }
  }

  public String getJscommand() {
    return jscommand;
  }

  public void setJscommand( String jscommand ) {
    this.jscommand = jscommand;
    if ( jscommand != null ) {
      menuitem.setCommand( new Command() {
        public void execute() {
          executeJS( GwtMenuitem.this.jscommand );
        }
      } );
    }
  }

  @Override
  public String toString() {
    return getLabel();
  }

}
