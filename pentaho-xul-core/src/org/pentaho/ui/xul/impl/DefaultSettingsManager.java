package org.pentaho.ui.xul.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Properties;

import org.pentaho.ui.xul.XulSettingsManager;

public class DefaultSettingsManager extends Properties implements XulSettingsManager {

  File propFile;
  
  public DefaultSettingsManager(File propFile) throws IOException{
    if(propFile.exists() == false){
      propFile.createNewFile();
    }
    this.propFile = propFile;
    InputStream in;
    in = new FileInputStream(propFile);
    
    super.load(in);
  }
  
  public String getSetting(String prop) {
    return super.getProperty(prop);
  }

  public void storeSetting(String prop, String val) {
    super.setProperty(prop, val);
  }
  
  public void save() throws IOException{
    OutputStream out = null;
    try{
      out = new FileOutputStream(propFile);
      super.store(out, "");
      out.flush();
    } finally {
      out.close();
    }
  }
  
  /** ==============Implementation Specific methods ======================== **/
}
