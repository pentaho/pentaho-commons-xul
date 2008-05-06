package org.pentaho.ui.xul.swing.tags;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingMenuitem extends SwingElement implements XulMenuitem{
	
	private static final Log logger = LogFactory.getLog(SwingMenuitem.class);
  private JMenuItem menuitem;
  private String image;
  private String onCommand;
  
  public SwingMenuitem(XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("menuitem");
    
    children = new ArrayList<XulComponent>();
    
    menuitem = new JMenuItem();
    managedObject = menuitem;
    
  }

  public String getAcceltext() {
    return String.valueOf(menuitem.getAccelerator().getKeyChar());
  }

  public String getAccesskey() {
    return String.valueOf(
        menuitem.getText().charAt(
            menuitem.getDisplayedMnemonicIndex()
        )
    );
  }

  public boolean getDisabled() {
    return menuitem.isEnabled();
  }

  public String getLabel() {
    return menuitem.getText();
  }

  public void setAcceltext(String accel) {
    menuitem.setAccelerator(KeyStroke.getKeyStroke(accel));
  }

  public void setAccesskey(String accessKey) {
    menuitem.setMnemonic(accessKey.charAt(0));
  }

  public void setDisabled(boolean disabled) {
    menuitem.setEnabled(!disabled);
  }

  public void setLabel(String label) {
    menuitem.setText(label);
  }

  public String getImage() {
      return image;
  }

  public boolean isSelected() {
    return menuitem.isSelected();
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getCommand() {
    return this.onCommand;  
  }

  public void setCommand(final String command) {
    this.onCommand = command;
    menuitem.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent evt){

        Document doc = getDocument();
        XulWindow window = (XulWindow) doc.getRootElement();
        XulDomContainer container = window.getXulDomContainer();
        
        try{
          container.invoke(command, new Object[]{});
        } catch (XulException e){
          logger.error("Error calling oncommand event",e);
        }
        
      }
    }); 
  }
  
}

  