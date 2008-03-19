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
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.containers.XulVbox;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.util.Orient;

/**
 * @author OEM
 *
 */
public class SwingVbox extends SwingElement implements XulVbox  {

  
  public SwingVbox(XulElement parent, XulDomContainer domContainer, String tagName) {
    super("Vbox");
    this.orientation = Orient.VERTICAL;
    
    children = new ArrayList<XulComponent>();
    
    container = new JPanel(new GridBagLayout());
    //container.setBorder(BorderFactory.createLineBorder(Color.green));
    managedObject = container;
    
    
    gc = new GridBagConstraints();
    gc.gridy = gc.RELATIVE;
    gc.gridx = 0;
    gc.gridheight = 1;
    gc.gridwidth = gc.REMAINDER;
    gc.insets = new Insets(2,2,2,2);
    gc.fill = gc.HORIZONTAL;
    gc.anchor = gc.NORTHWEST;
    gc.weightx = 1;
    
   
  }
  
  
}
