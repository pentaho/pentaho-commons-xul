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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulToolbaritem;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;

public class SwtToolbaritem extends AbstractSwtXulContainer implements XulToolbaritem {

  private XulComponent parent;
  private XulDomContainer container;
  private Composite panel;
  private ToolItem item;
  private ToolBar toolbar;
  private final int MARGIN_VALUE = 3;
  private static final Log logger = LogFactory.getLog( SwtToolbaritem.class );

  public SwtToolbaritem( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "treeitem" );

    toolbar = (ToolBar) parent.getManagedObject();
    this.parent = parent;
    this.container = domContainer;

    item = new ToolItem( (ToolBar) parent.getManagedObject(), SWT.SEPARATOR );
    setManagedObject( parent.getManagedObject() );

  }

  /**
   * Get item method required because managedObject is parent object.
   */
  public ToolItem getItem() {
    return item;
  }

  @Override
  public void layout() {
    if ( getChildNodes().size() > 0 ) {
      XulComponent c = getChildNodes().get( 0 );
      Control control = (Control) c.getManagedObject();
      control.pack();
      item.setWidth( control.getSize().x );
      item.setControl( control );
    }
    ( (ToolBar) parent.getManagedObject() ).pack();
  }

  public void setFlex( int flex ) {
    super.setFlex( flex );

    if ( getFlex() > 0 ) {
      // only support one flexible spacer per toolbar for now.
      toolbar.addControlListener( new ControlAdapter() {

        @Override
        public void controlResized( ControlEvent arg0 ) {
          int totalWidth = toolbar.getBounds().width;
          int childTotalWidth = 0;
          for ( ToolItem i : toolbar.getItems() ) {
            if ( i != item ) {
              childTotalWidth += i.getBounds().width + MARGIN_VALUE;
            }
          }
          item.setWidth( Math.max( 0, totalWidth - childTotalWidth ) );
        }

      } );
    }
  }
}
