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

    container.addBinding(this);
  }
  
  public Binding(XulDomContainer container, XulEventSource source, String sourceAttr, String targetId, String targetAttr){
    this.source = source;
    this.sourceAttr = sourceAttr;
    this.target = container.getDocumentRoot().getElementById(targetId);;
    this.targetAttr = targetAttr;

    container.addBinding(this);
  }
  
  public Binding(XulDomContainer container, String sourceId, String sourceAttr, XulEventSource target, String targetAttr){
    this.source = container.getDocumentRoot().getElementById(sourceId);
    this.sourceAttr = sourceAttr;
    this.target = target;
    this.targetAttr = targetAttr;

    container.addBinding(this);
  }
  

  public Binding(XulEventSource source, String sourceAttr, XulEventSource target, String targetAttr){
    this.source = source;
    this.sourceAttr = sourceAttr;
    this.target = target;
    this.targetAttr = targetAttr;
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

  