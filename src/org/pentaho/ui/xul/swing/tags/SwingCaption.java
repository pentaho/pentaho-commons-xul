/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.components.XulCaption;
import org.pentaho.ui.xul.components.XulScript;
import org.pentaho.ui.xul.swing.SwingElement;

/**
 * @author OEM
 *
 */
public class SwingCaption extends SwingElement implements XulCaption{
  private String caption;
  public SwingCaption(XulElement parent, XulDomContainer domContainer, String tagName) {
    super("caption");
    managedObject = caption;

  }
  
  public String getLabel(){
    return caption;
  }
  
  public void setLabel(String caption){
    this.caption = caption;
    managedObject = caption;

  }
  

  public void layout(){
  }
}