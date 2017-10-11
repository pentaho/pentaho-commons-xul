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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.containers.XulRoot;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.swing.SwingRoot;
import org.pentaho.ui.xul.util.Direction;
import org.pentaho.ui.xul.util.Orient;

/**
 * @author nbaker
 * 
 */
public class SwingButton extends SwingElement implements XulButton {
  private class OnClickRunnable implements Runnable {
    public OnClickRunnable() {
    }

    public void run() {

      final String method = getOnclick();
      if ( method == null ) {
        return;
      }
      try {
        Document doc = getDocument();
        XulRoot window = (XulRoot) doc.getRootElement();
        final XulDomContainer con = window.getXulDomContainer();
        con.invoke( method, new Object[] {} );
      } catch ( XulException e ) {
        logger.error( "Error calling oncommand event: " + method, e );
      }
    }
  }

  private static final Log logger = LogFactory.getLog( SwingButton.class );
  private String image;
  private Direction dir;
  private String group;
  private Type type;
  private ButtonGroup buttonGroup;
  private XulDomContainer domContainer;
  private String onclick;
  private boolean selected;

  public SwingButton( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "button" );
    setManagedObject( new JButton() );
    this.domContainer = domContainer;
  }

  public SwingButton() {
    this( null, null, null, null );
  }

  protected AbstractButton getButton() {
    return (AbstractButton) getManagedObject();
  }

  @Override
  public void onDomReady() {
    if ( this.group != null && getDocument() != null && getDocument().getRootElement() instanceof SwingRoot ) {
      buttonGroup = ( (SwingRoot) SwingButton.this.getDocument().getRootElement() ).getButtonGroup( group );
      AbstractButton button = (AbstractButton) getManagedObject();
      buttonGroup.add( button );
      if ( buttonGroup.getButtonCount() == 1 ) {
        // first button in, TODO: remove once selected="true" attribute supported
        button.setSelected( true );
      }
    }
  }

  protected void setButton( final AbstractButton button ) {
    if ( button == null ) {
      throw new NullPointerException();
    }
    setManagedObject( button );
  }

  public void setLabel( String label ) {
    getButton().setText( label );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.components.XulButton#setOnClick(java.lang.String)
   */
  public void setOnclick( final String method ) {
    onclick = method;
    getButton().addActionListener( new ActionListener() {
      public void actionPerformed( ActionEvent evt ) {
        SwingUtilities.invokeLater( new OnClickRunnable() );
      }
    } );
  }

  public String getOnclick() {
    return this.onclick;
  }

  public String getLabel() {
    return getButton().getText();
  }

  public boolean isDisabled() {
    return !getButton().isEnabled();
  }

  public void setDisabled( boolean dis ) {
    final AbstractButton button = getButton();
    boolean previous = !button.isEnabled();
    button.setEnabled( !dis );
    this.changeSupport.firePropertyChange( "disabled", previous, dis );
  }

  public String getImage() {
    return this.image;

  }

  public void setImage( String src ) {
    try {
      this.image = src;
      URL rootDir = SwingButton.class.getClassLoader().getResource( this.domContainer.getXulLoader().getRootDir() );
      URL iconPath = new URL( rootDir, src );
      Icon ico = new ImageIcon( iconPath );
      if ( ico == null ) {
        logger.error( "Image could not be found: " + ico );
      } else {
        this.getButton().setIcon( ico );
      }
    } catch ( MalformedURLException e ) {
      logger.error( "Could not find resource" );
      logger.error( this.domContainer.getXulLoader().getRootDir() + src );
    }
  }

  public String getDir() {
    if ( dir == null ) {
      return null;
    }
    return dir.toString().toLowerCase();
  }

  public void setDir( String dir ) {
    this.dir = Direction.valueOf( dir.toUpperCase() );
  }

  @Override
  public void layout() {
    // check type to see if it's a toggleButton
    if ( type == Type.CHECKBOX || type == Type.RADIO ) {
      final AbstractButton oldButton = getButton();

      final AbstractButton button = new JToggleButton();
      button.setText( oldButton.getText() );
      button.setIcon( oldButton.getIcon() );
      button.setEnabled( oldButton.isEnabled() );
      button.setSelected( this.selected );
      setButton( button );

      if ( this.getOnclick() != null ) {
        this.setOnclick( this.getOnclick() );
      }
    }
    final AbstractButton button = getButton();
    // adjust orientation of label and icon
    if ( this.orientation == Orient.VERTICAL ) {
      button.setHorizontalTextPosition( JButton.CENTER );
      if ( this.dir == Direction.FORWARD ) {
        button.setVerticalTextPosition( JButton.BOTTOM );
      } else {
        button.setVerticalTextPosition( JButton.TOP );
      }
    } else {
      button.setVerticalTextPosition( JButton.CENTER );
      if ( this.dir == Direction.FORWARD ) {
        button.setHorizontalTextPosition( JButton.RIGHT );
      } else {
        button.setHorizontalTextPosition( JButton.LEFT );
      }
    }

    // Square button patch. if no label and icon is square, set min/max to square up button
    final Icon icon = button.getIcon();
    if ( "".equals( button.getText() ) && icon != null && icon.getIconHeight() == icon.getIconWidth() ) {
      Dimension dim = button.getPreferredSize();
      button.setMinimumSize( new Dimension( dim.height, dim.height ) );
      button.setPreferredSize( new Dimension( dim.height, dim.height ) );
    }

    button.setToolTipText( this.getTooltiptext() );

    super.layout();
  }

  public String getGroup() {
    return this.group;
  }

  public String getType() {
    if ( this.type == null ) {
      return null;
    }
    return this.type.toString();
  }

  public void setGroup( final String group ) {
    this.group = group;
  }

  public void setType( String type ) {
    this.type = Type.valueOf( type.toString().toUpperCase() );

  }

  public void setSelected( String selected ) {
    this.selected = Boolean.parseBoolean( selected );
    getButton().setSelected( this.selected );
  }

  public boolean isSelected() {
    return getButton().isSelected();
  }

  public void setSelected( boolean selected ) {
    this.selected = selected;
    getButton().setSelected( this.selected );
  }

  public void doClick() {
    getButton().doClick();
  }

}
