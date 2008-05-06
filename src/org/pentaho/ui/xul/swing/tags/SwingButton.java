/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.impl.AbstractXulComponent;
import org.pentaho.ui.xul.swing.SwingElement;

/**
 * @author nbaker
 *
 */
public class SwingButton extends SwingElement implements XulButton{
  private JButton button;
  private static final Log logger = LogFactory.getLog(SwingButton.class);

  
  public SwingButton(XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("button");
    button = new JButton();
    this.managedObject = button;
  }
  
  public SwingButton() {
    this(null,null,null);
  }
  
  public void setLabel(String label){
    button.setText(label);
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.components.XulButton#setOnClick(java.lang.String)
   */
  public void setOnclick( final String method) {
    button.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent evt){

        Document doc = getDocument();
        XulWindow window = (XulWindow) doc.getRootElement();
        XulDomContainer container = window.getXulDomContainer();
        
        try{
          container.invoke(method, new Object[]{});
        } catch (XulException e){
          logger.error("Error calling oncommand event",e);
        }
      }
    });
  }


  public String getLabel() {
    return button.getText();
  }


  public boolean isDisabled() {
    return ! button.isEnabled();
  }


  public void setDisabled(boolean dis) {
    button.setEnabled(!dis);
  }
  
}
