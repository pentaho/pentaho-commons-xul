package org.pentaho.ui.xul.swing.tags;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulMenuitem;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingMenuitem extends SwingElement implements XulMenuitem{

  private JMenuItem menuitem;
  private String image;
  private String onCommand;
  
  public SwingMenuitem(XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("menuitem");
    
    children = new ArrayList<XulComponent>();
    
    menuitem = new JMenuItem();
    managedObject = menuitem;
    
  }

  @Override
  public String getAcceltext() {
    return String.valueOf(menuitem.getAccelerator().getKeyChar());
  }

  @Override
  public String getAccesskey() {
    return String.valueOf(
        menuitem.getText().charAt(
            menuitem.getDisplayedMnemonicIndex()
        )
    );
  }

  @Override
  public boolean getDisabled() {
    return menuitem.isEnabled();
  }

  @Override
  public String getLabel() {
    return menuitem.getText();
  }

  @Override
  public void setAcceltext(String accel) {
    menuitem.setAccelerator(KeyStroke.getKeyStroke(accel));
  }

  @Override
  public void setAccesskey(String accessKey) {
    menuitem.setMnemonic(accessKey.charAt(0));
  }

  @Override
  public void setDisabled(boolean disabled) {
    menuitem.setEnabled(!disabled);
  }

  @Override
  public void setLabel(String label) {
    menuitem.setText(label);
  }

  @Override
  public String getImage() {
      return image;
  }

  @Override
  public boolean isSelected() {
    return menuitem.isSelected();
  }

  @Override
  public void setImage(String image) {
    this.image = image;
  }

  @Override
  public String getCommand() {
    return this.onCommand;  
  }

  @Override
  public void setCommand(final String command) {
    this.onCommand = command;
    menuitem.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent evt){
        Document doc = SwingMenuitem.this.getDocument();
        Element rootElement = doc.getRootElement();
        XulWindow window = (XulWindow) rootElement;
        window.invoke(command, new Object[]{});
        
      }
    }); 
  }
  
}

  