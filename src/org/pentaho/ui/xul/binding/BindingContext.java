package org.pentaho.ui.xul.binding;

import groovyjarjarasm.asm.tree.MethodNode;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulEventSource;

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
    bindings.add(bind);
    //forward binding
    setupBinding(bind, bind.getSource(), bind.getSourceAttr(), bind.getTarget(), bind.getTargetAttr());

    //reverse binding
    if (bind.getBindingType() == Binding.Type.BI_DIRECTIONAL) {
      setupBinding(bind, bind.getTarget(), bind.getTargetAttr(), bind.getSource(), bind.getSourceAttr());
    }
  }

  private Method findGetMethod(Object o, String property) {
    String methodName = null;
    try {
      methodName = "get" + (String.valueOf(property.charAt(0)).toUpperCase()) + property.substring(1);
      Method getMethod = o.getClass().getMethod(methodName);
      return getMethod;
    } catch (NoSuchMethodException e) {
      logger.debug("could not resolve getter method ["+methodName+"] for property [" + property + "] on object ["
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

  private Method findSetMethod(Object o, String property) {
    String methodName = "set" + (String.valueOf(property.charAt(0)).toUpperCase()) + property.substring(1);

    for (Method m : o.getClass().getMethods()) {
      //just match on name
      if (m.getName().equals(methodName)) {
        return m;
      }
    }
    throw new BindingException("Could not resolve setter method for property [" + property + "] on object ["
        + o.getClass().getName() + "]");

  }

  private void setupBinding(final Binding bind, final XulEventSource source, final String sourceAttr,
      final XulEventSource target, final String targetAttr) {
    Method sourceGetMethod = findGetMethod(source, sourceAttr);
    final Method targetSetMethod = findSetMethod(target, targetAttr);

    //setup prop change listener to handle binding
    PropertyChangeListener listener = new PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent evt) {
        //          logger.debug("binding received property change from source: " + evt + "  Calling dest bean: "
        //              + target.getClass().getName() + "." + targetSetMethod);
        if (evt.getPropertyName().equalsIgnoreCase(sourceAttr)) {
          try {
            targetSetMethod.invoke(target, bind.evaluateExpressions(evt.getNewValue()));
          } catch (Exception e) {
            throw new BindingException("Error invoking setter method [" + targetSetMethod.getName() + "]", e);
          }
        }
      }
    };

    source.addPropertyChangeListener(listener);
    logger.info("Binding established: " + source.getClass().getName() + "." + sourceAttr + " <==> "
        + target.getClass().getName() + "." + targetAttr);
  }

}