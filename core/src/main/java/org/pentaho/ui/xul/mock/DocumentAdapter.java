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

package org.pentaho.ui.xul.mock;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.dom.Attribute;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.dom.Namespace;

import java.util.List;

public abstract class DocumentAdapter implements Document {

  public void addBinding( Binding bind ) {
    // TODO Auto-generated method stub

  }

  public void addOverlay( String src ) throws XulException {
    // TODO Auto-generated method stub

  }

  public XulComponent createElement( String elementName ) throws XulException {
    // TODO Auto-generated method stub
    return null;
  }

  public XulComponent getRootElement() {
    // TODO Auto-generated method stub
    return null;
  }

  public void invokeLater( Runnable runnable ) {
    // TODO Auto-generated method stub

  }

  public boolean isRegistered( String elementName ) {
    // TODO Auto-generated method stub
    return false;
  }

  public void loadFragment( String id, String src ) throws XulException {
    // TODO Auto-generated method stub

  }

  public void removeOverlay( String src ) throws XulException {
    // TODO Auto-generated method stub

  }

  public void setXulDomContainer( XulDomContainer container ) {
    // TODO Auto-generated method stub

  }

  public void addChild( Element element ) {
    // TODO Auto-generated method stub

  }

  public void addChildAt( Element element, int idx ) {
    // TODO Auto-generated method stub

  }

  public String getAttributeValue( String attributeName ) {
    // TODO Auto-generated method stub
    return null;
  }

  public List<Attribute> getAttributes() {
    // TODO Auto-generated method stub
    return null;
  }

  public List<XulComponent> getChildNodes() {
    // TODO Auto-generated method stub
    return null;
  }

  public Document getDocument() {
    // TODO Auto-generated method stub
    return null;
  }

  public XulComponent getElementById( String id ) {
    // TODO Auto-generated method stub
    return null;
  }

  public XulComponent getElementByXPath( String path ) {
    // TODO Auto-generated method stub
    return null;
  }

  public Object getElementObject() {
    // TODO Auto-generated method stub
    return null;
  }

  public List<XulComponent> getElementsByTagName( String tagName ) {
    // TODO Auto-generated method stub
    return null;
  }

  public XulComponent getFirstChild() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getName() {
    // TODO Auto-generated method stub
    return null;
  }

  public Namespace getNamespace() {
    // TODO Auto-generated method stub
    return null;
  }

  public XulComponent getParent() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getText() {
    // TODO Auto-generated method stub
    return null;
  }

  public void removeChild( Element element ) {
    // TODO Auto-generated method stub

  }

  public void replaceChild( XulComponent oldElement, XulComponent newElement ) throws XulDomException {
    // TODO Auto-generated method stub

  }

  public void setAttribute( Attribute attribute ) {
    // TODO Auto-generated method stub

  }

  public void setAttribute( String name, String value ) {
    // TODO Auto-generated method stub

  }

  public void setAttributes( List<Attribute> attribute ) {
    // TODO Auto-generated method stub

  }

  public void setNamespace( String prefix, String uri ) {
    // TODO Auto-generated method stub

  }

}
