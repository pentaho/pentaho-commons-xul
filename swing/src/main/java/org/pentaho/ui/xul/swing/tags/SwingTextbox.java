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

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.util.TextType;

/**
 * @author nbaker
 * 
 */
public class SwingTextbox extends SwingElement implements XulTextbox {
  private JTextField textField;

  private boolean multiline = false;

  private JTextArea textArea;

  boolean disabled = false;

  private String value = "";

  private JScrollPane scrollPane;

  private static final Log logger = LogFactory.getLog( SwingTextbox.class );

  private boolean readonly = false;

  private TextType type = TextType.NORMAL;

  private JTextComponent textComp = null;

  private String onInput;

  private int min = -1;

  private int max = -1;

  private int maxlength = -1;

  private String oldValue = null;

  public SwingTextbox( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "textbox" );
    setManagedObject( null );
  }

  public String getValue() {
    if ( textComp != null ) {
      return textComp.getText();
    } else {
      logger.error( "Attempt to get Textbox's value before it's instantiated" );
      return null;
    }
  }

  public void setValue( String text ) {
    String oldVal = this.value;
    if ( textComp != null && textComp.getText().equals( text ) ) {
      return;
    }
    if ( textComp != null ) {
      textComp.setText( text );
    }
    this.value = text;
    if ( text != null || oldVal != null ) {
      this.changeSupport.firePropertyChange( "value", oldVal, text );
    }
  }

  @Override
  public void layout() {
  }

  public int getMaxlength() {
    return maxlength;
  }

  public boolean isDisabled() {
    return this.disabled;
  }

  public void setDisabled( boolean dis ) {
    boolean oldDisabled = this.disabled;
    this.disabled = dis;
    if ( textComp != null ) {
      textComp.setEnabled( !dis );
    }
    this.changeSupport.firePropertyChange( "disabled", oldDisabled, dis );
  }

  public void setMaxlength( int length ) {
    maxlength = length;
  }

  public boolean isMultiline() {
    return multiline;
  }

  public void setMultiline( boolean multi ) {
    this.multiline = multi;
  }

  public boolean isReadonly() {
    return readonly;
  }

  public void setReadonly( boolean readOnly ) {
    this.readonly = readOnly;
  }

  public String getType() {
    if ( type == null ) {
      return null;
    }

    return type.toString();
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

  public void selectAll() {
    textComp.selectAll();
  }

  public void setFocus() {

  }

  public Object getTextControl() {
    return getManagedObject();
  }

  @Override
  public int getWidth() {
    if ( width <= 0 ) {
      return 150; // reasonable default
    }
    return width;
  }

  @Override
  public void setWidth( int width ) {
    super.setWidth( width );
    if ( textComp == null ) {
      return;
    }
    Dimension box = new Dimension();
    box.height = this.textComp.getPreferredSize().height;
    box.width = width;
    this.textComp.setMaximumSize( box );
    this.textComp.setPreferredSize( box );
    this.textComp.setMinimumSize( box );
  }

  @Override
  public Object getManagedObject() {
    if ( super.getManagedObject() == null ) {
      switch ( this.type ) {
        case PASSWORD:
          JPasswordField pass = new JPasswordField( ( value != null ) ? value : "" );
          pass.setPreferredSize( new Dimension( getWidth(), pass.getPreferredSize().height ) );
          pass.setMinimumSize( new Dimension( pass.getPreferredSize().width, pass.getPreferredSize().height ) );
          pass.setEditable( !readonly );
          textComp = pass;
          setManagedObject( pass );
          break;
        case NUMERIC:
        default: // regular text
          if ( this.multiline ) {
            textArea = new JTextArea( ( value != null ) ? value : "" );
            scrollPane = new JScrollPane( textArea );
            textComp = textArea;
            setManagedObject( scrollPane );
            textArea.setEditable( !readonly );
            this.scrollPane.setMinimumSize( new Dimension( getWidth(), this.height ) );
          } else {
            textField = new JTextField( ( value != null ) ? value : "" );
            textField.setPreferredSize( new Dimension( getWidth(), textField.getPreferredSize().height ) );
            textField.setMinimumSize( new Dimension( textField.getPreferredSize().width,
                textField.getPreferredSize().height ) );
            textField.setEditable( !readonly );
            setManagedObject( textField );
            textComp = textField;
          }

          // constrin Numeric only here
          if ( this.type == TextType.NUMERIC ) {
            textComp.setDocument( new NumericDocument( min, max ) );
          }
          textComp.setEnabled( !disabled );
          break;
      }

      textComp.addKeyListener( new KeyListener() {
        public void keyPressed( KeyEvent e ) {
          oldValue = textComp.getText();
        }

        public void keyReleased( KeyEvent e ) {
          if ( oldValue != null && !oldValue.equals( textComp.getText() ) ) {
            SwingTextbox.this.changeSupport.firePropertyChange( "value", oldValue, SwingTextbox.this.getValue() );
            oldValue = textComp.getText();
          } else if ( oldValue == null ) {
            // AWT error where sometimes the keyReleased is fired before keyPressed.
            oldValue = textComp.getText();
          } else {
            logger.debug( "Special key pressed, ignoring" );
          }
        }

        public void keyTyped( KeyEvent e ) {
        }

      } );

      textComp.setToolTipText( this.getTooltiptext() );

    }

    return super.getManagedObject();

  }

  public void setOninput( final String method ) {
    if ( textComp != null ) {
      textComp.addKeyListener( new KeyAdapter() {

        @Override
        public void keyReleased( KeyEvent e ) {
          invoke( method );
        }
      } );
    } else { // Not instantiated, save for later
      onInput = method;
    }
  }

  public String getMin() {
    return "" + min;
  }

  public void setMin( String min ) {
    this.min = Integer.parseInt( min );
  }

  public String getMax() {
    return "" + max;
  }

  public void setMax( String max ) {
    this.max = Integer.parseInt( max );
  }

  @SuppressWarnings( "serial" )
  private class NumericDocument extends PlainDocument {
    private int min;

    private int max;

    public NumericDocument( int min, int max ) {
      super();
      this.min = min;
      this.max = max;
    }

    @Override
    public void insertString( int offs, String str, AttributeSet a ) throws BadLocationException {
      if ( str == null ) {
        return;
      }
      if ( !validString( str ) ) {
        logger.error( "Textbox input not a valid number: " + str );
        return;
      }

      // if not special chars, evaluate number for range
      if ( str.charAt( str.length() - 1 ) != '.' && str.charAt( str.length() - 1 ) != '-' ) {

        if ( max > -1 && Double.parseDouble( super.getText( 0, super.getLength() ) + str ) > max ) {
          logger.error( String.format( "Textbox Greater [%f]less than max: %d", Float.parseFloat( super.getText( 0,
              super.getLength() )
              + str ), max ) );
          return;
        }

      }

      // everything checks out insert string
      super.insertString( offs, str, a );

    }

    private boolean validString( String str ) {
      return StringUtils.isNumeric( str.replace( ".", "" ).replace( "-", "" ) ) || str.equals( "-" )
          || str.equals( "." );
    }
  }

  public void setCommand( String command ) {
    throw new RuntimeException( "command not implemented on textbox" );
  }

}
