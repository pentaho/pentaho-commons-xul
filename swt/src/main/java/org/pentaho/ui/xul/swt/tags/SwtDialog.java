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
 * Copyright (c) 2002-2019 Hitachi Vantara..  All rights reserved.
 */

package org.pentaho.ui.xul.swt.tags;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulSettingsManager;
import org.pentaho.ui.xul.components.XulDialogheader;
import org.pentaho.ui.xul.containers.XulDialog;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.swt.DialogButton;
import org.pentaho.ui.xul.swt.custom.BasicDialog;
import org.pentaho.ui.xul.util.Orient;
import org.pentaho.ui.xul.util.SwtXulUtil;

public class SwtDialog extends AbstractSwtXulContainer implements XulDialog {

  protected XulDomContainer domContainer = null;

  protected BasicDialog dialog = null;

  protected String title = null;

  protected String onload;
  protected String onclose;
  protected String onunload;

  protected XulDialogheader header;

  protected int height = -999;

  protected int width = -999;

  protected boolean isDialogHidden = true;

  protected int returnCode = -9999;

  protected BUTTON_ALIGN buttonAlignment;

  protected enum BUTTON_ALIGN {
    START, CENTER, END, LEFT, RIGHT, MIDDLE
  };

  protected Map<String, SwtButton> activeDialogButtons = new HashMap<String, SwtButton>();

  protected String buttonlabelaccept;

  protected String buttonlabelcancel;

  protected String buttonlabelextra1;

  protected String buttonlabelextra2;

  protected String[] buttons = new String[]{ "accept", "cancel" };

  protected String ondialogaccept;

  protected String ondialogcancel;

  protected String ondialogextra1;

  protected String ondialogextra2;

  protected boolean resizable = false;

  protected boolean buttonsCreated = false;

  protected String appIcon;

  protected static final Log logger = LogFactory.getLog( SwtDialog.class );

  protected boolean pack;

  protected XulSettingsManager settingsManager;

  protected boolean closing;

  protected boolean letDialogDispose;

  public SwtDialog( Element self, XulComponent parent, XulDomContainer container, String tagName ) {
    super( tagName );

    this.setOrient( Orient.VERTICAL.toString() );
    this.domContainer = container;
    this.setId( self.getAttributeValue( "ID" ) );

    // TODO: defer this creation until later when all attributes are assigned. For now just get the
    // resizable one
    String resizableStr = self.getAttributeValue( "resizable" );
    this.setResizable( resizableStr == null || resizableStr.equals( "true" ) );

    if ( self != null ) {
      // Extract appIcon
      setAppicon( self.getAttributeValue( "appicon" ) );
    }

    dialog = createDialog( self, parent );
    Composite c = createDialogComposite();
    setManagedObject( c );

    settingsManager = container.getSettingsManager();
  }

  protected Shell getParentShell( XulComponent parent ) {

    Shell parentShell = null;
    if ( parent != null ) {
      if ( parent instanceof XulWindow ) {

        // See if they registered an outer context replacement for the Window's Shell
        if ( domContainer.getOuterContext() != null ) {
          parentShell = (Shell) domContainer.getOuterContext();
        }
      }
      if ( parentShell == null && parent instanceof XulRoot ) {
        parentShell = (Shell) ( (XulRoot) parent ).getRootObject();
      }
    }
    if ( parentShell == null ) {
      parentShell = new Shell( SWT.SHELL_TRIM );
    }
    return parentShell;
  }

  private BasicDialog createDialog( XulComponent parent ) {
    return createDialog( null, parent );
  }

