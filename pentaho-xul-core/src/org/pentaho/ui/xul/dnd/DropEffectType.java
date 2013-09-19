package org.pentaho.ui.xul.dnd;

public enum DropEffectType {
  COPY,
  MOVE,
  LINK,
  NONE;

  public static DropEffectType valueOfIgnoreCase(String value) {
    return valueOf(value.toUpperCase());
  }
}