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


package org.pentaho.ui.xul.jface.tags;

import org.eclipse.jface.action.IMenuManager;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuseparator;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;

public class JfaceMenuseparator extends SwtElement implements XulMenuseparator {

  private static int separatorSerialNo = 0;

  public JfaceMenuseparator( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "menuseparator" );

    CustomSeparator separator = new CustomSeparator();

    String id = getId();
    if ( id == null ) {
      id = "menuseparator-" + separatorSerialNo;
      separatorSerialNo++;
    }
    separator.setId( id );

    IMenuManager manager = (IMenuManager) parent.getManagedObject();

    manager.add( separator );

    separatorSerialNo++;
  }

}
