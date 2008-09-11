package org.pentaho.ui.xul.binding;

public abstract class BindingConvertor<V, R> {
  public enum Direction{FORWARD, BACK};
  
  public abstract R sourceToTarget(V value);

  public abstract V targetToSource(R value);

}
