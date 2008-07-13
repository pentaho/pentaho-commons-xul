package org.pentaho.ui.xul.binding;

import org.pentaho.ui.xul.dom.Document;

public class DocumentRegisteringBindingFactory implements BindingFactory {
  
  private Document document;
  
  private Binding.Type type = Binding.Type.BI_DIRECTIONAL;

  public void setDocument(Document document) {
    this.document = document;
  }
  
  public void setBindingType(Binding.Type type) {
    this.type = type;
  }
  
  private Binding applyBinding(Binding b) {
    b.setBindingType(type);
    document.addBinding(b);
    return b;
  }
  
  public Binding createBinding(String sourceId, String sourceAttr, String targetId, String targetAttr, BindingConvertor... bindingConvertors) {
    Binding b = new Binding(document, sourceId, sourceAttr, targetId, targetAttr);
    return applyBinding(b);
  }

  public Binding createBinding(Object source, String sourceAttr, String targetId, String targetAttr, BindingConvertor... bindingConvertors) {
    Binding b = new Binding(document, source, sourceAttr, targetId, targetAttr);
    return applyBinding(b);
  }

  public Binding createBinding(String sourceId, String sourceAttr, Object target, String targetAttr, BindingConvertor... bindingConvertors) {
    Binding b = new Binding(document, sourceId, sourceAttr, target, targetAttr);
    return applyBinding(b);
  }
  
  public Binding createBinding(Object source, String sourceAttr, Object target, String targetAttr, BindingConvertor... bindingConvertors) {
    Binding b = new Binding(source, sourceAttr, target, targetAttr);
    return applyBinding(b);
  }

}
