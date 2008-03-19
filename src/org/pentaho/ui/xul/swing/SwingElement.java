/**
 * 
 */
package org.pentaho.ui.xul.swing;

import java.awt.Component;
import java.awt.GridBagConstraints;

import javax.swing.Box;
import javax.swing.JPanel;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.util.Orient;

/**
 * @author OEM
 *
 */
public class SwingElement extends XulElement{

  protected JPanel container;
  protected Orient orientation;
  
  public SwingElement(String tagName, Object managedObject) {
    super(tagName, managedObject);
  }

  public SwingElement(String tagName) {
    super(tagName);
  }
  
  public void layout(){
    double totalFlex = 0.0;
    for(XulComponent comp : children){
      if(comp.getManagedObject() == null){
        continue;
      }
      if(comp.getFlex() > 0){
        flexLayout = true;
        totalFlex += comp.getFlex();
      }
    }
    
    if(flexLayout)
      gc.fill = GridBagConstraints.BOTH;

    for(int i=0; i<children.size(); i++){
      XulComponent comp = children.get(i);
    
      Object maybeComponent = comp.getManagedObject();
      if(maybeComponent == null || !(maybeComponent instanceof Component)){
        continue;
      }
      if(this.getOrientation() == Orient.VERTICAL){ //VBox and such
        gc.gridheight = comp.getFlex()+1;
        gc.gridwidth = GridBagConstraints.REMAINDER;
        gc.weighty = (totalFlex == 0)? 0 : (comp.getFlex()/totalFlex);
      } else {
        gc.gridwidth = comp.getFlex()+1;
        gc.gridheight = GridBagConstraints.REMAINDER;
        gc.weightx = (totalFlex == 0)? 0 : (comp.getFlex()/totalFlex);
      }
      
      Component component = (Component) maybeComponent;
      container.add(component, gc);

      if(i+1 == children.size() && !flexLayout){

        if(this.getOrientation() == Orient.VERTICAL){ //VBox and such
          gc.weighty = 1.0;
        } else {
          gc.weightx = 1.0;
        }
        
        container.add(Box.createGlue(), gc);
      }
    }
   
  }
  
  public Orient getOrientation(){
    return this.orientation;
  }

}
