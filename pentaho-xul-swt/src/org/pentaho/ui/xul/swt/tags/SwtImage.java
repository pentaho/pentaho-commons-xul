package org.pentaho.ui.xul.swt.tags;

import java.awt.image.BufferedImage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulImage;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.SwtSwingConversion;

public class SwtImage extends SwtElement implements XulImage{
  
  private XulDomContainer domContainer;
  private XulComponent parent;
  private Label label;
  private String src;
  
  public SwtImage(Element self, XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    this.domContainer = container;
    this.parent = parent;
    label = new Label(((Composite) parent.getManagedObject()), SWT.NONE);
   
    managedObject = label;
  }

  public String getSrc() {
    return src;
  }

  public void refresh() {
      
  }

  public void setSrc(String src) {
    this.src = src;
    label.setImage(new Image(((Composite) parent.getManagedObject()).getDisplay(), SwtButton.class.getClassLoader().getResourceAsStream(this.domContainer.getXulLoader().getRootDir()+src)));
  }

  public void setSrc(java.awt.Image img) {
    label.setImage(
      new Image(
          ((Composite) parent.getManagedObject()).getDisplay(), 
          SwtSwingConversion.convertToSWT((BufferedImage) img)
        )
    );
  }
  
}

  