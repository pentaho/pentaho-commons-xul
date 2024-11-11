/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.dnd.DropEvent;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.TextType;

public class SwtTextbox extends SwtElement implements XulTextbox {

  private static final long serialVersionUID = 4928464432190672877L;

  protected org.eclipse.swt.widgets.Text textBox;
  protected Composite parentComposite = null;
  private boolean disabled = false;
  private boolean multiLine = false;
  private boolean readOnly = false;
  private int maxLength;
  private String text;
  protected TextType type = TextType.NORMAL;
  private int max;
  private int min;
  private String oldValue = "";
  private String command;

  public SwtTextbox( Element self, XulComponent parent, XulDomContainer container, String tagName ) {
    super( tagName );
    parentComposite = (Composite) parent.getManagedObject();
    textBox = createNewText();
    setManagedObject( textBox );
  }

  public Text createNewText() {
    textBox = new org.eclipse.swt.widgets.Text( parentComposite, SWT.BORDER );
    addKeyListener( textBox );
    return textBox;
  }

  protected void addKeyListener( final org.eclipse.swt.widgets.Text box ) {
    if ( box == null ) {
      return;
    }
    box.addModifyListener( new ModifyListener() {

      public void modifyText( ModifyEvent arg0 ) {
        if ( !oldValue.equals( getValue() ) ) {
          changeSupport.firePropertyChange( "value", null, getValue() );
          oldValue = box.getText();
        }
      }

    } );
    box.addListener( SWT.DefaultSelection, new Listener() {
      public void handleEvent( Event e ) {
        if ( command != null ) {
          invoke( command );
        }
      }
    } );
  }

  /**
   * @param text
   */
  public void setValue( String text ) {
    if ( text == null ) {
      text = "";
    }
    // the check needs to be here for the following scenario:
    // xul def has a default text value and isMultiLine -
    // the textbox will be re-created, but the text isn't reset,
    // leaving the default state of the textBox text value wrong.
    // Run the getValue() test of the SwtTextboxTest for validation.
    if ( ( this.text == null ) || ( !this.text.equals( text ) )
        || ( ( !textBox.isDisposed() ) && ( !text.equals( textBox.getText() ) ) ) ) {
      this.text = text;
      if ( ( !textBox.isDisposed() ) ) {
        int pos = textBox.getCaretPosition();
        textBox.setText( text );
        textBox.setSelection( pos );
      }
    }
  }

  public String getValue() {
    if ( !textBox.isDisposed() ) {
      text = textBox.getText();
    }
    return text;
  }

  public boolean isDisabled() {
    if ( !textBox.isDisposed() ) {
      disabled = !textBox.isEnabled();
    }
    return disabled;
  }

  public void setDisabled( boolean disabled ) {
    this.disabled = disabled;
    if ( !textBox.isDisposed() ) {
      textBox.setEnabled( !disabled );
    }
  }

  /**
   * @return int The maximum number of characters that the textbox allows to be entered.
   */
  public int getMaxlength() {
    return maxLength;
  }

  public void setMaxlength( int length ) {
    this.maxLength = length;
    if ( ( !textBox.isDisposed() ) && ( maxLength > 0 ) ) {
      textBox.setTextLimit( maxLength );
    }
  }

  public boolean isMultiline() {

    return multiLine;
  }

  public void setMultiline( boolean multi ) {

    if ( multi == multiLine ) {
      return; // nothing has changed...
    }
    multiLine = multi;
    textBox.dispose();
  }

  @Override
  /**
   * Override here because the multiline attribute requires new 
   * construction; We can't ever guarantee the order of the setters, and
   * I think this is the most efficient way to reconstruct the textBox... 
   * May fall down if there are other ways to get to the managed object. 
   */
  public Object getManagedObject() {
    if ( textBox.isDisposed() ) {
      int style = isMultiline() ? SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL : SWT.BORDER;
      textBox = new org.eclipse.swt.widgets.Text( parentComposite, style );
      setDisabled( disabled );
      setMaxlength( maxLength );
      setValue( text );
      setReadonly( readOnly );
      setType( type );
      setManagedObject( textBox );
      addKeyListener( textBox );
    }
    return super.getManagedObject();
  }

  public boolean isReadonly() {
    if ( !textBox.isDisposed() ) {
      readOnly = !textBox.getEditable();
    }
    return readOnly;
  }

  public void setReadonly( boolean readOnly ) {
    this.readOnly = readOnly;
    if ( !textBox.isDisposed() ) {
      textBox.setEditable( !readOnly );
    }
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
    if ( this.type == null ) {
      return;
    }
    if ( !textBox.isDisposed() ) {

      switch ( this.type ) {
        case PASSWORD:
          textBox.setEchoChar( '*' );
          break;
        default:
          // TODO log not implemented yet for autocomplete, number, timed
      }
    }

  }

  public void selectAll() {
    textBox.selectAll();
  }

  public void setFocus() {
    textBox.setFocus();
  }

  public Object getTextControl() {
    return getManagedObject();
  }

  public void setOninput( String method ) {
    // throw new NotImplementedException();
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

  @Override
  protected void onSwtDragDropAccepted( DropEvent event ) {
    String text = "";
    for ( int i = 0; i < event.getDataTransfer().getData().size(); i++ ) {
      if ( i != 0 ) {
        text += ", ";
      }
      text += event.getDataTransfer().getData().get( i ).toString();
    }
    textBox.setText( text );
  }

  public void setOndrop( String ondrop ) {
    super.setOndrop( ondrop );
    super.enableDrop();
  }

  public void setCommand( String command ) {
    this.command = command;
  }

}
