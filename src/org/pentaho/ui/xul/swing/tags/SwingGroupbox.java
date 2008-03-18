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
import javax.swing.JPanel;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.components.XulCaption;
import org.pentaho.ui.xul.containers.XulGroupbox;
import org.pentaho.ui.xul.containers.XulVbox;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.swt.Orient;

/**
 * @author OEM
 *
 */
public class SwingGroupbox  extends SwingElement implements XulGroupbox {


  public SwingGroupbox(XulElement parent, XulDomContainer domContainer, String tagName) {
    super("groupbox");
    this.orientation = Orient.VERTICAL;
    
    children = new ArrayList<XulComponent>();
    
    container = new JPanel(new GridBagLayout());
    container.setBorder(BorderFactory.createTitledBorder(""));
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

}