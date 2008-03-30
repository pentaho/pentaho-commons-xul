/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulGroupbox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.util.Orient;

/**
 * @author OEM
 *
 */
public class SwingGroupbox  extends SwingElement implements XulGroupbox {


  public SwingGroupbox(XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("groupbox");
    this.orientation = Orient.VERTICAL;
    
    children = new ArrayList<XulComponent>();
    
    container = new JPanel(new GridBagLayout());
    container.setBorder(BorderFactory.createTitledBorder(""));
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
  
  public void setCaption(String caption){
    container.setBorder(BorderFactory.createTitledBorder(caption));
    
  }
  
  @Override
  public void layout(){
    super.layout();
    for(XulComponent comp : children){
      if(comp instanceof SwingCaption){
        this.setCaption(((SwingCaption) comp).getLabel());
      }
    }
  }
  
  public Orient getOrientation() {
    return Orient.VERTICAL;
  }
  

  @Override
  public void replaceChild(Element oldElement, Element newElement) {
    this.resetContainer();
    super.replaceChild(oldElement, newElement);
  }

}