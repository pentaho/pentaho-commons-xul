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
 * Copyright (c) 2002-2020 Hitachi Vantara..  All rights reserved.
 */

package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;

public class SwtLabel extends SwtElement implements XulLabel {
  private static final long serialVersionUID = 5202737172518086153L;

  private boolean disabled;
  private String onclick;
  private Link link;
  CLabel cLabel;
  Label label;

  public static final String TAG_NAME = "label";

  public SwtLabel( Element self, XulComponent parent, XulDomContainer container, String tagName ) {
    super( tagName );

    if ( self != null && self.getAttributeValue( "onclick" ) != null ) {
      link = new Link( (Composite) parent.getManagedObject(), SWT.NONE );
      link.addSelectionListener( new SelectionListener() {
        public void widgetSelected( SelectionEvent selectionEvent ) {
          invoke( onclick );
        }

        public void widgetDefaultSelected( SelectionEvent selectionEvent ) {
          invoke( onclick );
        }
      } );
      setManagedObject( link );
    } else {
      String multi = ( self != null ) ? self.getAttributeValue( "multiline" ) : null;
      if ( multi != null && multi.equals( "true" ) ) {
        label = new Label( (Composite) parent.getManagedObject(), SWT.WRAP );
        setManagedObject( label );
      } else {
        cLabel = new CLabel( (Composite) parent.getManagedObject(), SWT.NONE );
        setManagedObject( cLabel );
      }
    }
  }

  /**
   * True parameter for bean-able attribute "value" (XUL attribute)
   *
   * @param text
   */
  public void setValue( String text ) {
    if ( text == null ) {
      text = "";
    }
    if ( link != null ) {
      // Wrap entire text in an anchor tag so it looks like a link. Only do this if an anchor is not provided so we can
      // support something like "Click <a>here</a> for more information."
      if ( !text.contains( "<a>" ) ) {
        text = "<a>" + text + "</a>";
      }
      link.setText( text );
    } else if ( label != null ) {
      label.setText( text );
      if ( getParent() != null ) {
        label.getShell().layout( true );
      }
    } else {
      cLabel.setText( text );
    }
  }

  public String getValue() {
    return link != null ? link.getText() : ( label != null ) ? label.getText() : cLabel.getText();
  }

  /**
   * XUL's attribute is "disabled", thus this acts exactly the opposite of SWT. If the property is not available, then
   * the control is enabled.
   *
   * @return boolean true if the control is disabled.
   */
  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled( boolean disabled ) {
    this.disabled = disabled;
    if ( link != null ) {
      link.setEnabled( !disabled );
    } else if ( label != null ) {
      if ( !label.isDisposed() ) {
        label.setEnabled( !disabled );
      }
    } else {
      cLabel.setEnabled( !disabled );
    }
  }

  public String getOnclick() {
    return onclick;
  }

  public void setOnclick( String onclick ) {
    this.onclick = onclick;
  }
}
