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

package org.pentaho.ui.xul.swing;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.ref.Reference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulEventSource;
import org.pentaho.ui.xul.binding.BindingConvertor.Direction;
import org.pentaho.ui.xul.binding.BindingException;
import org.pentaho.ui.xul.binding.BindingUtil;
import org.pentaho.ui.xul.binding.DefaultBinding;
import org.pentaho.ui.xul.dom.Document;

public class SwingBinding extends DefaultBinding {

  public SwingBinding( Document document, String sourceId, String sourceAttr, String targetId, String targetAttr ) {
    super( document, sourceId, sourceAttr, targetId, targetAttr );
  }

  public SwingBinding( Document document, Object source, String sourceAttr, String targetId, String targetAttr ) {
    super( document, source, sourceAttr, targetId, targetAttr );
  }

  public SwingBinding( Document document, String sourceId, String sourceAttr, Object target, String targetAttr ) {
    super( document, sourceId, sourceAttr, target, targetAttr );
  }

  public SwingBinding( Object source, String sourceAttr, Object target, String targetAttr ) {
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

            value = evaluateExpressions( value );
            // handle String specially
            if ( value != null && targetSetMethod.getParameterTypes()[0] == String.class
                && targetSetMethod.getParameterTypes()[0].isAssignableFrom( value.getClass() ) == false ) {
              value = value.toString();
            }

            final Object finalVal = value;

            if ( !EventQueue.isDispatchThread() && b.get() instanceof XulComponent ) {
              logger.error( "Binding Error! Update to XulComponenet (" + target.get() + "," + targetAttr
                  + ") outside of event thread!" );

              EventQueue.invokeLater( new Runnable() {
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

            }

            Object targetObject = b.get();
            if ( targetObject == null ) {
              logger.debug( "Binding target was Garbage Collected, removing propListener" );
              destroyBindings();
              return;
            }
            logger.info( "Setting val: " + finalVal + " on: " + targetObject );
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
