package org.pentaho.ui.xul.binding;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulEventSource;
import org.pentaho.ui.xul.binding.BindingConvertor.Direction;
import org.pentaho.ui.xul.dom.Document;

public class Binding {

  private Reference source;

  private Reference target;

  private String sourceAttr;

  private String targetAttr;

  private boolean negateBooleanAssignment = false;

  private boolean reverseConversion = false;

  private BindingConvertor conversion;

  private PropertyChangeListener forwardListener, reverseListener;

  private Stack<Method> getterMethods = new Stack<Method>();

  private Method sourceGetterMethod, targetGetterMethod;
  
  private BindingContext context;
 
  private static final Log logger = LogFactory.getLog(Binding.class);
  
  private boolean destroyed = false;

  public enum Type {
    ONE_WAY, BI_DIRECTIONAL
  };

  private Type bindingStrategy = Type.BI_DIRECTIONAL;

  public Binding(Document document, String sourceId, String sourceAttr, String targetId, String targetAttr) {
    this.source = new WeakReference(document.getElementById(sourceId));
    setSourceAttr(sourceAttr);
    this.target = new WeakReference(document.getElementById(targetId));
    setTargetAttr(targetAttr);
  }

  public Binding(Document document, Object source, String sourceAttr, String targetId, String targetAttr) {
    this.source = new WeakReference(source);
    setSourceAttr(sourceAttr);
    this.target = new WeakReference(document.getElementById(targetId));
    setTargetAttr(targetAttr);
  }

  public Binding(Document document, String sourceId, String sourceAttr, Object target, String targetAttr) {
    this.source = new WeakReference(document.getElementById(sourceId));
    setSourceAttr(sourceAttr);
    this.target = new WeakReference(target);
    setTargetAttr(targetAttr);
  }
  
  public Binding(Object source, String sourceAttr, Object target, String targetAttr) {
    this.source = new WeakReference(source);
    setSourceAttr(sourceAttr);
    this.target = new WeakReference(target);
    setTargetAttr(targetAttr);
  }

  public void setBindingType(Type t) {
    this.bindingStrategy = t;
  }

  public Type getBindingType() {
    return bindingStrategy;
  }

  public Reference getSource() {
    return source;
  }

  public void setSource(Object source) {

    this.source = new WeakReference(source);
  }

  public Reference getTarget() {

    return target;
  }

  public void setTarget(Object target) {

    this.target = new WeakReference(target);
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
    Object getRetVal = sourceGetterMethod.invoke(getSource().get());
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

  private PropertyChangeListener setupBinding(final Reference a, final String va, final Reference b, final String vb,
      final Direction dir) {
    if (a.get() == null || va == null) {
      throw new BindingException("source bean or property is null");
    }
    if (!(a.get() instanceof XulEventSource)) {
      throw new BindingException("Binding error, source object not a XulEventSource instance");
    }
    if (b.get() == null || vb == null) {
      throw new BindingException("target bean or property is null");
    }
    Method sourceGetMethod = BindingUtil.findGetMethod(a.get(), va);

    Class getterClazz = BindingUtil.getMethodReturnType(sourceGetMethod, a.get());
    getterMethods.push(sourceGetMethod);

    //find set method
    final Method targetSetMethod = BindingUtil.findSetMethod(b.get(), vb, getterClazz);

    //setup prop change listener to handle binding
    PropertyChangeListener listener = new PropertyChangeListener() {
      public void propertyChange(final PropertyChangeEvent evt) {
        final PropertyChangeListener cThis = this;
        if (evt.getPropertyName().equalsIgnoreCase(va)) {
          try {
            Object value = evaluateExpressions(evt.getNewValue());
            final Object finalVal = doConversions(value, dir);
            if(!EventQueue.isDispatchThread() && b instanceof XulComponent){
              logger.warn("Binding to XulComponenet outside of event thread");
              
                //Swing Specific code. break out once Binding is "flavored"
//              EventQueue.invokeLater(new Runnable(){
//                public void run() {
//                  try{
//                    Object targetObject = b.get();
//                    if(targetObject == null){
//                      logger.error("Binding target was Garbage Collected, removing propListener");
//                      Binding.this.destroyBindings();                      
//                      return;
//                    }
//                    targetSetMethod.invoke(targetObject, finalVal);
//                  } catch(InvocationTargetException e){
//                    throw new BindingException("Error invoking setter method [" + targetSetMethod.getName() + "] on target: "+target, e);
//                  } catch(IllegalAccessException e){
//                    throw new BindingException("Error invoking setter method [" + targetSetMethod.getName() + "] on target: "+target, e);
//                  }
//                }
//              });
            
            }
          
            Object targetObject = b.get();
            if(targetObject == null){
              logger.error("Binding target was Garbage Collected, removing propListener");
              Binding.this.destroyBindings();                      
              return;
            }
            targetSetMethod.invoke(targetObject, finalVal);
          
          } catch (Exception e) {
            throw new BindingException("Error invoking setter method [" + targetSetMethod.getName() + "] on target: "+target, e);
          }
        }
      }
    };
    ((XulEventSource) a.get()).addPropertyChangeListener(listener);

    return listener;
  }

  public void destroyBindings(){
    if(destroyed){
      //circular catch from context.remove()
      return;
    }
    Object sourceObj = getSource().get();
    Object targetObj = getTarget().get();
    
    if(forwardListener != null && sourceObj != null){
      ((XulEventSource) sourceObj).removePropertyChangeListener(forwardListener);
      logger.debug("Removing forward binding on "+sourceObj);
    } 
    
    if(reverseListener != null && targetObj != null && targetObj instanceof XulEventSource){
      ((XulEventSource) targetObj).removePropertyChangeListener(reverseListener);
      logger.debug("Removing reverse binding on "+targetObj);
    } 

    setDestroyed(true);
    if(context != null){
      context.remove(this);
    }
  }
  
  private void setDestroyed(boolean flag){
    this.destroyed = flag;
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

  public BindingContext getContext() {
    return context;
  }

  public void setContext(BindingContext context) {
    this.context = context;
  }

  
}
