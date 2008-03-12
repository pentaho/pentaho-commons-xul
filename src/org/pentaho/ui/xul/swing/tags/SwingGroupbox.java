/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.components.XulCaption;
import org.pentaho.ui.xul.containers.XulGroupbox;
import org.pentaho.ui.xul.containers.XulVbox;

/**
 * @author OEM
 *
 */
public class SwingGroupbox  extends XulElement implements XulGroupbox {

  private JPanel groupbox;
  private GridBagConstraints gc;
  public SwingGroupbox(){
    super("groupbox");
    groupbox = new JPanel(new GridBagLayout());
    managedObject = groupbox;
    
    gc = new GridBagConstraints();
    gc.gridx = gc.RELATIVE;
    gc.gridy = 0;
    gc.fill = gc.HORIZONTAL;
    gc.gridheight = 1;
    gc.gridwidth = 1;
    gc.insets = new Insets(2,2,2,2);
  }
  
  public void add(XulComponent c){
    if(c instanceof XulCaption){
      setCaption(((XulCaption) c).getCaption());
    } else {
      Component component = (Component) c.getManagedObject();
      groupbox.add(component, gc);
    }
  }
  
  public void setCaption(String caption){
    groupbox.setBorder(BorderFactory.createTitledBorder(caption));
    
  }

  public void layout(){
  }
}