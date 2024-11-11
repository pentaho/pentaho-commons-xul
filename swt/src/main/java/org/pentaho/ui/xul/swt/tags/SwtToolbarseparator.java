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


package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulToolbarseparator;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;

public class SwtToolbarseparator extends AbstractSwtXulContainer implements XulToolbarseparator {

  ToolItem spacer;

  public SwtToolbarseparator( Element self, XulComponent parent, XulDomContainer domContainer, String tagName ) {
    super( "toolbarseparator" );

    spacer = new ToolItem( (ToolBar) parent.getManagedObject(), SWT.SEPARATOR );
    setManagedObject( spacer );
  }

}