  private BasicDialog createDialog( Element self, XulComponent parent ) {
    Shell parentShell;

    if ( self != null
        && self.getAttributeValue( "proxyoutercontext" ) != null
        && domContainer.getOuterContext() != null
        && domContainer.getOuterContext() instanceof Shell ) {
      parentShell = (Shell) domContainer.getOuterContext();
    } else {
      parentShell = getParentShell( parent );
    }

    final BasicDialog newDialog = new BasicDialog( parentShell, this.getResizable() );
    newDialog.getShell().setBackgroundMode( SWT.INHERIT_DEFAULT );

    newDialog.getShell().addListener( SWT.Dispose, new Listener() {
      public void handleEvent( Event event ) {
        if ( !letDialogDispose ) {
          try {
            hide();
          } catch ( SWTException e ) {
            logger.error( e );
          }
        }
      }
    } );

    if ( StringUtils.isNotEmpty( this.appIcon ) ) {
      setAppicon( this.appIcon );
    } else if ( parentShell != null && parentShell.isDisposed() == false ) {
      Image parentImg = parentShell.getImage();
      if ( parentImg != null ) {
        newDialog.getShell().setImage( parentImg );
      }
    }
    return newDialog;
  }

  private Composite createDialogComposite() {

    Composite c = new Composite( (Composite) dialog.getMainDialogArea(), SWT.NONE );
    GridData gd = new GridData( GridData.FILL_BOTH );
    gd.grabExcessVerticalSpace = true;
    gd.grabExcessHorizontalSpace = true;

    c.setLayoutData( gd );

    return c;
  }

  public Shell getShell() {
    return dialog != null ? dialog.getShell() : null;
  }

  public void dispose() {
    letDialogDispose = true;
    if ( getShell() != null && !getShell().isDisposed() ) {
      getShell().dispose();
    }
  }

  public String getButtonlabelaccept() {
    return buttonlabelaccept;
  }

  public String getButtonlabelcancel() {
    return buttonlabelcancel;
  }

  public String getButtons() {
    return StringUtils.join( buttons, "," );
  }

  public String getOndialogaccept() {
    return ondialogaccept;
  }

  public String getOndialogcancel() {
    return ondialogcancel;
  }

  public String getTitle() {
    return title;
  }

  public void setButtonlabelaccept( String label ) {
    this.buttonlabelaccept = label;
  }

  public void setButtonlabelcancel( String label ) {
    this.buttonlabelcancel = label;
  }

  public void setButtons() {
    setButtons( dialog );
    buttonsCreated = true;
  }

  public void setButtons( String buttonList ) {
    if ( buttonList.equals( "" ) ) { //$NON-NLS-1$
      buttons = null;
    } else {
      List<String> newButtons = Arrays.asList( buttonList.split( "," ) ); //$NON-NLS-1$

      // Cleanup new buttons
      for ( int i = 0; i < newButtons.size(); i++ ) {
        newButtons.set( i, newButtons.get( i ).trim().toUpperCase() );
      }
      String[] existingButtons = buttons;
      buttons = newButtons.stream().toArray( String[]::new );

      for ( String existingButton : existingButtons ) {
        if ( !newButtons.contains( existingButton.trim().toUpperCase() ) ) {
          removeButton( existingButton );
        }
      }
    }
    if ( buttonsCreated ) {
      setButtons( dialog );
    }
  }

  protected void removeButton( String button ) {
    String bName = button.trim().toUpperCase();

    if ( activeDialogButtons.containsKey( bName ) ) {
      SwtButton b = activeDialogButtons.get( bName );
      Button swtB = (Button) b.getManagedObject();
      if ( !swtB.isDisposed() ) {
        swtB.dispose();
      }
      activeDialogButtons.remove( bName );
      removeChild( b );
    }
  }

  public void setOndialogaccept( String command ) {
    this.ondialogaccept = command;

  }

  public void setOndialogcancel( String command ) {
    this.ondialogcancel = command;
    if ( ondialogcancel != null ) {

      dialog.addShellListener( new ShellAdapter() {
        public void shellClosed( ShellEvent e ) {
          invoke( ondialogcancel );
        }
      } );

    }
  }

  public void setTitle( String title ) {
    this.title = title;
  }

  public void show() {
    show( true );
  }

