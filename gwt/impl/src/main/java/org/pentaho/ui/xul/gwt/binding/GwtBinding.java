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

package org.pentaho.ui.xul.gwt.binding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.pentaho.ui.xul.XulEventSource;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.binding.BindingContext;
import org.pentaho.ui.xul.binding.BindingConvertor;
import org.pentaho.ui.xul.binding.BindingException;
import org.pentaho.ui.xul.binding.BindingExceptionHandler;
import org.pentaho.ui.xul.binding.BindingConvertor.Direction;
import org.pentaho.ui.xul.dom.Document;

public class GwtBinding implements Binding {
  protected Object source;

  protected Object target;

  protected String sourceAttr;

  protected String targetAttr;

  protected boolean negateBooleanAssignment = false;

  protected boolean reverseConversion = false;

  protected BindingConvertor conversion;

  protected PropertyChangeListener forwardListener, reverseListener;

  protected Stack<GwtBindingMethod> getterMethods = new Stack<GwtBindingMethod>();

  protected GwtBindingMethod sourceGetterMethod, targetGetterMethod;

  protected BindingContext context;

  protected boolean destroyed = false;

  protected Type bindingStrategy = Type.BI_DIRECTIONAL;

  protected BindingExceptionHandler exceptionHandler;

  public GwtBinding() {

  }

  @Deprecated
  public GwtBinding( Document document, String sourceId, String sourceAttr, String targetId, String targetAttr ) {
    this.source = document.getElementById( sourceId );
    setSourceAttr( sourceAttr );
    this.target = document.getElementById( targetId );
    setTargetAttr( targetAttr );
  }

  @Deprecated
  public GwtBinding( Document document, Object source, String sourceAttr, String targetId, String targetAttr ) {
    this.source = source;
    setSourceAttr( sourceAttr );
    this.target = document.getElementById( targetId );
    setTargetAttr( targetAttr );
  }

  @Deprecated
  public GwtBinding( Document document, String sourceId, String sourceAttr, Object target, String targetAttr ) {
    this.source = document.getElementById( sourceId );
    setSourceAttr( sourceAttr );
    this.target = target;
    setTargetAttr( targetAttr );
  }

  public GwtBinding( Object source, String sourceAttr, Object target, String targetAttr ) {
    this.source = source;
    setSourceAttr( sourceAttr );
    this.target = target;
    setTargetAttr( targetAttr );
  }

  public void setBindingType( Type t ) {
    this.bindingStrategy = t;
  }

  public Type getBindingType() {
    return bindingStrategy;
  }

  public void initialize() {
    bindForward();

    if ( getBindingType() == Binding.Type.BI_DIRECTIONAL ) {
      bindReverse();
    }
  }

  public Object getSource() {
    return source;
  }

  public void setSource( Object source ) {

    this.source = source;
  }

  public Object getTarget() {

    return target;
  }

  public void setTarget( Object target ) {

    this.target = target;
  }

  public String getSourceAttr() {

    return sourceAttr;
  }

  public void setSourceAttr( String sourceAttr ) {

    if ( sourceAttr.charAt( 0 ) == '!' ) {
      // Negation of Boolean
      negateBooleanAssignment = !negateBooleanAssignment; // two negations will cancel
      sourceAttr = sourceAttr.substring( 1 );
    }
    this.sourceAttr = sourceAttr;

  }

  public String getTargetAttr() {

    return targetAttr;
  }

  public void setTargetAttr( String targetAttr ) {

    if ( targetAttr.charAt( 0 ) == '!' ) {
      // Negation of Boolean
      negateBooleanAssignment = !negateBooleanAssignment; // two negations will cancel
      targetAttr = targetAttr.substring( 1 );
    }
    this.targetAttr = targetAttr;
  }

  public Object evaluateExpressions( Object val ) {
    if ( negateBooleanAssignment && val instanceof Boolean ) {
      return !( (Boolean) val );
    }
    return val;
  }

  public Object doConversions( Object val, Direction dir ) {
    if ( conversion != null ) {
      return ( dir == Direction.FORWARD ) ? conversion.sourceToTarget( val ) : conversion.targetToSource( val );
    }
    return val;
  }

  public BindingConvertor getConversion() {
    return conversion;
  }

  public void setConversion( BindingConvertor conversion ) {
    this.conversion = conversion;
  }

  public boolean isReverseConversion() {

    return reverseConversion;
  }

  public void setReverseConversion( boolean reverseConversion ) {

    this.reverseConversion = reverseConversion;
  }

  public void fireSourceChanged() throws IllegalArgumentException, XulException, InvocationTargetException {
    try {
      Object getRetVal = sourceGetterMethod.invoke( source, new Object[] {} );
      if ( getRetVal != null ) {
        forwardListener.propertyChange( new PropertyChangeEvent( getSource(), getSourceAttr(), null, getRetVal ) );
      }
    } catch ( Exception e ) {
      // TODO: re-implement IllegalAccessException.
      // cannot be in interface due to GWT incompatibility.
      handleException( new BindingException( e ) );
    }
  }

