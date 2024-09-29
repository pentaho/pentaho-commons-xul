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


package org.pentaho.ui.xul.gwt.binding;

import org.pentaho.ui.xul.XulException;

public interface GwtBindingMethod {
  public Object invoke( Object obj, Object[] args ) throws XulException;
}
