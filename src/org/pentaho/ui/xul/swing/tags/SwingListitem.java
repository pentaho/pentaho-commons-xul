/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.components.XulCaption;
import org.pentaho.ui.xul.components.XulListitem;
import org.pentaho.ui.xul.components.XulScript;
import org.pentaho.ui.xul.swing.SwingElement;

/**
 * @author OEM
 *
 */
public class SwingListitem extends SwingElement implements XulListitem{
  private String label;
  public SwingListitem(XulElement parent, XulDomContainer domContainer, String tagName) {
    super("listitem");
    
  }
  
  public String getLabel(){
    return label;
  }
  
  public void setLabel(String label){
    this.label = label;
    managedObject = this.label;

  }
  
  public String toString(){
    return this.label;
  }
  

  public void layout(){
  }
  
}