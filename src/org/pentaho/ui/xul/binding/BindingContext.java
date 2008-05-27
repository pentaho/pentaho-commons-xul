package org.pentaho.ui.xul.binding;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;

public class BindingContext {
  
  private XulDomContainer container;
  private List<Binding> bindings = new ArrayList<Binding>();
  
  public BindingContext(XulDomContainer container){
    this.container = container;
  }
  
  public void add(XulComponent source, String expr){
    BindingExpression expression = BindingExpression.parse(expr);
    XulComponent target = container.getDocumentRoot().getElementById(expression.target);
    Binding newBinding = new Binding(source, expression.sourceAttr, target, expression.targetAttr);
    add(newBinding);
  }
  
  public void add(Binding bind){
    bindings.add(bind);
    
  }
}