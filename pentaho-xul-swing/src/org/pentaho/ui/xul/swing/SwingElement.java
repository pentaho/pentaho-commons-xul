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

package org.pentaho.ui.xul.swing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.components.XulSplitter;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.impl.AbstractXulComponent;
import org.pentaho.ui.xul.util.Align;
import org.pentaho.ui.xul.util.Orient;

/**
 * @author OEM
 * 
 */
public class SwingElement extends AbstractXulComponent {
  private static final Log logger = LogFactory.getLog( SwingElement.class );

  protected JPanel container;

  protected Orient orientation;

  protected Orient orient = Orient.HORIZONTAL;

  protected GridBagConstraints gc = new GridBagConstraints();

  protected PropertyChangeSupport changeSupport = new PropertyChangeSupport( this );

  public SwingElement( String tagName ) {
    super( tagName );
  }

  public void resetContainer() {
  }

  public void layout() {
    super.layout();
    double totalFlex = 0.0;

    if ( isVisible() == false ) {
      resetContainer();
      return;
    }

    for ( Element comp : getChildNodes() ) {
      // if (comp.getManagedObject() == null) {
      // continue;
      // }
      if ( ( (XulComponent) comp ).getFlex() > 0 ) {
        flexLayout = true;
        totalFlex += ( (XulComponent) comp ).getFlex();
      }
    }

    double currentFlexTotal = 0.0;

    Align alignment = ( getAlign() != null ) ? Align.valueOf( this.getAlign().toUpperCase() ) : null;

    for ( int i = 0; i < getChildNodes().size(); i++ ) {
      XulComponent comp = (XulComponent) getChildNodes().get( i );
      gc.fill = GridBagConstraints.BOTH;

      if ( comp instanceof XulSplitter ) {
        JPanel prevContainer = container;
        container = new ScrollablePanel( new GridBagLayout() );
        container.setOpaque( false );

        final JSplitPane splitter =
            new JSplitPane( ( this.getOrientation() == Orient.VERTICAL ) ? JSplitPane.VERTICAL_SPLIT
                : JSplitPane.HORIZONTAL_SPLIT, prevContainer, container );
        splitter.setContinuousLayout( true );

        final double splitterSize = currentFlexTotal / totalFlex;
        splitter.setResizeWeight( splitterSize );
        if ( totalFlex > 0 ) {
          splitter.addComponentListener( new ComponentListener() {
            public void componentHidden( ComponentEvent arg0 ) {
            }

            public void componentMoved( ComponentEvent arg0 ) {
            }

            public void componentShown( ComponentEvent arg0 ) {
            }

            public void componentResized( ComponentEvent arg0 ) {
              splitter.setDividerLocation( splitterSize );
              splitter.removeComponentListener( this );
            }

          } );

        }

        if ( !flexLayout ) {
          if ( this.getOrientation() == Orient.VERTICAL ) { // VBox and such
            gc.weighty = 1.0;
          } else {
            gc.weightx = 1.0;
          }

          prevContainer.add( Box.createGlue(), gc );
        }
        setManagedObject( splitter );
      }

      Object maybeComponent = comp.getManagedObject();
      if ( maybeComponent == null || !( maybeComponent instanceof Component ) ) {
        continue;
      }
      if ( this.getOrientation() == Orient.VERTICAL ) { // VBox and such
        gc.gridheight = comp.getFlex() + 1;
        gc.gridwidth = GridBagConstraints.REMAINDER;
        gc.weighty = ( totalFlex == 0 ) ? 0 : ( comp.getFlex() / totalFlex );
      } else {
        gc.gridwidth = comp.getFlex() + 1;
        gc.gridheight = GridBagConstraints.REMAINDER;
        gc.weightx = ( totalFlex == 0 ) ? 0 : ( comp.getFlex() / totalFlex );
      }

      currentFlexTotal += comp.getFlex();

      if ( this.getOrientation() == Orient.VERTICAL ) { // VBox and such
        if ( alignment != null ) {
          gc.fill = GridBagConstraints.NONE;
          switch ( alignment ) {
            case START:
              gc.anchor = GridBagConstraints.WEST;
              break;
            case CENTER:
              gc.anchor = GridBagConstraints.CENTER;
              break;
            case END:
              gc.anchor = GridBagConstraints.EAST;
              break;
          }
        }

      } else {
        if ( alignment != null ) {
          gc.fill = GridBagConstraints.NONE;
          switch ( alignment ) {
            case START:
              gc.anchor = GridBagConstraints.NORTH;
              break;
            case CENTER:
              gc.anchor = GridBagConstraints.CENTER;
              break;
            case END:
              gc.anchor = GridBagConstraints.SOUTH;
              break;
          }
        }
      }

      Component component = (Component) maybeComponent;

      if ( comp.getWidth() > 0 || comp.getHeight() > 0 ) {
        Dimension minSize = component.getMinimumSize();
        Dimension prefSize = component.getPreferredSize();

        if ( comp.getWidth() > 0 ) {
          minSize.width = comp.getWidth();
          prefSize.width = comp.getWidth();
        }
        if ( comp.getHeight() > 0 ) {
          minSize.height = comp.getHeight();
          prefSize.height = comp.getHeight();
        }
        component.setMinimumSize( minSize );
        component.setPreferredSize( prefSize );
      }

      container.add( component, gc );

      if ( i + 1 == getChildNodes().size() && !flexLayout ) {
        if ( this.getOrientation() == Orient.VERTICAL ) { // VBox and such
          gc.weighty = 1.0;

        } else {
          gc.weightx = 1.0;

        }
        container.add( Box.createGlue(), gc );
      }
    }

  }

