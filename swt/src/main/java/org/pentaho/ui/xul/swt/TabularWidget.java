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


package org.pentaho.ui.xul.swt;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;

public interface TabularWidget {

  public int getItemHeight();

  public void addSelectionListener( SelectionListener listener );

  public Composite getComposite();

  public Object[][] getValues();

  public int getSelectionIndex();

}
