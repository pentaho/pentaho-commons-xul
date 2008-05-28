package org.pentaho.ui.xul.binding;

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulEventSource;

public class Binding {
  
  private XulEventSource source;
  private XulEventSource target;
  private String sourceAttr;
  private String targetAttr;
  private boolean negateBooleanAssignment = false;
  

  public Binding(XulDomContainer container, String sourceId, String sourceAttr, String targetId, String targetAttr){
    this.source = container.getDocumentRoot().getElementById(sourceId);
    setSourceAttr(sourceAttr);
    this.target = container.getDocumentRoot().getElementById(targetId);
    setTargetAttr(targetAttr);

    container.addBinding(this);
  }
  
  public Binding(XulDomContainer container, XulEventSource source, String sourceAttr, String targetId, String targetAttr){
    this.source = source;
    setSourceAttr(sourceAttr);
    this.target = container.getDocumentRoot().getElementById(targetId);;
    setTargetAttr(targetAttr);

    container.addBinding(this);
  }
  
  public Binding(XulDomContainer container, String sourceId, String sourceAttr, XulEventSource target, String targetAttr){
    this.source = container.getDocumentRoot().getElementById(sourceId);
    setSourceAttr(sourceAttr);
    this.target = target;
    setTargetAttr(targetAttr);

    container.addBinding(this);
  }
  

  public Binding(XulEventSource source, String sourceAttr, XulEventSource target, String targetAttr){
    this.source = source;
    setSourceAttr(sourceAttr);
    this.target = target;
    setTargetAttr(targetAttr);
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
  
    
    if(sourceAttr.charAt(0) == '!'){
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

    if(targetAttr.charAt(0) == '!'){
      //Negation of Boolean
      negateBooleanAssignment = !negateBooleanAssignment; //two negations will cancel
      targetAttr = targetAttr.substring(1);
    }
    this.targetAttr = targetAttr;
  }
  
  public Object evaluateExpressions(Object val){
    if(negateBooleanAssignment && val instanceof Boolean){
      return !((Boolean) val);
    }
    return val;
  }
  
  
}

  