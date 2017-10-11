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

package org.pentaho.ui.xul.dom;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomException;

import java.util.List;

/**
 * @author NBaker
 * 
 */
public interface Element {
  public String getText();

  public String getName();

  public Document getDocument();

  public XulComponent getParent();

  public XulComponent getFirstChild();

  public List<XulComponent> getChildNodes();

  public void setNamespace( String prefix, String uri );

  public Namespace getNamespace();

  public XulComponent getElementById( String id );

  public XulComponent getElementByXPath( String path );

  public List<XulComponent> getElementsByTagName( String tagName );

  public void addChild( Element element );

  public void addChildAt( Element element, int idx );

  public void removeChild( Element element );

  public Object getElementObject();

  public List<Attribute> getAttributes();

  public void setAttributes( List<Attribute> attribute );

  public void setAttribute( Attribute attribute );

  public void setAttribute( String name, String value );

  public String getAttributeValue( String attributeName );

  public void replaceChild( XulComponent oldElement, XulComponent newElement ) throws XulDomException;

}
