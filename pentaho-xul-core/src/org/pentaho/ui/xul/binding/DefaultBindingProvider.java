package org.pentaho.ui.xul.binding;

import org.pentaho.ui.xul.XulEventSource;

/**
 * User: nbaker
 * Date: Jun 28, 2010
 */
public class DefaultBindingProvider implements BindingProvider{

  public Binding getBinding( XulEventSource source, String prop1, XulEventSource target, String prop2 ) {
    return new DefaultBinding(source, prop1, target, prop2);
  }

  public Binding getBinding( XulEventSource source, String prop1, XulEventSource target, String prop2,
                             BindingConvertor<?, ?> defaultConvertor ) {
    DefaultBinding binding = new DefaultBinding(source, prop1, target, prop2);
    binding.setConversion(defaultConvertor);
    return binding;
  }
}
