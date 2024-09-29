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
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulMenubar;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;

public class SwtMenubar extends AbstractSwtXulContainer implements XulMenubar {

  private Menu menuBar;

  private XulComponent parent;

  public SwtMenubar( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "menubar" );

    String attr = self.getAttributeValue( "parenttoouter" );
    Shell shell =
        ( attr != null && attr.equals( "true" ) && domContainer.getOuterContext() != null ) ? (Shell) domContainer
            .getOuterContext() : (Shell) parent.getManagedObject();
    menuBar = new Menu( shell, SWT.BAR );

    shell.setMenuBar( menuBar );
    setManagedObject( menuBar );
    this.parent = parent;
  }

  @Override
  public XulComponent getParent() {
    return parent;
  }

  @Override
  public void layout() {
    // for (XulComponent comp : children) {
    // if (comp instanceof SwtMenu) {
    // this.menuBar.add((JMenu) ((SwingMenu) comp).getManagedObject());
    //
    // }
    // }
    initialized = true;
  }

}