  public void bindForward() {
    setForwardListener( setupBinding( getSource(), getSourceAttr(), getTarget(), getTargetAttr(), Direction.FORWARD ) );
    sourceGetterMethod = getterMethods.pop();
    // System.out.println("Forward binding established: " + source + "." + sourceAttr + " ==> " + target + "." +
    // targetAttr);
  }

  public void bindReverse() {
    setReverseListener( setupBinding( getTarget(), getTargetAttr(), getSource(), getSourceAttr(), Direction.BACK ) );
    targetGetterMethod = getterMethods.pop();
    // System.out.println("Reverse binding established: " + source + "." + sourceAttr + " <== " + target + "." +
    // targetAttr);
  }

  protected PropertyChangeListener setupBinding( final Object a, final String va, final Object b, final String vb,
      final Direction dir ) {
    if ( a == null || va == null ) {
      handleException( new BindingException( "source bean or property is null" ) );
    }
    if ( !( a instanceof XulEventSource ) ) {
      handleException( new BindingException( "Binding error, source object " + a + " not a XulEventSource instance" ) );
    }
    if ( b == null || vb == null ) {
      handleException( new BindingException( "target bean or property is null" ) );
    }
    GwtBindingMethod sourceGetMethod = GwtBindingContext.typeController.findGetMethod( a, va );

    getterMethods.push( sourceGetMethod );

    // find set method
    final GwtBindingMethod targetSetMethod = GwtBindingContext.typeController.findSetMethod( b, vb );

    if ( targetSetMethod == null ) {
      throw new BindingException( "Error finding setter method [" + vb + "] on target: " + b );

    }

    // setup prop change listener to handle binding
    PropertyChangeListener listener = new PropertyChangeListener() {
      public void propertyChange( final PropertyChangeEvent evt ) {
        final PropertyChangeListener cThis = this;
        if ( evt.getPropertyName().equalsIgnoreCase( va ) ) {
          Object finalVal = null;
          try {
            Object value = doConversions( evt.getNewValue(), dir );
            finalVal = evaluateExpressions( value );

            Object targetObject = b;
            if ( targetObject == null ) {
              System.out.println( "Binding target was Garbage Collected, removing propListener" );
              GwtBinding.this.destroyBindings();
              return;
            }
            // System.out.println("Setting val: "+finalVal+" on: "+targetObject +" prop: "+vb);
            if ( targetSetMethod == null ) {
              System.out.println( "Error Setting val targetMethod null: " + finalVal + " on: " + targetObject + "."
                  + GwtBinding.this.targetAttr );
            }
            if ( targetObject == null ) {
              System.out.println( "Error Setting val targetObject null: " + finalVal + " on: " + targetObject + "."
                  + GwtBinding.this.targetAttr );
            }
            targetSetMethod.invoke( targetObject, new Object[] { finalVal } );

          } catch ( Exception e ) {
            e.printStackTrace();
            handleException( new BindingException( "Error invoking setter method for property [" + targetAttr
                + "] on target [" + b + "] with arg [" + finalVal + "] which is of type [" + finalVal == null ? "null"
                : finalVal.getClass().getName() + "]", e ) );
          }
        }
      }
    };
    ( (XulEventSource) a ).addPropertyChangeListener( listener );

    return listener;
  }

  public void destroyBindings() {
    if ( destroyed ) {
      // circular catch from context.remove()
      return;
    }
    Object sourceObj = getSource();
    Object targetObj = getTarget();

    if ( forwardListener != null && sourceObj != null && sourceObj instanceof XulEventSource ) {
      ( (XulEventSource) sourceObj ).removePropertyChangeListener( forwardListener );
      System.out.println( "Removing forward binding on " + sourceObj );
    }

    if ( reverseListener != null && targetObj != null && targetObj instanceof XulEventSource ) {
      ( (XulEventSource) targetObj ).removePropertyChangeListener( reverseListener );
      System.out.println( "Removing reverse binding on " + targetObj );
    }

    setDestroyed( true );
    if ( context != null ) {
      context.remove( this );
    }
  }

  protected void setDestroyed( boolean flag ) {
    this.destroyed = flag;
  }

  public PropertyChangeListener getForwardListener() {
    return forwardListener;
  }

  public void setForwardListener( PropertyChangeListener forwardListener ) {
    this.forwardListener = forwardListener;
  }

  public PropertyChangeListener getReverseListener() {
    return reverseListener;
  }

  public void setReverseListener( PropertyChangeListener reverseListener ) {
    this.reverseListener = reverseListener;
  }

  public List<PropertyChangeListener> getListeneners() {
    List<PropertyChangeListener> l = new ArrayList<PropertyChangeListener>();
    if ( forwardListener != null ) {
      l.add( forwardListener );
    }
    if ( reverseListener != null ) {
      l.add( reverseListener );
    }
    return l;
  }

  public BindingContext getContext() {
    return context;
  }

  public void setContext( BindingContext context ) {
    this.context = context;
  }

  public void setExceptionHandler( BindingExceptionHandler handler ) {
    this.exceptionHandler = handler;
  }

  protected void handleException( BindingException exception ) {
    if ( exceptionHandler != null ) {
      exceptionHandler.handleException( exception );
    } else {
      throw exception;
    }
  }

}
