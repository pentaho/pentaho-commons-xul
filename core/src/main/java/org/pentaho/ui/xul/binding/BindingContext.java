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



package org.pentaho.ui.xul.binding;

import org.pentaho.ui.xul.XulComponent;

public interface BindingContext {

  public abstract void add( XulComponent source, String expr );

  public abstract void remove( Binding bind );

  /*
   * 1. inits the binding defined by the bind object 2. registers binding listeners
   */
  public abstract void add( Binding bind );

  public abstract void addInitializedBinding( Binding binding );

}
