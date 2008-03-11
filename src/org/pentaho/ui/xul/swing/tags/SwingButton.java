/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.dom4j.Element;
import org.pentaho.ui.xul.XulElement;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.containers.XulWindow;

/**
 * @author OEM
 *
 */
public class SwingButton extends XulElement implements XulButton{
  private JButton button;
  private String onClickFunction;
  
  public SwingButton(String label){
    super("Button");
    button = new JButton(label);
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
  
  public void setText(String text){
    button.setText(text);
  }

  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.components.XulButton#setOnClick(java.lang.String)
   */
  public void setOnClick( final String method) {
    button.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent evt){
        Element rootElement = SwingButton.this.getDocument().getRootElement();
        XulWindow window = (XulWindow) rootElement;
        window.invoke(method, new Object[]{});
        
      }
    });
  }
  
  public void layout(){
  }
}