  public void show( boolean force ) {

    // createDialog();

    if ( ( force ) || ( !buttonsCreated ) ) {
      setButtons();
    }

    isDialogHidden = false;

    dialog.getShell().setText( title );

    int storedHeight = 0;
    int storedWidth = 0;
    int storedLeft = 0;
    int storedTop = 0;

    if ( settingsManager != null ) {
      String sWidth = settingsManager.getSetting( getId() + ".Width" );
      String sHeight = settingsManager.getSetting( getId() + ".Height" );
      String sTop = settingsManager.getSetting( getId() + ".Top" );
      String sLeft = settingsManager.getSetting( getId() + ".Left" );
      if ( sWidth != null && sHeight != null ) {
        storedWidth = Integer.parseInt( sWidth );
        storedHeight = Integer.parseInt( sHeight );
      }
      if ( sTop != null && sLeft != null ) {
        storedLeft = Integer.parseInt( sLeft );
        storedTop = Integer.parseInt( sTop );
      }
    }

    // Because the dialog is built after the create() method is called, we
    // need to ask the shell to try to re-determine an appropriate size for this dialog..
    if ( storedHeight > 0 && storedWidth > 0 ) {

      dialog.setHeight( storedHeight );
      dialog.setWidth( storedWidth );
    } else if ( ( height > 0 ) && ( width > 0 ) ) {

      // Don't allow the user to size the dialog smaller than is reasonable to
      // layout the child components
      // REMOVED: although the idea is sound, labels of great length that are ment to wrap
      // will report their preferred size and quite large, which when applied is undesirable.
      // Point pt = dialog.getPreferredSize();
      // dialog.setHeight( (pt.y < height) ? height : pt.y);
      // dialog.setWidth((pt.x < width) ? width : pt.x);

      // Don't allow the user to size the dialog smaller than is reasonable to
      // layout the child components
      dialog.setHeight( height );
      dialog.setWidth( width );

    }
    dialog.resizeBounds();

    if ( pack ) {
      dialog.getShell().pack();
    }
    dialog.getMainArea().layout( true, true );

    // Timing is everything - fire the onLoad evetns so tht anyone who is trying to
    notifyListeners( XulRoot.EVENT_ON_LOAD );

    if ( storedTop > 0 && storedLeft > 0 ) {
      dialog.getShell().setLocation( new Point( storedLeft, storedTop ) );
    }

    setAppicon( appIcon );

    returnCode = dialog.open();

    // dialog.setBlockOnOpen(true);
    // dialog.getShell().setVisible(true);
  }

  public void setButtons( final BasicDialog d ) {

    if ( buttons == null ) {
      d.getButtonArea().setVisible( false );
      d.getButtonArea().getParent().setVisible( false );
      ( (GridData) d.getButtonArea().getParent().getLayoutData() ).exclude = true;
      d.getShell().layout( true );
      return;
    }

    for ( String buttonName : buttons ) {
      if ( StringUtils.isEmpty( buttonName ) ) {
        return;
      }
      DialogButton thisButton = DialogButton.valueOf( buttonName.trim().toUpperCase() );

      SwtButton swtButton = null;
      SwtButton existingButton =
        ( this.getDocument() != null ) ? (SwtButton) this.getElementById( this.getId() + "_"
          + buttonName.trim().toLowerCase() ) : null;
      if ( this.getId() != null && existingButton != null ) {
        // existing button, just needs a new Widget parent
        swtButton = existingButton;
        Widget w = (Widget) existingButton.getManagedObject();
        if ( ( w == null ) || ( w.isDisposed() ) ) {
          Button button = d.createButton( thisButton, false );
          swtButton.setButton( button );
        }
      } else {
        // new button needed
        Button button = d.createButton( thisButton, false );
        swtButton = new SwtButton( button );
        swtButton.setId( this.getId() + "_" + buttonName.trim().toLowerCase() );
        this.addChild( swtButton );

      }
      switch ( thisButton ) {
        case ACCEPT:
          if ( ( getButtonlabelaccept() != null ) && ( getButtonlabelaccept().trim().length() > 0 ) ) {
            swtButton.setLabel( getButtonlabelaccept() );
          }
          if ( ( getOndialogaccept() != null ) && ( getOndialogaccept().trim().length() > 0 ) ) {
            swtButton.setOnclick( getOndialogaccept() );
          }
          break;
        case CANCEL:
          if ( ( getButtonlabelcancel() != null ) && ( getButtonlabelcancel().trim().length() > 0 ) ) {
            swtButton.setLabel( getButtonlabelcancel() );
          }
          if ( ( getOndialogcancel() != null ) && ( getOndialogcancel().trim().length() > 0 ) ) {
            swtButton.setOnclick( getOndialogcancel() );
          }
          break;
        case EXTRA1:
          if ( ( getButtonlabelextra1() != null ) && ( getButtonlabelextra1().trim().length() > 0 ) ) {
            swtButton.setLabel( getButtonlabelextra1() );
          }
          if ( ( getOndialogextra1() != null ) && ( getOndialogextra1().trim().length() > 0 ) ) {
            swtButton.setOnclick( getOndialogextra1() );
          }
          break;
        case EXTRA2:
          if ( ( getButtonlabelextra2() != null ) && ( getButtonlabelextra2().trim().length() > 0 ) ) {
            swtButton.setLabel( getButtonlabelextra2() );
          }
          if ( ( getOndialogextra2() != null ) && ( getOndialogextra2().trim().length() > 0 ) ) {
            swtButton.setOnclick( getOndialogextra2() );
          }
          break;
      }

      activeDialogButtons.put( thisButton.toString().toUpperCase(), swtButton );
    }

    int width = 75;
    for ( Map.Entry<String, SwtButton> entry : activeDialogButtons.entrySet() ) {
      // Compute the size required to render the component and grab the width
      width = Math.max( width, entry.getValue().button.computeSize( SWT.DEFAULT, SWT.DEFAULT, true ).x );
    }

    GridData gd = new GridData();
    gd.widthHint = width;
    for ( Map.Entry<String, SwtButton> entry : activeDialogButtons.entrySet() ) {
      entry.getValue().button.setLayoutData( gd );
    }

  }

