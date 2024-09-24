/*!
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
 */

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
