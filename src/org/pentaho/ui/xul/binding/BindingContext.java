package org.pentaho.ui.xul.binding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulEventSource;
import org.pentaho.ui.xul.binding.BindingConvertor.Direction;

public class BindingContext {

  private XulDomContainer container;

  private List<Binding> bindings = new ArrayList<Binding>();

  private static final Log logger = LogFactory.getLog(BindingContext.class);

  public BindingContext(XulDomContainer container) {
    this.container = container;
  }

  public void add(XulComponent source, String expr) {
    BindingExpression expression = BindingExpression.parse(expr);
    XulComponent target = container.getDocumentRoot().getElementById(expression.target);
    Binding newBinding = new Binding(source, expression.sourceAttr, target, expression.targetAttr);
    add(newBinding);
  }

  public void add(Binding bind) {
    try {
      bindings.add(bind);
      //forward binding
      setupBinding(bind, bind.getSource(), bind.getSourceAttr(), bind.getTarget(), bind.getTargetAttr(),
          Direction.FORWARD);

      //reverse binding
      if (bind.getBindingType() == Binding.Type.BI_DIRECTIONAL) {
        logger.info("Bi-directional");
        setupBinding(bind, bind.getTarget(), bind.getTargetAttr(), bind.getSource(), bind.getSourceAttr(),
            Direction.BACK);
      } else {
        logger.info("Uni-directional");
      }
    } catch (Throwable t) {
      throw new BindingException("Binding failed: " + bind.getSource() + "." + bind.getSourceAttr() + " <==> "
          + bind.getTarget() + "." + bind.getTargetAttr(), t);
    }
  }

  private Method findGetMethod(Object o, String property) {
    String methodName = null;
    try {
      methodName = "get" + (String.valueOf(property.charAt(0)).toUpperCase()) + property.substring(1);
      Method getMethod = o.getClass().getMethod(methodName);
      return getMethod;
    } catch (NoSuchMethodException e) {
      logger.debug("could not resolve getter method [" + methodName + "] for property [" + property + "] on object ["
          + o.getClass().getName() + "].  Trying to resolve as boolean style getter...");
      try {
        String isMethodName = "is" + (String.valueOf(property.charAt(0)).toUpperCase()) + property.substring(1);
        Method getMethod = o.getClass().getMethod(isMethodName);
        return getMethod;
      } catch (NoSuchMethodException ex) {
        throw new BindingException("Could not resolve getter method for property [" + property + "] on object ["
            + o.getClass().getName() + "]", ex);
      }
    }
  }

  private Method findSetMethod(Object o, String property, Class paramClass) {
    String methodName = "set" + (String.valueOf(property.charAt(0)).toUpperCase()) + property.substring(1);

    try {
      //try to resolve by name and the Type of object returned by the getter
      Method setMethod = o.getClass().getMethod(methodName, paramClass);
      logger.debug("Found set method by name and type");
      return setMethod;
    } catch (NoSuchMethodException e) {
      logger.debug("could not find set method by name and type, trying name alone");
      //last resort, just return the set method regardless of paramater type (generics workaround)
      for (Method m : o.getClass().getMethods()) {
        //just match on name
        if (m.getName().equals(methodName)) {
          return m;
        }
      }
    }
    throw new BindingException("Could not resolve setter method for property [" + property + "] on object ["
        + o.getClass().getName() + "]");

  }

  private Class getObjectClassOrType(Object o) {
    if (o instanceof Boolean) {
      return Boolean.TYPE;
    } else if (o instanceof Integer) {
      return Integer.TYPE;
    } else if (o instanceof Float) {
      return Float.TYPE;
    } else if (o instanceof Double) {
      return Double.TYPE;
    } else if (o instanceof Short) {
      return Short.TYPE;
    } else if (o instanceof Long) {
      return Long.TYPE;
    } else {
      return o.getClass();
    }
  }

  private void setupBinding(final Binding bind, final XulEventSource source, final String sourceAttr,
      final XulEventSource target, final String targetAttr, final Direction dir) {
    if (source == null || sourceAttr == null) {
      throw new BindingException("source bean or property is null");
    }
    if (target == null || targetAttr == null) {
      throw new BindingException("target bean or property is null");
    }
    Method sourceGetMethod = findGetMethod(source, sourceAttr);

    //get class of object returned by getter
    Class getClazz = null;
    Object getRetVal = null;
    try {
      getRetVal = sourceGetMethod.invoke(source);
      logger.debug("Found get Return Value: " + getRetVal);
    } catch (IllegalAccessException e) {
      /*consume*/
    } catch (InvocationTargetException e) {
      /*consume*/
    }
    if (getRetVal != null) {
      logger.debug("Get Return was not null, checking it's type");
      //checks for Boxed primatives
      getClazz = getObjectClassOrType(getRetVal);
      logger.debug("Get Return type is: " + getClazz);
    }

    //find set method
    final Method targetSetMethod = findSetMethod(target, targetAttr, getClazz);

    //setup prop change listener to handle binding
    PropertyChangeListener listener = new PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equalsIgnoreCase(sourceAttr)) {
          try {
            Object value = bind.evaluateExpressions(evt.getNewValue());
            value = bind.doConversions(value, dir);
            targetSetMethod.invoke(target, value);
          } catch (Exception e) {
            throw new BindingException("Error invoking setter method [" + targetSetMethod.getName() + "]", e);
          }
        }
      }
    };

    source.addPropertyChangeListener(listener);
    logger.info("Binding established: " + source + "." + sourceAttr + " ==> " + target + "." + targetAttr);
  }

}