package org.pentaho.ui.xul.dnd;

import java.util.List;

/**
 * For now this only allows a single type of dnd object, to match more closely to Mozilla,
 * we could add a list of types:
 * 
 * https://developer.mozilla.org/En/DragDrop/DataTransfer
 * 
 * @author Will Gorman (wgorman@pentaho.com)
 */
public class DataTransfer {
  
  private DropEffectType dropEffect;
  private List<DropEffectType> effectsAllowed;
  private List<Object> data;
  
  public List<Object> getData() {
    return data;
  }
  
  public void setData(List<Object> data) {
    this.data = data;
  }
  
  public DropEffectType getDropEffect() {
    return dropEffect;
  }
  
  public void setDropEffect(DropEffectType dropEffect) {
    this.dropEffect = dropEffect;
  }
  
  public List<DropEffectType> getEffectsAllowed() {
    return effectsAllowed;
  }
  
  public void setEffectsAllowed(List<DropEffectType> effectsAllowed) {
    this.effectsAllowed = effectsAllowed;
  }
  
}
