package org.pentaho.ui.xul.test.samples;

import java.io.File;

import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulFileDialog;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.components.XulFileDialog.RETURN_CODE;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

public class FileBrowserHandler extends AbstractXulEventHandler{
  public void showSave(){
    try{
      XulTextbox filename = (XulTextbox) document.getElementById("fileName");
      
      XulFileDialog dialog = (XulFileDialog) document.createElement("filedialog");
      RETURN_CODE retval = dialog.showSaveDialog();
      if(retval == RETURN_CODE.OK){
        File file = dialog.getFile();
        filename.setValue(file.getName());
      }
      
    } catch (XulException e){
      System.out.println("Error creating file dialog");
    }
    
    
  }
}

  