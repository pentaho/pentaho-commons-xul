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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulToolbar;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;

public class SwtToolbar extends AbstractSwtXulContainer implements XulToolbar {

  ToolBar toolbar;
  private ToolbarMode mode = ToolbarMode.FULL;

  public SwtToolbar( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "toolbar" );

    String attr = self.getAttributeValue( "parenttoouter" );
    Object shell =
        ( attr != null && attr.equals( "true" ) && domContainer.getOuterContext() != null ) ? domContainer
            .getOuterContext() : null;
    if ( shell != null && shell instanceof Shell ) {
      toolbar = new ToolBar( (Shell) shell, SWT.HORIZONTAL | SWT.RIGHT );
    } else if ( shell != null && shell instanceof Composite ) {
      toolbar = new ToolBar( (Composite) shell, SWT.HORIZONTAL | SWT.RIGHT );
    } else {
      toolbar = new ToolBar( (Composite) parent.getManagedObject(), SWT.HORIZONTAL | SWT.RIGHT );
    }

    setManagedObject( toolbar );
  }

  public String getMode() {
    return mode.toString();
  }

  public String getToolbarName() {
    // TODO Auto-generated method stub
    return null;
  }

  public void setMode( String mode ) {
    this.mode = ToolbarMode.valueOf( mode.toUpperCase() );
  }

  public void setToolbarName( String name ) {
    // TODO Auto-generated method stub

  }

  @Override
  public void layout() {
  }

  @Override
  public void addChild( Element e ) {
    super.addChild( e );
    notifySpacers();
  }

  @Override
  public void addChildAt( Element c, int pos ) {
    super.addChildAt( c, pos );
    notifySpacers();
  }

  @Override
  public void removeChild( Element ele ) {
    super.removeChild( ele );
    notifySpacers();
  }

  private void notifySpacers() {
    for ( XulComponent child : getChildNodes() ) {
      if ( child instanceof SwtToolbarspacer ) {
        ( (SwtToolbarspacer) child ).recalculateSize();
      }
    }
  }
}
