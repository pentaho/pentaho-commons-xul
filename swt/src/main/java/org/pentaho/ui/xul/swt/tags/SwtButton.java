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

import java.util.WeakHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.Direction;
import org.pentaho.ui.xul.util.SwtXulUtil;

public class SwtButton extends SwtElement implements XulButton {
  private static final long serialVersionUID = -7218075117194366698L;

  protected org.eclipse.swt.widgets.Button button;
  private String label;
  private boolean disabled;
  private String image;
  private String disabledImage;
  private String selectedImage;
  private Direction dir;
  private Type type;
  private String group;
  private String onclick;
  private boolean selected;
  private XulComponent parent;
  private XulDomContainer domContainer;
  private Label imageButton;
  private static WeakHashMap<Display, Cursor> cursorMap = new WeakHashMap<Display, Cursor>();

  public SwtButton( Button button ) {
    super( "button" );
    this.button = button;
    button.addSelectionListener( new SelectionAdapter() {
      public void widgetSelected( org.eclipse.swt.events.SelectionEvent arg0 ) {
        invoke( onclick );
      }
    } );
    setManagedObject( this.button );
  }

  public SwtButton( Element self, XulComponent parent, XulDomContainer container, String tagName ) {
    super( tagName );
    this.parent = parent;
    this.domContainer = container;

    // Extract custom parameters
    if ( self != null ) {
      disabledImage = self.getAttributeValue( "disabledimage" ); //$NON-NLS-1$
      selectedImage = self.getAttributeValue( "selectedimage" ); //$NON-NLS-1$
      image = self.getAttributeValue( "image" ); //$NON-NLS-1$
      label = self.getAttributeValue( "label" ); //$NON-NLS-1$
    }
    // Special creation path for image buttons with no text. We don't want them to appear with the
    // traditional button border.
    if ( image != null && label == null ) {
      setManagedObject( createImageButton() );
    } else {
      button = createNewButton( (Composite) parent.getManagedObject() );
      setManagedObject( button );
    }
  }

  private Control createImageButton() {
    imageButton = new Label( ( (Composite) parent.getManagedObject() ), SWT.NONE );
    imageButton.addMouseListener( new MouseAdapter() {

      @Override
      public void mouseUp( MouseEvent arg0 ) {
        if ( !disabled && onclick != null ) {
          // PDI-14535: onclick can be null when menu assigned to control
          invoke( onclick );
        }
      }

    } );
    Display display = ( (Composite) parent.getManagedObject() ).getDisplay();
    Cursor cursor = cursorMap.get( display );
    if ( cursor == null ) {
      cursor = new Cursor( ( (Composite) parent.getManagedObject() ).getDisplay(), SWT.CURSOR_HAND );
      cursorMap.put( display, cursor );
    }
    imageButton.setCursor( cursor );

    return imageButton;

  }

  public void setButton( Button button ) {
    this.button = button;
    button.addSelectionListener( new SelectionAdapter() {
      public void widgetSelected( org.eclipse.swt.events.SelectionEvent arg0 ) {
        invoke( onclick );
      }
    } );
    setManagedObject( button );
    setVisible( isVisible() );
    // set the enabled state too
    button.setEnabled( !isDisabled() );
  }

  protected Button createNewButton( Composite parent ) {
    Button button = new Button( parent, SWT.NONE );
    button.addSelectionListener( new SelectionAdapter() {
      public void widgetSelected( org.eclipse.swt.events.SelectionEvent arg0 ) {
        invoke( onclick );
      }
    } );
    return button;
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
    if ( button != null ) {
      if ( button.isDisposed() ) {
        return;
      }
      button.setEnabled( !disabled );
    } else {
      if ( imageButton.isDisposed() ) {
        return;
      }
      this.imageButton.setEnabled( !disabled );
    }

    if ( this.disabled ) {
      displayImage( disabledImage );
    } else {
      displayImage( image );
    }
  }

  public void setOnclick( final String method ) {
    this.onclick = method;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel( String label ) {
    this.label = label;
    if ( button != null ) {
      button.setText( label );
    } else {
      imageButton.setText( label );
    }
  }

  public String getImage() {
    return this.image;
  }

  public void setImage( String src ) {
    this.image = src;
    displayImage( src );
  }

  public String getDisabledImage() {
    return this.disabledImage;
  }

  public void setDisabledImage( String src ) {
    this.disabledImage = src;
  }

  public String getDir() {
    return dir.toString().toLowerCase();
  }

  public void setDir( String dir ) {
    this.dir = Direction.valueOf( dir.toUpperCase() );
  }

  public String getGroup() {
    return this.group;
  }

  public void setGroup( String group ) {
    this.group = group;
  }

  public String getType() {
    return this.type.toString();
  }

  public void setType( String type ) {
    this.type = Type.valueOf( type.toUpperCase() );
  }

  public String getOnclick() {
    return this.onclick;
  }

  public void setSelected( String selected ) {
    setSelected( Boolean.parseBoolean( selected ) );
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected( boolean selected ) {
    this.selected = selected;
    if ( button != null ) {
      button.setSelection( this.selected );
    }

    if ( this.selected ) {
      displayImage( selectedImage );
    } else {
      displayImage( image );
    }
  }

  public void doClick() {
    if ( button != null ) {
      button.setSelection( true );
    } else if ( onclick != null ) {
      invoke( onclick );
    }
  }

  protected void displayImage( String src ) {
    if ( src == null ) {
      return;
    }

    Display d = ( (Composite) parent.getManagedObject() ).getDisplay();
    if ( d == null ) {
      d = Display.getCurrent() != null ? Display.getCurrent() : Display.getDefault();
    }

    Image img = SwtXulUtil.getCachedImage( src, domContainer, d );
    if ( img != null ) {
      if ( button != null ) {
        button.setImage( img );
      } else { // image button implementation
        imageButton.setImage( img );
      }
    }
  }
}
