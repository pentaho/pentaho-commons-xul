/**
 * 
 */
package org.pentaho.ui.xul.swing.tags;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.swing.SwingElement;
import org.pentaho.ui.xul.util.Direction;
import org.pentaho.ui.xul.util.Orient;

/**
 * @author nbaker
 *
 */
public class SwingButton extends SwingElement implements XulButton{
  private AbstractButton button;
  private static final Log logger = LogFactory.getLog(SwingButton.class);
  private String image;
  private Direction dir;
  private String group;
  private Type type;
  private ButtonGroup buttonGroup;
  private XulDomContainer domContainer;
  private String onclick;
  private boolean selected;
  
  public SwingButton(XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("button");
    button = new JButton();
    this.managedObject = button;
    this.domContainer = domContainer;
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
    this.onclick = method;
    button.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent evt){

        Document doc = getDocument();
        XulWindow window = (XulWindow) doc.getRootElement();
        final XulDomContainer container = window.getXulDomContainer();
        
        SwingUtilities.invokeLater(new Runnable(){
          public void run() {
            try{
              container.invoke(method, new Object[]{});
            } catch (XulException e){
              logger.error("Error calling oncommand event",e);
            }
          }
        });
        
      }
    });
  }
  
  public String getOnclick(){
    return this.onclick;
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

  public String getImage() {
    return this.image;
      
  }

  public void setImage(String src) {
    this.image = src;
    URL url = SwingButton.class.getClassLoader().getResource(this.domContainer.getXulLoader().getRootDir()+src);
    
    if(url == null){
      logger.error("Could not find resource");
      logger.error(this.domContainer.getXulLoader().getRootDir()+src);
      return;
    }
    Icon ico = new ImageIcon(url);
    if(ico == null){
      logger.error("Image could not be found: "+ico);
    } else {
      this.button.setIcon(ico);
    }
  }

  public String getDir() {
    return dir.toString().toLowerCase();
  }

  public void setDir(String dir) {
    this.dir = Direction.valueOf(dir.toUpperCase());
  }

  @Override
  public void layout() {
    //check type to see if it's a toggleButton
    if(type == Type.CHECKBOX || type == Type.RADIO){
      AbstractButton oldButton = button;

      
      button = new JToggleButton();
      button.setText(oldButton.getText());
      button.setIcon(oldButton.getIcon());
      button.setEnabled(oldButton.isEnabled());
      button.setSelected(this.selected);
      this.setOnclick(this.getOnclick());
      managedObject = button;
      
      if(this.group != null){
        /**
         * This listener is added so the button can register itself with the buttongroup collection as
         * durring creation there's no way of accessing the root level element (window) where the collection
         * is stored.
         */
        button.addComponentListener(new ComponentListener(){
          public void componentHidden(ComponentEvent arg0) {}
          public void componentMoved(ComponentEvent arg0) {}
          public void componentShown(ComponentEvent arg0) {}
          public void componentResized(ComponentEvent arg0) {
            buttonGroup = ((SwingWindow) SwingButton.this.getDocument().getRootElement()).getButtonGroup(group);
            buttonGroup.add(button);
            if(buttonGroup.getButtonCount() == 1){
              //first button in, TODO: remove once selected="true" attribute supported
              button.setSelected(true);
            }
            button.removeComponentListener(this);
          }
  
        });
      }
      
    }
    
    //adjust orientation of label and icon
    if(this.orientation == Orient.VERTICAL){
      this.button.setHorizontalTextPosition(JButton.CENTER);
      if(this.dir == Direction.FORWARD){
        this.button.setVerticalTextPosition(JButton.BOTTOM);
      } else {
        this.button.setVerticalTextPosition(JButton.TOP);
      }
    } else {
      this.button.setVerticalTextPosition(JButton.CENTER);
      if(this.dir == Direction.FORWARD){
        this.button.setHorizontalTextPosition(JButton.LEFT);
      } else {
        this.button.setHorizontalTextPosition(JButton.RIGHT);
      }
    }
    super.layout();
  }

  public String getGroup() {
    return this.group;
  }

  public String getType() {
    return this.type.toString();
  }

  public void setGroup(final String group) {
    this.group = group;    
  }

  public void setType(String type) {
    this.type = Type.valueOf(type.toString().toUpperCase());
    
  }

  public void setSelected(String selected) {
    this.selected = Boolean.parseBoolean(selected);  
    button.setSelected(this.selected);
  }

  public boolean isSelected() {
    return selected;  
  }

  public void setSelected(boolean selected) {
    this.selected = selected;  
    button.setSelected(this.selected);
  }

  public void doClick() {
    button.doClick();
  }
  
}
