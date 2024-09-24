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
