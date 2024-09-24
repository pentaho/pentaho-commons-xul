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
