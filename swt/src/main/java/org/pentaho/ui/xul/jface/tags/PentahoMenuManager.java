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


package org.pentaho.ui.xul.jface.tags;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class PentahoMenuManager extends MenuManager {

  public PentahoMenuManager( String text, String id ) {
    super( text, id );
  }

  public PentahoMenuManager( String text ) {
    super( text );
  }

  private boolean enabled = true;

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled( boolean enabled ) {
    this.enabled = enabled;
  }

  @Override
  public void update( boolean force ) {
    update( force, false );
  }

  @Override
  protected void update( boolean force, boolean recursive ) {
    super.update( force, recursive );
    updateMenuItem2();
  }

  @Override
  public void update() {
    updateMenuItem2();
  }

  private void updateMenuItem2() {
    Menu menu = getMenu();
    MenuItem menuItem = menu.getParentItem();
    if ( menuItem != null && !menuItem.isDisposed() && menu != null && !menu.isDisposed() ) {
      if ( menuItem.getEnabled() != enabled ) {
        menuItem.setEnabled( enabled );
        /*
         * Menu topMenu = menu; while (topMenu.getParentMenu() != null) { topMenu = topMenu.getParentMenu(); } if
         * (topMenu != null) { menuItem.setEnabled(enabled); }
         */
      }
    }
  }

}
