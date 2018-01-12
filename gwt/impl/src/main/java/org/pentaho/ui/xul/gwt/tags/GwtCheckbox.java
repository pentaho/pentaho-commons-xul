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

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulCheckbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.stereotype.Bindable;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;

public class GwtCheckbox extends AbstractGwtXulComponent implements XulCheckbox {

  static final String ELEMENT_NAME = "checkbox"; //$NON-NLS-1$
  private String command;
  private boolean checked;

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtCheckbox();
      }
    } );
  }

  private CheckBox checkBox;

  private enum Property {
    ID, CHECKED, DISABLED, COMMAND, CLASS
  }

  public GwtCheckbox() {
    super( ELEMENT_NAME );
    checkBox = new CheckBox();
    setManagedObject( checkBox );
    checkBox.setStylePrimaryName( "checkbox" );
    checkBox.addClickHandler( new ClickHandler() {
      public void onClick( ClickEvent event ) {
        try {
          setChecked( checkBox.getValue() );
          if ( command != null && command.length() > 0 ) {
            GwtCheckbox.this.getXulDomContainer().invoke( command, new Object[] {} );
          }
        } catch ( XulException e ) {
          e.printStackTrace();
        }
      }
    } );
  }

  @Override
  public void setAttribute( String name, String value ) {
    super.setAttribute( name, value );
    try {
      Property prop = Property.valueOf( name.replace( "pen:", "" ).toUpperCase() );
      switch ( prop ) {
        case CHECKED:
          setChecked( "true".equals( value ) );
          break;
        case DISABLED:
          setDisabled( "true".equals( value ) );
          break;
        case COMMAND:
          setCommand( value );
          break;
        case CLASS:
          setClass( value );
          break;
      }
    } catch ( IllegalArgumentException e ) {
      System.out.println( "Could not find Property in Enum for: " + name + " in class" + getClass().getName() );
    }
  }

  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    super.init( srcEle, container );
    setLabel( srcEle.getAttribute( "label" ) );
    setChecked( "true".equals( srcEle.getAttribute( "checked" ) ) );
    setDisabled( "true".equals( srcEle.getAttribute( "disabled" ) ) );
    setCommand( srcEle.getAttribute( "command" ) );
    if ( srcEle.getAttribute( "pen:class" ) != null && srcEle.getAttribute( "pen:class" ).length() > 0 ) {
      setClass( srcEle.getAttribute( "pen:class" ) );
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.components.XulCheckbox#getSelected()
   */
  @Bindable
  public boolean getSelected() {
    return checkBox.getValue();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.components.XulCheckbox#setSelected(boolean)
   */
  @Bindable
  public void setSelected( boolean selected ) {
    checkBox.setValue( selected );
  }

  public void layout() {
    checkBox.setTitle( this.getTooltiptext() );
  }

  @Bindable
  public void setLabel( String label ) {
    checkBox.setText( label );
  }

  @Bindable
  public String getLabel() {
    return checkBox.getText();
  }

  @Bindable
  public boolean isChecked() {
    return checkBox.getValue();

  }

  @Bindable
  public boolean isDisabled() {
    return !checkBox.isEnabled();
  }

  @Bindable
  public void setChecked( boolean checked ) {
    boolean previousVal = this.checked;
    if ( checked != checkBox.getValue() ) {
      checkBox.setValue( checked );
    }
    this.checked = checked;
    this.firePropertyChange( "checked", previousVal, checked );
  }

  @Bindable
  public void setDisabled( boolean dis ) {
    checkBox.setEnabled( !dis );
  }

  public void setCommand( final String command ) {
    this.command = command;
  }

  public String getCommand() {
    return command;
  }

  public void setClass( String className ) {
    checkBox.setStylePrimaryName( className );
  }

  @Override
  public void setTooltiptext( String tooltip ) {
    super.setTooltiptext( tooltip );
    checkBox.setTitle( this.getTooltiptext() );
  }

}
