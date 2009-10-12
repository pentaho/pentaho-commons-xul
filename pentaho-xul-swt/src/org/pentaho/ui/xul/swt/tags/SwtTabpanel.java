package org.pentaho.ui.xul.swt.tags;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulDomException;
import org.pentaho.ui.xul.components.XulTabpanel;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.AbstractSwtXulContainer;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.Orient;


public class SwtTabpanel extends AbstractSwtXulContainer implements XulTabpanel{
  
  private Composite panel;
  
  public SwtTabpanel(Element self, XulComponent parent, XulDomContainer domContainer, String tagName) {
    super("tabpanel");

    this.orient = Orient.VERTICAL;
    panel = new Composite((Composite) ((SwtTabpanels)parent).getTabbox().getManagedObject(), SWT.NONE);
    setManagedObject(panel);
    
  }
  
  @Override
  public void replaceChild(XulComponent oldElement, XulComponent newElement) throws XulDomException{
    super.replaceChild(oldElement, newElement);
  }

}
  