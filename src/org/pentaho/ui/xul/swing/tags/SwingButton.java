/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

/**
 * @author nbaker
 *
 */
public class SwingButton extends SwingElement implements XulButton{
  private JButton button;
  
  public SwingButton(XulElement parent, XulDomContainer domContainer, String tagName) {
    super("Button");
    button = new JButton();
    this.managedObject = button;
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
        Document doc = SwingButton.this.getDocument();
        Element rootElement = doc.getRootElement();
        XulWindow window = (XulWindow) rootElement;
        window.invoke(method, new Object[]{});
        
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
