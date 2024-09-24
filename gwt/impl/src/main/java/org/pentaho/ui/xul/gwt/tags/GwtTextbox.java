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

package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.gwt.widgets.client.utils.string.StringUtils;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;
import org.pentaho.ui.xul.stereotype.Bindable;
import org.pentaho.ui.xul.util.TextType;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

public class GwtTextbox extends AbstractGwtXulComponent implements XulTextbox {

  static final String ELEMENT_NAME = "textbox"; //$NON-NLS-1$

  protected String max, min, oninput, onblur, oncommand;
  protected TextType type = TextType.NORMAL;
  protected boolean readonly;
  protected boolean multiline = false;
  private Integer rows;
  private Integer cols = -1;
  private String value;

  private enum Property {
    ID, ROWS, COLS, VALUE, DISABLED
  }

  public static void register() {
    GwtXulParser.registerHandler( ELEMENT_NAME, new GwtXulHandler() {
      public Element newInstance() {
        return new GwtTextbox();
      }
    } );
  }

  private TextBoxBase textBox;

  public GwtTextbox() {
    super( ELEMENT_NAME );
    textBox = new TextBox();
    initManagedObject( textBox );

    // Firefox 2 and sometimes 3 fails to render cursors in Textboxes if they're contained in absolutely
    // positioned div's, such as when they're in dialogs. The workaround is to wrap the <input> in a div
    // with overflow: auto;
    setManagedObject( textBox );

    // textBox.setPreferredSize(new Dimension(150,18));
  }

  @Override
  public void setAttribute( String name, String value ) {
    super.setAttribute( name, value );
    try {
      Property prop = Property.valueOf( name.replace( "pen:", "" ).toUpperCase() );
      switch ( prop ) {

        case ROWS:
          setRows( Integer.valueOf( value ) );
          break;
        case COLS:
          setCols( Integer.valueOf( value ) );
          break;
        case VALUE:
          setValue( value );
          break;
        case DISABLED:
          setDisabled( "true".equals( value ) );
          break;
      }
    } catch ( IllegalArgumentException e ) {
      System.out.println( "Could not find Property in Enum for: " + name + " in class" + getClass().getName() );
    }
  }

  public void init( com.google.gwt.xml.client.Element srcEle, XulDomContainer container ) {
    super.init( srcEle, container );
    setValue( srcEle.getAttribute( "value" ) );
    if ( StringUtils.isEmpty( srcEle.getAttribute( "disabled" ) ) == false ) {
      setDisabled( "true".equals( srcEle.getAttribute( "disabled" ) ) );
    }
    setMultiline( "true".equals( srcEle.getAttribute( "multiline" ) ) );
    setRows( getInt( srcEle.getAttribute( "rows" ) ) );
    setCols( getInt( srcEle.getAttribute( "cols" ) ) );
    setOnblur( srcEle.getAttribute( "onblur" ) );
    setOncommand( srcEle.getAttribute( "oncommand" ) );
  }

  public Integer getInt( String val ) {
    if ( val != null ) {
      try {
        return Integer.parseInt( val );
      } catch ( Exception e ) {
        //ignore
      }
    }
    return null;
  }

  @Bindable
  public String getValue() {
    return value;
  }

  @Bindable
  public void setValue( String text ) {
    String prevVal = this.value;
    this.value = text;
    if ( textBox.getText().equals( text ) == false ) {
      textBox.setText( text );
    }
    this.firePropertyChange( "value", prevVal, text );
  }

  // TODO: this double initialization is not good. Any values previously set will be lost in a second layout
  // move to local variables if this late binding is really needed and take advantage of the new onDomReady event
  // to late bind instead of using layout.
  public void layout() {
    String typeString = this.getAttributeValue( "type" );
    if ( typeString != null && typeString.length() > 0 ) {
      setType( typeString );
    }
    switch ( this.type ) {
      case PASSWORD:
        textBox = new PasswordTextBox();
        initManagedObject( textBox );
        break;
      case NUMERIC:
      default: // regular text
        if ( multiline ) {
          textBox = new TextArea();
          initManagedObject( textBox );

          if ( cols != null && cols > -1 ) {
            ( (TextArea) textBox ).setCharacterWidth( cols );

          }
          if ( rows != null && rows > -1 ) {
            ( (TextArea) textBox ).setVisibleLines( rows );
          }

        }
        break;
    }
    setManagedObject( textBox );
    if ( this.getWidth() > 0 ) {
      textBox.setWidth( this.getWidth() + "px" );
    } else {
      textBox.setWidth( "100%" );
    }
    if ( this.getHeight() > 0 ) {
      textBox.setHeight( this.getHeight() + "px" );
    }
    textBox.setText( getValue() );
    textBox.setEnabled( !this.isDisabled() );
    setupListeners();
  }

