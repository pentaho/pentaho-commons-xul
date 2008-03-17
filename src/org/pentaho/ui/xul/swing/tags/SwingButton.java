/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

/**
 * @author OEM
 *
 */
public class SwingButton extends SwingElement implements XulButton{
  private JButton button;
  private String onClickFunction;
  
  public SwingButton(XulElement parent, XulDomContainer domContainer, String tagName) {
    super("Button");
    button = new JButton();
    this.managedObject = button;
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.components.XulButton#click()
   */
  public void click() {
    // TODO Auto-generated method stub
    
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.components.XulButton#dblClick()
   */
  public void dblClick() {
    // TODO Auto-generated method stub
    
  }
  
  public void setLabel(String label){
    button.setText(label);
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.components.XulButton#setOnClick(java.lang.String)
   */
  public void setOnClick( final String method) {
    button.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent evt){
        Document doc = SwingButton.this.getDocument();
        Element rootElement = doc.getRootElement();
        XulWindow window = (XulWindow) rootElement;
        window.invoke(method, new Object[]{});
        
      }
    });
  }
  
}
