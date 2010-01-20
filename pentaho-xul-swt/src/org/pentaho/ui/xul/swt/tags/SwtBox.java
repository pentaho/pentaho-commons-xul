package org.pentaho.ui.xul.swt.tags;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulBox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.util.Orient;
import org.pentaho.ui.xul.util.SwtXulUtil;
import org.pentaho.ui.xul.util.XulUtil;

public class SwtBox extends AbstractSwtXulContainer implements XulBox {
  private static final long serialVersionUID = 582736100041411600L;

  protected Composite box;
  protected String background, bgcolor;
  protected XulDomContainer container;
  private static Log logger = LogFactory.getLog(SwtBox.class);

  public SwtBox(Element self, XulComponent parent, XulDomContainer container, String tagName) {
    this(parent, tagName, container, Orient.HORIZONTAL);
  }

  public SwtBox(XulComponent parent, String tagName, XulDomContainer container, Orient orient) {
    super(tagName);
    box = createNewComposite((Composite) parent.getManagedObject());
    box.setBackgroundMode(SWT.INHERIT_DEFAULT);
    setOrient(orient.toString());
    this.container = container;
    setManagedObject(box);
  }

  protected Composite createNewComposite(Composite parent) {
    return new Composite(parent, SWT.NONE);
  }

  public String getBackground() {
    return background;
  }

  public void setBackground(String background) {
    
    this.background = background;
    Image backgroundImg = SwtXulUtil.getCachedImage(background, container, box.getDisplay());

    if (backgroundImg != null){
      box.setBackgroundMode(SWT.INHERIT_DEFAULT);
      box.setBackgroundImage(backgroundImg);
    }

  }

  public String getBgcolor() {
    return bgcolor;
  }

  public void setBgcolor(String bgcolor) {
    this.bgcolor = bgcolor;
    Color c = Color.decode(bgcolor);
    box.setBackground(new org.eclipse.swt.graphics.Color(box.getDisplay(), c.getRed(), c.getGreen(), c.getBlue() ));
    box.setBackgroundMode(SWT.INHERIT_DEFAULT);
  }
  
  
  
  
}
