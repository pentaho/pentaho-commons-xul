package org.pentaho.ui.xul.binding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulEventSource;
import org.pentaho.ui.xul.binding.BindingConvertor.Direction;
import org.pentaho.ui.xul.dom.Document;

public class Binding {

  private Object source;

  private Object target;

  private String sourceAttr;

  private String targetAttr;

  private boolean negateBooleanAssignment = false;

  private boolean reverseConversion = false;

  private BindingConvertor conversion;

  private PropertyChangeListener forwardListener, reverseListener;

  private Stack<Method> getterMethods = new Stack<Method>();

  private Method sourceGetterMethod, targetGetterMethod;

  private static final Log logger = LogFactory.getLog(Binding.class);

  public enum Type {
    ONE_WAY, BI_DIRECTIONAL
  };

  private Type bindingStrategy = Type.BI_DIRECTIONAL;

  public Binding(Document document, String sourceId, String sourceAttr, String targetId, String targetAttr) {
    this.source = document.getElementById(sourceId);
    setSourceAttr(sourceAttr);
    this.target = document.getElementById(targetId);
    setTargetAttr(targetAttr);
  }

  public Binding(Document document, Object source, String sourceAttr, String targetId, String targetAttr) {
    this.source = source;
    setSourceAttr(sourceAttr);
    this.target = document.getElementById(targetId);
    ;
    setTargetAttr(targetAttr);
  }

  public Binding(Object source, String sourceAttr, Object target, String targetAttr) {
    this.source = source;
    setSourceAttr(sourceAttr);
    this.target = target;
    setTargetAttr(targetAttr);
  }

  public void setBindingType(Type t) {
    this.bindingStrategy = t;
  }

  public Type getBindingType() {
    return bindingStrategy;
  }

  public Object getSource() {
    return source;
  }

  public void setSource(Object source) {

    this.source = source;
  }

  public Object getTarget() {

    return target;
  }

  public void setTarget(Object target) {

    this.target = target;
  }

  public String getSourceAttr() {

    return sourceAttr;
  }

  public void setSourceAttr(String sourceAttr) {

    if (sourceAttr.charAt(0) == '!') {
      //Negation of Boolean
      negateBooleanAssignment = !negateBooleanAssignment; //two negations will cancel
      sourceAttr = sourceAttr.substring(1);
    }
    this.sourceAttr = sourceAttr;

  }

  public String getTargetAttr() {

    return targetAttr;
  }

  public void setTargetAttr(String targetAttr) {

    if (targetAttr.charAt(0) == '!') {
      //Negation of Boolean
      negateBooleanAssignment = !negateBooleanAssignment; //two negations will cancel
      targetAttr = targetAttr.substring(1);
    }
    this.targetAttr = targetAttr;
  }

  public Object evaluateExpressions(Object val) {
    if (negateBooleanAssignment && val instanceof Boolean) {
      return !((Boolean) val);
    }
    return val;
  }

  public Object doConversions(Object val, Direction dir) {
    if (conversion != null) {
      return (dir == Direction.FORWARD) ? conversion.sourceToTarget(val) : conversion.targetToSource(val);
    }
    return val;
  }

  public BindingConvertor getConversion() {
    return conversion;
  }

  public void setConversion(BindingConvertor conversion) {
    this.conversion = conversion;
  }

  public boolean isReverseConversion() {

    return reverseConversion;
  }

  public void setReverseConversion(boolean reverseConversion) {

    this.reverseConversion = reverseConversion;
  }

  public void fireSourceChanged() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    Object getRetVal = sourceGetterMethod.invoke(source);
    forwardListener.propertyChange(new PropertyChangeEvent(getSource(), getSourceAttr(), null, getRetVal));
  }

  public void bindForward() {
    setForwardListener(setupBinding(getSource(), getSourceAttr(), getTarget(), getTargetAttr(), Direction.FORWARD));
    sourceGetterMethod = getterMethods.pop();
    logger.info("Forward binding established: " + source + "." + sourceAttr + " ==> " + target + "." + targetAttr);
  }

  public void bindReverse() {
    setReverseListener(setupBinding(getTarget(), getTargetAttr(), getSource(), getSourceAttr(), Direction.BACK));
    targetGetterMethod = getterMethods.pop();
    logger.info("Reverse binding established: " + source + "." + sourceAttr + " <== " + target + "." + targetAttr);
  }

  private PropertyChangeListener setupBinding(final Object a, final String va, final Object b, final String vb,
      final Direction dir) {
    if (!(a instanceof XulEventSource)) {
      throw new BindingException("Binding error, source object not a XulEventSource instance");
    }
    if (a == null || va == null) {
      throw new BindingException("source bean or property is null");
    }
    if (b == null || vb == null) {
      throw new BindingException("target bean or property is null");
    }
    Method sourceGetMethod = BindingUtil.findGetMethod(a, va);

    Class getterClazz = BindingUtil.getMethodReturnType(sourceGetMethod, a);
    getterMethods.push(sourceGetMethod);

    //find set method
    final Method targetSetMethod = BindingUtil.findSetMethod(b, vb, getterClazz);

    //setup prop change listener to handle binding
    PropertyChangeListener listener = new PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equalsIgnoreCase(va)) {
          try {
            Object value = evaluateExpressions(evt.getNewValue());
            value = doConversions(value, dir);
            targetSetMethod.invoke(b, value);
          } catch (Exception e) {
            throw new BindingException("Error invoking setter method [" + targetSetMethod.getName() + "] on target: "+target, e);
          }
        }
      }
    };
    ((XulEventSource) a).addPropertyChangeListener(listener);

    return listener;
  }

  public PropertyChangeListener getForwardListener() {
    return forwardListener;
  }

  public void setForwardListener(PropertyChangeListener forwardListener) {
    this.forwardListener = forwardListener;
  }

  public PropertyChangeListener getReverseListener() {
    return reverseListener;
  }

  public void setReverseListener(PropertyChangeListener reverseListener) {
    this.reverseListener = reverseListener;
  }

}
