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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulButton.Type;
import org.pentaho.ui.xul.components.XulStatusbarpanel;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.util.Direction;

public class SwingStatusbarpanel extends SwingElement implements XulStatusbarpanel {

  private static final Log logger = LogFactory.getLog( SwingStatusbarpanel.class );
  private String image;
  private Direction dir;
  private String group;
  private Type type;
  private ButtonGroup buttonGroup;
  private XulDomContainer domContainer;
  private String onclick;
  private boolean selected;
  private JPanel panel;
  private JLabel label;

  public SwingStatusbarpanel( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "statusbarpanel" );
    panel = new JPanel();
    this.domContainer = domContainer;

    panel.setBorder( BorderFactory.createLineBorder( Color.gray.brighter() ) );
    panel.setOpaque( false );
    label = new JLabel();
    // label.setPreferredSize(new Dimension(50,15));
    label.setFont( new Font( "Dialog", Font.PLAIN, 11 ) );
    setManagedObject( panel );
  }

  public void setLabel( String label ) {
    // this.label.setText(label);
    this.label = new JLabel( label );
    panel.removeAll();
    panel.add( this.label );
    panel.repaint();
  }

  public String getLabel() {
    return this.label.getText();
  }

  public String getImage() {
    return this.image;

  }

  public void setImage( String src ) {
    this.image = src;
    URL url = getClass().getClassLoader().getResource( this.domContainer.getXulLoader().getRootDir() + src );

    if ( url == null ) {
      logger.error( "Could not find resource" );
      logger.error( this.domContainer.getXulLoader().getRootDir() + src );
      return;
    }
    Icon ico = new ImageIcon( url );
    if ( ico == null ) {
      logger.error( "Image could not be found: " + ico );
    } else {
      panel.removeAll();
      panel.add( new JLabel( ico ) );
      this.panel.revalidate();
      this.panel.repaint();
      logger.info( "Set new image :" + src );
    }
  }

  @Override
  public void layout() {

    label.setPreferredSize( new Dimension( label.getPreferredSize().width, 12 ) );
  }

}
