/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import javax.swing.BoxLayout;

import org.dom4j.Element;
import org.pentaho.ui.xul.XulContainer;
import org.pentaho.ui.xul.XulTagHandler;
import org.pentaho.ui.xul.XulWindowContainer;
import org.pentaho.ui.xul.containers.XulVbox;

/**
 * @author OEM
 *
 */
public class SwingSpacerHandler implements XulTagHandler{
  
  public SwingSpacer parse(Element element, XulContainer parent, XulWindowContainer xulWindowContainer) {
    //TODO: break out to factory
    String sizeStr = element.attributeValue("size");
    if(sizeStr == null){
      System.out.println("Spacer size is null");
      return null;
    }
    int size;
    try{
      size = Integer.parseInt(sizeStr);
    } catch(NumberFormatException e){
      System.out.println("Error parsing Spacer size");
      return null;
    }

    return new SwingSpacer(
        size, 
        (parent instanceof XulVbox) ? BoxLayout.Y_AXIS : BoxLayout.X_AXIS
    );    

  }
}