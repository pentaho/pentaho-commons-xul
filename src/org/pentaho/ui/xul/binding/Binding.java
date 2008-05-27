package org.pentaho.ui.xul.binding;

import java.beans.BeanInfo;
import java.beans.Expression;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.Statement;
import java.lang.reflect.Method;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulEventSource;

public class Binding {
  
  private XulEventSource source;
  private XulEventSource target;
  private String sourceAttr;
  private String targetAttr;

  

  public Binding(XulDomContainer container, String sourceId, String sourceAttr, String targetId, String targetAttr){
    this.source = container.getDocumentRoot().getElementById(sourceId);
    this.sourceAttr = sourceAttr;
    this.target = container.getDocumentRoot().getElementById(targetId);
    this.targetAttr = targetAttr;

    setupLeftToRight();
    setupRightToLeft();
    container.addBinding(this);
  }
  
  public Binding(XulDomContainer container, XulEventSource source, String sourceAttr, String targetId, String targetAttr){
    this.source = source;
    this.sourceAttr = sourceAttr;
    this.target = container.getDocumentRoot().getElementById(targetId);;
    this.targetAttr = targetAttr;

    setupLeftToRight();
    setupRightToLeft();
    container.addBinding(this);
  }
  
  public Binding(XulDomContainer container, String sourceId, String sourceAttr, XulEventSource target, String targetAttr){
    this.source = container.getDocumentRoot().getElementById(sourceId);
    this.sourceAttr = sourceAttr;
    this.target = target;
    this.targetAttr = targetAttr;

    setupLeftToRight();
    setupRightToLeft();
    container.addBinding(this);
  }
  

  public Binding(XulEventSource source, String sourceAttr, XulEventSource target, String targetAttr){
    this.source = source;
    this.sourceAttr = sourceAttr;
    this.target = target;
    this.targetAttr = targetAttr;

    setupLeftToRight();
    setupRightToLeft();
  }
  
  private void setupLeftToRight(){

    Method method = null;
    String attrName = (String.valueOf(targetAttr.charAt(0)).toUpperCase())+targetAttr.substring(1);
    try{
    
      //Get value from one in order to determine type of the attribute
      String methodName = "get"+attrName;
      Expression state = new Expression(target, methodName, null);
      Object val = state.getValue();
      Class clazz = val.getClass();
      
      String setMethodName = "set"+attrName;
      
      method = target.getClass().getMethod(setMethodName, new Class[]{clazz});
      
    } catch(NoSuchMethodException e){
      try{
        //Get value from one in order to determine type of the attribute
        String methodName = "is"+attrName;
        Expression state = new Expression(target, methodName, null);
        Object val = state.getValue();
        Class clazz = val.getClass();
        
        String setMethodName = "set"+attrName;
        
        Class[] args = new Class[]{clazz};
        if(clazz == Boolean.class){
          args = new Class[]{Boolean.TYPE};
        } 
        
        method = target.getClass().getMethod(setMethodName, args );
      } catch(Exception ex){
        System.out.println("No such method: "+ex.getMessage());
        ex.printStackTrace(System.out);
      }
      
    } catch(Exception e){
      System.out.println("No such method: "+e.getMessage());
      e.printStackTrace(System.out);
    }
    final Method finalMethod = method;
    PropertyChangeListener listener = new PropertyChangeListener(){
      public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("In PropChange Listener");
        if(evt.getPropertyName().equalsIgnoreCase(sourceAttr)){
          System.out.println("Capturing Value");
          try{
            finalMethod.invoke(target, evt.getNewValue());
          } catch(Exception e){
            System.out.println("Error setting value");
          }
        }
      }
    };
    
    source.addPropertyChangeListener(listener);
  }
  
  private void setupRightToLeft(){

    String attrName = (String.valueOf(targetAttr.charAt(0)).toUpperCase())+targetAttr.substring(1);
    Method method = null;
    try{
    
      //Get value from one in order to determine type of the attribute
      String methodName = "get"+attrName;
      Expression state = new Expression(source, methodName, null);
      Object val = state.getValue();
      Class clazz = val.getClass();
      
      String setMethodName = "set"+attrName;
      method = source.getClass().getMethod(setMethodName, new Class[]{clazz});
      
     } catch(NoSuchMethodException e){
      try{
         //Get value from one in order to determine type of the attribute
        String methodName = "is"+attrName;
        Expression state = new Expression(source, methodName, null);
        Object val = state.getValue();
        Class clazz = val.getClass();
        
        String setMethodName = "set"+attrName;
        
        Class[] args = new Class[]{clazz};
        if(clazz == Boolean.class){
          args = new Class[]{Boolean.TYPE};
        } 
        
        method = target.getClass().getMethod(setMethodName, args );
      } catch(Exception ex){
        System.out.println("No such method: "+ex.getMessage());
        ex.printStackTrace(System.out);
      }
      
    } catch(Exception e){
      System.out.println("No such method: "+e.getMessage());
      e.printStackTrace(System.out);
    }
    final Method finalMethod = method;
    PropertyChangeListener listener = new PropertyChangeListener(){
      public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("In PropChange Listener");
        if(evt.getPropertyName().equalsIgnoreCase(targetAttr)){
          System.out.println("Capturing Value");
          try{
            finalMethod.invoke(source, evt.getNewValue());
          } catch(Exception e){
            System.out.println("Error setting value: "+e.getMessage());
            e.printStackTrace(System.out);
          }
        }
      }
    };
    
    target.addPropertyChangeListener(listener);
  }
  
  
  public XulEventSource getSource() {
    return source;
  }

  public void setSource(XulEventSource source) {
  
    this.source = source;
  }

  public XulEventSource getTarget() {
  
    return target;
  }

  public void setTarget(XulEventSource target) {
  
    this.target = target;
  }

  public String getSourceAttr() {
  
    return sourceAttr;
  }

  public void setSourceAttr(String sourceAttr) {
  
    this.sourceAttr = sourceAttr;
  }

  public String getTargetAttr() {
  
    return targetAttr;
  }

  public void setTargetAttr(String targetAttr) {
  
    this.targetAttr = targetAttr;
  }
  
  
}

  