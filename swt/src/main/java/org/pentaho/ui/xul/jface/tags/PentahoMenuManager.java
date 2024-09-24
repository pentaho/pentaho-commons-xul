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
