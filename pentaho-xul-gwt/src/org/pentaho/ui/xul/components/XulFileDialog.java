package org.pentaho.ui.xul.components;

import java.io.File;

import org.pentaho.ui.xul.XulComponent;

public interface XulFileDialog extends XulComponent {
  public enum RETURN_CODE{OK, CANCEL, ERROR}
  public enum SEL_TYPE{SINGLE, MULTIPLE}
  public enum VIEW_TYPE{FILES_DIRECTORIES, DIRECTORIES}
 
  public RETURN_CODE showOpenDialog();
  //public RETURN_CODE showOpenDialog(File f);
  
  public RETURN_CODE showSaveDialog();
  //public RETURN_CODE showSaveDialog(File f);
  
  //public File getFile();
  //public File[] getFiles();
  
  public void setSelectionMode(SEL_TYPE type);
  public SEL_TYPE getSelectionMode();
  
  public void setViewType(VIEW_TYPE type);
  public VIEW_TYPE getViewType();

  public void setModalParent(Object parent);
}

  