  public void setOrient( String orientation ) {
    this.orientation = Orient.valueOf( orientation.toUpperCase() );
  }

  public String getOrient() {
    return orientation.toString();
  }

  public Orient getOrientation() {
    return orientation;
  }

  @Override
  public void addChild( Element e ) {
    super.addChild( e );

    if ( e instanceof AbstractSwingContainer ) {
      AbstractSwingContainer container = (AbstractSwingContainer) e;
      if ( container.initialized == false ) {
        container.layout();
      }
    }

    if ( initialized ) {
      resetContainer();
      layout();
    }
  }

  public void addChildAt( Element c, int pos ) {
    super.addChildAt( c, pos );
    if ( initialized ) {
      resetContainer();
      layout();
    }
  }

  @Override
  public void removeChild( Element ele ) {
    super.removeChild( ele );
    if ( initialized ) {
      resetContainer();
      layout();
    }
  }

  @Override
  public void replaceChild( XulComponent oldElement, XulComponent newElement ) throws XulDomException {

    int idx = this.getChildNodes().indexOf( oldElement );
    if ( idx == -1 ) {
      logger.error( oldElement.getName() + " not found in children" );
      throw new XulDomException( oldElement.getName() + " not found in children" );
    } else {
      super.replaceChild( oldElement, newElement );
      container.removeAll();
      layout();
      this.container.revalidate();
    }
  }

  public JComponent getJComponent() {
    return getManagedObject() instanceof JComponent ? (JComponent) getManagedObject() : null;
  }

  public void setOnblur( final String method ) {
    super.setOnblur( method );

    if ( getJComponent() != null ) {
      getJComponent().addFocusListener( new FocusListener() {

        public void focusLost( FocusEvent e ) {
          invoke( method );
        }

        public void focusGained( FocusEvent e ) {
        }

      } );
    }
  }

  public void addPropertyChangeListener( PropertyChangeListener listener ) {
    changeSupport.addPropertyChangeListener( listener );
  }

  public void removePropertyChangeListener( PropertyChangeListener listener ) {
    changeSupport.removePropertyChangeListener( listener );
  }

  public void setDisabled( boolean disabled ) {
    if ( getJComponent() != null ) {
      getJComponent().setEnabled( !disabled );
    }
  }

  public boolean isDisabled() {
    if ( getJComponent() != null ) {
      return !getJComponent().isEnabled();
    }
    return false;
  }

  @Override
  public void setVisible( boolean visible ) {
    super.setVisible( visible );

    if ( getJComponent() != null ) {
      getJComponent().setVisible( visible );
    }
  }

  public void adoptAttributes( XulComponent component ) {
    // TODO Auto-generated method stub

  }
}
