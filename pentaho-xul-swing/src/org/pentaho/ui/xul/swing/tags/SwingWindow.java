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

/**
 * 
 */

package org.pentaho.ui.xul.swing.tags;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.TextAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.swing.SwingRoot;
import org.pentaho.ui.xul.util.Orient;

/**
 * @author OEM
 * 
 */
public class SwingWindow extends AbstractSwingContainer implements XulWindow, SwingRoot {
  private static final Log logger = LogFactory.getLog( SwingElement.class );

  JFrame frame;

  private int width;

  private int height;

  private XulDomContainer xulDomContainer;

  private String title = null;

  private String onload;
  private String onclose;
  private String onunload;

  private Clipboard clipboard;

  private String appicon = null;
  private XulDomContainer xulContainer;

  /**
   * Xul Buttons can belong to radio button groups without a "radiogroup" element in the DOM This collection facilitates
   * this behavior.
   */
  private Map<String, ButtonGroup> anonymousButtonGroups = new HashMap<String, ButtonGroup>();

  public SwingWindow( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "window" );
    frame = new JFrame();
    this.xulDomContainer = domContainer;

    this.orientation = Orient.VERTICAL;
    this.xulContainer = domContainer;

    container = new JPanel( new GridBagLayout() );
    // container.setBorder(BorderFactory.createLineBorder(Color.green));
    setManagedObject( container );

    clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    resetContainer();

    frame.getContentPane().setLayout( new BorderLayout() );
    frame.getContentPane().add( container, BorderLayout.CENTER );
    frame.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
    frame.addWindowListener( new WindowAdapter() {
      public void windowClosing( WindowEvent e ) {
        xulDomContainer.close();
      }
    } );

    setManagedObject( frame );
  }

  public void resetContainer() {

    container.removeAll();

    gc = new GridBagConstraints();
    gc.gridy = GridBagConstraints.RELATIVE;
    gc.gridx = 0;
    gc.gridheight = 1;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.insets = new Insets( 0, 0, 0, 0 );
    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.anchor = GridBagConstraints.NORTHWEST;
    gc.weightx = 1;

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.containers.XulPage#setTitle(java.lang.String)
   */
  public void setTitle( String title ) {
    this.title = title;
    frame.setTitle( title );
  }

  public String getTitle() {
    return title;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.containers.XulPage#getHeight()
   */
  public int getHeight() {
    // TODO Auto-generated method stub
    return height;
  }

  public void setHeight( int height ) {
    this.height = height;
    frame.setSize( new Dimension( this.width, this.height ) );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.containers.XulPage#getWidth()
   */
  public int getWidth() {
    // TODO Auto-generated method stub
    return width;
  }

  public void setWidth( int width ) {
    this.width = width;
    frame.setSize( new Dimension( this.width, this.height ) );
  }

  public void setXulDomContainer( XulDomContainer xulDomContainer ) {
    this.xulDomContainer = xulDomContainer;
  }

  public XulDomContainer getXulDomContainer() {
    return xulDomContainer;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.containers.XulWindow#getOnload(java.lang.String)
   */
  public String getOnload() {
    return onload;

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.containers.XulWindow#setOnload(java.lang.String)
   */
  public void setOnload( String onload ) {
    this.onload = onload;

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

  @Override
  public void layout() {
    super.layout();
  }

  public void close() {
    frame.setVisible( false );
  }

  public boolean isClosed() {
    return !frame.isVisible();
  }

  public void open() {
    frame.setVisible( true );
  }

  @Override
  public void replaceChild( XulComponent oldElement, XulComponent newElement ) throws XulDomException {
    this.resetContainer();
    super.replaceChild( oldElement, newElement );
  }

  public void copy() throws XulException {
    TextAction act = new DefaultEditorKit.CopyAction();
    act.actionPerformed( new ActionEvent( this.getManagedObject(), 999, "copy" ) );

  }

  public void cut() {
    TextAction act = new DefaultEditorKit.CutAction();
    act.actionPerformed( new ActionEvent( this.getManagedObject(), 999, "cut" ) );
  }

  public void paste() {
    TextAction act = new DefaultEditorKit.PasteAction();
    act.actionPerformed( new ActionEvent( this.getManagedObject(), 999, "paste" ) );
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

  public void copy( String content ) throws XulException {
    StringSelection data = new StringSelection( content );
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents( data, data );
  }

  public Object getRootObject() {
    return frame;
  }

  public void invokeLater( Runnable runnable ) {
    EventQueue.invokeLater( runnable );
  }

  public void setAppicon( String icon ) {
    try {
      this.appicon = icon;
      URL url = SwingButton.class.getClassLoader().getResource( this.xulContainer.getXulLoader().getRootDir() + icon );

      ImageIcon ico = new ImageIcon( url );
      if ( ico.getImage() != null ) {
        frame.setIconImage( ico.getImage() );
      }
    } catch ( Exception e ) {
      logger.error( "Error loading application icon: " + icon, e );
    }
  }

}
