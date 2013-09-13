package org.pentaho.ui.xul.swt.tags;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.components.XulImage;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.swt.tags.treeutil.XulTreeLabelProvider;
import org.pentaho.ui.xul.util.SwtSwingConversion;
import org.pentaho.ui.xul.util.SwtXulUtil;
import org.pentaho.ui.xul.util.XulUtil;

public class SwtImage extends SwtElement implements XulImage{
  
  private XulDomContainer domContainer;
  private XulComponent parent;
  private Label label;
  private String src;
  private static Log logger = LogFactory.getLog(SwtImage.class);
  
  public SwtImage(Element self, XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    this.domContainer = container;
    this.parent = parent;
    label = new Label(((Composite) parent.getManagedObject()), SWT.NONE);
   
    setManagedObject(label);
  }

  public String getSrc() {
    return src;
  }

  public void refresh() {
      
  }

  public void setSrc(String src) {
    this.src = src;
    Image img = SwtXulUtil.getCachedImage(src, domContainer, ((Composite) parent.getManagedObject()).getDisplay());
    if (img != null){
      label.setImage(img);
    }
  }

  public void setSrc(Object img) {
  	if(img instanceof String) {
  		setSrc((String) img);
  	} else if (img instanceof BufferedImage) {
	    label.setImage(
	      new Image(
	          ((Composite) parent.getManagedObject()).getDisplay(), 
	          SwtSwingConversion.convertToSWT((BufferedImage) img)
	        )
	    );
  	}
  }
  

  @Override
  public void setVisible(boolean visible) {
    label.setVisible(visible);
    label.getParent().layout(true);
  }
  
  
}

  