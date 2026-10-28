/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 - 2026 by Pentaho Canada Inc. : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2030-06-15
 ******************************************************************************/



package org.pentaho.ui.xul.gwt.overlay;

import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;

/**
 * User: nbaker Date: 10/12/11
 */
public class RemoveOverlayAction implements IOverlayAction {

  private Element element;
  private Element parent;
  private int oldPos;

  public RemoveOverlayAction( Element element ) {
    this.element = element;
    this.parent = element.getParent();
  }

  public void perform() {
    oldPos = parent.getChildNodes().indexOf( element );
    parent.removeChild( element );
    ( (AbstractGwtXulComponent) parent ).layout();
  }

  public void remove() {
    parent.addChildAt( element, oldPos );
    ( (AbstractGwtXulComponent) parent ).layout();
  }
}
