/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.components.XulScript;

/**
 * @author OEM
 *
 */
public class SwingScript extends XulElement implements XulScript{
  private String id;
  private String className;

  public SwingScript(String id, String className){
    super("script");
    this.id = id;
    this.className = className;
  }
  
  public String getId() {
    return id;
  }

  public String getClassName() {
    return className;
  }

  public void layout(){
  }

}
