/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.containers.XulVbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.util.Orient;

/**
 * @author nbaker
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
    
    resetContainer();
    
  }
  
  

  public void resetContainer(){
    
    container.removeAll();
    
    gc = new GridBagConstraints();
    gc.gridy = GridBagConstraints.RELATIVE;
    gc.gridx = 0;
    gc.gridheight = 1;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.insets = new Insets(2,2,2,2);
    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.anchor = GridBagConstraints.NORTHWEST;
    gc.weightx = 1;
    
  }
  
  

  @Override
  public void replaceChild(Element oldElement, Element newElement) {
    this.resetContainer();
    super.replaceChild(oldElement, newElement);
  }

}
