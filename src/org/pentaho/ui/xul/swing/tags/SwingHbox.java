/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.containers.XulHbox;
import org.pentaho.ui.xul.containers.XulVbox;

/**
 * @author OEM
 *
 */
public class SwingHbox extends XulElement implements XulHbox{
 
  private JPanel hbox;
  private GridBagConstraints gc;
  
  public SwingHbox(){
    super("Hbox");
    hbox = new JPanel(new GridBagLayout());
    //hbox.setBorder(BorderFactory.createLineBorder(Color.blue));
    
    managedObject = hbox;
    gc = new GridBagConstraints();
    gc.gridx = gc.RELATIVE;
    gc.gridy = 0;
    gc.fill = gc.HORIZONTAL;
    gc.gridheight = 1;
    gc.gridwidth = 1;
    gc.insets = new Insets(2,2,2,2);
  }
  
  public void addComponent(XulComponent c){
    hbox.add((Component) c.getManagedObject(), gc);
  }
  
  public void layout(){
  }

}

