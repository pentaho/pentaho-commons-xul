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

package org.pentaho.ui.xul.swt;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.ref.Reference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.swt.widgets.Display;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulEventSource;
import org.pentaho.ui.xul.binding.BindingConvertor.Direction;
import org.pentaho.ui.xul.binding.BindingException;
import org.pentaho.ui.xul.binding.BindingUtil;
import org.pentaho.ui.xul.binding.DefaultBinding;
import org.pentaho.ui.xul.dom.Document;

public class SwtBinding extends DefaultBinding {

  @Deprecated
  public SwtBinding( Document document, String sourceId, String sourceAttr, String targetId, String targetAttr ) {
    super( document, sourceId, sourceAttr, targetId, targetAttr );
  }

  @Deprecated
  public SwtBinding( Document document, Object source, String sourceAttr, String targetId, String targetAttr ) {
    super( document, source, sourceAttr, targetId, targetAttr );
  }

  @Deprecated
  public SwtBinding( Document document, String sourceId, String sourceAttr, Object target, String targetAttr ) {
    super( document, sourceId, sourceAttr, target, targetAttr );
  }

  public SwtBinding( Object source, String sourceAttr, Object target, String targetAttr ) {
    super( source, sourceAttr, target, targetAttr );
  }

  @Override
  protected PropertyChangeListener setupBinding( final Reference a, final String va, final Reference b,
      final String vb, final Direction dir ) {
    if ( a.get() == null || va == null ) {
      handleException( new BindingException( "source bean or property is null" ) );
    }
    if ( !( a.get() instanceof XulEventSource ) ) {
      handleException( new BindingException( "Binding error, source object " + a.get()
          + " not a XulEventSource instance" ) );
    }
    if ( b.get() == null || vb == null ) {
      handleException( new BindingException( "target bean or property is null" ) );
    }
    Method sourceGetMethod = BindingUtil.findGetMethod( a.get(), va );

    Class getterClazz = BindingUtil.getMethodReturnType( sourceGetMethod, a.get() );
    getterMethods.push( sourceGetMethod );

    // find set method
    final Method targetSetMethod = BindingUtil.findSetMethod( b.get(), vb, getterClazz );

    // setup prop change listener to handle binding
    PropertyChangeListener listener = new PropertyChangeListener() {
      public void propertyChange( final PropertyChangeEvent evt ) {
        final PropertyChangeListener cThis = this;
        if ( evt.getPropertyName().equalsIgnoreCase( va ) ) {
          try {
            Object value = doConversions( evt.getNewValue(), dir );
            final Object finalVal = evaluateExpressions( value );

            if ( !Display.getDefault().getThread().equals( Thread.currentThread() ) && b.get() instanceof XulComponent ) {
              logger.error( "Binding Error! Update to XulComponenet (" + target.get() + "," + targetAttr
                  + ") outside of event thread!" );

              Display display = Display.getCurrent();
              if ( display == null ) {
                display = Display.getDefault();
              }
              display.syncExec( new Runnable() {
                public void run() {
                  try {
                    Object targetObject = b.get();
                    if ( targetObject == null ) {
                      logger.error( "Binding target was Garbage Collected, removing propListener" );
                      destroyBindings();
                      return;
                    }
                    targetSetMethod.invoke( targetObject, finalVal );
                  } catch ( InvocationTargetException e ) {
                    handleException( new BindingException( "Error invoking setter method [" + targetSetMethod.getName()
                        + "] on target: " + target, e ) );
                  } catch ( IllegalAccessException e ) {
                    handleException( new BindingException( "Error invoking setter method [" + targetSetMethod.getName()
                        + "] on target: " + target, e ) );
                  }
                }
              } );

              return;
            }

            Object targetObject = b.get();
            if ( targetObject == null ) {
              logger.debug( "Binding target was Garbage Collected, removing propListener" );
              destroyBindings();
              return;
            }
            logger.debug( "Setting val: " + finalVal + " on: " + targetObject );
            targetSetMethod.invoke( targetObject, finalVal );

          } catch ( Exception e ) {
            handleException( new BindingException( "Error invoking setter method [" + targetSetMethod.getName()
                + "] on target: " + target.get(), e ) );
          }
        }
      }
    };
    ( (XulEventSource) a.get() ).addPropertyChangeListener( listener );

    return listener;
  }

}
