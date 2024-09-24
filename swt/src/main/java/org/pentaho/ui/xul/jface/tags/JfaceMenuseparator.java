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

import org.eclipse.jface.action.IMenuManager;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuseparator;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;

public class JfaceMenuseparator extends SwtElement implements XulMenuseparator {

  private static int separatorSerialNo = 0;

  public JfaceMenuseparator( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "menuseparator" );

    CustomSeparator separator = new CustomSeparator();

    String id = getId();
    if ( id == null ) {
      id = "menuseparator-" + separatorSerialNo;
      separatorSerialNo++;
    }
    separator.setId( id );

    IMenuManager manager = (IMenuManager) parent.getManagedObject();

    manager.add( separator );

    separatorSerialNo++;
  }

}
