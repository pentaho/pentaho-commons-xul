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


package org.pentaho.ui.xul.swt.tags;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.action.LegacyActionTools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.XulUtil;

public class SwtMenuitem extends SwtElement implements XulMenuitem {

  private static final Log logger = LogFactory.getLog( SwtMenuitem.class );
  private String onCommand;
  private boolean disabled = false;
  private MenuItem item;
  private XulDomContainer domContainer;
  private XulComponent parent;

  public SwtMenuitem( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "menuitem" );
    this.parent = parent;
    setManagedObject( "empty" );
    this.domContainer = domContainer;
    if ( parent.getManagedObject() != null && parent.getManagedObject() instanceof Menu ) {
      createItem( self, parent, -1 );

    }

  }

  public SwtMenuitem( XulComponent parent, XulDomContainer domContainer, String tagName, int pos ) {
    super( "menuitem" );
    this.parent = parent;
    setManagedObject( "empty" );
    this.domContainer = domContainer;
    if ( parent.getManagedObject() != null && parent.getManagedObject() instanceof Menu ) {
      createItem( null, parent, pos );
    }

  }

  private void createItem( Element self, XulComponent parent, int pos ) {
    int style = SWT.PUSH;
    if ( self != null && self.getAttributeValue( "type" ) != null
        && self.getAttributeValue( "type" ).equals( "checkbox" ) ) {
      style = SWT.CHECK;
    }

    if ( pos > -1 ) {
      item = new MenuItem( (Menu) parent.getManagedObject(), style, pos );
    } else {
      item = new MenuItem( (Menu) parent.getManagedObject(), style );
    }

    item.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( SelectionEvent arg0 ) {
        String command = SwtMenuitem.this.onCommand;
        if ( command != null ) {
          invoke( command );
        }
      }
    } );

    setManagedObject( item );
  }

  private String acceltext = "";
  private String accesskey = "";
  private String label = "";
  private String image = "";
  private boolean selected = false;

  public String getAcceltext() {
    return acceltext;
  }

  public String getAccesskey() {
    return accesskey;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public String getLabel() {
    return label;
  }

  public void setAcceltext( String accel ) {
    this.acceltext = accel;
    setText();
  }

  private void setText() {
    if ( item != null && item.isDisposed() == false ) {
      String text = "";
      if ( this.label != null ) {
        text += this.label;
      }
      text += "\t" + acceltext;
      item.setText( text );
    }
  }

  public void setAccesskey( String accessKey ) {

    if ( item != null && item.isDisposed() == false ) {
      int mask = 0;
      if ( accessKey.indexOf( "ctrl" ) > -1 ) { //$NON-NLS-1$
        mask |= SWT.MOD1;
      }
      if ( accessKey.indexOf( "shift" ) > -1 ) { //$NON-NLS-1$
        mask |= SWT.SHIFT;
      }
      if ( accessKey.indexOf( "alt" ) > -1 ) { //$NON-NLS-1$
        mask |= SWT.ALT;
      }
      if ( accessKey.indexOf( "pageup" ) > -1 ) { //$NON-NLS-1$
        mask |= SWT.PAGE_UP;
      }
      if ( accessKey.indexOf( "pagedown" ) > -1 ) { //$NON-NLS-1$
        mask |= SWT.PAGE_DOWN;
      }
      if ( accessKey.indexOf( "home" ) > -1 ) { //$NON-NLS-1$
        mask |= SWT.HOME;
      }
      if ( accessKey.indexOf( "esc" ) > -1 ) { //$NON-NLS-1$
        mask |= SWT.ESC;
      }
      if ( accessKey.indexOf( "up" ) > -1 && accessKey.indexOf( "pageup" ) == -1 ) { //$NON-NLS-1$
        mask |= SWT.ARROW_UP;
      }
      if ( accessKey.indexOf( "down" ) > -1 && accessKey.indexOf( "pagedown" ) == -1 ) { //$NON-NLS-1$
        mask |= SWT.ARROW_DOWN;
      }
      if ( accessKey.indexOf( "left" ) > -1 ) { //$NON-NLS-1$
        mask |= SWT.ARROW_LEFT;
      }
      if ( accessKey.indexOf( "right" ) > -1 ) { //$NON-NLS-1$
        mask |= SWT.ARROW_RIGHT;
      }

      String remainder =
          accessKey.replaceAll( "ctrl", "" ).replaceAll( "shift", "" ).replaceAll( "alt", "" ).replaceAll( "-", "" )
              .trim();
      if ( remainder.length() == 1 ) {
        mask += remainder.toUpperCase().charAt( 0 );
      } else if ( remainder.length() > 1 && remainder.startsWith( "f" ) ) { //$NON-NLS-1$
        // function key
        mask += LegacyActionTools.findKeyCode( remainder );
      }

      item.setAccelerator( mask );
    }
  }

  public void setDisabled( boolean disabled ) {
    this.disabled = disabled;
    if ( item != null && item.isDisposed() == false ) {
      item.setEnabled( !disabled );
    }
  }

  public void setLabel( String label ) {
    this.label = label;
    setText();
  }

  public String getImage() {
    return image;
  }

  public boolean isSelected() {
    return ( item != null && item.isDisposed() == false ) ? item.getSelection() : selected;
  }

  public void setSelected( boolean val ) {
    selected = val;
    if ( item != null && item.isDisposed() == false ) {
      item.setSelection( this.selected );
    }
  }

  public void setImage( String image ) {
    this.image = image;
    if ( StringUtils.isNotEmpty( image ) ) {
      InputStream in = null;
      try {
        in = XulUtil.loadResourceAsStream( image, domContainer );
        Image img = new Image( item.getDisplay(), in );

        int pixelIndex = img.getImageData().palette.getPixel( new RGB( 255, 255, 255 ) );

        img.getImageData().transparentPixel = pixelIndex;
        Image tempImage = new Image( item.getDisplay(), img.getImageData() );
        img.dispose();
        img = tempImage;

        if ( item != null && item.isDisposed() == false ) {
          item.setImage( img );
        }

      } catch ( FileNotFoundException e ) {
        logger.warn( e );
      } finally {
        try {
          if ( in != null ) {
            in.close();
          }
        } catch ( IOException ignored ) {
        }
      }
    }
  }

  public String getCommand() {
    return this.onCommand;
  }

  public void setCommand( final String command ) {
    this.onCommand = command;
  }

  public String toString() {
    return this.getLabel();
  }

  public void reposition( int position ) {
    int accel = item.getAccelerator();
    item.dispose();
    createItem( this, parent, position );
    setDisabled( isDisabled() );
    this.setImage( getImage() );
    setSelected( isSelected() );
    item.setAccelerator( accel );
    setAcceltext( getAcceltext() );
  }

  public void addSelectionListener( SelectionListener listener ) {
    item.addSelectionListener( listener );
  }
}
