/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulRadio;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

/**
 * @author aphillips
 *
 */
public class SwingRadio extends SwingElement implements XulRadio{
  
  private JRadioButton radioButton;
  private static final Log logger = LogFactory.getLog(SwingRadio.class);
  
  public SwingRadio(XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("radio");
    radioButton = new JRadioButton();
    managedObject = radioButton;
  }
  
  /*
   * (non-Javadoc)
   * @see org.pentaho.ui.xul.components.XulRadio#isSelected()
   */
  public boolean isSelected() {
    // TODO Auto-generated method stub
    return radioButton.isSelected();
  }
  /* 
   * (non-Javadoc)
   * @see org.pentaho.ui.xul.components.XulRadio#setSelected(boolean)
   */
  public void setSelected(boolean selected) {
    radioButton.setSelected(selected);
  }
  
  public void layout(){
  }
  
  public void setLabel(String label){
    radioButton.setText(label);
  }

  public String getLabel() {
    return radioButton.getText();
  }

  public boolean isDisabled() {
    return !radioButton.isEnabled();
  }

  public void setDisabled(boolean dis) {
    radioButton.setEnabled(!dis);
  }

  public void setCommand(final String method) {
    radioButton.addChangeListener(new ChangeListener(){
      public void stateChanged(ChangeEvent evt){

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
}