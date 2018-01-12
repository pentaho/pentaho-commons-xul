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

package org.pentaho.ui.xul.swing.tags;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMessageBox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingMessageBox extends SwingElement implements XulMessageBox {

  // valid button values...
  private static final String OK = "OK";
  private static final String CANCEL = "Cancel";
  private static final String YES = "Yes";
  private static final String NO = "No";
  private static final String CLOSE = "Close";

  private Component parentObject = null;
  private String message = "Default Message";
  private String title = "Message Box";
  private Object[] defaultButtons = new Object[] { OK };
  private Object[] buttons = defaultButtons;
  private Object icon = new Integer( JOptionPane.INFORMATION_MESSAGE );
  private boolean scrollable = false;
  private XulComponent parent;

  private String acceptLabel = "OK";

  public SwingMessageBox( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "messagebox" );
    this.parent = domContainer.getDocumentRoot().getRootElement();
    this.parent.addChild( this );
  }

  public Object[] getButtons() {
    return buttons;
  }

  public Object getIcon() {
    return icon;
  }

  public String getMessage() {
    return message;
  }

  public String getTitle() {
    return title;
  }

  public void setButtons( Object[] buttons ) {
    // Can't have null buttons - accept default
    this.buttons = ( buttons == null ) ? defaultButtons : buttons;
  }

  public void setIcon( Object icon ) {
    this.icon = icon;
  }

  public void setMessage( String message ) {
    this.message = message;
  }

  public void setTitle( String title ) {
    this.title = title;
  }

  public int open() {
    int messageType = ( icon instanceof Integer ) ? ( (Integer) icon ).intValue() : JOptionPane.INFORMATION_MESSAGE;
    Icon imageIcon = ( icon instanceof Icon ) ? (Icon) icon : null;

    Object msgObject = null;

    if ( scrollable ) {
      JScrollPane sp = new JScrollPane( new JTextArea( message ) );
      sp.setPreferredSize( new Dimension( this.getWidth(), this.getHeight() ) );
      msgObject = sp;

    } else {
      msgObject = message;
    }

    return JOptionPane.showOptionDialog( getParentObject(), msgObject, title, JOptionPane.DEFAULT_OPTION, messageType,
        imageIcon, buttons, buttons[0] );
  }

  public void setScrollable( boolean scroll ) {
    this.scrollable = scroll;
  }

  public void setModalParent( Object parent ) {
    parentObject = (Component) parent;
  }

  private Component getParentObject() {
    if ( parentObject != null ) {
      return parentObject;
    } else {
      return (Component) this.parent.getManagedObject();
    }
  }

  public void setAcceptLabel( String label ) {
    this.acceptLabel = label;
  }

}
