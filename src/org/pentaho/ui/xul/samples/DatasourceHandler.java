/**
 * 
 */
package org.pentaho.ui.xul.samples;

import org.pentaho.ui.xul.XulEventHandler;
import org.pentaho.ui.xul.components.XulListitem;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.containers.XulListbox;

/**
 * @author OEM
 *
 */
public class DatasourceHandler extends XulEventHandler{
 
  XulTextbox connectionTextbox;
  XulListbox connectionType;
  XulTextbox hostName;
  XulTextbox databaseName;
  XulTextbox portNumber;
  XulTextbox username;
  XulTextbox password;
  
  
  public void onLoad(){
    connectionTextbox = (XulTextbox) document.getElementById("connection-name-text");
    connectionType = (XulListbox) document.getElementById("connection-type-list");
    hostName = (XulTextbox) document.getElementById("server-host-name-text");
    databaseName = (XulTextbox) document.getElementById("database-name-text");
    portNumber = (XulTextbox) document.getElementById("port-number-text");
    username = (XulTextbox) document.getElementById("username-text");
    password = (XulTextbox) document.getElementById("password-text");
    
    System.out.println("In init()");
  }
  
  
  public void changeConnectionType(){
    XulListitem selectedItem = (XulListitem) connectionType.getSelectedItem();
    String selectedValue = selectedItem.getLabel();
    
    hostName.setValue( String.format("%s host name", selectedValue) );
    databaseName.setValue( String.format("%s database name", selectedValue) );
    portNumber.setValue( String.format("%s port number", selectedValue) );
    username.setValue( String.format("%s username", selectedValue) );
    password.setValue( String.format("%s password", selectedValue) );
    
  }


  @Override
  public Object getData() {
    // TODO Auto-generated method stub
    return null;
  }
 
  
  
}
