/**
 * 
 */
package org.pentaho.ui.xul.swing;

import java.awt.Component;

import javax.swing.JPanel;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.swt.Orient;

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
    
    System.out.println("flex: "+flexLayout);
    if(flexLayout)
      gc.fill = gc.BOTH;

    for(XulComponent comp : children){
      Object managedObject = comp.getManagedObject();
      if(managedObject == null || !(managedObject instanceof Component)){
        continue;
      }
      if(this.getOrientation() == Orient.VERTICAL){ //VBox and such
        gc.gridheight = comp.getFlex()+1;
        gc.gridwidth = gc.REMAINDER;
        gc.weighty = (totalFlex == 0)? 0 : (comp.getFlex()/totalFlex);
      } else {
        gc.gridwidth = comp.getFlex()+1;
        gc.gridheight = gc.REMAINDER;
        gc.weightx = (totalFlex == 0)? 0 : (comp.getFlex()/totalFlex);
      }
      Object obj = comp.getManagedObject();
      Component component = (Component) obj;
      container.add(component, gc);
    }
   
  }
  
  public Orient getOrientation(){
    return this.orientation;
  }

}