  public boolean isDisposing() {
    return letDialogDispose;
  }

  public void hide() {
    // dialog.getShell().removeListener(SWT.Dispose, listener);
    if ( closing || dialog.getMainArea().isDisposed() || getParentShell( getParent() ).isDisposed()
      || ( getParent() instanceof SwtDialog && ( (SwtDialog) getParent() ).isDisposing() ) ) {
      return;
    }
    closing = true;
    if ( settingsManager != null ) {
      settingsManager.storeSetting( getId() + ".Left", "" + dialog.getShell().getLocation().x );
      settingsManager.storeSetting( getId() + ".Top", "" + dialog.getShell().getLocation().y );
      settingsManager.storeSetting( getId() + ".Height", "" + dialog.getShell().getSize().y );
      settingsManager.storeSetting( getId() + ".Width", "" + dialog.getShell().getSize().x );
      try {
        settingsManager.save();
      } catch ( IOException e ) {
        logger.error( e );
      }
    }

    returnCode = IDialogConstants.CLOSE_ID;

    BasicDialog newDialog = createDialog( getParent() );
    Control[] controlz = newDialog.getMainArea().getChildren();
    for ( Control c : controlz ) {
      if (  c != null && !c.isDisposed() ) {
        c.dispose();
      }
    }

    Control[] controls = dialog.getMainArea().getChildren();
    for ( Control c : controls ) {
      c.setParent( newDialog.getMainArea() );
    }
    setButtons( newDialog );
    setAppicon( this.appIcon );

    newDialog.getShell().layout();
    BasicDialog outgoingDialog = dialog;
    dialog = newDialog;

    outgoingDialog.close();

    isDialogHidden = true;

    setManagedObject( dialog.getMainArea() );
    closing = false;
  }

  public void setVisible( boolean visible ) {
    if ( visible ) {
      show();
    } else {
      hide();
    }
  }

  @Override
  public void layout() {
    setButtons();
    super.layout();

    for ( XulComponent comp : getChildNodes() ) {
      if ( comp instanceof XulDialogheader ) {
        header = (XulDialogheader) comp;
      }
    }
  }

  public int getHeight() {
    return this.height;
  }

  public int getWidth() {
    return this.width;
  }

  public void setHeight( int height ) {
    this.height = height;
  }

  public void setWidth( int width ) {
    this.width = width;
  }

  public String getButtonalign() {
    return this.buttonAlignment.toString().toLowerCase();
  }

