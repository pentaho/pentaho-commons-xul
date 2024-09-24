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

package org.pentaho.ui.xul.gwt.overlay;

import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * User: nbaker Date: 11/30/11
 */
public class ChangePropertyGroup implements IOverlayAction {
  private Element targetElement;

  public ChangePropertyGroup( Element target ) {
    this.targetElement = target;
  }

  List<ChangePropertyAction> propChanges = new ArrayList<ChangePropertyAction>();

  public void addAction( ChangePropertyAction action ) {
    propChanges.add( action );
  }

  public void perform() {
    for ( ChangePropertyAction action : propChanges ) {
      action.perform();
    }
    ( (AbstractGwtXulComponent) targetElement ).layout();

  }

  public void remove() {

    for ( ChangePropertyAction action : propChanges ) {
      action.remove();
    }
    ( (AbstractGwtXulComponent) targetElement ).layout();

  }
}
