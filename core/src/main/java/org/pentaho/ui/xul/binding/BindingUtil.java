/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.ui.xul.binding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BindingUtil {
  private static final Log logger = LogFactory.getLog( BindingUtil.class );

  public static List<InlineBindingExpression> getBindingExpressions( String bindingText ) {
    List<InlineBindingExpression> exps = new ArrayList<InlineBindingExpression>();
    for ( String bindingExpText : bindingText.split( "," ) ) {
      exps.add( new InlineBindingExpression( bindingExpText.trim() ) );
    }
    return exps;
  }

  public static Method findGetMethod( Object o, String property ) {
    String methodName = null;
    try {
      methodName = "get" + ( String.valueOf( property.charAt( 0 ) ).toUpperCase() ) + property.substring( 1 );
      Method getMethod = o.getClass().getMethod( methodName );
      return getMethod;
    } catch ( NoSuchMethodException e ) {
      logger.debug( "could not resolve getter method [" + methodName + "] for property [" + property + "] on object ["
          + o.getClass().getName() + "].  Trying to resolve as boolean style getter..." );
      try {
        String isMethodName = "is" + ( String.valueOf( property.charAt( 0 ) ).toUpperCase() ) + property.substring( 1 );
        Method getMethod = o.getClass().getMethod( isMethodName );
        return getMethod;
      } catch ( NoSuchMethodException ex ) {
        throw new BindingException( "Could not resolve getter method for property [" + property + "] on object ["
            + o.getClass().getName() + "]", ex );
      }
    }
  }

  public static Method findSetMethod( Object o, String property, Class paramClass ) {
    String methodName = "set" + ( String.valueOf( property.charAt( 0 ) ).toUpperCase() ) + property.substring( 1 );

    try {
      if ( paramClass != null ) {
        // try to resolve by name and the Type of object returned by the getter
        Method setMethod = o.getClass().getMethod( methodName, paramClass );
        logger.debug( "Found set method by name and type" );
        return setMethod;
      } else {
        return getMethodNameByNameOnly( o, methodName );
      }
    } catch ( Exception e ) {
      try {
        return getMethodNameByNameOnly( o, methodName );
      } catch ( BindingException ex ) {
        throw new BindingException( "Could not resolve setter method for property [" + property + "] on object ["
            + o.getClass().getName() + "] with paramClass: " + paramClass, e );
      }
    }
  }

  public static Class getMethodReturnType( Method method, Object o ) {
    // get class of object returned by getter
    Class getClazz = null;
    Object getRetVal = null;
    try {
      getRetVal = method.invoke( o );
      logger.debug( "Found get Return Value: " + getRetVal );
    } catch ( IllegalAccessException e ) {
      throw new BindingException( "Error invoking getter method [" + method.getName() + "]", e );
    } catch ( InvocationTargetException e ) {
      throw new BindingException( "Error invoking getter method [" + method.getName() + "]", e );
    }
    if ( getRetVal != null ) {
      logger.debug( "Get Return was not null, checking it's type" );
      // checks for Boxed primatives
      getClazz = getObjectClassOrType( getRetVal );
      logger.debug( "Get Return type is: " + getClazz );
    }
    return getClazz;
  }

  private static Class getObjectClassOrType( Object o ) {
    if ( o instanceof Boolean ) {
      return Boolean.TYPE;
    } else if ( o instanceof Integer ) {
      return Integer.TYPE;
    } else if ( o instanceof Float ) {
      return Float.TYPE;
    } else if ( o instanceof Double ) {
      return Double.TYPE;
    } else if ( o instanceof Short ) {
      return Short.TYPE;
    } else if ( o instanceof Long ) {
      return Long.TYPE;
    } else {
      return o.getClass();
    }
  }

  private static Method getMethodNameByNameOnly( Object o, String methodName ) throws BindingException {
    for ( Method m : o.getClass().getMethods() ) {
      // just match on name
      if ( m.getName().equals( methodName ) ) {
        return m;
      }
    }
    throw new BindingException( "Could not resolve method by name either. You're really hosed, check property name" );
  }
}
