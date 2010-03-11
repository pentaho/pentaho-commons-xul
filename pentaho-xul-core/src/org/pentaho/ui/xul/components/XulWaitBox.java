package org.pentaho.ui.xul.components;


public interface XulWaitBox  extends XulProgressmeter {
  
  public void setRunnable(WaitBoxRunnable runnable);
  
  public void start();
  public void stop();
  public void setModalParent(Object parent);
  public void setCanCancel(boolean canCancel);
  public void setTitle(String title);
  public String getTitle();
  public void setMessage(String message);
  public String getMessage();
  public void setDialogParent(Object parent);
  public void setCancelLabel(String cancel);
}
