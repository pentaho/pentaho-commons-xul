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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulGrid;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.AbstractSwingContainer;
import org.pentaho.ui.xul.util.Align;

/**
 * User: nbaker Date: Apr 14, 2009
 */
public class SwingGrid extends AbstractSwingContainer implements XulGrid {
  private JPanel grid = new JPanel();
  private static final Log logger = LogFactory.getLog( SwingGrid.class );

  public SwingGrid( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "grid" );

    grid.setLayout( new GridBagLayout() );
    grid.setOpaque( false );
    setManagedObject( grid );
    resetContainer();
  }

  @Override
  public void resetContainer() {
    grid.removeAll();

    gc = new GridBagConstraints();
    gc.gridy = 0;
    gc.gridx = 0;
    gc.gridheight = 1;
    gc.gridwidth = 1;
    gc.insets = new Insets( 2, 2, 2, 2 );
    gc.anchor = GridBagConstraints.NORTHWEST;
    gc.weightx = 1;
  }

  @Override
  public void layout() {

    if ( this.getChildNodes().size() < 2 ) {
      logger.warn( "Grid does not contain Column and Row children" );
      return;
    }

    XulComponent columns = this.getChildNodes().get( 0 );
    XulComponent rows = this.getChildNodes().get( 1 );

    int colCount = 0;
    int rowCount = 0;
    float colFlexTotal = 0;
    float rowTotalFlex = 0;
    for ( XulComponent col : columns.getChildNodes() ) {
      if ( col.getFlex() > 0 ) {
        colFlexTotal += col.getFlex();
      }
      colCount++;
    }

    for ( XulComponent row : rows.getChildNodes() ) {
      if ( row.getFlex() > 0 ) {
        rowTotalFlex += row.getFlex();
      }
      rowCount++;
    }

    for ( XulComponent row : rows.getChildNodes() ) {
      gc.gridx = 0;

      for ( XulComponent xulComp : row.getChildNodes() ) {
        gc.weightx = 0.0;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.weighty = 0.0;
        gc.anchor = GridBagConstraints.NORTHWEST;
        gc.fill = GridBagConstraints.NONE;

        Component comp = (Component) xulComp.getManagedObject();
        float colFlex = columns.getChildNodes().get( gc.gridx ).getFlex();
        int rowFlex = row.getFlex();

        Align colAlignment = null;
        Align rowAlignment = null;
        String colAlignmentStr = xulComp.getAlign();
        String rowAlignStr = row.getAlign();
        if ( colAlignmentStr != null ) {
          colAlignment = Align.valueOf( colAlignmentStr );
        }
        if ( rowAlignStr != null ) {
          rowAlignment = Align.valueOf( rowAlignStr );
        }

        if ( colFlex > 0 ) {
          gc.weightx = ( colFlex / colFlexTotal );
        }
        if ( rowFlex > 0 ) {
          gc.weighty = ( rowFlex / rowTotalFlex );
        }
        if ( colAlignment == Align.STRETCH && xulComp.getFlex() > 0 ) {
          gc.fill = GridBagConstraints.BOTH;
        } else if ( colAlignment == Align.STRETCH ) {
          gc.fill = GridBagConstraints.HORIZONTAL;
        } else if ( xulComp.getFlex() > 0 ) {
          gc.fill = GridBagConstraints.VERTICAL;
        }

        if ( row.getChildNodes().indexOf( xulComp ) + 1 == row.getChildNodes().size() ) {
          gc.gridwidth = GridBagConstraints.REMAINDER;
        } else {
          gc.gridwidth = 1;
        }
        if ( rows.getChildNodes().indexOf( row ) + 1 == rows.getChildNodes().size() ) {
          gc.gridheight = GridBagConstraints.REMAINDER;
        } else {
          gc.gridheight = 1;
        }

        // gc.gridheight = row.getFlex() + 1;

        if ( colAlignment != null && rowAlignment != null ) {
          switch ( rowAlignment ) {
            case START:
              switch ( colAlignment ) {
                case START:
                  gc.anchor = GridBagConstraints.NORTHWEST;
                  break;
                case CENTER:
                  gc.anchor = GridBagConstraints.NORTH;
                  break;
                case END:
                  gc.anchor = GridBagConstraints.NORTHEAST;
                  break;
              }
              break;
            case CENTER:
              switch ( colAlignment ) {
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
              break;
            case END:
              switch ( colAlignment ) {
                case START:
                  gc.anchor = GridBagConstraints.SOUTHWEST;
                  break;
                case CENTER:
                  gc.anchor = GridBagConstraints.SOUTH;
                  break;
                case END:
                  gc.anchor = GridBagConstraints.SOUTHEAST;
                  break;
              }
          }
        } else if ( rowAlignment != null ) {
          switch ( rowAlignment ) {
            case START:
              gc.anchor = GridBagConstraints.NORTHWEST;
              break;
            case CENTER:
              gc.anchor = GridBagConstraints.WEST;
              break;
            case END:
              gc.anchor = GridBagConstraints.SOUTHWEST;
              break;
          }
        } else if ( colAlignment != null ) {

          switch ( colAlignment ) {
            case START:
              gc.anchor = GridBagConstraints.NORTHWEST;
              break;
            case CENTER:
              gc.anchor = GridBagConstraints.NORTH;
              break;
            case END:
              gc.anchor = GridBagConstraints.NORTHEAST;
              break;
          }
        }

        if ( comp.getWidth() > 0 || comp.getHeight() > 0 ) {
          Dimension minSize = comp.getMinimumSize();
          Dimension prefSize = comp.getPreferredSize();

          if ( comp.getWidth() > 0 ) {
            minSize.width = comp.getWidth();
            prefSize.width = comp.getWidth();
          }
          if ( comp.getHeight() > 0 ) {
            minSize.height = comp.getHeight();
            prefSize.height = comp.getHeight();
          }
          comp.setMinimumSize( minSize );
          comp.setPreferredSize( prefSize );
        } else {
          comp.setPreferredSize( comp.getMinimumSize() );
        }

        grid.add( comp, gc );
        gc.gridx++;
      }

      gc.gridy++;
    }

    if ( rowTotalFlex == 0 ) {
      // Add in an extra row at the bottom to push others up
      gc.gridy++;
      gc.weighty = 1;
      gc.fill = gc.REMAINDER;
      grid.add( Box.createGlue(), gc );
    }
    this.initialized = true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.containers.XulGrid#update()
   */
  public void update() {
    resetContainer();
    layout();
  }
}
