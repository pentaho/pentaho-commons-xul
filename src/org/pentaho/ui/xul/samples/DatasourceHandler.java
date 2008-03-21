/**
 * 
 */
package org.pentaho.ui.xul.samples;

import java.io.InputStream;

import org.dom4j.Document;
import org.pentaho.core.util.CleanXmlHelper;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.XulEventHandler;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulCheckbox;
import org.pentaho.ui.xul.components.XulLabel;
import org.pentaho.ui.xul.components.XulListitem;
import org.pentaho.ui.xul.components.XulTextbox;
import org.pentaho.ui.xul.containers.XulGroupbox;
import org.pentaho.ui.xul.containers.XulListbox;
import org.pentaho.ui.xul.swing.SwingXulLoader;
import org.pentaho.ui.xul.swing.SwingXulRunner;

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
    
    if(selectedValue.equals("Oracle")){
        try{
          SwingXulLoader loader = new SwingXulLoader();
          InputStream in = SwingXulRunner.class.getClassLoader().getResourceAsStream("org/pentaho/ui/xul/samples/datasource_oracle.xul");
          Document doc = CleanXmlHelper.getDocFromStream(in);
          
          XulDomContainer container = loader.loadXulFragment(doc);
          XulGroupbox newBox = (XulGroupbox) container.getDocumentRoot().getRootElement();
          XulGroupbox oldBox = (XulGroupbox) document.getElementById("database-options-box");
          
          document.getElementById("button-box").replaceChild((XulElement)oldBox, (XulElement)newBox);
          
          
        } catch(Exception e){
          System.out.println("XulException loading fragment: "+e.getMessage());
          e.printStackTrace(System.out);
        }
    }
    
//    hostName.setValue( String.format("%s host name", selectedValue) );
//    databaseName.setValue( String.format("%s database name", selectedValue) );
//    portNumber.setValue( String.format("%s port number", selectedValue) );
//    username.setValue( String.format("%s username", selectedValue) );
//    password.setValue( String.format("%s password", selectedValue) );
//    
  }


  @Override
  public Object getData() {
    return null;
  }


  @Override
  public void setData(Object data) {
    
  }
 
  
  
}
