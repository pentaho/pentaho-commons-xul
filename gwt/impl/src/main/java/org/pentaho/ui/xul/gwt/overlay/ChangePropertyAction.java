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


package org.pentaho.ui.xul.gwt.overlay;

import org.pentaho.ui.xul.dom.Element;

/**
 * User: nbaker Date: 10/25/11
 */
public class ChangePropertyAction implements IOverlayAction {

  private final Element ele;
  private final String property;
  private final String value;
  private String prevVal;

  public ChangePropertyAction( Element ele, String property, String value ) {

    this.ele = ele;
    this.property = property;
    this.value = value;
  }

  public void perform() {
    prevVal = ele.getAttributeValue( property );
    ele.setAttribute( property, value );

  }

  public void remove() {
    ele.setAttribute( property, prevVal );
  }
}
