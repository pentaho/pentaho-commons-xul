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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.apache.commons.lang.StringUtils;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.components.XulDialogheader;
import org.pentaho.ui.xul.containers.XulDialog;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;
import org.pentaho.ui.xul.swing.SwingRoot;
import org.pentaho.ui.xul.util.Orient;

public class SwingDialog extends AbstractSwingContainer implements XulDialog, SwingRoot {

  XulDomContainer domContainer = null;

  private JDialog dialog = null;

  private Boolean resizable = false;

  private Boolean modal = true;

  private String buttonlabelaccept;

  private String buttonlabelcancel;

  private String buttonlabelextra1;

  private String buttonlabelextra2;

  private LinkedHashMap<SwingDialog.BUTTONS, XulButton> buttons = new LinkedHashMap<SwingDialog.BUTTONS, XulButton>();

  private String ondialogaccept;

  private String ondialogcancel;

  private String ondialogextra1;

  private String ondialogextra2;

  private String title = "Dialog";

  private String onload;
  private String onclose;
  private String onunload;

  private XulDialogheader header;

  private int height = 300;

  private int width = 450;

  private String btns;

  private BUTTON_ALIGN buttonAlignment;

  private boolean pack;

  private enum BUTTONS {
    ACCEPT, CANCEL, HELP, EXTRA1, EXTRA2
  };

  private enum BUTTON_ALIGN {
    START, CENTER, END, LEFT, RIGHT, MIDDLE
  };

  private JFrame frame;

  private String ID;
  private XulComponent parent = null;
  private Box buttonPanel = Box.createHorizontalBox();

  /**
   * Xul Buttons can belong to radio button groups without a "radiogroup" element in the DOM This collection facilitates
   * this behavior.
   */
  private Map<String, ButtonGroup> anonymousButtonGroups = new HashMap<String, ButtonGroup>();

  public SwingDialog( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "dialog" );
    ID = self.getAttributeValue( "ID" );

    this.parent = parent;
    this.domContainer = domContainer;

    this.orientation = Orient.VERTICAL;

    container = new JPanel( new GridBagLayout() );
    setManagedObject( "empty" );

