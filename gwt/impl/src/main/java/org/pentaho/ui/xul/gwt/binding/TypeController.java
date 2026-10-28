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



package org.pentaho.ui.xul.gwt.binding;

public interface TypeController {
  public GwtBindingMethod findGetMethod( Object obj, String propertyName );

  public GwtBindingMethod findSetMethod( Object obj, String propertyName );

  public GwtBindingMethod findMethod( Object obj, String propertyName );

}
