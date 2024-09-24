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

import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.binding.BindingConvertor.Direction;

import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface Binding {

  public enum Type {
    ONE_WAY, BI_DIRECTIONAL
  };

  void setBindingType( Type t );

  Type getBindingType();

  void initialize();

  Object getSource();

  void setSource( Object source );

  Object getTarget();

  void setTarget( Object target );

  String getSourceAttr();

  void setSourceAttr( String sourceAttr );

  String getTargetAttr();

  void setTargetAttr( String targetAttr );

  Object evaluateExpressions( Object val );

  Object doConversions( Object val, Direction dir );

  BindingConvertor getConversion();

  void setConversion( BindingConvertor conversion );

  boolean isReverseConversion();

  void setReverseConversion( boolean reverseConversion );

  void fireSourceChanged() throws IllegalArgumentException, XulException, InvocationTargetException;

  void bindForward();

  void bindReverse();

  void destroyBindings();

  PropertyChangeListener getForwardListener();

  void setForwardListener( PropertyChangeListener forwardListener );

  PropertyChangeListener getReverseListener();

  void setReverseListener( PropertyChangeListener reverseListener );

  List<PropertyChangeListener> getListeneners();

  BindingContext getContext();

  void setContext( BindingContext context );

  void setExceptionHandler( BindingExceptionHandler handler );

}
