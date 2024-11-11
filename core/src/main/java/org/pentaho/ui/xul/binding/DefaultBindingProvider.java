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
public class DefaultBindingProvider implements BindingProvider {

  public Binding getBinding( XulEventSource source, String prop1, XulEventSource target, String prop2 ) {
    return new DefaultBinding( source, prop1, target, prop2 );
  }

  public Binding getBinding( XulEventSource source, String prop1, XulEventSource target, String prop2,
      BindingConvertor<?, ?> defaultConvertor ) {
    DefaultBinding binding = new DefaultBinding( source, prop1, target, prop2 );
    binding.setConversion( defaultConvertor );
    return binding;
  }
}
