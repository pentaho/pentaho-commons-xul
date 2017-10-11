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

package org.pentaho.ui.xul.gwt.binding;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.binding.BindingContext;
import org.pentaho.ui.xul.binding.BindingException;

import com.google.gwt.core.client.GWT;

public class GwtBindingContext implements BindingContext {

  private List<Binding> bindings = new ArrayList<Binding>();

  public static TypeController typeController;
  static {
    try {
      typeController = GWT.create( TypeController.class );
    } catch ( Exception e ) {
      System.out.println( e );
      e.printStackTrace();
    }
  }

  // internal map of Binding to PropChangeListeners, used to cleanup upon removal
  private Map<Binding, List<PropertyChangeListener>> bindingListeners =
      new HashMap<Binding, List<PropertyChangeListener>>();

  public GwtBindingContext() {
  }

  public void add( XulComponent source, String expr ) {
    // BindingExpression expression = BindingExpression.parse(expr);
    // XulComponent target = container.getDocumentRoot().getElementById(expression.target);
    //
    // TODO: bindingFactory.createBinding
    // Binding newBinding = new GwtBinding(source, expression.sourceAttr, target, expression.targetAttr);
    // add(newBinding);
  }

  public void remove( Binding bind ) {
    if ( !bindingListeners.containsKey( bind ) && !bindings.contains( bind ) ) {
      return;
    }
    bind.destroyBindings();
    bindingListeners.remove( bind );
    bindings.remove( bind );

  }

  /*
   * 1. inits the binding defined by the bind object 2. registers binding listeners
   */
  public void add( Binding bind ) {
    try {
      bindings.add( bind );
      bind.bindForward();

      if ( !bindingListeners.containsKey( bind ) ) {
        bindingListeners.put( bind, new ArrayList<PropertyChangeListener>() );
      }
      bindingListeners.get( bind ).add( bind.getForwardListener() );

      if ( bind.getBindingType() == Binding.Type.BI_DIRECTIONAL ) {
        bind.bindReverse();
        bindingListeners.get( bind ).add( bind.getReverseListener() );
      }

      bind.setContext( this );
    } catch ( Throwable t ) {
      throw new BindingException( "Binding failed: " + bind.getSource() + "." + bind.getSourceAttr() + " <==> "
          + bind.getTarget() + "." + bind.getTargetAttr(), t );
    }
  }

  public void addInitializedBinding( Binding binding ) {
    try {
      bindings.add( binding );

      if ( !bindingListeners.containsKey( binding ) ) {
        bindingListeners.put( binding, new ArrayList<PropertyChangeListener>() );
      }
      bindingListeners.get( binding ).addAll( binding.getListeneners() );

      binding.setContext( this );
    } catch ( Throwable t ) {
      throw new BindingException( "Binding failed: " + binding.getSource() + "." + binding.getSourceAttr() + " <==> "
          + binding.getTarget() + "." + binding.getTargetAttr(), t );
    }
  }
}
