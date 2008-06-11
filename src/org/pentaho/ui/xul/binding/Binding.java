package org.pentaho.ui.xul.binding;

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulEventSource;
import org.pentaho.ui.xul.binding.BindingConvertor.Direction;

public class Binding {

  private XulEventSource source;

  private XulEventSource target;

  private String sourceAttr;

  private String targetAttr;

  private boolean negateBooleanAssignment = false;
  
  private boolean reverseConversion = false;

  private BindingConvertor conversion;

  public enum Type {
    ONE_WAY, BI_DIRECTIONAL
  };

  private Type bindingStrategy = Type.BI_DIRECTIONAL;

  public Binding(XulDomContainer container, String sourceId, String sourceAttr, String targetId, String targetAttr) {
    this.source = container.getDocumentRoot().getElementById(sourceId);
    setSourceAttr(sourceAttr);
    this.target = container.getDocumentRoot().getElementById(targetId);
    setTargetAttr(targetAttr);
  }

  public Binding(XulDomContainer container, XulEventSource source, String sourceAttr, String targetId, String targetAttr) {
    this.source = source;
    setSourceAttr(sourceAttr);
    this.target = container.getDocumentRoot().getElementById(targetId);
    ;
    setTargetAttr(targetAttr);

    container.addBinding(this);
  }

  public Binding(XulEventSource source, String sourceAttr, XulEventSource target, String targetAttr) {
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
  
  public Object doConversions(Object val, Direction dir){
    if(conversion != null){
      return (dir == Direction.FORWARD) ? 
          conversion.sourceToTarget(val):
          conversion.targetToSource(val);
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

}
