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

package org.pentaho.ui.xul.binding;

import org.pentaho.ui.xul.XulEventSource;

/**
 * User: nbaker Date: Jun 28, 2010
 */
public abstract class FactoryBasedBindingProvider implements BindingProvider {

  private BindingFactory bf;

  public FactoryBasedBindingProvider( BindingFactory bf ) {
    this.bf = bf;
  }

  public Binding getBinding( XulEventSource source, String prop1, XulEventSource target, String prop2 ) {
    Binding binding = bf.createBinding( source, prop1, target, prop2 );

    BindingConvertor conv = getConvertor( source, prop1, target, prop2 );
    if ( conv != null ) {
      binding.setConversion( conv );
    }
    return binding;
  }

  public Binding getBinding( XulEventSource source, String prop1, XulEventSource target, String prop2,
      BindingConvertor<?, ?> defaultConvertor ) {
    Binding binding = bf.createBinding( source, prop1, target, prop2 );
    BindingConvertor conv = getConvertor( source, prop1, target, prop2 );

    binding.setConversion( ( conv != null ) ? conv : defaultConvertor );
    return binding;
  }

  public abstract BindingConvertor getConvertor( XulEventSource source, String prop1, XulEventSource target,
      String prop2 );

}
