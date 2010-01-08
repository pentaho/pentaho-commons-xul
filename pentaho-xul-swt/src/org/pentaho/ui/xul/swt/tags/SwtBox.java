package org.pentaho.ui.xul.swt.tags;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.containers.XulBox;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.util.Orient;
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
    InputStream in = null;
    try{
      in = XulUtil.loadResourceAsStream(background, container);
      final Image img = new Image(box.getDisplay(), in);
      box.setBackgroundMode(SWT.INHERIT_DEFAULT);
      box.setBackgroundImage(img);
    } catch (FileNotFoundException e){
      logger.error(e);
    } finally {
      try{
        if(in != null){
          in.close();
        }
      } catch(IOException ignored){}
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
