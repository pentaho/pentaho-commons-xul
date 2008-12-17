package org.pentaho.ui.xul.gwt.binding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.pentaho.ui.xul.XulEventSource;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.binding.Binding;
import org.pentaho.ui.xul.binding.BindingContext;
import org.pentaho.ui.xul.binding.BindingConvertor;
import org.pentaho.ui.xul.binding.BindingException;
import org.pentaho.ui.xul.binding.BindingConvertor.Direction;
import org.pentaho.ui.xul.dom.Document;

public class GwtBinding implements Binding{
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

  public GwtBinding(){
    
  }
  
  @Deprecated
  public GwtBinding(Document document, String sourceId, String sourceAttr, String targetId, String targetAttr) {
    this.source = document.getElementById(sourceId);
    setSourceAttr(sourceAttr);
    this.target = document.getElementById(targetId);
    setTargetAttr(targetAttr);
  }

  @Deprecated
  public GwtBinding(Document document, Object source, String sourceAttr, String targetId, String targetAttr) {
    this.source = source;
    setSourceAttr(sourceAttr);
    this.target = document.getElementById(targetId);
    setTargetAttr(targetAttr);
  }

  @Deprecated
  public GwtBinding(Document document, String sourceId, String sourceAttr, Object target, String targetAttr) {
    this.source = document.getElementById(sourceId);
    setSourceAttr(sourceAttr);
    this.target = target;
    setTargetAttr(targetAttr);
  }
  
  public GwtBinding(Object source, String sourceAttr, Object target, String targetAttr) {
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
  
  public void initialize() {
    bindForward();

    if (getBindingType() == Binding.Type.BI_DIRECTIONAL) {
      bindReverse();
    }
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

  public void fireSourceChanged() throws IllegalArgumentException, XulException, InvocationTargetException {
    try{
      Object getRetVal = sourceGetterMethod.invoke(target, new Object[]{getSource()});
      forwardListener.propertyChange(new PropertyChangeEvent(getSource(), getSourceAttr(), null, getRetVal));
    } catch(Exception e){
      //TODO: re-implement IllegalAccessException.
      //cannot be in interface due to GWT incompatibility.
      throw new XulException(e);
    }
  }

  public void bindForward() {
    setForwardListener(setupBinding(getSource(), getSourceAttr(), getTarget(), getTargetAttr(), Direction.FORWARD));
    sourceGetterMethod = getterMethods.pop();
    System.out.println("Forward binding established: " + source + "." + sourceAttr + " ==> " + target + "." + targetAttr);
  }

  public void bindReverse() {
    setReverseListener(setupBinding(getTarget(), getTargetAttr(), getSource(), getSourceAttr(), Direction.BACK));
    targetGetterMethod = getterMethods.pop();
    System.out.println("Reverse binding established: " + source + "." + sourceAttr + " <== " + target + "." + targetAttr);
  }

  protected PropertyChangeListener setupBinding(final Object a, final String va, final Object b, final String vb,
      final Direction dir) {
    if (a == null || va == null) {
      throw new BindingException("source bean or property is null");
    }
    if (!(a instanceof XulEventSource)) {
      throw new BindingException("Binding error, source object "+a+" not a XulEventSource instance");
    }
    if (b == null || vb == null) {
      throw new BindingException("target bean or property is null");
    }
    GwtBindingMethod sourceGetMethod = GwtBindingContext.typeController.findGetMethod(a, va);

    getterMethods.push(sourceGetMethod);
    
    //find set method
    final GwtBindingMethod targetSetMethod = GwtBindingContext.typeController.findSetMethod(b, vb);

    //setup prop change listener to handle binding
    PropertyChangeListener listener = new PropertyChangeListener() {
      public void propertyChange(final PropertyChangeEvent evt) {
        final PropertyChangeListener cThis = this;
        if (evt.getPropertyName().equalsIgnoreCase(va)) {
          try {
            Object value = evaluateExpressions(evt.getNewValue());
            final Object finalVal = doConversions(value, dir);
            
            Object targetObject = b;
            if(targetObject == null){
              System.out.println("Binding target was Garbage Collected, removing propListener");
              GwtBinding.this.destroyBindings();                      
              return;
            }
            System.out.println("Setting val: "+finalVal+" on: "+targetObject);
            targetSetMethod.invoke(targetObject, new Object[]{finalVal});
          
          } catch (Exception e) {
            throw new BindingException("Error invoking setter method [" + targetSetMethod + "] on target: "+target, e);
          }
        }
      }
    };
    ((XulEventSource) a).addPropertyChangeListener(listener);

    return listener;
  }

  public void destroyBindings(){
    if(destroyed){
      //circular catch from context.remove()
      return;
    }
    Object sourceObj = getSource();
    Object targetObj = getTarget();
    
    if(forwardListener != null && sourceObj != null && sourceObj instanceof XulEventSource){
      ((XulEventSource) sourceObj).removePropertyChangeListener(forwardListener);
      System.out.println("Removing forward binding on "+sourceObj);
    }
    
    if(reverseListener != null && targetObj != null && targetObj instanceof XulEventSource){
      ((XulEventSource) targetObj).removePropertyChangeListener(reverseListener);
      System.out.println("Removing reverse binding on "+targetObj);
    } 

    setDestroyed(true);
    if(context != null){
      context.remove(this);
    }
  }
  
  protected void setDestroyed(boolean flag){
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
  
  public List<PropertyChangeListener> getListeneners() {
    List<PropertyChangeListener> l = new ArrayList<PropertyChangeListener>();
    if(forwardListener != null) {
      l.add(forwardListener);
    }
    if(reverseListener != null) {
      l.add(reverseListener);
    }
    return l;
  }

  public BindingContext getContext() {
    return context;
  }

  public void setContext(BindingContext context) {
    this.context = context;
  }
  
}

  