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

/**
 * 
 */

package org.pentaho.ui.xul.dom.dom4j;

import org.pentaho.ui.xul.XulComponent;

/**
 * @author OEM
 * 
 */
public class XulElementDom4J extends org.dom4j.tree.BaseElement {
  private static final long serialVersionUID = -2463576912677723967L;

  private XulComponent xulElement;

  public XulElementDom4J() {
    super( "blank" );
  }

  public XulElementDom4J( String name, XulComponent element ) {
    super( name );
    this.xulElement = element;
  }

  public XulElementDom4J( String name ) {
    super( name );
  }

  public XulComponent getXulElement() {
    return xulElement;
  }

  public void setXulElement( XulComponent c ) {
    this.xulElement = c;
  }
}
