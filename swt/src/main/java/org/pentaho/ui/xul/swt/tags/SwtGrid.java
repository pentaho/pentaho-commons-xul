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

package org.pentaho.ui.xul.swt.tags;

import java.awt.Color;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulDialog;
import org.pentaho.ui.xul.containers.XulGrid;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.swt.SwtAlign;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.Orient;

/**
 * User: nbaker Date: Apr 14, 2009
 */
public class SwtGrid extends AbstractSwtXulContainer implements XulGrid {
  private static final Log logger = LogFactory.getLog( SwtGrid.class );
  private SwtRows rows;
  private SwtColumns cols;
  private String background;
  private Composite grid;
  private XulDomContainer domContainer;

  public SwtGrid( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "grid" );

    this.domContainer = domContainer;
    int style = SWT.None;
    if ( self.getAttributeValue( "border" ) != null ) {
      style = SWT.BORDER;
    }

    grid = new Composite( (Composite) parent.getManagedObject(), style );
    grid.setBackgroundMode( SWT.INHERIT_DEFAULT );
    setManagedObject( grid );
  }

  @Override
  public void addChild( Element e ) {
    super.addChild( e );
    if ( e instanceof SwtRows ) {
      rows = (SwtRows) e;
    } else if ( e instanceof SwtColumns ) {
      cols = (SwtColumns) e;
    }
  }

  @Override
  public void addChildAt( Element c, int pos ) {
    super.addChildAt( c, pos );
    if ( c instanceof SwtRows ) {
      rows = (SwtRows) c;
    } else if ( c instanceof SwtColumns ) {
      cols = (SwtColumns) c;
    }
  }

  @Override
  public void layout() {

    if ( this.getChildNodes().size() < 2 ) {
      logger.warn( "Grid does not contain Column and Row children" );
      return;
    }

    XulComponent columns = this.getChildNodes().get( 0 );
    XulComponent rows = this.getChildNodes().get( 1 );

    int totalFlex = 0;
    int thisFlex = 0;
    boolean everyChildIsFlexing = true;

    for ( Object child : cols.getChildNodes() ) {
      thisFlex = ( (SwtElement) child ).getFlex();
      if ( thisFlex <= 0 ) {
        everyChildIsFlexing = false;
      }
      totalFlex += thisFlex;
    }

    Composite container = (Composite) getManagedObject();

    int columnCount = cols.getChildNodes().size() + totalFlex;

    GridLayout layout = new GridLayout( columnCount, everyChildIsFlexing );
    if ( this.getPadding() > -1 ) {
      layout.marginWidth = this.getPadding();
      layout.marginHeight = this.getPadding();
    }
    if ( this.getSpacing() > -1 ) {
      layout.horizontalSpacing = this.getSpacing();
      layout.verticalSpacing = this.getSpacing();
    }
    container.setLayout( layout );

    boolean atLeastOneRowIsFlexed = false;

    for ( XulComponent row : rows.getChildNodes() ) {
      if ( row.getFlex() >= 1 ) {
        atLeastOneRowIsFlexed = true;
      }
      for ( Element cell : row.getChildNodes() ) {

        SwtElement swtChild = (SwtElement) cell;

        // some children have no object they are managing... skip these kids!

        Object mo = swtChild.getManagedObject();
        if ( mo == null || !( mo instanceof Control ) || swtChild instanceof XulDialog ) {
          continue;
        }

        Control c = (Control) swtChild.getManagedObject();

        GridData data = new GridData();

        // How many columns or rows should the control span? Use the flex value plus
        // 1 "point" for the child itself.

        data.horizontalSpan = cols.getChildNodes().get( row.getChildNodes().indexOf( cell ) ).getFlex() + 1;
        data.verticalSpan = row.getFlex() + 1;

        if ( row.getFlex() >= 1 ) {
          data.verticalAlignment = SWT.FILL;
          data.grabExcessVerticalSpace = true;
        }

        if ( swtChild.getFlex() > 0 && swtChild.getWidth() == 0 ) {
          data.grabExcessHorizontalSpace = true;
          data.horizontalAlignment = SWT.FILL;

        }

        if ( swtChild.getWidth() > 0 ) {
          data.widthHint = swtChild.getWidth();
        }

        if ( swtChild.getHeight() > 0 ) {
          data.heightHint = swtChild.getHeight();
        }

        // And finally, deal with the align attribute...
        // Align is the PARENT'S attribute, and affects the
        // opposite direction of the orientation.

        if ( swtChild.getFlex() <= 0 && swtChild.getAlign() != null ) {
          SwtAlign swtAlign = SwtAlign.valueOf( swtChild.getAlign().toString() );
          switch ( swtAlign ) {
            case START:
              data.horizontalAlignment = GridData.BEGINNING;
              break;
            case END:
              data.horizontalAlignment = GridData.END;
              break;
            case CENTER:
              data.horizontalAlignment = GridData.CENTER;
              break;
          }
        }

        if ( orient.equals( Orient.HORIZONTAL ) ) {

          if ( swtChild.getHeight() < 0 ) {
            data.grabExcessVerticalSpace = true;
          }

        } else { // Orient.VERTICAL

          if ( swtChild.getWidth() < 0 ) {
            data.grabExcessHorizontalSpace = true;
          }
        }
        c.setLayoutData( data );
      }
    }
    if ( atLeastOneRowIsFlexed == false ) { // Add placeholder row to push others up

    }
    container.layout( true );

    this.initialized = true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.pentaho.ui.xul.containers.XulGrid#update()
   */
  public void update() {
    layout();
  }

  public String getBgcolor() {
    return bgcolor;
  }

  public void setBgcolor( String bgcolor ) {
    this.bgcolor = bgcolor;
    Color c = Color.decode( bgcolor );
    grid.setBackground( new org.eclipse.swt.graphics.Color( grid.getDisplay(), c.getRed(), c.getGreen(), c.getBlue() ) );
    grid.setBackgroundMode( SWT.INHERIT_DEFAULT );
  }
}
