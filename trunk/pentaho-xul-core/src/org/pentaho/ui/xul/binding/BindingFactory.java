package org.pentaho.ui.xul.binding;

import org.pentaho.ui.xul.dom.Document;

public interface BindingFactory {

  public abstract void setDocument(Document document);

  public abstract void setBindingType(Binding.Type type);

  public abstract Binding createBinding(String sourceId, String sourceAttr, String targetId, String targetAttr, BindingConvertor... converters);

  public abstract Binding createBinding(Object source, String sourceAttr, String targetId, String targetAttr, BindingConvertor... converters);

  public abstract Binding createBinding(String sourceId, String sourceAttr, Object target, String targetAttr, BindingConvertor... converters);

  public abstract Binding createBinding(Object source, String sourceAttr, Object target, String targetAttr, BindingConvertor... converters);

}