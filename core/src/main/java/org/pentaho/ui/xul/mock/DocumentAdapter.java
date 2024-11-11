/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


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
