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