  public void setButtonalign( String align ) {
    this.buttonAlignment = SwtDialog.BUTTON_ALIGN.valueOf( align.toUpperCase() );

  }

  public String getOnload() {
    return onload;
  }

  public void setOnload( final String method ) {
    this.onload = method;

    // @TODO This whole listener pattern needs to be replaced with a generic solution

    // dialog.getShell().addListener(XulRoot.EVENT_ON_LOAD, new Listener() {
    // public void handleEvent(Event e) {
    // if(!StringUtils.isEmpty(method)){
    //
    // // only call this if the application is ready. Otherwise it's being handled in the main of the
    // // program
    // if(SwtDialog.this.domContainer.isInitialized()){
    // invoke(method);
    // }
    // }
    // }
    // });
  }

  /**
   * @param event
   * @deprecated This will be replaced by an agnostic listener pattern in the next version of Xul
   */
  @Deprecated
  public void notifyListeners( int event ) {
    if ( !dialog.getShell().isDisposed() ) {
      dialog.getShell().notifyListeners( event, new Event() );
    }
  }

  public boolean isHidden() {
    return isDialogHidden;
  }

  public String getButtonlabelextra1() {
    return buttonlabelextra1;
  }

  public void setButtonlabelextra1( String buttonlabelextra1 ) {
    this.buttonlabelextra1 = buttonlabelextra1;
  }

  public String getButtonlabelextra2() {
    return buttonlabelextra2;
  }

  public void setButtonlabelextra2( String buttonlabelextra2 ) {
    this.buttonlabelextra2 = buttonlabelextra2;
  }

  public String getOndialogextra1() {
    return ondialogextra1;
  }

  public void setOndialogextra1( String ondialogextra1 ) {
    this.ondialogextra1 = ondialogextra1;
  }

  public String getOndialogextra2() {
    return ondialogextra2;
  }

  public void setOndialogextra2( String ondialogextra2 ) {
    this.ondialogextra2 = ondialogextra2;
  }

  public XulDomContainer getXulDomContainer() {
    return this.domContainer;
  }

  public void setXulDomContainer( XulDomContainer xulDomContainer ) {
    this.domContainer = xulDomContainer;
  }

  public int getReturnCode() {
    return returnCode;
  }

  public Object getRootObject() {
    return dialog.getShell();
  }

  public String getOnclose() {
    return onclose;
  }

  public String getOnunload() {
    return onunload;
  }

  public void setOnclose( String onclose ) {
    this.onclose = onclose;
  }

  public void setOnunload( String onunload ) {
    this.onunload = onunload;
  }

  public void invokeLater( Runnable runnable ) {
    dialog.getShell().getDisplay().asyncExec( runnable );
  }

  public Boolean getResizable() {
    return resizable;
  }

  public void setResizable( Boolean resizable ) {
    this.resizable = resizable;
  }

  public void setModal( Boolean modal ) {
    throw new RuntimeException( "Not Yet Implemented" );
  }

  public void applyParentShellIcon() {

  }

  public void setAppicon( String icon ) {
    this.appIcon = icon;

    if ( appIcon == null || dialog == null ) {
      return;
    }

    Display d = dialog.getShell().getDisplay();
    if ( d == null ) {
      d = Display.getCurrent() != null ? Display.getCurrent() : Display.getDefault();
    }

    // first look for the appIcon in XUL known paths ...
    Image img = SwtXulUtil.getCachedImage( appIcon, domContainer, d );

    // try second method of finding the appIcon...
    if ( img == null ) {
      InputStream in = null;
      in = getClass().getResourceAsStream( appIcon );
      if ( in == null ) {

        try {
          File f = new File( icon );
          if ( f.exists() ) {
            try {
              in = new FileInputStream( f );
            } catch ( FileNotFoundException e ) {
            }
          } else {
            logger.warn( "could not find image: " + appIcon );
            return;
          }
          img = new Image( dialog.getShell().getDisplay(), in );
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
    if ( img != null && dialog != null ) {
      dialog.getShell().setImage( img );
    }

  }

  public boolean isPack() {
    return pack;
  }

  public void setPack( boolean pack ) {
    this.pack = pack;
  }

  @Override
  public void center() { }
}
