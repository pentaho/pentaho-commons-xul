package org.pentaho.ui.xul.samples;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import org.pentaho.ui.xul.components.XulImage;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

public class ImageHandler extends AbstractXulEventHandler {
  public void switchSrc(){
    XulImage img = (XulImage) document.getElementById("img");
    Image newImg = new ImageIcon(ImageHandler.class.getClassLoader().getResource("resource/documents/testImage2.png")).getImage();
    img.setSrc(newImg);
  }
}

  