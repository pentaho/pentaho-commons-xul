package org.pentaho.ui.xul.components;

import org.pentaho.ui.xul.XulComponent;

public interface XulFileUpload  extends XulComponent {

  public String getAction();
  public void setAction(String action);
  public void setOnUploadSuccess(String method);
  public void setOnUploadFailure(String method);
  public String getOnUploadSuccess();
  public String getOnUploadFailure();
  public String getSeletedFile();
  public void setSelectedFile(String name);
  public void submit();
  public void addParameter(String name, String value);  
}