  private void initManagedObject( Widget widget ) {
    String stylePrimaryName = widget.getStylePrimaryName();
    widget.setStylePrimaryName( "xul-textbox" );
    if ( !StringUtils.isEmpty( stylePrimaryName ) ) {
      widget.addStyleName( stylePrimaryName );
    }
    widget.addStyleName( "flex-item-h" );
  }

  @SuppressWarnings( "deprecation" )
  private void setupListeners() {
    textBox.addKeyboardListener( new KeyboardListener() {

      public void onKeyDown( Widget sender, char keyCode, int modifiers ) {
        if ( onblur != null && !onblur.equalsIgnoreCase( "" ) ) { //$NON-NLS-1$
          if ( keyCode == KeyboardListener.KEY_TAB ) {
            Event.getCurrentEvent().cancelBubble( true );
            Event.getCurrentEvent().preventDefault();

            try {
              GwtTextbox.this.getXulDomContainer().invoke( GwtTextbox.this.getOnblur(), new Object[] {} );
            } catch ( XulException e ) {
              e.printStackTrace();
            }
          }
        }
      }

      public void onKeyPress( Widget sender, char keyCode, int modifiers ) {
      }

      public void onKeyUp( Widget sender, char keyCode, int modifiers ) {
        setValue( textBox.getText() );
      }

    } );

    textBox.addBlurHandler( new BlurHandler() {
      public void onBlur( BlurEvent event ) {
        if ( onblur != null && !onblur.equalsIgnoreCase( "" ) ) { //$NON-NLS-1$
          try {
            GwtTextbox.this.getXulDomContainer().invoke( GwtTextbox.this.getOnblur(), new Object[] {} );
          } catch ( XulException e ) {
            e.printStackTrace();
          }
        }
      }
    } );

  }

  public void setOncommand( String method ) {
    this.oncommand = method;
  }

  public String getOncommand() {
    return oncommand;
  }

  public int getMaxlength() {
    return 0;
  }

  @Bindable
  public boolean isDisabled() {
    return disabled;
  }

  @Bindable
  public void setDisabled( boolean dis ) {
    this.disabled = dis;
    textBox.setEnabled( !dis );
  }

  public void setMaxlength( int length ) {
  }

  public boolean isMultiline() {
    return false;
  }

  public void setMultiline( boolean multi ) {
    this.multiline = multi;
  }

  public String getMax() {
    return min;
  }

  public String getMin() {
    return min;
  }

  public Object getTextControl() {
    return getManagedObject();
  }

  public String getType() {
    if ( type == null ) {
      return null;
    }

    return type.toString();
  }

  public boolean isReadonly() {
    return readonly;
  }

  public void selectAll() {
    // TODO Auto-generated method stub
  }

  public void setFocus() {
    this.textBox.setFocus( true );
  }

  public void setMax( String max ) {
    this.max = max;
  }

  public void setMin( String min ) {
    this.min = min;
  }

  public void setOninput( String method ) {
    this.oninput = method;
  }

  public String getOnblur() {
    return this.onblur;
  }

  public void setOnblur( String method ) {
    this.onblur = method;
  }

  public void setReadonly( boolean readOnly ) {
    this.readonly = readOnly;
  }

  public void setType( String type ) {
    if ( type == null ) {
      return;
    }
    setType( TextType.valueOf( type.toUpperCase() ) );
  }

  public void setType( TextType type ) {
    this.type = type;
  }

  void setRows( Integer rows ) {
    this.rows = rows;
  }

  Integer getRows() {
    return rows;
  }

  void setCols( Integer cols ) {
    this.cols = cols;
  }

  Integer getCols() {
    return cols;
  }

  public void setCommand( String command ) {
    throw new RuntimeException( "command not implemented on textbox" );
  }
}
