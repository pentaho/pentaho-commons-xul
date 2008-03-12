/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.components.XulCaption;
import org.pentaho.ui.xul.components.XulScript;

/**
 * @author OEM
 *
 */
public class SwingCaption extends XulElement implements XulCaption{
  private String caption;
  public SwingCaption(String caption){
    super("caption");
    this.caption = caption;
    managedObject = caption;

  }
  
  public String getCaption(){
    return caption;
  }
  

  public void layout(){
  }
}