    resetContainer();
  }

  public ButtonGroup getButtonGroup( String group ) {
    if ( anonymousButtonGroups.containsKey( group ) ) {
      return anonymousButtonGroups.get( group );
    } else {
      ButtonGroup grp = new ButtonGroup();
      anonymousButtonGroups.put( group, grp );
      return grp;
    }
  }

  public void resetContainer() {

    container.removeAll();

    gc = new GridBagConstraints();
    gc.gridy = GridBagConstraints.RELATIVE;
    gc.gridx = 0;
    gc.gridheight = 1;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    int pad = getPadding();
    gc.insets = new Insets( pad, pad, pad, pad );
    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.anchor = GridBagConstraints.NORTHWEST;
    gc.weightx = 1;

  }

  public JComponent getContainer() {
    return container;
  }

  public JDialog getDialog() {
    if ( dialog == null ) {
      createDialog();
    }
    return dialog;
  }

  public String getButtonlabelaccept() {
    return buttonlabelaccept;
  }

  public String getButtonlabelcancel() {
    return buttonlabelcancel;
  }

  public String getButtons() {
    return btns;
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

  public void setButtons( String buttons ) {
    btns = buttons;
    if ( buttonsProcessed ) {
      buttonsProcessed = false;
      this.layout();
      populateButtonPanel();
    }

  }

  public void setOndialogaccept( String command ) {
    this.ondialogaccept = command;

  }

  public void setOndialogcancel( String command ) {
    this.ondialogcancel = command;

  }

  public void setTitle( String title ) {
    this.title = title;
  }

  public void show() {

    // we delay instantiation in order to setup a modal relationship.

    if ( dialog == null ) {

      // adding more children in the createDialog step, setting this to false keeps
      // layout from being called again, looping forever
      this.initialized = false;
      createDialog();
      dialog.pack();
      this.initialized = true;
    }

    dialog.setLocationRelativeTo( centerComp );
    dialog.setVisible( true );
  }

  public void hide() {
    dialog.setVisible( false );
  }

  public void setVisible( boolean visible ) {
    if ( visible ) {
      show();
    } else {
      hide();
    }
  }

  private boolean buttonsProcessed = false;

  @Override
  public void layout() {

    // Unfortunately, the dialog buttons need to be on the DOM, but not processed by the layout routine.
    // We could have copied all of the layout routine here, instead I'm removing the buttons from the DOM
    // and then adding them back later.
    this.initialized = false;
    for ( Map.Entry<BUTTONS, XulButton> btn : this.buttons.entrySet() ) {
      this.removeChild( btn.getValue() );
    }
    super.layout();

    for ( Element comp : getChildNodes() ) {
      if ( comp instanceof XulDialogheader ) {
        header = (XulDialogheader) comp;
      }
    }

    // setting initialized = false prevents layout from looping
    this.initialized = false;
    if ( !buttonsProcessed ) {

      this.buttons.clear();
      if ( this.btns != null ) {
        String[] tempButtons = btns.split( "," );

        for ( int i = 0; i < tempButtons.length; i++ ) {

          if ( StringUtils.isEmpty( tempButtons[i].trim() ) ) {
            continue;
          }
          SwingButton btn = new SwingButton( null, this, this.domContainer, "button" );
          addChild( btn );
          this.buttons.put( SwingDialog.BUTTONS.valueOf( tempButtons[i].trim().toUpperCase() ), btn );
          btn.setId( ID + "_"
              + SwingDialog.BUTTONS.valueOf( tempButtons[i].trim().toUpperCase() ).toString().toLowerCase() );
        }
      }
      buttonsProcessed = true;
    } else {
      // add the buttons to the DOM so they can be accessed by others
      for ( Map.Entry<BUTTONS, XulButton> btn : this.buttons.entrySet() ) {
        this.addChild( btn.getValue() );
      }
    }
    this.initialized = true;

  }

  public int getHeight() {
    return this.height;
  }

  public int getWidth() {
    return this.width;
  }

  public void setHeight( int height ) {
    this.height = height;
    if ( dialog != null ) {
      dialog.setSize( dialog.getSize().width, height );
    }
  }

  public void setWidth( int width ) {
    this.width = width;
    if ( dialog != null ) {
      dialog.setSize( width, dialog.getSize().height );
    }
  }

  public void setModal( Boolean modal ) {
    this.modal = modal;
  }

  public Boolean isModal() {
    return modal;
  }

  public String getButtonalign() {
    return this.buttonAlignment.toString().toLowerCase();
  }

  public void setButtonalign( String align ) {
    this.buttonAlignment = SwingDialog.BUTTON_ALIGN.valueOf( align.toUpperCase() );

  }

  public String getOnload() {
    return onload;
  }

  public void setOnload( String onload ) {
    // check to see if this is a child of a window

    if ( parent == null && !( parent instanceof XulRoot ) ) {
      // dialog is root, no JFrame Parent
      this.onload = onload;
    } else {
      // add onLoad event to the XulWindow parent.
      XulRoot root = ( (XulRoot) parent );
      String prevOnload = root.getOnload();
      root.setOnload( prevOnload + "," + onload );
    }

    this.onload = onload;
  }

  private void populateButtonPanel() {

    buttonPanel.removeAll();
    if ( this.buttonAlignment == BUTTON_ALIGN.RIGHT || this.buttonAlignment == BUTTON_ALIGN.END
        || this.buttonAlignment == BUTTON_ALIGN.MIDDLE || this.buttonAlignment == BUTTON_ALIGN.CENTER
        || this.buttonAlignment == null ) {
      buttonPanel.add( Box.createHorizontalGlue() );
    }

    ArrayList<BUTTONS> buttonKeyList = new ArrayList<BUTTONS>( buttons.keySet() );
    for ( int i = 0; i < buttonKeyList.size(); i++ ) {
      buttonPanel.add( Box.createHorizontalStrut( 5 ) );
      buttonPanel.add( (JButton) this.buttons.get( buttonKeyList.get( i ) ).getManagedObject() );
      this.addChild( this.buttons.get( buttonKeyList.get( i ) ) );
    }

    buttonPanel.add( Box.createHorizontalStrut( 5 ) );

    if ( this.buttonAlignment == BUTTON_ALIGN.START || this.buttonAlignment == BUTTON_ALIGN.LEFT
        || this.buttonAlignment == BUTTON_ALIGN.MIDDLE || this.buttonAlignment == BUTTON_ALIGN.CENTER ) {
      buttonPanel.add( Box.createHorizontalGlue() );
    }

    if ( buttons.containsKey( SwingDialog.BUTTONS.ACCEPT ) ) {
      this.buttons.get( SwingDialog.BUTTONS.ACCEPT ).setLabel( this.getButtonlabelaccept() );
      this.buttons.get( SwingDialog.BUTTONS.ACCEPT ).setOnclick( this.getOndialogaccept() );
    }
    if ( buttons.containsKey( SwingDialog.BUTTONS.CANCEL ) ) {
      this.buttons.get( SwingDialog.BUTTONS.CANCEL ).setLabel( this.getButtonlabelcancel() );
      this.buttons.get( SwingDialog.BUTTONS.CANCEL ).setOnclick( this.getOndialogcancel() );
    }

    // FIXME
    if ( buttons.containsKey( SwingDialog.BUTTONS.EXTRA1 ) ) {
      this.buttons.get( SwingDialog.BUTTONS.EXTRA1 ).setLabel( this.getButtonlabelextra1() );
      this.buttons.get( SwingDialog.BUTTONS.EXTRA1 ).setOnclick( this.getOndialogextra1() );
    }
    if ( buttons.containsKey( SwingDialog.BUTTONS.EXTRA2 ) ) {
      this.buttons.get( SwingDialog.BUTTONS.EXTRA2 ).setLabel( this.getButtonlabelextra2() );
      this.buttons.get( SwingDialog.BUTTONS.EXTRA2 ).setOnclick( this.getOndialogextra2() );
    }
  }

  private Component centerComp;

  private void createDialog() {

    if ( getParent() instanceof XulDialog ) {
      Object parentObj = ( (SwingDialog) getParent() ).getDialog();
      dialog = new JDialog( (Dialog) parentObj );
      centerComp = (Component) parentObj;
    } else if ( getParent() instanceof XulWindow ) {

      Object parentObj = getParent().getManagedObject();
      dialog = new JDialog( (Frame) parentObj );
      centerComp = (Component) parentObj;
    } else {

      Document doc = getDocument();
      Element rootElement = doc.getRootElement();
      XulWindow window = null;
      if ( rootElement != this ) { // dialog is root, no JFrame Parent
        window = (XulWindow) rootElement;
      }

      if ( window != null ) {
        frame = (JFrame) window.getManagedObject();
        dialog = new JDialog( frame );
        centerComp = frame;
      } else {

        final Object context = domContainer.getOuterContext();
        if ( context instanceof JFrame ) {
          frame = (JFrame) context;
          centerComp = (Component) context;
          dialog = new JDialog( frame );
        } else if ( context instanceof JDialog ) {
          dialog = new JDialog( (JDialog) context );
          centerComp = (Component) context;
        } else if ( context instanceof JComponent ) {
          dialog = new JDialog();
          centerComp = (Component) context;
        } else {
          dialog = new JDialog();
        }
      }
    }

    dialog.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
    dialog.setResizable( getResizable() );
    dialog.setLayout( new BorderLayout() );

    JPanel mainPanel = new JPanel( new BorderLayout() );
    mainPanel.setOpaque( true );
    int pad = ( this.getPadding() > -1 ) ? getPadding() : 3;
    mainPanel.setBorder( BorderFactory.createEmptyBorder( pad, pad, pad, pad ) );

    dialog.setTitle( title );
    dialog.setModal( isModal() );
    dialog.add( mainPanel, BorderLayout.CENTER );
    mainPanel.add( container, BorderLayout.CENTER );
    container.setOpaque( false );

    if ( this.header != null ) {

      JPanel headerPanel = new JPanel( new BorderLayout() );
      headerPanel.setBackground( Color.decode( "#5F86C0" ) );
      headerPanel.setOpaque( true );
      JPanel headerPanelInner = new JPanel( new BorderLayout() );
      headerPanelInner.setBorder( BorderFactory.createEmptyBorder( 3, 3, 3, 3 ) );
      headerPanelInner.setOpaque( false );

      headerPanel.setBorder( BorderFactory.createBevelBorder( BevelBorder.LOWERED, Color.decode( "#8FB1E5" ), Color
          .decode( "#446CA9" ) ) );

      JLabel title = new JLabel( this.header.getTitle() );

      title.setForeground( Color.white );
      headerPanelInner.add( title, BorderLayout.WEST );

      JLabel desc = new JLabel( this.header.getDescription() );
      desc.setForeground( Color.white );
      headerPanelInner.add( desc, BorderLayout.EAST );

      headerPanel.add( headerPanelInner, BorderLayout.CENTER );

      mainPanel.add( headerPanel, BorderLayout.NORTH );
    }

    populateButtonPanel();
    mainPanel.add( buttonPanel, BorderLayout.SOUTH );
    dialog.setSize( new Dimension( getWidth(), getHeight() ) );
    dialog.setPreferredSize( new Dimension( getWidth(), getHeight() ) );
    dialog.setMinimumSize( new Dimension( getWidth(), getHeight() ) );

    if ( this.getBgcolor() != null ) {
      mainPanel.setBackground( Color.decode( this.getBgcolor() ) );
    }

  }

  public boolean isHidden() {
    return dialog == null || !dialog.isVisible();
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

  public Object getRootObject() {
    if ( dialog == null ) {
      createDialog();
    }
    return dialog;
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
    EventQueue.invokeLater( runnable );
  }

  public Boolean getResizable() {
    return resizable;
  }

  public void setResizable( Boolean resizable ) {
    this.resizable = resizable;
  }

  public void setAppicon( String icon ) {
    // TODO Auto-generated method stub
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
