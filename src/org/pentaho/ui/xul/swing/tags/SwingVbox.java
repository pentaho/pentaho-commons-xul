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
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.containers.XulVbox;

/**
 * @author OEM
 *
 */
public class SwingVbox extends XulElement implements XulVbox  {

  private JPanel vbox;
  private GridBagConstraints gc;
  public SwingVbox(){
    super("Vbox");
    vbox = new JPanel(new GridBagLayout());
    //vbox.setBorder(BorderFactory.createLineBorder(Color.green));
    managedObject = vbox;
    
    gc = new GridBagConstraints();
    gc.gridy = gc.RELATIVE;
    gc.gridx = 0;
    gc.gridheight = 1;
    gc.gridwidth = 1;
    gc.insets = new Insets(2,2,2,2);
    gc.fill = gc.HORIZONTAL;
    gc.anchor = gc.NORTHWEST;
  }
  
  public void add(XulComponent c){
    Component component = (Component) c.getManagedObject();
    vbox.add(component, gc);
  }
  
  public void layout(){
  }

}
