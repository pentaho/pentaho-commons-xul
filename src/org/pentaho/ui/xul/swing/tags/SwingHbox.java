/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.containers.XulHbox;
import org.pentaho.ui.xul.containers.XulVbox;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.swt.Orient;

/**
 * @author OEM
 *
 */
public class SwingHbox extends SwingElement implements XulHbox{
 
  
  public SwingHbox(XulElement parent, XulDomContainer domContainer, String tagName) {
    super("Hbox");
    
    children = new ArrayList<XulComponent>();
    
    container = new JPanel(new GridBagLayout());
    container.setBorder(BorderFactory.createLineBorder(Color.green));
    managedObject = container;
    
    
    gc = new GridBagConstraints();
    gc.gridx = gc.RELATIVE;
    gc.gridy = 0;
    gc.gridheight = gc.REMAINDER;
    gc.gridwidth = 1;
    gc.insets = new Insets(2,2,2,2);
    gc.fill = gc.HORIZONTAL;
    gc.anchor = gc.NORTHWEST;
    gc.weighty = 1;
    
   
  }
  
  public Orient getOrientation() {
    return Orient.HORIZONTAL;
  }

}

