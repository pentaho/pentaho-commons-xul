package org.pentaho.ui.xul.swing.tags;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulImage;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swing.SwingElement;

public class SwingImage extends SwingElement implements XulImage{
  
  private String src;
  private static final Log logger = LogFactory.getLog(SwingImage.class);
  private XulDomContainer container;
  private JPanel panel;
  private ImageIcon ico = null;
  private Image image;
  private JLabel lbl = null;
  
  public SwingImage(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("image");
    panel = new JPanel(new BorderLayout());
    container = domContainer;
    this.managedObject = panel;
  }
  public String getSrc() {
    return src;  
  }
  public void setSrc(String src) {

    URL url = SwingButton.class.getClassLoader().getResource(this.container.getXulLoader().getRootDir()+src);
    
    if(url == null){
      logger.error("Could not find resource: "+src);
      return;
    }
    ico = new ImageIcon(url);
    if(ico == null){
      logger.error("Image could not be found: "+ico);
    }
    this.src = src;
    
  }
  public void setSrc(Object img) {
    this.image = (Image)img;
    ico = new ImageIcon(this.image);
    if(lbl != null){
      lbl.setIcon(ico);
    }
    logger.info("set new Image Src");
  }

  
  @Override
  public void layout(){
    this.panel.removeAll();
    if(ico == null){
      return;
    }
  
    //If scaling in place resize ImageIcon
    if(getHeight() > 0 && getWidth() > 0){
      ico = new ImageIcon(ico.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_AREA_AVERAGING));
    }
    lbl = new JLabel(ico);
    
    this.panel.add(lbl, BorderLayout.CENTER);

    //Set panel to sizing
    if(getHeight() > 0 && getWidth() > 0){
      this.panel.setMinimumSize(new Dimension(this.getWidth(), this.getHeight()));
    }
    
  }
  public void refresh() {
    this.panel.updateUI();
  }
  
  
}

  