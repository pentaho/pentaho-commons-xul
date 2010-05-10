package org.pentaho.ui.xul.binding;

import org.pentaho.ui.xul.dom.Document;

public interface BindingFactory {

  void setDocument(Document document);

  void setBindingType(Binding.Type type);

  Binding createBinding(String sourceId, String sourceAttr, String targetId, String targetAttr, BindingConvertor... converters);

  Binding createBinding(Object source, String sourceAttr, String targetId, String targetAttr, BindingConvertor... converters);

  Binding createBinding(String sourceId, String sourceAttr, Object target, String targetAttr, BindingConvertor... converters);

  Binding createBinding(Object source, String sourceAttr, Object target, String targetAttr, BindingConvertor... converters);
  
  void setExceptionHandler(BindingExceptionHandler handler);

}