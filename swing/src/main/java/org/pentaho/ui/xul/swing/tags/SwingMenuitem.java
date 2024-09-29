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


package org.pentaho.ui.xul.swing.tags;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingMenuitem extends SwingElement implements XulMenuitem {

  private static final Log logger = LogFactory.getLog( SwingMenuitem.class );
  private JMenuItem menuitem;
  private String image;
  private String onCommand;

  public SwingMenuitem( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "menuitem" );

    menuitem = new JMenuItem();
    setManagedObject( menuitem );

  }

  public String getAcceltext() {
    return String.valueOf( menuitem.getAccelerator().getKeyChar() );
  }

  public String getAccesskey() {
    return String.valueOf( menuitem.getText().charAt( menuitem.getDisplayedMnemonicIndex() ) );
  }

  public boolean isDisabled() {
    return !menuitem.isEnabled();
  }

  public String getLabel() {
    return menuitem.getText();
  }

  public void setAcceltext( String accel ) {
    menuitem.setAccelerator( KeyStroke.getKeyStroke( accel ) );
  }

  public void setAccesskey( String accessKey ) {
    if ( accessKey == null || accessKey.length() == 0 ) {
      menuitem.setMnemonic( 0 );
    } else {
      menuitem.setMnemonic( accessKey.charAt( 0 ) );
    }
  }

  public void setDisabled( boolean disabled ) {
    menuitem.setEnabled( !disabled );
  }

  public void setDisabled( String disabled ) {
    menuitem.setEnabled( !Boolean.parseBoolean( disabled ) );
  }

  public void setLabel( String label ) {
    menuitem.setText( label );
  }

  public String getImage() {
    return image;
  }

  public boolean isSelected() {
    return menuitem.isSelected();
  }

  public void setSelected( boolean selected ) {
    menuitem.setSelected( selected );
  }

  public void setImage( String image ) {
    this.image = image;
  }

  public String getCommand() {
    return this.onCommand;
  }

  public void setCommand( final String command ) {
    this.onCommand = command;
    menuitem.addActionListener( new ActionListener() {
      public void actionPerformed( ActionEvent evt ) {
        invoke( command );
      }
    } );
  }

  @Override
  public String toString() {
    return getLabel();
  }

}
