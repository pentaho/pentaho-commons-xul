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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulRadioGroup;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.stereotype.Bindable;

public class GwtRadioGroup extends GwtVbox implements XulRadioGroup, PropertyChangeListener {

  private static final String NAME = "radiogroup"; //$NON-NLS-1$

  private List<GwtRadio> radios;
  private String value;

  public GwtRadioGroup() {
    super( NAME );
    this.radios = new ArrayList<GwtRadio>();
    GwtRadio.currentGroup = this;
  }

  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer aContainer ) {
    super.init( srcEle, aContainer );
    setDisabled( "true".equals( srcEle.getAttribute( "disabled" ) ) );
    GwtRadio.currentGroup = this;
  }

  public void registerRadio( GwtRadio aRadio ) {
    if ( aRadio.isChecked() ) {
      setValue( aRadio.getValue() );
    }
    aRadio.addPropertyChangeListener( "value", this );
    aRadio.addPropertyChangeListener( "checked", this ); //$NON-NLS-1$
    aRadio.setDisabled( isDisabled() );
    this.radios.add( aRadio );
  }

  @Bindable
  public String getValue() {
    return value;
  }

  @Bindable
  public void setValue( String value ) {
    String prev = this.value;
    this.value = value;
    boolean found = false;
    GwtRadio custom = null;

    if ( prev == null || !prev.equals( value ) ) {
      for ( GwtRadio radio : this.radios ) {
        if ( radio.getValue().equals( value ) ) {
          radio.setChecked( true );
          found = true;
        } else {
          radio.setChecked( false );
        }
        if ( radio.isCustomValue() ) {
          custom = radio;
        }
      }

      // handle the custom value radio ("other")
      if ( custom != null && !found ) {
        custom.setChecked( true );
        custom.setValue( value );
      }

      firePropertyChange( "value", prev, value ); //$NON-NLS-1$
    }
  }

  public static void register() {
    GwtXulParser.registerHandler( NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtRadioGroup();
      }
    } );
  }

  public void replaceChild( Element oldElement, Element newElement ) {
    resetContainer();
    super.replaceChild( oldElement, newElement );
  }

  public void propertyChange( PropertyChangeEvent evt ) {
    Boolean checked = null;
    GwtRadio radio = (GwtRadio) evt.getSource();
    if ( evt.getPropertyName().equals( "checked" ) ) {
      checked = (Boolean) evt.getNewValue();

      if ( !checked && radio.isCustomValue() ) {
        // a custom value radio is becomong unselected, make the other textbox disabled
        radio.disableCustomValueTextBox();
      }

    } else if ( evt.getPropertyName().equals( "value" ) ) {
      checked = true;
    }

    // only care about the radio becoming selected, not the one being unselected
    if ( checked ) {
      // let listeners know that the selected radio has changed
      setValue( radio.getValue() );
    }
  }

  @Bindable
  public void setDisabled( boolean disabled ) {
    boolean prev = isDisabled();
    this.disabled = disabled;
    for ( GwtRadio radio : radios ) {
      radio.setDisabled( disabled );
    }
    super.setDisabled( disabled );
    firePropertyChange( "disabled", prev, disabled );
  }

}
