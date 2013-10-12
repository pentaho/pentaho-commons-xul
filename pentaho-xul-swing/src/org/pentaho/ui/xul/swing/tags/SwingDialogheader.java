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
 * Copyright (c) 2002-2013 Pentaho Corporation..  All rights reserved.
 */

package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulDialogheader;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingDialogheader extends SwingElement implements XulDialogheader {

  private String description;
  private String title;

  public SwingDialogheader( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "dialogheader" );
    setManagedObject( "empty" ); // enclosing containers should not try to attach this as a child

  }

  public String getDescription() {
    return description;
  }

  public String getTitle() {
    return title;
  }

  public void setDescription( String desc ) {
    this.description = desc;
  }

  public void setTitle( String title ) {
    this.title = title;
  }

}
