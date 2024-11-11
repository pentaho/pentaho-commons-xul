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
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulSplitter;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.Orient;

public class SwtSplitter extends AbstractSwtXulContainer implements XulSplitter {

  private SashForm sash;

  public SwtSplitter( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "splitter" );
    int dir = ( getOrientation() == Orient.VERTICAL ) ? SWT.VERTICAL : SWT.HORIZONTAL;
    sash = new SashForm( (Composite) parent.getManagedObject(), dir );
    sash.setBackground( sash.getDisplay().getSystemColor( SWT.COLOR_WIDGET_BACKGROUND ) );
    setManagedObject( sash );

  }

  @Override
  public void layout() {

    int thisFlex = 0;
    int childNum = 0;
    int[] weights = new int[getChildNodes().size()];

    for ( Object child : this.getChildNodes() ) {
      thisFlex = ( (SwtElement) child ).getFlex();
      weights[childNum++] = ( thisFlex <= 0 ) ? 0 : thisFlex;
    }
    super.layout();
    if ( weights.length > 0 ) {
      sash.setWeights( weights );
    }
    sash.setVisible( true );
    sash.layout( true );
  }

  @Override
  public void setOrient( String orientation ) {
    super.setOrient( orientation );
    if ( sash != null ) {
      int dir = ( getOrientation() == Orient.VERTICAL ) ? SWT.VERTICAL : SWT.HORIZONTAL;
      sash.setOrientation( dir );
    }
  }

}
