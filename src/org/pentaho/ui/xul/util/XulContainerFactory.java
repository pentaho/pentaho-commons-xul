package org.pentaho.ui.xul.util;

import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.containers.XulDialog;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.impl.AbstractXulComponent;
import org.pentaho.ui.xul.impl.XulEventHandler;

public class XulContainerFactory {

  private XulDomContainer container;
  private Document document;
  
  public XulContainerFactory(XulDomContainer container, Document document) {
    this.container = container;
    this.document = document;
  }
  
  public XulDialog buildDialog(String xulLocation, XulEventHandler... handlers) throws XulException {
    XulDomContainer fragmentContainer = null;
    fragmentContainer = this.container.loadFragment(xulLocation);
    
    for(XulEventHandler handler : handlers){
      fragmentContainer.addEventHandler(handler);
    }
    
    XulDialog dialog = (XulDialog) fragmentContainer.getDocumentRoot().getRootElement().getFirstChild();

    ((AbstractXulComponent) dialog).layout();

    //Merge in Event Handlers
    this.container.mergeContainer(fragmentContainer);

    document.getRootElement().addChild(dialog);

    dialog.getOnload();

    return dialog;
  }
}
