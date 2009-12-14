package org.pentaho.ui.xul.dnd;

public class DropEvent {
  
  private DataTransfer dataTransfer;
  private Object nativeEvent;
  private Object dropParent;
  private int dropIndex;
  private boolean accepted;
  
  public void setDataTransfer(DataTransfer dataTransfer) {
    this.dataTransfer = dataTransfer;
  }
  public DataTransfer getDataTransfer() {
    return dataTransfer;
  }
  public void setAccepted(boolean accepted) {
    this.accepted = accepted;
  }
  public boolean isAccepted() {
    return accepted;
  }
  public void setNativeEvent(Object nativeEvent) {
    this.nativeEvent = nativeEvent;
  }
  public Object getNativeEvent() {
    return nativeEvent;
  }
  public void setDropParent(Object dropParent) {
    this.dropParent = dropParent;
  }
  
  /**
   * For the case of trees, this is the node in the tree where the item was dropped,
   * only for bound trees.
   * For all other elements, this is null.
   * @return parent node
   */
  public Object getDropParent() {
    return dropParent;
  }
  public void setDropIndex(int dropIndex) {
    this.dropIndex = dropIndex;
  }
  
  /**
   * For elements that have ordered lists in them (trees and lists) this
   * is the index of the drop.
   * 
   * @return index
   */
  public int getDropIndex() {
    return dropIndex;
  }
  
}
