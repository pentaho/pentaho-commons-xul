package org.pentaho.ui.xul.binding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
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
    setupBinding(bind, bind.getTarget(), bind.getTargetAttr(), bind.getSource(), bind.getSourceAttr());
  }

  private void setupBinding(final Binding bind, final XulEventSource source, final String sourceAttr, final XulEventSource target, final String targetAttr){

    Method method = null;
    String setMethodName = "set" + (String.valueOf(targetAttr.charAt(0)).toUpperCase()) + targetAttr.substring(1);

    String sourceGetMethodName = "get" + (String.valueOf(sourceAttr.charAt(0)).toUpperCase()) + sourceAttr.substring(1);
    try {

      //Get value from one in order to determine type of the attribute
      String methodName = "get" + (String.valueOf(targetAttr.charAt(0)).toUpperCase()) + targetAttr.substring(1);
      //Expression state = new Expression(target, methodName, null);

      Method getMethod = target.getClass().getMethod(methodName);
      Type retType = getMethod.getGenericReturnType();
      Class retClazz = getMethod.getReturnType();

      Class[] methodArgs = null;
      //if(retType != null){
      //  methodArgs = new Class[]{retType};
      //} else {
      methodArgs = new Class[] { retClazz };
      //}

      try {
        //FIXME: cannot resolve method signature so I hacked it.
        if (setMethodName.equals("setElements")) {
          method = target.getClass().getMethod(setMethodName, new Class[]{Collection.class});
        } else {
          method = target.getClass().getMethod(setMethodName, methodArgs);
        }
      } catch (NoSuchMethodException e) {
        //Method not found. Most likely a Wrapped Primitive type argument

        Class[] args = null;
        if (retClazz == Boolean.class) {
          args = new Class[] { Boolean.TYPE };
        } else if (retClazz == Integer.class) {
          args = new Class[] { Integer.TYPE };
        } else if (retClazz == Float.class) {
          args = new Class[] { Float.TYPE };
        } else if (retClazz == Double.class) {
          args = new Class[] { Double.TYPE };
        }
        method = target.getClass().getMethod(setMethodName, new Class[]{});

      }
    } catch (NoSuchMethodException e) {
      logger
          .info("Method not found: " + e.getMessage() + ", checking to see if using boolean convention, e.g. isFoo()");
      try {
        //Hum. method not found. Check is[fieldName] boolean convention
        String methodName = "is" + (String.valueOf(targetAttr.charAt(0)).toUpperCase()) + targetAttr.substring(1);
        //if you made it here you were correct that it IS a boolean

        //change args to primimtive boolean type instead of Boolean Class
        method = target.getClass().getMethod(setMethodName, new Class[]{Boolean.TYPE} );

      } catch (Exception ex) {
        throw new IllegalArgumentException("Cannot find setter method by name of [" + setMethodName
            + "] for property: [" + targetAttr + "] in bean [" + target.getClass().getName() + "]");
      }

    } catch (Exception e) {
      throw new IllegalArgumentException("Unknown error finding binding method: ", e);
    }

    //setup prop change listener to handle binding
    final Method finalMethod = method;
    PropertyChangeListener listener = new PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent evt) {
        logger.debug("binding received property change from source: " + evt + "  Calling dest bean: "
            + target.getClass().getName() + "." + finalMethod);
        if (evt.getPropertyName().equalsIgnoreCase(sourceAttr)) {
          try {
            finalMethod.invoke(target, bind.evaluateExpressions(evt.getNewValue()) );
          } catch (Exception e) {
            throw new RuntimeException("Error invoking binding method [" + finalMethod + "]: " + e);
          }
        }
      }
    };

    source.addPropertyChangeListener(listener);
  }
}