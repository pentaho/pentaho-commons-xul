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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulEventSource;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.binding.BindingConvertor.Direction;
import org.pentaho.ui.xul.dom.Document;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DefaultBinding implements Binding {

  protected Reference source;

  protected Reference target;

  protected String sourceAttr;

  protected String targetAttr;

  protected boolean negateBooleanAssignment = false;

  protected boolean reverseConversion = false;

  protected BindingConvertor conversion;

  protected PropertyChangeListener forwardListener, reverseListener;

  protected Stack<Method> getterMethods = new Stack<Method>();

  protected Method sourceGetterMethod, targetGetterMethod;

  protected BindingContext context;

  protected static final Log logger = LogFactory.getLog( DefaultBinding.class );

  protected boolean destroyed = false;

  protected Type bindingStrategy = Type.BI_DIRECTIONAL;

  private BindingExceptionHandler exceptionHandler;

  public DefaultBinding() {

  }

  @Deprecated
  public DefaultBinding( Document document, String sourceId, String sourceAttr, String targetId, String targetAttr ) {
    this.source = new WeakReference<XulComponent>( document.getElementById( sourceId ) );
    setSourceAttr( sourceAttr );
    this.target = new WeakReference<XulComponent>( document.getElementById( targetId ) );
    setTargetAttr( targetAttr );
  }

  @Deprecated
  public DefaultBinding( Document document, Object source, String sourceAttr, String targetId, String targetAttr ) {
    this.source = new WeakReference<Object>( source );
    setSourceAttr( sourceAttr );
    this.target = new WeakReference<XulComponent>( document.getElementById( targetId ) );
    setTargetAttr( targetAttr );
  }

  @Deprecated
  public DefaultBinding( Document document, String sourceId, String sourceAttr, Object target, String targetAttr ) {
    this.source = new WeakReference<XulComponent>( document.getElementById( sourceId ) );
    setSourceAttr( sourceAttr );
    this.target = new WeakReference<Object>( target );
    setTargetAttr( targetAttr );
  }

  public DefaultBinding( Object source, String sourceAttr, Object target, String targetAttr ) {
    this.source = new WeakReference<Object>( source );
    setSourceAttr( sourceAttr );
    this.target = new WeakReference<Object>( target );
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

  public Reference getSource() {
    return source;
  }

  public void setSource( Object source ) {

    this.source = new WeakReference( source );
  }

  public Reference getTarget() {

    return target;
  }

  public void setTarget( Object target ) {

    this.target = new WeakReference( target );
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
      Object getRetVal = sourceGetterMethod.invoke( getSource().get() );
      forwardListener.propertyChange( new PropertyChangeEvent( getSource(), getSourceAttr(), null, getRetVal ) );
    } catch ( IllegalAccessException e ) {
      // TODO: re-implement IllegalAccessException.
      // cannot be in interface due to GWT incompatibility.
      handleException( new BindingException( e ) );
    }
  }

  public void bindForward() {
    setForwardListener( setupBinding( getSource(), getSourceAttr(), getTarget(), getTargetAttr(), Direction.FORWARD ) );
    sourceGetterMethod = getterMethods.pop();
    logger.debug( "Forward binding established: " + source.get() + "." + sourceAttr + " ==> " + target.get() + "."
        + targetAttr );
  }

  public void bindReverse() {
    setReverseListener( setupBinding( getTarget(), getTargetAttr(), getSource(), getSourceAttr(), Direction.BACK ) );
    targetGetterMethod = getterMethods.pop();
    logger.debug( "Reverse binding established: " + source.get() + "." + sourceAttr + " <== " + target.get() + "."
        + targetAttr );
  }

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
            Object targetObject = b.get();
            if ( targetObject == null ) {
              logger.debug( "Binding target was Garbage Collected, removing propListener" );
              DefaultBinding.this.destroyBindings();
              return;
            }

            Object value = doConversions( evt.getNewValue(), dir );
            final Object finalVal = evaluateExpressions( value );

            logger.debug( "Setting val: " + finalVal + " on: " + targetObject );
            targetSetMethod.invoke( targetObject, finalVal );

          } catch ( Exception e ) {
            logger.debug( e );
            handleException( new BindingException( "Error invoking setter method [" + targetSetMethod.getName()
                + "] on target: " + target.get(), e ) );
          }
        }
      }
    };
    ( (XulEventSource) a.get() ).addPropertyChangeListener( listener );

    return listener;
  }

  protected void handleException( BindingException exception ) {
    if ( exceptionHandler != null ) {
      exceptionHandler.handleException( exception );
    } else {
      throw exception;
    }
  }

  public void destroyBindings() {
    if ( destroyed ) {
      // circular catch from context.remove()
      return;
    }
    Object sourceObj = getSource().get();
    Object targetObj = getTarget().get();

    if ( forwardListener != null && sourceObj != null && sourceObj instanceof XulEventSource ) {
      ( (XulEventSource) sourceObj ).removePropertyChangeListener( forwardListener );
      logger.debug( "Removing forward binding on " + sourceObj );
    }

    if ( reverseListener != null && targetObj != null && targetObj instanceof XulEventSource ) {
      ( (XulEventSource) targetObj ).removePropertyChangeListener( reverseListener );
      logger.debug( "Removing reverse binding on " + targetObj );
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

}
